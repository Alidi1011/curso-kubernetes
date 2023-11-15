package com.aarteaga.msvc.cursos.services;

import com.aarteaga.msvc.cursos.models.Usuario;
import com.aarteaga.msvc.cursos.models.entities.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
  List<Curso> listar();
  Optional<Curso> porId(Long id);
  Curso guardar(Curso curso);
  void eliminar(Long id);
  void eliminarCursoUsuarioPorId(Long id);

  Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
  Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
  Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);

  Optional<Curso> porIdConUsuarios(Long id);
}
