package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Piedra {
	double x;
	double y;
	Image piedra;
	boolean disparando;
	boolean seFueDePantalla;
	Entorno entorno;
	Image humo;

	public Piedra(double x, double y) {
		this.x = x;
		this.y = y;
		this.disparando=false;
		this.seFueDePantalla =false;
	}

	public void dibujarse(Entorno entorno){
		humo = Herramientas.cargarImagen("humo.png");
		piedra = Herramientas.cargarImagen("TirarPiedra.png");
		entorno.dibujarImagen(piedra, this.x, this.y, 0, 0.045);
	}

	public void disparar() {		
		this.disparando=true;}

	public void mover() {
		if (this.disparando) {
			this.x +=6;
			if (this.x>850) {   
				this.seFueDePantalla=true;
				this.disparando=false;
			}
		}
	}
}



