package com.inversiones.model;

public class Cliente {
    private int id;
    private String nombre;
    private double saldo;

    public Cliente(int id, String nombre, double saldo) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
    }

    // Agregar este Getter
    public int getId() {
        return id;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean descontarSaldo(double monto) {
        if (monto <= saldo) {
            saldo -= monto;
            return true;
        }
        return false;
    }

    public void setSaldo(double v) {
        this.saldo = v;
    }

    public void acreditarSaldo(double monto) {
        this.saldo +=monto;
    }
}










