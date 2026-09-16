package com.app;

import com.inversiones.model.*;
import java.util.Scanner;

public class Menu_del_Cliente {
    public void iniciar(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== REGISTRO DE CLIENTE ===");
        System.out.print("Ingrese su nombre: ");
        String nom = scanner.nextLine();

        System.out.print("Ingrese su Saldo: ");
        double sal = scanner.nextDouble();

        System.out.print("Ingrese su ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpieza de buffer

        // Creamos el cliente
        Cliente jose = new Cliente(id, nom, sal);

        // Instanciamos el menú principal y le pasamos el cliente creado
        Menu_Interactivo menuInversiones = new Menu_Interactivo();
        menuInversiones.iniciar(jose);
    }
}