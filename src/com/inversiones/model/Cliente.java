package com.inversiones.model;

public class Cliente{
    //ATRIBUTOS PRIVADO
    private int id;
    private String nombre;
    private double saldo;

    //CONSTRUCTOR
    public Cliente(int id,String nombre,double saldo){
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
    }

    //GETTERS
    public double getSaldo(){
        return saldo;
    }

    public String getNombre(){
        return nombre;
    }

    public boolean descontarSaldo(double monto) {
        if (monto <= saldo) {
            saldo -= monto;
            return true;
        }
        return false; // Saldo insuficiente
    }

}











