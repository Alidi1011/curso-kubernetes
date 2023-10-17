package com.aarteaga.msvc.usuarios.services;

import com.aarteaga.msvc.usuarios.models.Usuario;
import com.aarteaga.msvc.usuarios.models.entities.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
  List<Curso> listar();
  Optional<Curso> porId(Long id);
  Curso guardar(Curso curso);
  void eliminar(Long id);

  Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
  Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
  Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);

}
