package com.aarteaga.msvc.usuarios.controller;

import com.aarteaga.msvc.usuarios.models.entity.Usuario;
import com.aarteaga.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UsuarioController {
  @Autowired
  private UsuarioService service;

  @Autowired
  private ApplicationContext context;

  @Autowired
  private Environment env;

  @GetMapping("/crash")
  public void crash(){
    ((ConfigurableApplicationContext)context).close();
  }


  @GetMapping
  public Map<String, Object> listar(){
    Map<String, Object> body = new HashMap<>();
    body.put("users", service.findAll());
    body.put("podinfo", env.getProperty("MY_POD_NAME") + ": " + env.getProperty("MY_POD_IP"));
    //return Collections.singletonMap("users", service.findAll());
    return body;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> detalle(@PathVariable(name = "id") Long id){
    Optional<Usuario> usuarioOptional = service.getById(id);
    if(usuarioOptional.isPresent()){
      System.out.println("actualizado get user by id");
      return ResponseEntity.ok().body(usuarioOptional.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  //@ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result){
    if (result.hasErrors()) {
      return validar(result);
    }

    //if(!usuario.getEmail().isEmpty() && service.porEmail(usuario.getEmail()).isPresent()){
    if(!usuario.getEmail().isEmpty() && service.existePorEmail(usuario.getEmail())){
      return ResponseEntity.badRequest()
        .body(Collections
          .singletonMap("Mensaje", "Ya existe un usuario con ese Correo Electrónico!"));
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuario));
  }
  @PutMapping("/{id}")
  public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
    if (result.hasErrors()) {
      return validar(result);
    }

    Optional<Usuario> o = service.getById(id);
    if(o.isPresent()){
      Usuario usuarioDb = o.get();

      if(!usuario.getEmail().isEmpty() && !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) &&
        service.porEmail(usuario.getEmail()).isPresent()){
        return ResponseEntity.badRequest()
          .body(Collections
            .singletonMap("mensaje", "Ya existe un usuario con ese correo electrónico!"));
      }

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

  @GetMapping("/usuarios-por-curso")
  public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
    return ResponseEntity.ok().body(service.listarPorIds(ids));
  }

  private ResponseEntity<Map<String, String>> validar(BindingResult result){
    Map<String, String>  errors = new HashMap<>();
    result.getFieldErrors().forEach(err -> {
      errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }
}
