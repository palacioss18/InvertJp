package com.inversiones.model;

import java.time.LocalDateTime;

public class Transaccion {
    //ATRIBUTOS
    private int idTransaccion;
    private LocalDateTime fecha;
    private double monto;
    private String tipoOperacion;

    //CONTRUCTOR
    public Transaccion(int idTransaccion,double monto,String tipoOperacion){
        this.idTransaccion = idTransaccion;
        this.fecha = LocalDateTime.now();
        this.monto = monto;
        this.tipoOperacion = tipoOperacion;
    }

    //GETTERS Y toStrings
    public int getIdTransaccion(){
        return this.idTransaccion;
    }

    public LocalDateTime getFecha(){
        return this.fecha;
    }

    public double getMonto(){
        return this.monto;
    }

    public String getTipoOperacion(){
        return tipoOperacion;
    }

    @Override
    public String toString(){
        return "Transaccion #" + idTransaccion + " | Tipo: "+tipoOperacion+" | Monto: "+monto+"$"+"| Fecha: "+fecha;
    }


}