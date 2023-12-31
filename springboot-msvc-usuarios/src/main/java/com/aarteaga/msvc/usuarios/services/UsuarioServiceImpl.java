package com.aarteaga.msvc.usuarios.services;

import com.aarteaga.msvc.usuarios.clients.CursoClienteRest;
import com.aarteaga.msvc.usuarios.models.entity.Usuario;
import com.aarteaga.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{
  @Autowired
  private UsuarioRepository repository;

  @Autowired
  private CursoClienteRest client;

  @Transactional(readOnly = true)
  public List<Usuario> findAll() {
    return (List<Usuario>) repository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Usuario> getById(Long id) {
    return repository.findById(id);
  }

  @Transactional
  public Usuario save(Usuario usuario) {
    return repository.save(usuario);
  }

  @Transactional
  public void deleteById(Long id) {
      repository.deleteById(id);
      client.eliminarCursoUsuarioPorId(id);
  }

  @Override
  public Optional<Usuario> porEmail(String email) {
    return repository.porEmail(email);
  }

  @Override
  public boolean existePorEmail(String email) {
    return repository.existsByEmail(email);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Usuario> listarPorIds(Iterable<Long> ids) {
    return (List<Usuario>) repository.findAllById(ids);
  }
}
