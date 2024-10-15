package com.coppel.api.denuncias.api_denuncias.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.coppel.api.denuncias.api_denuncias.Entities.Denuncia;
import com.coppel.api.denuncias.api_denuncias.Repositories.DenunciaRepository;
import com.coppel.api.denuncias.api_denuncias.Exceptions.DenunciaNotFoundException;
import com.coppel.api.denuncias.api_denuncias.Entities.GeneradorFolio;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/denuncias")
public class DenunciaController {

    @Autowired
    private DenunciaRepository denunciaRepository;

    // Obtener una denuncia por folio
    @GetMapping("/{folio}")
    public ResponseEntity<Denuncia> obtenerDenunciaPorFolio(@PathVariable String folio) {
        Denuncia denuncia = denunciaRepository.findByFolio(folio);
        if (denuncia == null) {
            throw new DenunciaNotFoundException(folio);
        }
        return new ResponseEntity<>(denuncia, HttpStatus.OK);
    }

    // Crear una nueva denuncia
    @PostMapping
    @Transactional
    public ResponseEntity<Denuncia> crearDenuncia(@RequestBody Map<String, Object> payload) {
        try {
            System.out.println("Payload recibido: " + payload);

            // Validar que los datos requeridos estén presentes
            if (!payload.containsKey("id_empresa") || !payload.containsKey("id_pais") ||
                !payload.containsKey("id_estado") || !payload.containsKey("centro") ||
                !payload.containsKey("detalle") || !payload.containsKey("fecha_hechos") || 
                !payload.containsKey("contrasena")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Obtener los IDs directamente
            int empresaId = Integer.parseInt(payload.get("id_empresa").toString());
            int paisId = Integer.parseInt(payload.get("id_pais").toString());
            int estadoId = Integer.parseInt(payload.get("id_estado").toString());

            // Crear la denuncia
            Denuncia denuncia = new Denuncia();
            denuncia.setIdEmpresa(empresaId);
            denuncia.setIdPais(paisId);
            denuncia.setIdEstado(estadoId);
            denuncia.setCentro(payload.get("centro").toString());
            denuncia.setDetalle(payload.get("detalle").toString());
            denuncia.setFechaHechos(LocalDate.parse(payload.get("fecha_hechos").toString()));
            denuncia.setContrasena(payload.get("contrasena").toString());

            // Verificar si es anónima
            Boolean anonimato = Boolean.valueOf(payload.get("anonimato").toString());
            denuncia.setAnonimato(anonimato);

            if (!anonimato) {
                denuncia.setNombre(payload.get("nombre").toString());
                denuncia.setCorreo(payload.get("correo").toString());
                denuncia.setTelefono(payload.get("telefono").toString());
            }

            // Generar folio y asegurarse de que no sea nulo
            String folio = GeneradorFolio.generarFolio(denunciaRepository);
            if (folio == null || folio.isEmpty()) {
                throw new RuntimeException("No se pudo generar un folio.");
            }
            denuncia.setFolio(folio);

            // Guardar la denuncia en la base de datos
            Denuncia nuevaDenuncia = denunciaRepository.saveAndFlush(denuncia);
            System.out.println("Denuncia guardada con ID: " + nuevaDenuncia.getIdDenuncia());

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaDenuncia);
        } catch (Exception e) {
            System.err.println("Error al crear denuncia: " + e.getMessage());
            e.printStackTrace(); // Imprimir el error para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
    String folio = payload.get("folio");
    String contrasenaIngresada = payload.get("contrasena");

    // Buscar la denuncia por folio
    Denuncia denuncia = denunciaRepository.findByFolio(folio);
    if (denuncia == null) {
        return new ResponseEntity<>(Map.of("mensaje", "Folio no encontrado"), HttpStatus.NOT_FOUND); // Folio no encontrado
    }

    // Verificar la contraseña sin cifrado
    if (!denuncia.getContrasena().equals(contrasenaIngresada)) {
        return new ResponseEntity<>(Map.of("mensaje", "Credenciales invalidas"), HttpStatus.UNAUTHORIZED); // Contraseña incorrecta
    }

    // Devolver la denuncia si todo es correcto
    return new ResponseEntity<>(Map.of("mensaje", "Credenciales validas"), HttpStatus.OK); // Contraseña incorrecta
}

@PostMapping("/consultar")
public ResponseEntity<?> consultarDenuncia(@RequestBody Map<String, String> payload) {
    String folio = payload.get("folio");
    String contrasenaIngresada = payload.get("contrasena");

    // Buscar la denuncia por folio
    Denuncia denuncia = denunciaRepository.findByFolio(folio);
    if (denuncia == null) {
        return new ResponseEntity<>(Map.of("mensaje", "Folio no encontrado"), HttpStatus.NOT_FOUND); // Folio no encontrado
    }

    // Verificar la contraseña sin cifrado
    if (!denuncia.getContrasena().equals(contrasenaIngresada)) {
        return new ResponseEntity<>(Map.of("mensaje", "Contraseña incorrecta"), HttpStatus.UNAUTHORIZED); // Contraseña incorrecta
    }

    // Devolver la denuncia si todo es correcto
    return new ResponseEntity<>(denuncia, HttpStatus.OK);
}


    // Agregar un comentario a una denuncia (manteniendo el estatus actual)
@PutMapping("/{folio}/agregarComentario")
public ResponseEntity<String> agregarComentario(@PathVariable String folio, @RequestBody Map<String, String> datos) {
    try {
        // Buscar la denuncia por folio
        Denuncia denuncia = denunciaRepository.findByFolio(folio);
        if (denuncia == null) {
            return new ResponseEntity<>("Denuncia no encontrada", HttpStatus.NOT_FOUND);
        }

        // Obtener el nuevo comentario desde los datos
        String nuevoComentario = datos.get("comentario");

        // Validar que el comentario no sea nulo
        if (nuevoComentario == null || nuevoComentario.isEmpty()) {
            return new ResponseEntity<>("Falta el comentario", HttpStatus.BAD_REQUEST);
        }

        // Manejar el array de comentarios
        String[] comentariosExistentes = denuncia.getComentarios();

        // Si no hay comentarios existentes, inicializamos el array
        if (comentariosExistentes == null) {
            comentariosExistentes = new String[]{nuevoComentario};
        } else {
            // Si ya existen comentarios, agregamos el nuevo comentario
            String[] nuevosComentarios = new String[comentariosExistentes.length + 1];
            System.arraycopy(comentariosExistentes, 0, nuevosComentarios, 0, comentariosExistentes.length);
            nuevosComentarios[nuevosComentarios.length - 1] = nuevoComentario;
            comentariosExistentes = nuevosComentarios;
        }

        // Actualizar la lista de comentarios
        denuncia.setComentarios(comentariosExistentes);

        // Guardar los cambios en la base de datos
        denunciaRepository.save(denuncia);

        return new ResponseEntity<>("Comentario agregado exitosamente", HttpStatus.OK);
    } catch (Exception e) {
        e.printStackTrace();  // Imprimir el error en los logs para depuración
        return new ResponseEntity<>("Error interno en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @PutMapping("/{folio}/actualizar")
    public ResponseEntity<String> actualizarDenuncia(@PathVariable String folio, @RequestBody Map<String, String> datos) {
        try {
            // Buscar la denuncia por folio
            Denuncia denuncia = denunciaRepository.findByFolio(folio);
            if (denuncia == null) {
                return new ResponseEntity<>("Denuncia no encontrada", HttpStatus.NOT_FOUND);
            }
    
            // Obtener el comentario y el estatus desde los datos
            String nuevoComentario = datos.get("comentarios");
            String nuevoEstatus = datos.get("estatus");
    
            // Validar que los datos no sean nulos
            if (nuevoComentario == null || nuevoEstatus == null) {
                return new ResponseEntity<>("Faltan datos de comentario o estatus", HttpStatus.BAD_REQUEST);
            }
    
            // Manejar el array de comentarios
            String[] comentariosExistentes = denuncia.getComentarios();
    
            // Si no hay comentarios existentes, inicializamos el array
            if (comentariosExistentes == null) {
                comentariosExistentes = new String[]{nuevoComentario};
            } else {
                // Si ya existen comentarios, agregamos el nuevo comentario
                String[] nuevosComentarios = new String[comentariosExistentes.length + 1];
                System.arraycopy(comentariosExistentes, 0, nuevosComentarios, 0, comentariosExistentes.length);
                nuevosComentarios[nuevosComentarios.length - 1] = nuevoComentario;
                comentariosExistentes = nuevosComentarios;
            }
    
            // Actualizar la lista de comentarios
            denuncia.setComentarios(comentariosExistentes);
    
            // Actualizar el estatus
            denuncia.setEstatus(nuevoEstatus);
    
            // Guardar los cambios en la base de datos
            denunciaRepository.save(denuncia);
    
            return new ResponseEntity<>("Denuncia actualizada exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();  // Imprimir el error en los logs para depuración
            return new ResponseEntity<>("Error interno en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    


    

}
