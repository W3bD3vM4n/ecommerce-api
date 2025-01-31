package com.w3bd3vm4n.ecommerce_api.service;

import com.w3bd3vm4n.ecommerce_api.dto.*;
import com.w3bd3vm4n.ecommerce_api.mapper.UsuarioMapper;
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
    private final UsuarioMapper usuarioMapper;

    public List<UsuarioResponseDTO> obtenerTodosLosUsuariosDesdeRepositorio() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::convertirAUsuarioResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioResponseDTO> obtenerUsuarioPorIdDesdeRepositorio(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::convertirAUsuarioResponseDTO);
    }

    @Transactional
    public UsuarioResponseDTO crearUsuarioDesdeRepositorio(UsuarioCreateDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.convertirAUsuario(usuarioDTO);

        usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));

        Rol rol = rolRepository.findById(usuarioDTO.getRolId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        usuario.setRol(rol);

        return usuarioMapper.convertirAUsuarioResponseDTO(usuarioRepository.save(usuario));
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
            return usuarioMapper.convertirAUsuarioResponseDTO(usuarioRepository.save(usuario));
        });
    }

    public boolean borrarUsuarioDesdeRepositorio(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        usuario.ifPresent(usuarioRepository::delete);
        return usuario.isPresent();
    }

}
