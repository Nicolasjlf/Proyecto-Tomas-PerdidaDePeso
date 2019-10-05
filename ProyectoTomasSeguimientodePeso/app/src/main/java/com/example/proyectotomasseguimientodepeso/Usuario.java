package com.example.proyectotomasseguimientodepeso;

public class Usuario {

    private String nombre;
    private String mail;
    private Integer peso;
    private Integer altura;
    private Integer pesoObjetivo;
    private Boolean enKilos;

    private Usuario(){

    }

    public Usuario(String nombre, String mail, Integer peso, Integer altura, Integer pesoObjetivo, Boolean enKilos) {
        this.nombre = nombre;
        this.mail = mail;
        this.peso = peso;
        this.altura = altura;
        this.pesoObjetivo = pesoObjetivo;
        this.enKilos = enKilos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Integer getPesoObjetivo() {
        return pesoObjetivo;
    }

    public void setPesoObjetivo(Integer pesoObjetivo) {
        this.pesoObjetivo = pesoObjetivo;
    }

    public Boolean getEnKilos() {
        return enKilos;
    }

    public void setEnKilos(Boolean enKilos) {
        this.enKilos = enKilos;
    }
}
