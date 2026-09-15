package com.inversiones.model;

//CLASE ABSTRACTA
public abstract class Inversion implements Calculable{
    //ATRIBUTOS
    private double monto;
    private int dias;

    //CONSTRUCTOR
    public Inversion(double monto,int dias){
        this.monto = monto;
        this.dias = dias;
    }


    //MÉTODO ABSTRACTO: CADA TIPO DE INVERSION LO VA A CALCULAR A SU MANERA
    public abstract double calcularGanancia();

    //GETTERs
    public double getMonto(){
        return monto;
    }

    public int getDias(){
        return dias;
    }
}