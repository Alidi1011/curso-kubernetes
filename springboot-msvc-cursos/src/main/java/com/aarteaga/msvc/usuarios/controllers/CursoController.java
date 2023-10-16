package com.aarteaga.msvc.usuarios.controllers;

import com.aarteaga.msvc.usuarios.entity.Curso;
import com.aarteaga.msvc.usuarios.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CursoController {

  @Autowired
  private CursoService service;

  @GetMapping
  public ResponseEntity<List<Curso>> listar(){
    return ResponseEntity.ok().body(service.listar());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> detalle(@PathVariable Long id){
    Optional<Curso> c =  service.porId(id);
    if(c.isPresent()){
      return ResponseEntity.ok(c.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/")
  public ResponseEntity<?> crear(@RequestBody Curso curso){
    Curso cursoDb = service.guardar(curso);
    return ResponseEntity.ok().body(cursoDb);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id){
    Optional<Curso> optionalCurso = service.porId(id);
    if(optionalCurso.isPresent()){
      Curso cursoDb = optionalCurso.get();
      cursoDb.setName(curso.getName());
      return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cursoDb));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminar(@PathVariable Long id){
    Optional<Curso> oc = service.porId(id);
    if (oc.isPresent()) {
      service.eliminar(oc.get().getId());
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}
