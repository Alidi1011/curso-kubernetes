package com.aarteaga.msvc.usuarios.repositories;

import com.aarteaga.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

}
