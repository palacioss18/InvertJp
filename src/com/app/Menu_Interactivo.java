package com.app;

import com.inversiones.exception.InversionInvalidaException;
import com.inversiones.exception.SaldoInsuficienteException;
import com.inversiones.model.Calculable;
import com.inversiones.model.Cliente;
import com.inversiones.service.InversionService;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.inversiones.model.Transaccion;

public class Menu_Interactivo {
    private final InversionService servicio = new InversionService();

    public void iniciar(Cliente cliente) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("=== SISTEMA DE GESTIÓN DE INVERSIONES ===");

        while (continuar) {
            System.out.println("\n=== CLIENTE: " + cliente.getNombre() + " | SALDO: " + cliente.getSaldo() + "$ ===");
            System.out.println("1. Agregar Plazo fijo\n2. Agregar Accion\n" +
                    "3. Agregar Fondo Común de Inversión\n4. Ver ganancias detalladas\n" +
                    "5. Ver ganancia total acumulada\n6. Ver Transacciones\n7. Salir\nSELECCIONE UNA OPCIÓN: ");

            int opcion;
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entero válido.");
                scanner.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    try {
                        System.out.print("Ingrese el monto a invertir: ");
                        double montoPF = scanner.nextDouble();
                        System.out.print("Ingrese la cantidad de días: ");
                        int diasPF = scanner.nextInt();
                        System.out.print("Ingrese la TNA (Ej: 70.0): ");
                        double tna = scanner.nextDouble();

                        // Invoca al servicio que lanza excepciones de negocio
                        servicio.agregarPlazoFijo(cliente, montoPF, diasPF, tna);
                        System.out.println("-> Plazo fijo agendado con éxito.");

                    } catch (SaldoInsuficienteException | InversionInvalidaException e) {
                        System.out.println(" Error: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Error en la entrada de datos.");
                        scanner.nextLine();
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Ingrese el monto total invertido: ");
                        double montoAcc = scanner.nextDouble();
                        System.out.print("Ingrese la cantidad de días: ");
                        int diasAcc = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Ingrese el nombre/ticker del activo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese la cantidad de acciones: ");
                        double cantidad = scanner.nextDouble();
                        System.out.print("Ingrese el precio de compra unitario: ");
                        double pCompra = scanner.nextDouble();
                        System.out.print("Ingrese el precio actual unitario: ");
                        double pActual = scanner.nextDouble();

                        servicio.agregarAccion(cliente, montoAcc, diasAcc, nombre, cantidad, pCompra, pActual);
                        System.out.println("-> Acción agregada con éxito.");

                    } catch (SaldoInsuficienteException | InversionInvalidaException e) {
                        System.out.println(" Error: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Error en la entrada de datos.");
                        scanner.nextLine();
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Ingrese el monto invertido: ");
                        double montoFCI = scanner.nextDouble();
                        System.out.print("Ingrese la cantidad de días: ");
                        int diasFCI = scanner.nextInt();
                        System.out.print("Ingrese el valor inicial de la cuotaparte: ");
                        double cpInicial = scanner.nextDouble();
                        System.out.print("Ingrese el valor actual de la cuotaparte: ");
                        double cpActual = scanner.nextDouble();

                        servicio.agregarFCI(cliente, montoFCI, diasFCI, cpInicial, cpActual);
                        System.out.println("-> FCI agregado con éxito.");

                    } catch (SaldoInsuficienteException | InversionInvalidaException e) {
                        System.out.println(" Error: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Error en la entrada de datos.");
                        scanner.nextLine();
                    }
                    break;

                case 4:
                    if (servicio.getCartera().isEmpty()) {
                        System.out.println("No hay inversiones registradas.");
                    } else {
                        System.out.println("\n--- DETALLE DE INVERSIONES ---");
                        for (int i = 0; i < servicio.getCartera().size(); i++) {
                            Calculable inv = servicio.getCartera().get(i);
                            String tipo = inv.getClass().getSimpleName();
                            System.out.println("[" + (i + 1) + "] Tipo: " + tipo + " | Ganancia proyectada: $" + inv.calcularGanancia());
                        }
                    }
                    break;

                case 5:
                    System.out.println("\n>>> Ganancia total estimada de la cartera: $" + servicio.calcularGananciaTotal() + " <<<");
                    break;

                case 6:
                    List<Transaccion> historial = servicio.obtenerHistorialCliente(cliente.getId());
                    if(historial.isEmpty()){
                        System.out.println("No hay transacciones registradas para este cliente.");
                    }
                    else{
                        System.out.println("\n-- HISTORIAL DE TRANSACCIONES (ID Cliente: "+cliente.getId()+") --");
                        for(Transaccion t : historial){
                            System.out.println(t);
                        }
                    }
                    break;

                case 7:
                    continuar = false;
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }
}






