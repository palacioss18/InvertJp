package com.inversiones.service;

import com.inversiones.exception.InversionInvalidaException;
import com.inversiones.exception.SaldoInsuficienteException;
import com.inversiones.model.*;
import java.util.ArrayList;
import java.util.List;

public class InversionService {
    private final List<Calculable> cartera = new ArrayList<>();

    public void agregarPlazoFijo(Cliente cliente, double monto, int dias, double tna)
            throws SaldoInsuficienteException, InversionInvalidaException {

        if (monto <= 0 || dias <= 0 || tna <= 0) {
            throw new InversionInvalidaException("Los parámetros del Plazo Fijo deben ser mayores a cero.");
        }
        if (!cliente.descontarSaldo(monto)) {
            throw new SaldoInsuficienteException("Saldo insuficiente para constituir el Plazo Fijo por $" + monto);
        }
        cartera.add(new PlazoFijo(monto, dias, tna));
    }

    public void agregarAccion(Cliente cliente, double monto, int dias, String nombre, double cantidad, double pCompra, double pActual)
            throws SaldoInsuficienteException, InversionInvalidaException {

        if (monto <= 0 || cantidad <= 0 || pCompra <= 0) {
            throw new InversionInvalidaException("Los valores ingresados para la Acción deben ser mayores a cero.");
        }
        if (!cliente.descontarSaldo(monto)) {
            throw new SaldoInsuficienteException("Saldo insuficiente para comprar la acción " + nombre);
        }
        cartera.add(new Accion(monto, dias, nombre, cantidad, pCompra, pActual));
    }

    public void agregarFCI(Cliente cliente, double monto, int dias, double cpInicial, double cpActual)
            throws SaldoInsuficienteException, InversionInvalidaException {

        if (monto <= 0 || cpInicial <= 0) {
            throw new InversionInvalidaException("El monto y la cuotaparte inicial deben ser mayores a cero.");
        }
        if (!cliente.descontarSaldo(monto)) {
            throw new SaldoInsuficienteException("Saldo insuficiente para suscribir al Fondo Común.");
        }
        cartera.add(new FondoComunInversion(monto, dias, cpInicial, cpActual));
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