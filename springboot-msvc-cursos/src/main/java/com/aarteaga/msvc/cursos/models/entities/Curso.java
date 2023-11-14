package com.aarteaga.msvc.cursos.models.entities;

import com.aarteaga.msvc.cursos.models.Usuario;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotEmpty
  private String name;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) /*lazy by default*/
  @JoinColumn(name = "curso_id")
  private List<CursoUsuario> cursoUsuarios;

  @Transient
  private List<Usuario> usuarios;

  public Curso() {
    cursoUsuarios = new ArrayList<>();
    usuarios = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<CursoUsuario> getCursoUsuarios() {
    return cursoUsuarios;
  }

  public void setCursoUsuarios(List<CursoUsuario> cursoUsuarios) {
    this.cursoUsuarios = cursoUsuarios;
  }

  public void addCursoUsuario(CursoUsuario cursoUsuario){
    this.cursoUsuarios.add(cursoUsuario);
  }

  public void removeCursoUsuario(CursoUsuario cursoUsuario){
    this.cursoUsuarios.remove(cursoUsuario);
  }

  public List<Usuario> getUsuarios() {
    return usuarios;
  }

  public void setUsuarios(List<Usuario> usuarios) {
    this.usuarios = usuarios;
  }
}
