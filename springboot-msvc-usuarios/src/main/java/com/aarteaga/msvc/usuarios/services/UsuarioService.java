package com.aarteaga.msvc.usuarios.services;

import com.aarteaga.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
  List<Usuario> findAll();
  Optional<Usuario> getById(Long id);
  Usuario save(Usuario usuario);
  void deleteById(Long id);

  Optional<Usuario> porEmail(String email);
  boolean existePorEmail(String email);
}
