package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Basura {
	double x;
	double y;
	Image basura;

	public Basura(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void dibujar(Entorno entorno) {
		basura= Herramientas.cargarImagen("basura.png");
		entorno.dibujarImagen(basura, this.x, this.y, 0, 0.20);
	}

	public void moverse() {		//activa el movimiento de la basura.
		this.x -= 2.8;
		if (this.x < -150) {
			this.x = 1000;
		}
	}
	
	public void nuevaBasura() {		//crea una nueva basura.
		double aux = Math.random() * 200 + 100;
		this.x=850+aux;
	}
}
