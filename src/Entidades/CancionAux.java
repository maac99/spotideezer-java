/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author julia
 */
public class CancionAux implements Comparable<CancionAux> {
    private String titulo_c;
    private String titulo_alabum;
    private String nombre_real;
    private String nombre_artistico;
    private int orden;
    private String ordens;

    public CancionAux(String titulo_c, String titulo_alabum, String nombre_real, String nombre_artistico, int orden) {
        this.titulo_c = titulo_c;
        this.titulo_alabum = titulo_alabum;
        this.nombre_real = nombre_real;
        this.nombre_artistico = nombre_artistico;
        this.orden = orden;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public CancionAux() {
    }

    public void setTitulo_c(String titulo_c) {
        this.titulo_c = titulo_c;
    }

    public void setTitulo_alabum(String titulo_alabum) {
        this.titulo_alabum = titulo_alabum;
    }

    public void setNombre_real(String nombre_real) {
        this.nombre_real = nombre_real;
    }

    public void setNombre_artistico(String nombre_artistico) {
        this.nombre_artistico = nombre_artistico;
    }
    
    

    public String getTitulo_c() {
        return titulo_c;
    }

    public String getTitulo_alabum() {
        return titulo_alabum;
    }

    public String getNombre_real() {
        return nombre_real;
    }

    public String getNombre_artistico() {
        return nombre_artistico;
    }

    public String getOrdens() {
        return ordens;
    }

    public void setOrdens(String ordens) {
        this.ordens = ordens;
    }

    @Override
    public int compareTo(CancionAux o) {
        if(this.orden < o.getOrden()){
            return -1;
        }
        else if(this.orden > o.getOrden()){
            return 1;
        }
        return 0;
    }
    
}
