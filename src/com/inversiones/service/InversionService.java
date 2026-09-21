package com.inversiones.service;

import com.inversiones.exception.InversionInvalidaException;
import com.inversiones.exception.SaldoInsuficienteException;
import com.inversiones.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InversionService {
    private final List<Calculable> cartera = new ArrayList<>();

    // Mapea ID del cliente -> lista de sus transacciones
    private final Map<Integer, List<Transaccion>> historialTransacciones = new HashMap<>();
    private int contadorTransacciones = 1;

    private void registrarTransaccion(int clienteId, double monto, String tipo) {
        // computeIfAbsent crea la lista vacía si es la primera vez que opera el cliente
        historialTransacciones.computeIfAbsent(clienteId, k -> new ArrayList<>())
                .add(new Transaccion(contadorTransacciones++, monto, tipo));
    }

    public void agregarPlazoFijo(Cliente cliente, double monto, int dias, double tna)
            throws SaldoInsuficienteException, InversionInvalidaException {

        if (monto <= 0 || dias <= 0 || tna <= 0) {
            throw new InversionInvalidaException("Los parámetros del Plazo Fijo deben ser mayores a cero.");
        }
        if (!cliente.descontarSaldo(monto)) {
            throw new SaldoInsuficienteException("Saldo insuficiente para constituir el Plazo Fijo por $" + monto);
        }
        cartera.add(new PlazoFijo(monto, dias, tna));

        // Registramos la transacción en el historial del cliente
        registrarTransaccion(cliente.getId(), monto, "PLAZO_FIJO");
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

        // Registramos la transacción en el historial del cliente
        registrarTransaccion(cliente.getId(), monto, "ACCION");
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

        // Registramos la transacción en el historial del cliente
        registrarTransaccion(cliente.getId(), monto, "FCI");
    }

    // Devuelve el historial de un cliente o una lista vacía si no tiene transacciones
    public List<Transaccion> obtenerHistorialCliente(int clienteId) {
        return historialTransacciones.getOrDefault(clienteId, new ArrayList<>());
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


    public void venderAccion(Cliente cliente, Accion accion) throws InversionInvalidaException {
        if (!cartera.contains(accion)) {
            throw new InversionInvalidaException("La acción no pertenece a la cartera actual.");
        }

        // 1. Calculamos el total recibido por la venta
        double montoRecibido = accion.getCantidad() * accion.getPrecioActual();

        // 2. Acreditamos el saldo al cliente
        cliente.setSaldo(cliente.getSaldo() + montoRecibido);

        // 3. Quitamos la acción de la cartera
        cartera.remove(accion);

        // 4. Registramos la transacción
        registrarTransaccion(cliente.getId(), montoRecibido, "VENTA_ACCION");
    }
}