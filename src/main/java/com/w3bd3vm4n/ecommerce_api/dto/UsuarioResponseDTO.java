package com.w3bd3vm4n.ecommerce_api.dto;

import lombok.Data;

@Data
public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private Boolean habilitado;
    private RolResponseDTO rol;

}
