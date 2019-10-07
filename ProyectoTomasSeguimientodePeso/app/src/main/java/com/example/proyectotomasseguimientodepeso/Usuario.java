package com.example.proyectotomasseguimientodepeso;


//esta es la clase usuario
public class Usuario {

    private String nombre;
    private Double altura;
    private Double peso;
    private Double pesoObjetivo;
    private Integer pasosObjetivo;
    private Boolean enKilos;

    private Usuario(){

    }

    public Usuario(String nombre, Double peso, Double altura, Double pesoObjetivo, Integer pasosObjetivo, Boolean enKilos) {
        this.nombre = nombre;
        this.peso = peso;
        this.altura = altura;
        this.pesoObjetivo = pesoObjetivo;
        this.pasosObjetivo = pasosObjetivo;
        this.enKilos = enKilos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPesoObjetivo() {
        return pesoObjetivo;
    }

    public void setPesoObjetivo(Double pesoObjetivo) {
        this.pesoObjetivo = pesoObjetivo;
    }

    public Integer getPasosObjetivo() {
        return pasosObjetivo;
    }

    public void setPasosObjetivo(Integer pasosObjetivo) {
        this.pasosObjetivo = pasosObjetivo;
    }

    public Boolean getEnKilos() {
        return enKilos;
    }

    public void setEnKilos(Boolean enKilos) {
        this.enKilos = enKilos;
    }
}
