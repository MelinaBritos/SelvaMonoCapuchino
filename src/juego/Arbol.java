package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Arbol {
	double x;
	double y;
	Image arbol;
	Image tronco;
	boolean tieneSerpiente;
	double escala;
	boolean tieneFruta;


	public Arbol(double x, double y, double escala) {
		this.x = x;
		this.y = y;
		arbol = Herramientas.cargarImagen("arbol2.png");
		tronco = Herramientas.cargarImagen("tronco.png");
		this.escala=escala;
		tieneSerpiente=false;
		tieneFruta=false;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(arbol, this.x, this.y, 0, escala);
		entorno.dibujarRectangulo(x,y-20,60,1,0,Color.ORANGE);
		entorno.dibujarImagen(tronco, this.x, this.y-20, 0, 0.3);
	}

	public void moverse() {
		this.x -= 2.8;
		if (this.x < -150) {
			this.x = 925;
		}
	}
}

