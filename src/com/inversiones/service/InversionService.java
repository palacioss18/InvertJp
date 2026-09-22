package com.inversiones.service;

import com.inversiones.enums.TipoOperacion;
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

    private void registrarTransaccion(int clienteId, double monto, TipoOperacion tipo) {
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
        registrarTransaccion(cliente.getId(), monto, TipoOperacion.PLAZO_FIJO);
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
        registrarTransaccion(cliente.getId(), monto, TipoOperacion.ACCION);
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
        registrarTransaccion(cliente.getId(), monto, TipoOperacion.FCI);
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


    public void venderAccion(Cliente cliente, int indice) throws InversionInvalidaException {
        if (indice < 0 || indice >= cartera.size()) {
            throw new InversionInvalidaException("Índice de inversión inválido.");
        }

        Calculable item = cartera.get(indice);
        if (!(item instanceof Accion)) {
            throw new InversionInvalidaException("La inversión seleccionada no es una Acción.");
        }

        Accion accion = (Accion) item;
        // Total devuelto: Capital original invertido (monto) + Ganancia/Pérdida realizada
        double montoVenta = (accion.getCantidad() * accion.getPrecioActual());

        cliente.acreditarSaldo(montoVenta);
        cartera.remove(indice);

        registrarTransaccion(cliente.getId(), montoVenta, TipoOperacion.VENTA_ACCION);
    }

    public void rescatarFCI(Cliente cliente, int indice) throws InversionInvalidaException {
        if (indice < 0 || indice >= cartera.size()) {
            throw new InversionInvalidaException("Índice de inversión inválido.");
        }

        Calculable item = cartera.get(indice);
        if (!(item instanceof FondoComunInversion)) {
            throw new InversionInvalidaException("La inversión seleccionada no es un Fondo Común de Inversión.");
        }

        FondoComunInversion fci = (FondoComunInversion) item;
        // Total devuelto: Monto inicial + Ganancia/Pérdida
        double montoRescate = fci.getMonto() + fci.calcularGanancia();

        cliente.acreditarSaldo(montoRescate);
        cartera.remove(indice);

        registrarTransaccion(cliente.getId(), montoRescate, TipoOperacion.RESCATE_FCI);
    }
}