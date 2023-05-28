package com.sanita.ospedale.controller.apicontroller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanita.ospedale.MedicoNonTrovatoException;
import com.sanita.ospedale.PazienteNonTrovatoException;
import com.sanita.ospedale.model.Medico;
import com.sanita.ospedale.model.Paziente;
import com.sanita.ospedale.repository.MedicoRepo;
import com.sanita.ospedale.repository.PazienteRepo;

@RestController
public class OspedaleApiController {
    
    @Autowired
    PazienteRepo pazienteRepo;
    @Autowired
    MedicoRepo medicoRepo;

    //Metodo per vedere i medici
    @GetMapping("/api/v1/medici")
    public ArrayList<Medico> medici(){
       return (ArrayList<Medico>) medicoRepo.findAll();
    }

    //Metodo per aggiungiere medici
    //Aggiungo il return come rsponse entity cosi posso dare la conferma dell'avvenuta aggiunto usando l'HTTP code
    @PostMapping("/api/v1/medici")
    public ResponseEntity<Medico> aggiungimedico(@RequestBody Medico medico){
        medicoRepo.save(medico);
        return new ResponseEntity<>(medico,HttpStatus.OK);
    }

    //Metodo per vedere i pazienti
    @GetMapping("/api/v1/pazienti")
    public ArrayList<Paziente> pazienti(){
       return (ArrayList<Paziente>) pazienteRepo.findAll();
    }

    //Metodo per aggiungiere pazienti
    @PostMapping("/api/v1/pazienti")
    public ResponseEntity<Paziente> aggiungipaziente(@RequestBody Paziente paziente){
        pazienteRepo.save(paziente);
        return new ResponseEntity<>(paziente,HttpStatus.OK);
    }

    //Metodo per eliminare i pazienti
    @DeleteMapping("/api/v1/pazienti/{id}")
    public ResponseEntity<Void> eliminapaziente(@PathVariable Long id) {

        if (pazienteRepo.existsById(id)) {
            pazienteRepo.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
    
    //Metodo per eliminare medici
    @DeleteMapping("/api/v1/medici/{id}")
    public ResponseEntity<Void> eliminamedico(@PathVariable Long id) {

        if (medicoRepo.existsById(id)) {
            medicoRepo.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    //Metodo per cercare i medici
    @GetMapping("/api/v1/medici/search")
    public ResponseEntity<Object> searchmedico(@RequestParam(value = "key", defaultValue = "") String key) throws MedicoNonTrovatoException {

            try {
                ArrayList<Medico> medicoList = medicoRepo.findByMatricolaLike(key);
                if (medicoList.isEmpty()) {
                    throw new MedicoNonTrovatoException();
                }
                return ResponseEntity.ok(medicoList);
            } catch (MedicoNonTrovatoException ex) {
                String errorMessage = ex.getMessage();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
            }
        }
    
    //Metodo per cercare i pazienti
    @GetMapping("/api/v1/pazienti/search")
    public ResponseEntity<Object> searchpazienti(@RequestParam(value = "key", defaultValue = "") String key) throws PazienteNonTrovatoException {

            try {
                ArrayList<Paziente> pazienteList = pazienteRepo.findByCodicefiscaleLike(key);
                if (pazienteList.isEmpty()) {
                    throw new PazienteNonTrovatoException();
                }
                return ResponseEntity.ok(pazienteList);
            } catch (PazienteNonTrovatoException ex) {
                String errorMessage = ex.getMessage();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
            }
        }

    @PostMapping("/api/v1/pazienti/assegna")
    public ResponseEntity<Object> assegnaMedico(@RequestParam(value = "key", defaultValue = "") String matricolaMedico,@RequestParam(value="key2", defaultValue="")String codiceFiscale) throws PazienteNonTrovatoException,MedicoNonTrovatoException {

        String regex = "^([A-Z]{6}[0-9LMNPQRSTUV]{2}[ABCDEHLMPRST]{1}[0-9LMNPQRSTUV]{2}[A-Z]{1}[0-9LMNPQRSTUV]{3}[A-Z]{1})$|([0-9]{11})$";
        if(!codiceFiscale.matches(regex)){
            throw new PazienteNonTrovatoException();
        }
        try {
            Medico medico = medicoRepo.findByMatricolaContaining(matricolaMedico);
            Paziente paziente = pazienteRepo.findFirstByCodicefiscaleLike(codiceFiscale);
    
            if (medico == null) {
                throw new MedicoNonTrovatoException();
            }
    
            if (paziente == null) {
                throw new PazienteNonTrovatoException();
            }
    
            paziente.setMedico(medico);
            pazienteRepo.save(paziente); // Salva le modifiche al paziente nel repository
    
            return ResponseEntity.ok("Medico assegnato correttamente al paziente.");
        } catch (PazienteNonTrovatoException ex) {
            String errorMessage = "Errore: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (MedicoNonTrovatoException ex) {
            String errorMessage = "Errore: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }   
    }
    
    }



    



