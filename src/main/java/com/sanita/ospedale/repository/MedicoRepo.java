package com.sanita.ospedale.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.sanita.ospedale.model.Medico;

public interface MedicoRepo extends CrudRepository<Medico,Long>{

    ArrayList<Medico> findByMatricolaLike(String matricola);
    Medico findByMatricolaContaining(String matricola);

}
