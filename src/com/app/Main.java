package com.app;

public class Main {
    public static void main(String[] args) {
        // Inicia el registro del cliente, que luego abrirá el menú interactivo con sus datos
        Menu_del_Cliente menuCliente = new Menu_del_Cliente();
        menuCliente.iniciar();
    }
}

