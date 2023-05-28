package com.sanita.ospedale;

public class PazienteNonTrovatoException extends Exception {
   
    @Override
    public String getMessage() {

        return "Codice Fiscale non trovato";
    }

}
