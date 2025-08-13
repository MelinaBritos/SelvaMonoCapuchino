package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Mono {
	double x;
	double y;
	Image mono;
	boolean saltando;
	boolean subiendo;
	boolean congelado;
	boolean tocoBasura;
	boolean tocoFruta;
	boolean bajando;
	boolean tropezando;

	public Mono(double x, double y) {		
		this.x = x;
		this.y = y;
		this.saltando = false;
		this.subiendo = false;
		this.congelado = false;
		this.tocoBasura=false;
		this.tropezando=false;
		this.tocoFruta=false;
	}

	public void dibujar(Entorno entorno) {		//dibuja el objeto
		mono = Herramientas.cargarImagen("mono.png");
		entorno.dibujarImagen(mono, this.x, this.y, 0, 0.19);
	}

	public void saltar() {					//activar el salto
		if (!this.saltando) {
			this.saltando = true;
			this.subiendo = true;
		}
	}

	public void bajarDePlataforma() { 	//booleano para bajar de la plataforma
		this.congelado = false;
	}

	public void mover() {			//activa los movimientos del objeto
		if (this.saltando && !this.congelado&&!this.tropezando) {
			if (this.subiendo) {
				this.y -= 6;
			} else {
				this.y += 6;
			}
			if (this.y < 200) {
				this.subiendo = false;
			}
			if (this.y > 420) {
				this.y = 420;
				this.saltando = false;
			}
		}
		if (this.tropezando&&!this.congelado) {
			if (this.subiendo) {
				this.y -= 0.5;
			} else {
				this.y += 0.5;
			}
			if (this.y < 410) {
				this.subiendo = false;
			}
			if (this.y > 420) {
				this.y = 420;
				this.tropezando = false;
			}
		}
	}
	public void tropezar() {
		this.tropezando=true;
		this.subiendo = true;
	}
}