package com.coppel.api.denuncias.api_denuncias.Repositories;

import com.coppel.api.denuncias.api_denuncias.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsuario(String usuario);  // Este método debe existir
}
