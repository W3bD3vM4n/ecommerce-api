package com.w3bd3vm4n.ecommerce_api.service;

import com.w3bd3vm4n.ecommerce_api.dto.UsuarioCreateDTO;
import com.w3bd3vm4n.ecommerce_api.dto.UsuarioUpdateDTO;
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

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> obtenerListaUsuariosDesdeRepositorio() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorIdDesdeRepositorio(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario crearUsuarioDesdeRepositorio(UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioCreateDTO.getNombre());
        usuario.setContrasena(passwordEncoder.encode(usuarioCreateDTO.getContrasena()));
        usuario.setHabilitado(usuarioCreateDTO.getHabilitado());

        Rol rol = rolRepository.findById(usuarioCreateDTO.getRolId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Optional<Usuario> actualizarUsuarioDesdeRepositorio(Long id, UsuarioUpdateDTO usuarioUpdateDTO) {
        return usuarioRepository.findById(id).map(usuario -> {
            if (usuarioUpdateDTO.getNombre() != null) {
                usuario.setNombre(usuarioUpdateDTO.getNombre());
            }
            if (usuarioUpdateDTO.getContrasena() != null) {
                usuario.setContrasena(passwordEncoder.encode(usuarioUpdateDTO.getContrasena()));
            }
            if (usuarioUpdateDTO.getHabilitado() != null) {
                usuario.setHabilitado(usuarioUpdateDTO.getHabilitado());
            }
            if (usuarioUpdateDTO.getRolId() != null) {
                Rol rol = rolRepository.findById(usuarioUpdateDTO.getRolId())
                        .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
                usuario.setRol(rol);
            }
            return usuarioRepository.save(usuario);
        });
    }

    public boolean borrarUsuarioDesdeRepositorio(Long id) {
        Optional<Usuario> obtenerUsuario = obtenerUsuarioPorIdDesdeRepositorio(id);
        obtenerUsuario.ifPresent(usuarioRepository::delete);
        return obtenerUsuario.isPresent();
    }

}
