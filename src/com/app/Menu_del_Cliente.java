package com.app;
import com.inversiones.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu_del_Cliente {
    public void iniciar(){
        Scanner scanner = new Scanner(System.in);

        boolean continuar = true;
        while(continuar){
            System.out.print("Ingrese su nombre: ");
            String nom = scanner.nextLine();

            System.out.print("Ingrese su Saldo: ");
            double sal = scanner.nextDouble();

            System.out.print("SU ID: ");
            int id = scanner.nextInt();

            Cliente jose = new Cliente(id,nom,sal);
        }
    }
}
