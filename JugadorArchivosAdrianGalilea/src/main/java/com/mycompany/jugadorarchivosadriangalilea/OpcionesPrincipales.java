package com.mycompany.jugadorarchivosadriangalilea;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que contiene las opciones principales del menú para la gestión de jugadores.
 * Proporciona funcionalidades para dar de alta, baja, modificar, listar jugadores
 * y configurar la gestión de archivos.
 */
public class OpcionesPrincipales {

    /**
     * Objeto que maneja la gestión de archivos para la manipulación de datos de jugadores.
     */
    private static GestionFichero gestionFichero;

    /**
     * Scanner para la lectura de datos desde la entrada estándar.
     */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Muestra el menú principal de opciones al usuario.
     */
    public static void mostrarMenuPrincipal() {
        System.out.println("Menú Principal");
        System.out.println("1. Alta de jugadores");
        System.out.println("2. Baja de jugadores");
        System.out.println("3. Modificación de jugadores");
        System.out.println("4. Listado por código (ID)");
        System.out.println("5. Listado general");
        System.out.println("6. Configuración");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Permite agregar un nuevo jugador al sistema. Solicita la información del jugador al usuario.
     *
     * @throws IOException si ocurre un error durante la operación de escritura en el archivo.
     */
    public static void altaJugador() throws IOException {
        int id, experiencia, nivelVida, monedas;
        String nick;

        boolean b = false;
        int comprobacion=0;

        do {
            System.out.println("Ingrese ID del jugador:");
            try {
                comprobacion = Integer.parseInt(scanner.nextLine());
                b=false;

            } catch (Exception e) {
                b = true;

            }

        } while (b);
        id = comprobacion;
        b=true;

        System.out.print("Ingrese Nick: ");
        nick = scanner.nextLine();

        do {
            System.out.println("Ingrese experiencia: ");
            try {
                comprobacion = Integer.parseInt(scanner.nextLine());
                b=false;

            } catch (Exception e) {
                b = true;

            }

        } while (b);
        experiencia =comprobacion;
        b=true;

        do {
            System.out.println("Ingrese nivel de vida: ");
            try {
                comprobacion = Integer.parseInt(scanner.nextLine());
                b=false;

            } catch (Exception e) {
                b = true;

            }

        } while (b);
        nivelVida = comprobacion;
        b=true;

        do {
            System.out.println("Ingrese monedas: ");
            try {
                comprobacion = Integer.parseInt(scanner.nextLine());
                b=false;

            } catch (Exception e) {
                b = true;

            }

        } while (b);
        monedas = comprobacion;

        Jugador nuevoJugador = new Jugador(id, nick, experiencia, nivelVida, monedas);

        try {
            boolean resultado = gestionFichero.agregarJugador(nuevoJugador);
            if (resultado) {
                System.out.println("Jugador agregado con éxito.");
            } else {
                System.out.println("Jugador ya existe.");
            }
        } catch (IOException e) {
            System.out.println("Error al agregar jugador: " + e.getMessage());
        }
    }

    /**
     * Permite eliminar un jugador del sistema en base a su ID.
     *
     * @throws IOException si ocurre un error durante la operación de eliminación en el archivo.
     */
    public static void bajaJugador() throws IOException {
        boolean b = false;
        int comprobacion=0,id;

        do {
            System.out.println("Ingrese ID del jugador:");
            try {
                comprobacion = Integer.parseInt(scanner.nextLine());
                b=false;
            } catch (Exception e) {
                b = true;
            }
        } while (b);
        id = comprobacion;

        try {
            boolean resultado = gestionFichero.eliminarJugador(id);
            if (resultado) {
                System.out.println("Jugador eliminado con éxito.");
            } else {
                System.out.println("Jugador no existe.");
            }
        } catch (IOException e) {
            System.out.println("Error al eliminar jugador: " + e.getMessage());
        }
    }

    /**
     * Permite modificar los datos de un jugador existente.
     */
    public static void modificarJugador() {
        int id, experiencia, nivelVida, monedas;
        String nick;

        boolean b = false;
        int comprobacion=0;

        do {
            System.out.println("Ingrese ID del jugador:");
            try {
                comprobacion = Integer.parseInt(scanner.nextLine());
                b=false;

            } catch (Exception e) {
                b = true;

            }

        } while (b);
        id = comprobacion;
        b=true;

        System.out.print("Ingrese Nick: ");
        nick = scanner.nextLine();

        do {
            System.out.println("Ingrese experiencia: ");
            try {
                comprobacion = Integer.parseInt(scanner.nextLine());
                b=false;

            } catch (Exception e) {
                b = true;

            }

        } while (b);
        experiencia =comprobacion;
        b=true;

        do {
            System.out.println("Ingrese nivel de vida: ");
            try {
                comprobacion = Integer.parseInt(scanner.nextLine());
                b=false;

            } catch (Exception e) {
                b = true;

            }

        } while (b);
        nivelVida = comprobacion;
        b=true;

        do {
            System.out.println("Ingrese monedas: ");
            try {
                comprobacion = Integer.parseInt(scanner.nextLine());
                b=false;
            } catch (Exception e) {
                b = true;
            }
        } while (b);
        monedas = comprobacion;

        Jugador jugadorModificado = new Jugador(id, nick, experiencia, nivelVida, monedas);

        try {
            boolean resultado = true;
            gestionFichero.modificarJugador(jugadorModificado.getId(), jugadorModificado);

            if (resultado) {
                System.out.println("Jugador modificado con éxito.");
            } else {
                System.out.println("Jugador no existe.");
            }
        } catch (IOException e) {
            System.out.println("Error al modificar jugador: " + e.getMessage());
        }
    }

    /**
     * Lista la información de un jugador específico con base en su ID.
     */
    public static void listarJugadorPorID() {
        boolean b = false;
        int comprobacion=0,id;

        do {
            System.out.println("Ingrese ID del jugador a mostrar:");
            try {
                comprobacion = Integer.parseInt(scanner.nextLine());
                b=false;
            } catch (Exception e) {
                b = true;
            }
        } while (b);
        id = comprobacion;

        try {
            Jugador j = gestionFichero.obtenerJugador(id);
            System.out.println(j.toString());
            System.out.println("Jugador listado con éxito.");
        } catch (IOException e) {
            System.out.println("Error al listar jugador: " + e.getMessage());
        }
    }

    /**
     * Lista la información de todos los jugadores almacenados en el sistema.
     */
    public static void listarTodosLosJugadores() {
        try {
            List<Jugador> jugadores = gestionFichero.listarJugadores();
            for (Jugador j : jugadores) {
                System.out.println(j.toString());
            }
            System.out.println("Jugadores listados con éxito.");
        } catch (IOException e) {
            System.out.println("Error al listar jugadores: " + e.getMessage());
        }
    }

    /**
     * Configura la forma de gestionar el archivo para almacenar los datos de los jugadores.
     *
     * @return Devuelve una instancia de la clase GestionFichero según la selección del usuario.
     * @throws IOException si ocurre un error durante la operación de configuración del archivo.
     */
    public static GestionFichero configuracion() throws IOException {
        boolean b = false;
        int comprobacion=0;
        do{
            do {
                System.out.println("Ingresa la manera que quieres escribir a los jugadores: 1 Texto 2 Binario 3 Objeto 4 Aleatorio 5 XML");
                try {
                    comprobacion = Integer.parseInt(scanner.nextLine());
                    b=false;

                } catch (Exception e) {
                    b = true;

                }

            } while (b);
            int escribir = comprobacion;

            System.out.println("Escribe la ruta del archivo a modificar o crear. Si no está creado pon el nombre del archivo + su extensión al final de la ruta");
            String ruta = scanner.nextLine();

            switch (escribir) {
                case 1:
                    return gestionFichero = new GestionTexto(ruta);
                case 2:
                    return gestionFichero = new GestionBinario(ruta);
                case 3:
                    return gestionFichero = new GestionObjetos(ruta);
                case 4:
                    return gestionFichero = new GestionAccesoAleatorio(ruta);
                case 5:
                    return gestionFichero = new GestionXML(ruta);
                default:
                    System.out.println("Opción no válida.");

            }
        }while (true);
    }

    /**
     * Cierra el programa y libera los recursos utilizados por la gestión de archivos.
     */
    public static void salir() {
        try {
            if (gestionFichero != null) {
                gestionFichero.cerrar();
            }
            System.out.println("Saliendo del programa...");
        } catch (IOException e) {
            System.out.println("Error al cerrar el archivo: " + e.getMessage());
        }
    }
}
