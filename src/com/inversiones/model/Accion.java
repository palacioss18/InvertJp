package com.inversiones.model;

public class Accion extends Inversion{
	//ATRIBUTOS
	

	//CONSTRUCTOR


	//METODOS
	@Override
	public double calcularGanancia(){
		return cantidad * (precioActual - precioCompra);
	}

}