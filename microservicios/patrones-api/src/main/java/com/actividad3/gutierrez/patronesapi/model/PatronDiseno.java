package com.actividad3.gutierrez.patronesapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class PatronDiseno {

    private Long id;

    @NotBlank(message = "Titulo es obligatorio")
    private String titulo;

    @NotBlank(message = "Descripcion es obligatorio")
    private String descripcion;

    @NotEmpty(message = "Debe tener al menos una palabra clave")
    private List<String> palabrasClave;


    public PatronDiseno() {}

    public PatronDiseno(Long id, String titulo, String descripcion, List<String> palabrasClave) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.palabrasClave = palabrasClave;
    }

    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<String> getPalabrasClave() { return palabrasClave; }
    public void setPalabrasClave(List<String> palabrasClave) { this.palabrasClave = palabrasClave; }

}
