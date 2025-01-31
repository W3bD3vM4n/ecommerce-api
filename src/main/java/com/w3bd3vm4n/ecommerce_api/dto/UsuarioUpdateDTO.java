package com.w3bd3vm4n.ecommerce_api.dto;

import lombok.Data;

@Data
public class UsuarioUpdateDTO {

    private String nombre;
    private String contrasena;
    private Boolean habilitado;
    private Long rolId;

}
