package com.sanita.ospedale;

public class MedicoNonTrovatoException extends Exception {
   
    @Override
    public String getMessage() {

        return "Medico non trovato";
    }

}
