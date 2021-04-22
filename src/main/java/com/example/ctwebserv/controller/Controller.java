package com.example.ctwebserv.controller;

import com.example.ctwebserv.models.Association;
import com.example.ctwebserv.models.Cagnotte;
import com.example.ctwebserv.models.Donation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.annotation.Repeatable;
import java.net.URI;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api")
public class Controller {
    static HashMap<String, Association> associationHashMap = new HashMap<>();
    static HashMap<String, ArrayList<String>> linkAssocCagnotte = new HashMap<>();
    static HashMap<String, Cagnotte> cagnotteHashMap = new HashMap<>();
    static HashMap<String, ArrayList<Donation>> linkCagnotteDonation = new HashMap<>();

    public static Association getAssociationByName(String associationNom){
        return associationHashMap.get(associationNom);
    }
    public static boolean hasCagnotteActive(String nomAssoc){
        boolean hasActive = false;
        for(String idCagnotte : linkAssocCagnotte.get(nomAssoc)){
            Cagnotte c = cagnotteHashMap.get(idCagnotte);
            if (c.isActive()) return true;
        }
        return hasActive;
    }

    public static Association getAssociationByResponsableName(String s) {
        for (Association assoc : associationHashMap.values()){
            if (assoc.getNomResponsable().equals(s)) return assoc;
        }
        return null;
    }

    @PostMapping(value = "/associations", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Association> declareAssociation(@RequestBody Association association){
        if (association == null || association.getNomResponsable().isBlank() || association.getNomAssociation().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        if (associationHashMap.containsKey(association.getNomAssociation())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        associationHashMap.put(association.getNomAssociation(), association);
        linkAssocCagnotte.put(association.getNomAssociation(), new ArrayList<>());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{nom}")
                .buildAndExpand(association.getNomAssociation()).toUri();

        return ResponseEntity.created(location).body(association);

    }

    @PostMapping(value = "/cagnottes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cagnotte> virement(@PathVariable String id, @RequestBody Donation donation){
        if (id == null || id.isBlank() || donation == null || donation.getMontant() == 0 ){
            return ResponseEntity.badRequest().build();
        }
        if ( !cagnotteHashMap.containsKey(id)){
            return ResponseEntity.notFound().build();
        }
        Cagnotte c = cagnotteHashMap.get(id);
        c.setMontantDemandee(c.getMontantDemandee() - donation.getMontant());
        linkCagnotteDonation.get(id).add(donation);
        return ResponseEntity.ok(c);

    }

    @GetMapping(value = "/cagnottes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Donation>> getDonation(Principal principal){
        Association assoc = Controller.getAssociationByResponsableName(principal.getName());
        if (assoc == null){
            return ResponseEntity.notFound().build();
        }
        ArrayList<String> cagnottesID = Controller.linkAssocCagnotte.get(assoc.getNomAssociation());
        ArrayList<Donation> res = new ArrayList<>();
        for(String idCagnotte : cagnottesID){
            res.addAll(Controller.linkCagnotteDonation.get(idCagnotte));
        }
        return ResponseEntity.ok(res);

    }

    @PostMapping(value = "/cagnottes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cagnotte> createCagnotte(Principal principal, @RequestBody Cagnotte cagnotte){
        if (cagnotte == null || cagnotte.getNomCagnotte().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        Association assoc = Controller.getAssociationByResponsableName(principal.getName());
        if (assoc == null){
            return ResponseEntity.notFound().build();
        }
        if (Controller.hasCagnotteActive(assoc.getNomAssociation())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        UUID id = UUID.randomUUID();
        cagnotteHashMap.put(id.toString(), cagnotte);
        linkAssocCagnotte.get(assoc.getNomAssociation()).add(id.toString());
        linkCagnotteDonation.put(id.toString(), new ArrayList<>());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(id.toString()).toUri();

        return ResponseEntity.created(location).body(cagnotte);

    }
}
