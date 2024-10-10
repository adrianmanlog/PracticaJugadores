package com.mycompany.jugadorarchivosadriangalilea;

import com.mycompany.jugadorarchivosadriangalilea.GestionFichero;
import com.mycompany.jugadorarchivosadriangalilea.Jugador;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase GestionTexto extiende de la clase abstracta GestionFichero y proporciona una implementación
 * para gestionar jugadores utilizando archivos de texto plano.
 * Permite realizar operaciones como agregar, eliminar, modificar, obtener y listar jugadores
 * almacenados en un archivo de texto.
 * Cada línea del archivo representa un jugador y se almacena en el indicado en la practica
 */
public class GestionTexto extends GestionFichero {

    private BufferedWriter writer;
    private BufferedReader reader;
    private File archivo;

    /**
     * Constructor de la clase GestionTexto.
     * Inicializa el archivo de texto especificado si no existe.
     *
     * @param nombreArchivo Nombre del archivo donde se almacenarán los datos de los jugadores.
     * @throws IOException Si ocurre un error al crear o acceder al archivo.
     */
    public GestionTexto(String nombreArchivo) throws IOException {
        archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
    }

    /**
     * Agrega un nuevo jugador al archivo de texto.
     * Verifica que no exista un jugador con el mismo ID antes de agregarlo.
     *
     * @param jugador Objeto de la clase Jugador que representa al jugador a agregar.
     * @return true si el jugador se agrega correctamente, false si ya existe un jugador con el mismo ID.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    @Override
    public Boolean agregarJugador(Jugador jugador) throws IOException {
        List<Jugador> jugadores = listarJugadores();
        for (Jugador j : jugadores) {
            if (j.getId() == jugador.getId()) {
                return false;
            }
        }
        writer = new BufferedWriter(new FileWriter(archivo, true));
        writer.write(jugador.toString());
        writer.newLine();
        writer.close();
        return true;
    }

    /**
     * Elimina un jugador del archivo de texto según su ID.
     * Elimina la línea correspondiente al jugador con el ID especificado.
     *
     * @param id ID del jugador que se desea eliminar.
     * @return true si el jugador fue eliminado correctamente, false si no se encontró el jugador con el ID especificado.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    @Override
    public Boolean eliminarJugador(int id) throws IOException {
        List<Jugador> jugadores = listarJugadores();
        int noExiste = 0;
        writer = new BufferedWriter(new FileWriter(archivo, false));
        for (Jugador j : jugadores) {
            if (j.getId() != id) {
                writer.write(j.toString());
                writer.newLine();
            } else {
                noExiste++;
            }
        }
        writer.close();
        return noExiste != 0;
    }

    /**
     * Modifica la información de un jugador en el archivo de texto según su ID.
     * Reemplaza la línea correspondiente al jugador con la nueva información.
     *
     * @param id ID del jugador a modificar.
     * @param jugadorModificado Objeto de la clase Jugador con los nuevos datos del jugador.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    @Override
    public void modificarJugador(int id, Jugador jugadorModificado) throws IOException {
        List<Jugador> jugadores = listarJugadores();
        writer = new BufferedWriter(new FileWriter(archivo, false));
        for (Jugador j : jugadores) {
            if (j.getId() == id) {
                writer.write(jugadorModificado.toString());
            } else {
                writer.write(j.toString());
            }
            writer.newLine();
        }
        writer.close();
    }

    /**
     * Obtiene la información de un jugador según su ID.
     * Busca en el archivo de texto la línea correspondiente al jugador con el ID especificado.
     *
     * @param id ID del jugador que se desea obtener.
     * @return Un objeto Jugador con la información del jugador encontrado, o null`si no se encontró.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Override
    public Jugador obtenerJugador(int id) throws IOException {
        reader = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = reader.readLine()) != null) {
            Jugador jugador = parsearJugador(linea);
            if (jugador.getId() == id) {
                reader.close();
                return jugador;
            }
        }
        reader.close();
        return null;
    }

    /**
     * Lista todos los jugadores almacenados en el archivo de texto.
     * Lee el archivo línea por línea y crea una lista de objetos Jugador.
     *
     * @return Una lista de objetos Jugador con todos los jugadores almacenados.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Override
    public List<Jugador> listarJugadores() throws IOException {
        List<Jugador> jugadores = new ArrayList<>();
        reader = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = reader.readLine()) != null) {
            jugadores.add(parsearJugador(linea));
        }
        reader.close();
        return jugadores;
    }

    /**
     * Cierra las conexiones abiertas con el archivo de texto.
     * Libera los recursos asociados con BufferedWriter y BufferedReader.
     *
     * @throws IOException Si ocurre un error al cerrar el archivo.
     */
    @Override
    public void cerrar() throws IOException {
        if (writer != null) {
            writer.close();
        }
        if (reader != null) {
            reader.close();
        }
    }

    /**
     * Convierte una línea del archivo de texto en un objeto Jugador.
     * El formato de la línea debe coincidir con el formato de toString() de la clase Jugador.
     *
     * @param linea Línea de texto que representa a un jugador.
     * @return Un objeto Jugador con los datos extraídos de la línea.
     */
    private Jugador parsearJugador(String linea) {
        linea = linea.replace("[", "").replace("]", "");
        String[] datos = linea.split(", ");
        int id = Integer.parseInt(datos[0].split(" = ")[1]);
        String nick = datos[1].split(" = ")[1];
        int experiencia = Integer.parseInt(datos[2].split(" = ")[1]);
        int nivelVida = Integer.parseInt(datos[3].split(" = ")[1]);
        int monedas = Integer.parseInt(datos[4].split(" = ")[1]);
        return new Jugador(id, nick, experiencia, nivelVida, monedas);
    }
}
