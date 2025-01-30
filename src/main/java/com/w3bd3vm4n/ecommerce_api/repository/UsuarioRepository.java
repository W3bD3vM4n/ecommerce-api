package com.w3bd3vm4n.ecommerce_api.repository;

import com.w3bd3vm4n.ecommerce_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
