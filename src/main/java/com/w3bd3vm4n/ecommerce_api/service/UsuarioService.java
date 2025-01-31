package com.w3bd3vm4n.ecommerce_api.service;

import com.w3bd3vm4n.ecommerce_api.dto.*;
import com.w3bd3vm4n.ecommerce_api.model.Rol;
import com.w3bd3vm4n.ecommerce_api.model.Usuario;
import com.w3bd3vm4n.ecommerce_api.repository.RolRepository;
import com.w3bd3vm4n.ecommerce_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    private UsuarioResponseDTO convertirAUsuarioResponseDTO(Usuario usuario) {
        UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setHabilitado(usuario.getHabilitado());

        if (usuario.getRol() != null) {
            RolResponseDTO rolDTO = new RolResponseDTO();
            rolDTO.setId(usuario.getRol().getId());
            rolDTO.setNombre(usuario.getRol().getNombre());
            usuarioDTO.setRol(rolDTO);
        }

        return usuarioDTO;
    }

    private Usuario convertirAUsuario(UsuarioCreateDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setHabilitado(dto.getHabilitado());
        return usuario;
    }

    public List<UsuarioResponseDTO> obtenerTodosLosUsuariosDesdeRepositorio() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirAUsuarioResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioResponseDTO> obtenerUsuarioPorIdDesdeRepositorio(Long id) {
        return usuarioRepository.findById(id)
                .map(this::convertirAUsuarioResponseDTO);
    }

    @Transactional
    public UsuarioResponseDTO crearUsuarioDesdeRepositorio(UsuarioCreateDTO usuarioDTO) {
        Usuario usuario = convertirAUsuario(usuarioDTO);

        Rol rol = rolRepository.findById(usuarioDTO.getRolId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        usuario.setRol(rol);

        return convertirAUsuarioResponseDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public Optional<UsuarioResponseDTO> actualizarUsuarioDesdeRepositorio(Long id, UsuarioUpdateDTO usuarioDTO) {
        return usuarioRepository.findById(id).map(usuario -> {
            if (usuarioDTO.getNombre() != null) {
                usuario.setNombre(usuarioDTO.getNombre());
            }
            if (usuarioDTO.getContrasena() != null) {
                usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));
            }
            if (usuarioDTO.getHabilitado() != null) {
                usuario.setHabilitado(usuarioDTO.getHabilitado());
            }
            if (usuarioDTO.getRolId() != null) {
                Rol rol = rolRepository.findById(usuarioDTO.getRolId())
                        .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
                usuario.setRol(rol);
            }
            return convertirAUsuarioResponseDTO(usuarioRepository.save(usuario));
        });
    }

    public boolean borrarUsuarioDesdeRepositorio(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        usuario.ifPresent(usuarioRepository::delete);
        return usuario.isPresent();
    }

}
