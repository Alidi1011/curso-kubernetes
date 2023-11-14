package com.aarteaga.msvc.cursos.controllers;

import com.aarteaga.msvc.cursos.services.CursoService;
import com.aarteaga.msvc.cursos.models.Usuario;
import com.aarteaga.msvc.cursos.models.entities.Curso;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    //Optional<Curso> c =  service.porId(id);
    Optional<Curso> c =  service.porIdConUsuarios(id);
    if(c.isPresent()){
      return ResponseEntity.ok(c.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/")
  public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result){
    if (result.hasErrors()) {
      return validar(result);
    }
    Curso cursoDb = service.guardar(curso);
    return ResponseEntity.ok().body(cursoDb);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
    if (result.hasErrors()) {
      return validar(result);
    }

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

  @PutMapping("/asignar-usuario/{cursoId}")
  public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
    Optional<Usuario> o;
    try{
      o = service.asignarUsuario(usuario, cursoId);
    } catch (FeignException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Collections
          .singletonMap("mensaje", "No existe el usuario por el id " +
            "o error en la comunicación: " + e.getMessage()));
    }

    if(o.isPresent()){
      return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/crear-usuario/{cursoId}")
  public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
    Optional<Usuario> o;
    try{
      o = service.crearUsuario(usuario, cursoId);
    } catch (FeignException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Collections
          .singletonMap("mensaje", "No se pudo crear el usuario " +
            "o error en la comunicación: " + e.getMessage()));
    }

    if(o.isPresent()){
      return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/eliminar-usuario/{cursoId}")
  public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
    Optional<Usuario> o;
    try{
      o = service.eliminarUsuario(usuario, cursoId);
    } catch (FeignException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Collections
          .singletonMap("mensaje", "No existe el usuario por el id " +
            "o error en la comunicación: " + e.getMessage()));
    }

    if(o.isPresent()){
      return ResponseEntity.status(HttpStatus.OK).body(o.get());
    }
    return ResponseEntity.notFound().build();
  }

  private ResponseEntity<Map<String, String>> validar(BindingResult result){
    Map<String, String>  errors = new HashMap<>();
    result.getFieldErrors().forEach(err -> {
      errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }
}
