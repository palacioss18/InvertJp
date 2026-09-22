package com.inversiones.model;

import com.inversiones.enums.TipoOperacion;

import java.time.LocalDateTime;

public class Transaccion {
    //ATRIBUTOS
    private int idTransaccion;
    private LocalDateTime fecha;
    private double monto;
    private TipoOperacion tipoOperacion;

    //CONTRUCTOR
    public Transaccion(int idTransaccion,double monto,TipoOperacion tipoOperacion){
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

    public TipoOperacion getTipoOperacion(){
        return tipoOperacion;
    }

    @Override
    public String toString(){
        return "Transaccion #" + idTransaccion + " | Tipo: "+tipoOperacion+" | Monto: "+monto+"$"+"| Fecha: "+fecha;
    }


}