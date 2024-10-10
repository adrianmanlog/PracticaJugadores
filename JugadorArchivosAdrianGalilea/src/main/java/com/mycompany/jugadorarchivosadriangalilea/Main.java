package com.mycompany.jugadorarchivosadriangalilea;

import com.mycompany.jugadorarchivosadriangalilea.GestionFichero;
import com.mycompany.jugadorarchivosadriangalilea.Jugador;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static OpcionesPrincipales opcionesPrincipales=new OpcionesPrincipales();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int opcion,comprobacion=0;
        boolean b = false;
        opcionesPrincipales.configuracion();
        do {
            do {
                try {
                    opcionesPrincipales.mostrarMenuPrincipal();
                    comprobacion = Integer.parseInt(scanner.nextLine());
                    b=false;

                } catch (Exception e) {
                    b = true;

                }
            } while (b);
            opcion = comprobacion;

            switch (opcion) {
                case 1:
                    opcionesPrincipales.altaJugador();
                    break;
                case 2:
                    opcionesPrincipales.bajaJugador();
                    break;
                case 3:
                    opcionesPrincipales.modificarJugador();
                    break;
                case 4:
                    opcionesPrincipales.listarJugadorPorID();
                    break;
                case 5:
                    opcionesPrincipales.listarTodosLosJugadores();
                    break;
                case 6:
                    opcionesPrincipales.configuracion();
                    break;
                case 7:
                    opcionesPrincipales.salir();
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 7);
    }
}