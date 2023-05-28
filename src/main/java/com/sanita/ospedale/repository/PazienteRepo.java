package com.sanita.ospedale.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import com.sanita.ospedale.model.Paziente;

public interface PazienteRepo extends CrudRepository<Paziente,Long> {

    ArrayList<Paziente> findByCodicefiscaleLike(String codicefiscale);

    Paziente findByCodicefiscaleContaining(String codicefiscale);
    
}
