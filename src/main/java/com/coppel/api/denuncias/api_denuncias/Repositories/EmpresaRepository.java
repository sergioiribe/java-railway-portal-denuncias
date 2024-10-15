package com.coppel.api.denuncias.api_denuncias.Repositories;

import com.coppel.api.denuncias.api_denuncias.Entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}
