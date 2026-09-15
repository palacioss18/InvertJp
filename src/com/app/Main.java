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
        misInversiones.add(new Accion("YPF", 10, 1000, 1200)); 
        misInversiones.add(new PlazoFijo(50000, 0.70)); 
        misInversiones.add(new FondoComunInversion(20000, 1.15));

        double gananciaTotal = 0;

        // Recorremos la lista sin importar de qué tipo sea cada inversión
        for (Calculable inv : misInversiones) {
            gananciaTotal += inv.calcularGanancia(); // Java sabe qué calcularGanancia() llamar según el objeto
        }

        System.out.println("Ganancia total del cliente: $" + gananciaTotal);
    }
}
}