package com.inversiones.service;

import com.inversiones.model.*;
import java.util.ArrayList;
import java.util.List;

public class InversionService {
    private final List<Calculable> cartera = new ArrayList<>();

    public boolean agregarPlazoFijo(Cliente cliente, double monto, int dias, double tna) {
        if (!cliente.descontarSaldo(monto)) {
            return false;
        }
        cartera.add(new PlazoFijo(monto, dias, tna));
        return true;
    }

    public boolean agregarAccion(Cliente cliente, double monto, int dias, String nombre, double cantidad, double pCompra, double pActual) {
        if (!cliente.descontarSaldo(monto)) {
            return false;
        }
        cartera.add(new Accion(monto, dias, nombre, cantidad, pCompra, pActual));
        return true;
    }

    public boolean agregarFCI(Cliente cliente, double monto, int dias, double cpInicial, double cpActual) {
        if (!cliente.descontarSaldo(monto)) {
            return false;
        }
        cartera.add(new FondoComunInversion(monto, dias, cpInicial, cpActual));
        return true;
    }

    public List<Calculable> getCartera() {
        return cartera;
    }

    public double calcularGananciaTotal() {
        double total = 0.0;
        for (Calculable inv : cartera) {
            total += inv.calcularGanancia();
        }
        return total;
    }
}