package com.w3bd3vm4n.ecommerce_api.service;

import com.w3bd3vm4n.ecommerce_api.model.Usuario;
import com.w3bd3vm4n.ecommerce_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerListaUsuariosDesdeRepositorio() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorIdDesdeRepositorio(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario crearUsuarioDesdeRepositorio(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> actualizarUsuarioDesdeRepositorio(Long id, Usuario usuarioDetalles) {
        Optional<Usuario> usuarioOptional = obtenerUsuarioPorIdDesdeRepositorio(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setNombre(usuarioDetalles.getNombre());
            usuario.setContrasena(usuarioDetalles.getContrasena());
            usuario.setHabilitado(usuarioDetalles.isHabilitado());
            usuario.setRol(usuarioDetalles.getRol());
            return Optional.of(usuarioRepository.save(usuario));
        } else {
            throw new IllegalArgumentException("Usuario con ID " + id + " no existe");
        }
    }

    public boolean borrarUsuarioDesdeRepositorio(Long id) {
        Optional<Usuario> usuarioOptional = obtenerUsuarioPorIdDesdeRepositorio(id);
        if (usuarioOptional.isPresent()) {
            usuarioRepository.delete(usuarioOptional.get());
            return true;
        } else {
            return false;
        }
    }

}
