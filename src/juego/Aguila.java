package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Aguila {
	double x;
	double y;
	double angulo;
	double radio;
	Image aguila;
	boolean atacando;
	int velocidad=3;

	public Aguila(double x, double y) {
		this.x = x;
		this.y = y;
		this.radio = 50;
		this.angulo = 0;
	}

	public void dibujar(Entorno entorno) {
		aguila = Herramientas.cargarImagen("aguila.png");
		entorno.dibujarImagen(aguila, this.x, this.y+10, 0, 0.15);
	}

	public void mover(Mono mono, Entorno entorno) {
		double ax = x - mono.x;
		double ay = y - mono.y;
		this.angulo = Math.atan2(ay, ax);
		x -= velocidad * Math.cos(this.angulo);
		y -= velocidad * Math.sin(this.angulo);
	}

	public void atacar() {
		if (!atacando) {
			this.atacando=true;
		}	
	}

	public void nuevoAguila() {
		this.x=900;
		this.y=100;
	}
}


