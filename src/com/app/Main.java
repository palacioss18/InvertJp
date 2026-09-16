package com.app;

import com.inversiones.model.Calculable;
import com.inversiones.model.Accion;
import com.inversiones.model.PlazoFijo;
import com.inversiones.model.FondoComunInversion;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ver menu del cliente o menu interactivo: ");
        String opc = scanner.nextLine();
        if(opc.equalsIgnoreCase("menu del cliente")){
            Menu_del_Cliente menu_cliente = new Menu_del_Cliente();
            menu_cliente.iniciar();
        }
        else if(opc.equalsIgnoreCase("menu interactivo")){
            Menu_Interactivo menu = new Menu_Interactivo();
            menu.iniciar();
        }


    }
}

