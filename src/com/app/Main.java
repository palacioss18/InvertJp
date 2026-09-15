package com.app;

import com.inversiones.model.Calculable;
import com.inversiones.model.Accion;
import com.inversiones.model.PlazoFijo;
import com.inversiones.model.FondoComunInversion;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // Creamos una lista de cosas "Calculables"
        List<Calculable> misInversiones = new ArrayList<>();

        // Agregamos distintas inversiones a la misma lista
        // 1. Accion: (monto, dias, nombre, cantidad, precioCompra, precioActual)
        misInversiones.add(new Accion(10000, 30, "YPF", 10, 1000, 1200));

        // 2. PlazoFijo: (monto, dias, tna)
        misInversiones.add(new PlazoFijo(50000, 30, 70.0));

        // 3. FondoComunInversion: (monto, dias, valorCuotaparteInicial, valorCuotaparteActual)
        misInversiones.add(new FondoComunInversion(20000, 30, 100.0, 115.0));

        double gananciaTotal = 0;

        // Recorremos la lista sin importar de qué tipo sea cada inversión
        for (Calculable inv : misInversiones) {
            gananciaTotal += inv.calcularGanancia(); // Polimorfismo puro
        }

        System.out.println("Ganancia total del cliente: $" + gananciaTotal);
    }
}