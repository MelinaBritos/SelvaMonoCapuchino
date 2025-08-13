package juego;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Fruta {
	double x;
	double y;
	Image fruta;


	public Fruta(double x, double y) {
		this.x = x;
		this.y = y;
		fruta = Herramientas.cargarImagen("fruta.png");
	}
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(fruta, this.x, this.y, 0, 0.03);
	}
	
	public void moverse() {
		this.x -= 2.8;
		if (this.x < -150) {
			this.x = 925;
		}
	}
}
