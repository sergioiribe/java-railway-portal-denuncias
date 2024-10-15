package com.coppel.api.denuncias.api_denuncias.Exceptions;


public class DenunciaNotFoundException extends RuntimeException {
    public DenunciaNotFoundException(String folio) {
        super("Denuncia con folio " + folio + " no encontrada.");
    }
}
