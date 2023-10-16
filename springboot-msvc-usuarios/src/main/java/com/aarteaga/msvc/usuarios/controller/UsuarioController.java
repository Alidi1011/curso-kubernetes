package com.aarteaga.msvc.usuarios.controller;

import com.aarteaga.msvc.usuarios.models.entity.Usuario;
import com.aarteaga.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {
  @Autowired
  private UsuarioService service;

  @GetMapping
  public List<Usuario> listar(){
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> detalle(@PathVariable(name = "id") Long id){
    Optional<Usuario> usuarioOptional = service.getById(id);
    if(usuarioOptional.isPresent()){
      return ResponseEntity.ok().body(usuarioOptional.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  //@ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> crear(@RequestBody Usuario usuario){
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuario));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Usuario usuario){
    Optional<Usuario> o = service.getById(id);
    if(o.isPresent()){
      Usuario usuarioDb = o.get();
      usuarioDb.setName(usuario.getName());
      usuarioDb.setLastName(usuario.getLastName());
      usuarioDb.setEmail(usuario.getEmail());
      usuarioDb.setPassword(usuario.getPassword());
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuarioDb));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?>  eliminar(@PathVariable Long id){
    Optional<Usuario> o = service.getById(id);
    if(o.isPresent()) {
      service.deleteById(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}
