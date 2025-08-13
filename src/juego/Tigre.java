package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Tigre {
	double x;
	double y;
	Image tigre;

	public Tigre(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void dibujar(Entorno entorno) {
		tigre = Herramientas.cargarImagen("tigre.png");
		entorno.dibujarImagen(tigre, this.x, this.y+10, 0, 0.18);
	}
	
	public void moverse() {
		this.x -= 2.8;
		if (this.x < -150) {
			this.x = 1000;
		}
	}
}