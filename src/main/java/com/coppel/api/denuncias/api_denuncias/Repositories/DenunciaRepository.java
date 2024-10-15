package com.coppel.api.denuncias.api_denuncias.Repositories;

import com.coppel.api.denuncias.api_denuncias.Entities.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DenunciaRepository extends JpaRepository<Denuncia, Integer> {

    Denuncia findByFolio(String folio);

    Boolean existsByFolio(String folio);
}
