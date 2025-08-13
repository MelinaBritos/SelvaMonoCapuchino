package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Serpiente {
	double x;
	double y;
	Image serpiente;

	public Serpiente(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void dibujar(Entorno entorno) {
		serpiente = Herramientas.cargarImagen("Serpiente.png");
		entorno.dibujarImagen(serpiente, this.x, this.y, 0, 0.20);
	}

	public void moverse() {
		this.x -= 2.8;
		if (this.x < -150) {
			this.x = 925;
		}
	}
}