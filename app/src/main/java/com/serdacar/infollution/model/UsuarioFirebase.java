package com.serdacar.infollution.model;

public class UsuarioFirebase {
    private String nombre;
    private String email;
    private String password;

    public UsuarioFirebase(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
