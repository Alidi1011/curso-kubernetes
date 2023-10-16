package com.aarteaga.msvc.usuarios.services;

import com.aarteaga.msvc.usuarios.entity.Curso;
import com.aarteaga.msvc.usuarios.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CursoServiceImpl implements CursoService{

  @Autowired
  private CursoRepository repository;
  @Override
  public List<Curso> listar() {
    return (List<Curso>) repository.findAll();
  }

  @Override
  public Optional<Curso> porId(Long id) {
    return repository.findById(id);
  }

  @Override
  public Curso guardar(Curso curso) {
    return repository.save(curso);
  }

  @Override
  public void eliminar(Long id) {
      repository.deleteById(id);
  }
}
