package com.coppel.api.denuncias.api_denuncias.Repositories;

import com.coppel.api.denuncias.api_denuncias.Entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    // Consulta personalizada para obtener el nombre de la empresa por ID
    @Query("SELECT e.nombre FROM Empresa e WHERE e.idEmpresa = :idEmpresa")
    String findNombreById(@Param("idEmpresa") Long idEmpresa);
}
