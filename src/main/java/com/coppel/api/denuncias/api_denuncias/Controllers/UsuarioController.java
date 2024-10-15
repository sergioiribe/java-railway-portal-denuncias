package com.coppel.api.denuncias.api_denuncias.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coppel.api.denuncias.api_denuncias.Entities.Usuario;
import com.coppel.api.denuncias.api_denuncias.Repositories.UsuarioRepository;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Endpoint para autenticar al usuario (login)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginData) {
        String usuario = loginData.get("usuario");
        String contrasena = loginData.get("contrasena");

        // Validar los datos de entrada
        if (usuario == null || contrasena == null) {
            return new ResponseEntity<>("Usuario o contraseña faltantes", HttpStatus.BAD_REQUEST);
        }

        // Buscar el usuario en la base de datos
        Usuario usuarioAdmin = usuarioRepository.findByUsuario(usuario);
        if (usuarioAdmin == null || !usuarioAdmin.getContrasena().equals(contrasena)) {
            return new ResponseEntity<>("Usuario o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Login exitoso", HttpStatus.OK);
    }

    // Endpoint para crear un nuevo usuario
    @PostMapping("/crear")
    public ResponseEntity<String> crearUsuario(@RequestBody Map<String, String> usuarioData) {
        String usuario = usuarioData.get("usuario");
        String contrasena = usuarioData.get("contrasena");

        // Verificar si el nombre de usuario ya existe
        if (usuarioRepository.findByUsuario(usuario) != null) {
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.CONFLICT);
        }

        // Crear el nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsuario(usuario);
        nuevoUsuario.setContrasena(contrasena);  // Guardar la contraseña en texto plano

        // Guardar el nuevo usuario en la base de datos
        usuarioRepository.save(nuevoUsuario);

        return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
    }
}
