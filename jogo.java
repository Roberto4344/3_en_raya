import java.util.Scanner;
import java.util.Random;

public class jogo {
	static Random r = new Random();
	static String jugador1;
	static String jugador2;
	static int num1;
	static int num2;

	// Muestra lo que contienen las celdas.
	public static final int VACIO = 0;
	public static final int CRUZ = 1;
	public static final int CIRCULO = 2;

	// Nombre de las constantes para el juego
	public static final int JUGANDO = 0;
	public static final int EMPATE = 1;
	public static final int X_GANA = 2;
	public static final int O_GANA = 3;

	// El estado del tablero
	public static final int FILAS = 3, COLUMNAS = 3; // Numero de filas y columnas.
	public static int[][] tablero = new int[FILAS][COLUMNAS]; // El tablero.
	public static int estadoActual; // El estado actual de la partida.
	public static int juagadorActual; // El jugador que es el turno.
	public static int filaActual, columnaActual; // El estado actual .
	public static Scanner sc = new Scanner(System.in); //Aqui creamos el scanner.

	/** El programa se inicia aqui */
	public static void main(String[] args) {
		String nombre1;
		String nombre2;
		// Iniciamos el juego.
		iniciarJuego();
		System.out.println("Introduce el nombre jugador 1");
		nombre1 = sc.nextLine();
		System.out.println("Introduce el nombre del jugador 2");
		nombre2 = sc.nextLine();
		System.out.println("Ahora se decidira quien va primero con un dado,el que saque el numero mas alto empieza.");
		System.out.println("En el caso de ser iguales, empieza el primero que tira el dado.");
		num1= r.nextInt(6) + 1;
		System.out.println(nombre1+" ha sacado un: "+num1);
		num2= r.nextInt(6) + 1;
		System.out.println(nombre2+" ha sacado un: "+num2);
		if(num1<num2) {
			jugador1=nombre2;
			jugador2=nombre1;
		}else {
			jugador1=nombre1;
			jugador2=nombre2;
		}
		System.out.println("Muy bien, empieza: "+jugador1);
		do {
			movJugador(juagadorActual); // Actualiza la fila y la columna.
			acTablero(juagadorActual, filaActual, columnaActual); // Actualizar el estado de la partida.
			mostablero();
			// Lo que se imprime al acabar la partida.
			if (estadoActual == X_GANA) {
				System.out.println(jugador1 + " gana!");
			} else if (estadoActual == O_GANA) {
				System.out.println(jugador2 + " gana");
			} else if (estadoActual == EMPATE) {
				System.out.println("¡¡Vaya!!, ha sido Empate");
			}
			// Cambiar jugador.
			juagadorActual = (juagadorActual == CRUZ) ? CIRCULO : CRUZ;
		} while (estadoActual == JUGANDO); // Repetir si el juego no se ha acabado.
	}

	/** Aqui es el inicio del juego */
	public static void iniciarJuego() {
		for (int fila = 0; fila < FILAS; ++fila) {
			for (int columna = 0; columna < COLUMNAS; ++columna) {
				tablero[fila][columna] = VACIO; // Todas las celdas vacias
			}
		}
		estadoActual = JUGANDO; // Listo para jugar.
		juagadorActual = CRUZ; // Las x siempre empiezan primero.
	}

	/**Indica de que jugador es el turno y le sale el mensaje.*/
	public static void movJugador(int estado) {
		boolean introduccionValida = false; 
		do {
			if (estado == CRUZ) {
				System.out.print(
						jugador1 + ", introduce la fila y la columna ( 1 al 3) pulsa ENTER depues de cada numero:");
			} else {
				System.out.print(
						jugador2 + ", introduce la fila y la columna ( 1 al 3) pulsa ENTER depues de cada numero:");
			}
			int fila = sc.nextInt() - 1;
			int columna = sc.nextInt() - 1;
			if (fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS && tablero[fila][columna] == VACIO) {
				filaActual = fila;
				columnaActual = columna;
				tablero[filaActual][columnaActual] = estado; // Actualizar el tablero.
				introduccionValida = true; // Salir del bucle.
			} else {
				System.out.println("No puedes (" + (fila + 1) + "," + (columna + 1) + "),esta ocupado.Intentalo con otra");
			}
		} while (!introduccionValida); // Repetir si el dato es invalido.
	}

	/**Actualiza el tablero para ver si alguien ha ganado.*/
	public static void acTablero(int estado, int filaActual, int columnaActual) {
		if (haGanado(estado, filaActual, columnaActual)) { // Comprueba si hay coincidencia con ganador.
			estadoActual = (estado == X_GANA) ? X_GANA : O_GANA;
		} else if (esEmpate()) { // Comprueba si hay empate.
			estadoActual = EMPATE;
		}
	}

	/** Devulve empate cuando no haya mas celdas vacias */
	public static boolean esEmpate() {
		for (int fila = 0; fila < FILAS; ++fila) {
			for (int columna = 0; columna < COLUMNAS; ++columna) {
				if (tablero[fila][columna] == VACIO) {
					return false; // Cuando una celda este vacia.
				}
			}
		}
		return true; // Cuando es empate.
	}

	/**Devuelve true cuando uno de los jugadores gana */
	public static boolean haGanado(int estado, int filaActual, int columnaActual) {
		return (tablero[filaActual][0] == estado // 3 en fila
				&& tablero[filaActual][1] == estado && tablero[filaActual][2] == estado
				|| tablero[0][columnaActual] == estado // 3 en columna
						&& tablero[1][columnaActual] == estado && tablero[2][columnaActual] == estado
				|| filaActual == columnaActual // 3 en diaogonal 1
						&& tablero[0][0] == estado && tablero[1][1] == estado && tablero[2][2] == estado
				|| filaActual + columnaActual == 2 // 3 en la otra diagonal 2
						&& tablero[0][2] == estado && tablero[1][1] == estado && tablero[2][0] == estado);
	}

	/** Se imprime el tablero */
	public static void mostablero() {
		for (int fila = 0; fila < FILAS; ++fila) {
			for (int columna = 0; columna < COLUMNAS; ++columna) {
				printCell(tablero[fila][columna]);
				if (columna != COLUMNAS - 1) {
					System.out.print("|");
				}
			}
			System.out.println();
			if (fila != FILAS - 1) {
				System.out.println("-----------");
			}
		}
		System.out.println();
	}

	/** Lo que se encuentra dentro de cada celda */
	public static void printCell(int contenido) {
		switch (contenido) {
		case VACIO:
			System.out.print("   ");
			break;
		case CIRCULO:
			System.out.print(" O ");
			break;
		case CRUZ:
			System.out.print(" X ");
			break;
		}
	}
}