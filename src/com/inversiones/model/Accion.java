package com.inversiones.model;

public class Accion extends Inversion{
	//ATRIBUTOS
	private String nombre;
	private double cantidad;
	private double precioCompra;
	private double precioActual;

	//CONSTRUCTOR
	public Accion(double monto,int dias,String nombre,double cantidad,double precioCompra,precioActual){
	super.(monto,dias);
	this.nombre = nombre;
	this.cantidad = cantidad;
	this.precioCompra = precioCompra;
	this.precioActual = precioActual;
	}

	//METODOS
	@Override
	public double calcularGanancia(){
		return cantidad * (precioActual - precioCompra);
	}

	//getters y setters
	public double getCantidad(){ 
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioCompra(){
		return precioCompra;
	}

	public void setPrecioCompra(double precioCompra){
		this.precioCompra = precioCompra;
	}

	public double getPrecioActual() { 
		return precioActual;
	}

    public void setPrecioActual(double precioActual) {
		this.precioActual = precioActual;
	}

}