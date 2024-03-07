package com.aarteaga.msvc.cursos.clients;

import com.aarteaga.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Iterator;
import java.util.List;

@FeignClient(name = "msvc-usuarios", url="msvc-usuarios:8001")
public interface UsuarioClienteRest {
  @GetMapping("/{id}")
  Usuario detalle(@PathVariable Long id);

  @PostMapping("/")
  Usuario crear(@RequestBody Usuario usuario);

  @GetMapping("/usuarios-por-curso")
  List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);
}
