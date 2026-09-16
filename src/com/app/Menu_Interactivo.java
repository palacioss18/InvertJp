package com.app;
import com.inversiones.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu_Interactivo {
    public void iniciar(){
        Scanner scanner = new Scanner(System.in);
        List<Calculable> cartera = new ArrayList<>();
        boolean continuar = true;

        System.out.println("=== SISTEMA DE GESTIÓN DE INVERSIONES ===");

        while(continuar){
            System.out.println("\n MENÚ PRINCIPAL\n1. Agregar Plazo fijo\n2. Agregar Accion\n" +
                    "3. Agregar Fondo Común de Inversión\n4. Ver ganancias detalladas\n" +
                    "5. Ver ganancia total acumulada\n6. Salir\nSELECCIONE UNA OPCIÓN: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();// Limpia el buffer

            switch(opcion){
                case 1:
                    System.out.print("Ingrese el monto a invertir: ");
                    double montoPF = scanner.nextDouble();
                    System.out.print("Ingrese la cantidad de dias: ");
                    int diasPF = scanner.nextInt();
                    System.out.print("Ingrese la TNA (Ej: 70.0): ");
                    double tna = scanner.nextDouble();

                    cartera.add(new PlazoFijo(montoPF,diasPF,tna));
                    System.out.println("Plazo fijo agendado con exitos.");
                    break;

                case 2:
                    System.out.print("Ingrese el monto total invertido: ");
                    double montoAcc = scanner.nextDouble();
                    System.out.print("Ingrese la cantidad de dias: ");
                    int diasAcc = scanner.nextInt();
                    scanner.nextLine();//Limpieza de buffer
                    System.out.print("Ingrese el nombre/ticker del activo (Ej: YPF, AAPL): ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese la cantidad de acciones: ");
                    double cantidad = scanner.nextDouble();
                    System.out.print("Ingrese el precio de compra unitario: ");
                    double pCompra = scanner.nextDouble();
                    System.out.print("Ingrese el precio actual unitario: ");
                    double pActual = scanner.nextDouble();

                    cartera.add(new Accion(montoAcc, diasAcc, nombre, cantidad, pCompra, pActual));
                    System.out.println("-> Acción agregada con éxito.");
                    break;

                case 3:
                    System.out.print("Ingrese el monto invertido: ");
                    double montoFCI = scanner.nextDouble();
                    System.out.print("Ingrese la cantidad de días: ");
                    int diasFCI = scanner.nextInt();
                    System.out.print("Ingrese el valor inicial de la cuotaparte: ");
                    double cpInicial = scanner.nextDouble();
                    System.out.print("Ingrese el valor actual de la cuotaparte: ");
                    double cpActual = scanner.nextDouble();

                    cartera.add(new FondoComunInversion(montoFCI, diasFCI, cpInicial, cpActual));
                    System.out.println("-> Fondo Común de Inversión agregado con éxito.");
                    break;
                case 4:
                    if (cartera.isEmpty()) {
                        System.out.println("No hay inversiones registradas.");
                    } else {
                        System.out.println("\n--- DETALLE DE INVERSIONES ---");
                        for (int i = 0; i < cartera.size(); i++) {
                            Calculable inv = cartera.get(i);
                            String tipo = inv.getClass().getSimpleName();
                            System.out.printf("[%d] Tipo: %-20s | Ganancia proyectada: $%.2f%n",
                                    (i + 1), tipo, inv.calcularGanancia());
                        }
                    }
                    break;
                case 5:
                    double gananciaTotal = 0.0;
                    for (Calculable inv : cartera) {
                        gananciaTotal += inv.calcularGanancia();
                    }
                    System.out.printf("%n>>> Ganancia total estimada de la cartera: $%.2f <<<%n", gananciaTotal);
                    break;
                case 6:
                    continuar = false;
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }
}























