package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Image fondo;
	private Image piso;
	private Image menu;
	private Image humo;
	private Image restart;
	private Image creditos1;
	private Image creditos2;
	private Image creditos3;
	private Image pausar;
	int puntaje;
	boolean sumandoPuntos;
	boolean pausa=false;
	int etapa =0;
	int creditos=0;

	// Variables y métodos propios de cada grupo
	Mono mono;
	Piedra piedra;
	Arbol[] arboles;
	Tigre[] tigres;
	Serpiente[]serpientes;
	Basura basura;
	Aguila aguila;
	Fruta[] frutas;

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Mono Capuchino en la selva - Grupo 3 - v2", 800, 600);
		fondo = Herramientas.cargarImagen("fondo.png");
		piso = Herramientas.cargarImagen("piso.png");
		menu = Herramientas.cargarImagen("menu.png");
		humo = Herramientas.cargarImagen("humo.png");
		pausar = Herramientas.cargarImagen("pausa.png");
		restart = Herramientas.cargarImagen("gameOver.png");
		creditos1 = Herramientas.cargarImagen("creditos1.png");
		creditos2 = Herramientas.cargarImagen("creditos2.png");
		creditos3 = Herramientas.cargarImagen("creditos3.png");
		// Inicializar lo que haga falta para el juego
		mono = new Mono(100, 420);
		piedra = new Piedra(mono.x,mono.y);
		aguila= new Aguila(900,100);
		int azar=0;
		arboles = new Arbol[5];
		int max=4;
		int min=1;
		int y=0;
		double escala=0;
		double aux = Math.random() * 200 + 100;
		basura=new Basura(300*2+aux,450);
		serpientes=new Serpiente[5];
		frutas=new Fruta[5];


		for (int i = 0; i < arboles.length; i++) {
			azar=(int)(Math.random()*(max-min)) +min;
			double azar1=(int)(Math.random()*(4-min))+min;
			double azar2=(int)(Math.random()*(4-min))+min;
			y=370;
			if (azar==1) {
				escala=0.22;
			}
			if (azar==2) {
				escala=0.3;
			}
			if (azar==3) {
				escala=0.38;
			}

			arboles[i] = new Arbol(100+220 * i, y, escala);
			if (azar1==1 || azar1==3) {
				arboles[i].tieneSerpiente=true;
				serpientes[i]=new Serpiente(arboles[i].x,arboles[i].y-50);
			}
			else {
				serpientes[i]=null;
			}

			if ((azar2==1 || azar2==3) && (arboles[i].escala==0.38 || arboles[i].escala==0.3) ) {
				arboles[i].tieneFruta=true;
				frutas[i]=new Fruta(arboles[i].x+29,arboles[i].y-70);
			}
			else {
				frutas[i]=null;
			}
		}

		tigres = new Tigre[3];
		for (int i=0;i<tigres.length;i++){ 
			tigres[i]=new Tigre(1000+600*i,410);
		}


		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase.
	 */
	public void tick() {
		////////////////////////////////////////ETAPA 0 = MENU////////////////////////////////////////
		if (etapa==0) {
			inicializarJuego();
			dibujarMenu();
			if (entorno.sePresiono(entorno.TECLA_ENTER)) {
				this.etapa +=1;
			}
		}
		////////////////////////////////////////ETAPA 1 = JUEGO////////////////////////////////////////
		if (etapa==1) {
			dibujarFondo();
			pausa();
			if (pausa) {
				entorno.dibujarImagen(pausar, 400, 350, 0, 0.4);
			}

			if(!pausa) {

				//arboles

				for (int i = 0; i < arboles.length; i++) {
					arboles[i].moverse(); 
					arboles[i].dibujar(entorno);

					if (arboles[i].tieneSerpiente && serpientes[i]!=null) {
						serpientes[i].moverse();
						serpientes[i].dibujar(entorno);
					}

					if (arboles[i].tieneFruta && frutas[i]!=null) {
						frutas[i].moverse();
						frutas[i].dibujar(entorno);
					}
				}

				for (int i=0; i<frutas.length; i++) {
					if(frutas[i]!=null){

						if (colision9(mono,frutas[i],50)){ 
							mono.tocoFruta=true;
						}

						if (mono.tocoFruta){														
							mono.tocoFruta=false;		
							puntaje+=5;
							frutas[i]=null;
						}
					}
				}

				//mono
				if (etapa==1) {
					mono.dibujar(entorno);
					mono.mover();
					if (entorno.sePresiono(entorno.TECLA_ARRIBA)) {  //tecla arriba para saltar
						mono.saltar();
					}

					//obstaculos 

					//basura (cascara de banana)
					if(basura!=null){
						basura.moverse();
						basura.dibujar(entorno);

						if (colision5(mono,basura,50)){ //detecta la colision entre el mono y basura
							mono.tocoBasura=true;
						}

						if (mono.tocoBasura){		//si hay colision
							mono.tropezar();		//el mono da un pequeño salto
							basura.nuevaBasura();	//genera una nueva basura
							mono.tocoBasura=false;
							if(puntaje>=5) {		//resta 5 puntos
								puntaje-=5;
							}
						}
					}
					
					//depredadores
					
					//tigre
					for (int i=0;i<tigres.length;i++){  //genera tigres
						if(tigres[i] != null) {
							tigres[i].dibujar(entorno);
							tigres[i].moverse();
						}
						if(tigres[i]!=null && etapa==1 && colision2(mono, tigres[i],70)){ //detecta la colision entre el mono y el tigre, si colisionan, termina el juego.
							tigres[i]=null;
							tigres[i] = new Tigre(1000, 410);
							this.etapa+=1;
						}

						if(colision6(tigres[0], tigres[1],200) || colision6(tigres[0], tigres[2],200) ){ //para que no se superpongan, al colisionar, se le suma 500 al x del segundo tigre.
							//						System.out.println("colision de tigres");
							this.tigres[0].x+=500;
						}
						if(colision6(tigres[1], tigres[2],200)){ //para que no se superpongan, al colisionar, se le suma 500 al x del segundo tigre.
							//						System.out.println("colision de tigres");
							this.tigres[1].x+=500;
						}
					}
					colisionPiedraTigre();
					
					//serpientes
					colisionMonoSerpiente();
					colisionPiedraSerpiente();
					
					//aguila
					if (aguila!=null) {
						if (puntaje%50==0&&puntaje!=0) {
							aguila.atacar();
						}
						if (aguila.atacando) {
							aguila.dibujar(entorno);
							aguila.mover(mono, entorno);
							colisionPiedraAguila();
							colisionMonoAguila();
						}
						if (etapa!=1) {
							aguila.atacando=false;
						}
					}
					//saltar en arbol
					mantenerEnPlataforma();
					dibujarPuntaje();
					dibujarPiedra();
					nuevaFruta();

					if (etapa==1 && entorno.estaPresionada(entorno.TECLA_ABAJO)) {
						mono.bajarDePlataforma();
					}
				}
			}
		}
		
		////////////////////////////////////////ETAPA 2 = RESTART////////////////////////////////////////
		if (etapa==2) {
			opcionRestart();
			if (entorno.sePresiono(entorno.TECLA_FIN)) {
				this.etapa +=1;
			}
		}
		
		////////////////////////////////////////ETAPA 3 = CREDITOS////////////////////////////////////////
		if (etapa==3) {
			creditos();
			cerrarJuego();		
		}
	}




	////////////////////////////////////////METODOS////////////////////////////////////////

	public boolean posado(Mono m, Arbol a) {
		return m.x < a.x + 70 && m.x > a.x - 70 && m.y-20 < a.y - 40 && m.y+25 > a.y - 40;
	}

	public boolean colision(Tigre t, Piedra p, double d) {
		return (t.x - p.x) * (t.x - p.x) + (t.y - p.y) * (t.y - p.y) < d * d;
	}
	public boolean colision2(Mono m, Tigre t, double d) {
		return (m.x - t.x) * (m.x - t.x) + (m.y - t.y) * (m.y - t.y) < d * d;
	}
	public boolean colision3(Mono m, Serpiente s, double d) {
		return (m.x - s.x) * (m.x - s.x) + (m.y - s.y) * (m.y - s.y) < d * d;
	}
	public boolean colision4(Serpiente s, Piedra p, double d) {
		return (s.x - p.x) * (s.x - p.x) + (s.y - p.y) * (s.y - p.y) < d * d;
	}
	public boolean colision5(Mono m, Basura b, double d) {
		return (m.x - b.x) * (m.x - b.x) + (m.y - b.y) * (m.y - b.y) < d * d;
	}
	public boolean colision6(Tigre m, Tigre b, double d) {
		return (m.x - b.x) * (m.x - b.x) + (m.y - b.y) * (m.y - b.y) < d * d;
	}
	public boolean colision7(Mono m, Aguila a, double d) {
		return (m.x - a.x) * (m.x - a.x) + (m.y - a.y) * (m.y - a.y) < d * d;
	}
	public boolean colision8(Aguila a, Piedra t, double d) {
		return (a.x - t.x) * (a.x - t.x) + (a.y - t.y) * (a.y - t.y) < d * d;
	}
	public boolean colision9(Mono m, Fruta f, double d) {
		return (m.x - f.x) * (m.x - f.x) + (m.y - f.y) * (m.y - f.y) < d * d;
	}

	public void dibujarFondo() {
		entorno.dibujarImagen(fondo, 400, 300, 0, 0.7);
		entorno.dibujarImagen(piso, 400, 600, 0, 2);
	}

	public void inicializarJuego() {
		mono.saltando=false;
		mono.congelado=false;
		mono.subiendo=false;
		piedra.disparando=false;
		new Mono(mono.x=100, mono.y=420);
		aguila.nuevoAguila();
		aguila.velocidad=3;
		puntaje=0;
	}

	public void pausa() {
		if (!pausa&&entorno.sePresiono(entorno.TECLA_DELETE)) {  //tecla SUPR para pausar
			this.pausa=true;
		}
		if (pausa&&entorno.sePresiono(entorno.TECLA_ENTER)) {   //tecla enter para continuar
			this.pausa=false;
		}
	}

	public void dibujarMenu() {
		dibujarFondo();
		entorno.dibujarImagen(menu, 400, 295, 0, 0.5);
	}

	public void humo() {  //genera humo al impactar la piedra
		entorno.dibujarImagen(humo, piedra.x, piedra.y, 0, 0.5);
	}

	public void mantenerEnPlataforma() {
		if(mono!=null){
			sumarPuntoArbol();
			for(int i=0; i<arboles.length;i++) {
				if(posado(mono, arboles[i])&& !mono.subiendo) {
					mono.congelado=true;
					break;
				}
				else {
					mono.congelado=false;
				}
			}
		}
	}


	public void dibujarPuntaje() {
		entorno.cambiarFont("", 20, Color.black);
		entorno.escribirTexto("Puntaje:"+puntaje , 650,30);
	}

	public void sumarPuntoArbol() {		//suma 5 puntos al posar una plataforma
		if (!sumandoPuntos && mono.congelado){
			sumandoPuntos=true;
			puntaje+=5;
		}
		if (!mono.congelado) {
			sumandoPuntos=false;
		}
	}

	public void dibujarPiedra() {
		if (mono!=null && entorno.sePresiono(entorno.TECLA_ESPACIO) &&!piedra.disparando) { //dispara al apretar la tecla espacio si es que no esta disparando
			piedra = new Piedra(this.mono.x+30 , this.mono.y+20);
			piedra.disparar();
			piedra.disparando=true;


		}
		if (piedra != null){							//si la piedra sale de la pantalla es null
			if(piedra.seFueDePantalla) {
				piedra.disparando=false;
				piedra = null;

			}
			else{
				if(piedra.disparando) {					//si esta disparando, la piedra se mueve y se dibuja 
					piedra.mover();
					piedra.dibujarse(entorno);
				}
			}
		}
		if (piedra==null) {
			piedra = new Piedra(this.mono.x+30 , this.mono.y+20);
		}
	}

	public void colisionPiedraTigre(){
		for (int i=0;i<tigres.length;i++){			//si una piedra colisiona con un tigre, este se vuelve  null y se crea uno nuevo
			if (piedra!=null && tigres[i] != null){
				if(piedra.disparando && colision(tigres[i], piedra,50)){
					tigres[i]=null;
					piedra.disparando=false;
					humo();
					piedra=null; 
				}
			}
			if(tigres[i]==null) {
				tigres[i] = new Tigre(1200, 410);
			}
		}
	}

	public void colisionMonoSerpiente() {
		for (int i=0; i<serpientes.length;i++) {
			if(serpientes[i]!=null && etapa==1 && colision3(mono,serpientes[i],40)) { ////detecta la colision entre el mono y la serpiente, si colisionan, termina el juego.
				serpientes[i]=null;
				//				System.out.println("muere");
				this.etapa+=1;
			}
		}
	}

	public void colisionPiedraAguila() {   //detecta la colision entre la piedra y el aguila, si colisionan,suma 15 puntos
		if (piedra!=null&&aguila !=null){
			if(colision8(aguila,piedra,30)) {
				piedra.disparando=false;
				humo();
				puntaje+=15;
				aguila.velocidad++;  //cada vez que el aguila muere, el proximo es mas rapido
				aguila.atacando=false;
				piedra=null;
				aguila.nuevoAguila();
			}
		}
	}

	public void colisionMonoAguila() { //detecta la colision entre el mono y el aguila, si colisionan,termina el juego.
		if (aguila!=null) {
			if(colision7(mono,aguila,50)) {
				aguila.atacando=false;
				this.etapa+=1;
				aguila.nuevoAguila();
			}
		}
	}

	public void colisionPiedraSerpiente() {
		for (int i=0;i<serpientes.length;i++) {
			if(piedra!=null && serpientes[i]!=null) {
				if(piedra.disparando && colision4(serpientes[i],piedra,30)) {
					serpientes[i]=null;
					piedra.disparando=false;
					humo();
					piedra=null;
				}
			}
		}
		int cont=0;
		for (int i=0;i<serpientes.length;i++) {
			if(arboles[i].tieneSerpiente && serpientes[i]!=null) {
				cont+=1;


			}
			if(cont<2 && arboles[i].tieneSerpiente && serpientes[i]==null && arboles[i].x<-130) {
				serpientes[i]=new Serpiente(arboles[i].x, arboles[i].y-50);
			}
		}

	}
	public void nuevaFruta() {
		int cont=0;
		for(int i=0;i<frutas.length;i++) {
			if(arboles[i].tieneFruta && frutas[i]!=null) {
				cont+=1;


			}
			if(cont<2 && arboles[i].tieneFruta && frutas[i]==null && arboles[i].x<-130) {
				frutas[i]=new Fruta(arboles[i].x+29,arboles[i].y-70);
			}
		}
	}
	public void dibujarAguila() {
		aguila.dibujar(entorno);
		aguila.mover(mono, entorno);
	}

	public void opcionRestart(){
		dibujarFondo();
		entorno.dibujarImagen(restart, 400, 390, 0, 0.5);
		entorno.cambiarFont("", 30, Color.black);
		entorno.escribirTexto("puntaje :"+puntaje, 300,100);

		if(entorno.sePresiono(entorno.TECLA_ENTER)){
			this.etapa=0;
		}
	}

	public void creditos() { //muestra los creditos antes de cerrar el juego
		dibujarFondo();
		if (creditos>60) {
			entorno.dibujarImagen(creditos1, 400, 100, 0, 0.3);
		}
		if (creditos>120) {
			entorno.dibujarImagen(creditos2, 400, 300, 0, 0.3);
		}
		if (creditos>180) {
			entorno.dibujarImagen(creditos3, 400, 350, 0, 0.3);
		}
		this.creditos++;
	}
	public void cerrarJuego() {
		if (creditos==300) { //tiempo para que se cierre el juego (60 creditos == 1 segundo)
			System.exit(0);
		}	
	}



	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
