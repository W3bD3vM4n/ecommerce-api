package com.w3bd3vm4n.ecommerce_api.mapper;

import com.w3bd3vm4n.ecommerce_api.dto.RolResponseDTO;
import com.w3bd3vm4n.ecommerce_api.dto.UsuarioCreateDTO;
import com.w3bd3vm4n.ecommerce_api.dto.UsuarioResponseDTO;
import com.w3bd3vm4n.ecommerce_api.model.Rol;
import com.w3bd3vm4n.ecommerce_api.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO convertirAUsuarioResponseDTO(Usuario usuario) {
        UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO();

        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setHabilitado(usuario.getHabilitado());

        if (usuario.getRol() != null) {
            usuarioDTO.setRol(convertirARolResponseDTO(usuario.getRol()));
        }

        return usuarioDTO;
    }

    public Usuario convertirAUsuario(UsuarioCreateDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setNombre(dto.getNombre());
        usuario.setHabilitado(dto.getHabilitado());

        return usuario;
    }

    private RolResponseDTO convertirARolResponseDTO(Rol rol) {
        RolResponseDTO rolDTO = new RolResponseDTO();

        rolDTO.setId(rol.getId());
        rolDTO.setNombre(rol.getNombre());

        return rolDTO;
    }

}
