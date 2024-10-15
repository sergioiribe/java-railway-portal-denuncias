package com.coppel.api.denuncias.api_denuncias.Entities;


import com.coppel.api.denuncias.api_denuncias.Repositories.DenunciaRepository;

public class GeneradorFolio {
    public static String generarFolio(DenunciaRepository denunciaRepository) {
        String folio;
        int intentos = 0;
        do {
            folio = String.valueOf((int) (Math.random() * 90000) + 10000); // Genera un número de 5 dígitos
            intentos++;
            System.out.println("Intento de generar folio: " + folio);
            if (intentos > 10) {
                throw new RuntimeException("No se pudo generar un folio único después de varios intentos");
            }
        } while (denunciaRepository.existsByFolio(folio));
        
        return folio;
    }
}
