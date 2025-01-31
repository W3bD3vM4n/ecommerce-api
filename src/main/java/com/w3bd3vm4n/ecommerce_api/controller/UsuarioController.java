package com.w3bd3vm4n.ecommerce_api.controller;

import com.w3bd3vm4n.ecommerce_api.dto.*;
import com.w3bd3vm4n.ecommerce_api.model.Usuario;
import com.w3bd3vm4n.ecommerce_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    private UsuarioResponseDTO convertirAUsuarioResponseDTO(Usuario usuario) {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(usuario.getId());
        usuarioResponseDTO.setNombre(usuario.getNombre());
        usuarioResponseDTO.setHabilitado(usuario.getHabilitado());

        if (usuario.getRol() != null) {
            RolResponseDTO rolResponseDTO = new RolResponseDTO();
            rolResponseDTO.setId(usuario.getRol().getId());
            rolResponseDTO.setNombre(usuario.getRol().getNombre());
            usuarioResponseDTO.setRol(rolResponseDTO);
        }

        return usuarioResponseDTO;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodosLosUsuarios() {
        List<Usuario> listaUsuarios = usuarioService.obtenerListaUsuariosDesdeRepositorio();
        List<UsuarioResponseDTO> listaUsuariosResponseDTOs = listaUsuarios.stream()
                .map(this::convertirAUsuarioResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaUsuariosResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorIdDesdeRepositorio(id)
                .map(this::convertirAUsuarioResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuarioCreado = usuarioService.crearUsuarioDesdeRepositorio(usuarioCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.convertirAUsuarioResponseDTO(usuarioCreado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        Optional<Usuario> actualizarUsuario = usuarioService.actualizarUsuarioDesdeRepositorio(id, usuarioUpdateDTO);
        return actualizarUsuario.map(contenido -> ResponseEntity.ok(this.convertirAUsuarioResponseDTO(contenido)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarUsuario(@PathVariable Long id) {
        return usuarioService.borrarUsuarioDesdeRepositorio(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
