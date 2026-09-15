package com.inversiones.model;

public class FondoComunInversion extends Inversion {

    // ATRIBUTOS
    private double valorCuotaparteInicial;
    private double valorCuotaparteActual;

    // CONSTRUCTOR
    public FondoComunInversion(double monto, int dias, double valorCuotaparteInicial, double valorCuotaparteActual) {
        super(monto, dias);
        this.valorCuotaparteInicial = valorCuotaparteInicial;
        this.valorCuotaparteActual = valorCuotaparteActual;
    }

    // MÉTODOS
    @Override
    public double calcularGanancia() {
        double cuotapartesCompradas = getMonto() / this.valorCuotaparteInicial;
        double valorActualTotal = cuotapartesCompradas * this.valorCuotaparteActual;

        return valorActualTotal - getMonto();
    }

    // GETTERS Y SETTERS
    public double getValorCuotaparteInicial() {
        return this.valorCuotaparteInicial;
    }

    public void setValorCuotaparteInicial(double valorCuotaparteInicial) {
        this.valorCuotaparteInicial = valorCuotaparteInicial;
    }

    public double getValorCuotaparteActual() {
        return this.valorCuotaparteActual;
    }

    public void setValorCuotaparteActual(double valorCuotaparteActual) {
        this.valorCuotaparteActual = valorCuotaparteActual;
    }
}