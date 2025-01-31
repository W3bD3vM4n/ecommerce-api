package com.w3bd3vm4n.ecommerce_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioCreateDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String contrasena;

    private Boolean habilitado = true;
    private Long rolId;

}
