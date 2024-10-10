package com.mycompany.jugadorarchivosadriangalilea;

import com.mycompany.jugadorarchivosadriangalilea.Jugador;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase GestionBinario extiende de la clase abstracta GestionFichero y proporciona una implementación
 * para gestionar jugadores utilizando un archivo binario. Esta clase permite realizar operaciones como agregar,
 * eliminar, modificar, obtener y listar jugadores almacenados en un archivo utilizando la clase DataOutputStream
 * y DataInputStream para la lectura y escritura de datos binarios.
 */
public class GestionBinario extends GestionFichero {

    private File archivo;
    private DataOutputStream dataOutput;
    private DataInputStream dataInput;

    /**
     * Constructor de la clase GestionBinario.
     * Inicializa el archivo binario especificado y lo crea si no existe.
     *
     * @param nombreArchivo Nombre del archivo binario donde se almacenarán los datos de los jugadores.
     * @throws IOException Si ocurre un error al crear o acceder al archivo.
     */
    public GestionBinario(String nombreArchivo) throws IOException {
        archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
    }

    /**
     * Agrega un nuevo jugador al archivo binario.
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
        dataOutput = new DataOutputStream(new FileOutputStream(archivo, true));
        escribirJugador(dataOutput, jugador);
        dataOutput.close();
        return true;
    }

    /**
     * Elimina un jugador del archivo binario según su ID.
     * Elimina al jugador con el ID especificado de la lista y reescribe el archivo con los jugadores restantes.
     *
     * @param id ID del jugador que se desea eliminar.
     * @return true si el jugador fue eliminado correctamente, false si no se encontró el jugador con el ID especificado.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    @Override
    public Boolean eliminarJugador(int id) throws IOException {
        List<Jugador> jugadores = listarJugadores();
        int noExiste = 0;
        dataOutput = new DataOutputStream(new FileOutputStream(archivo));
        for (Jugador j : jugadores) {
            if (j.getId() != id) {
                escribirJugador(dataOutput, j);
            } else {
                noExiste++;
                break;
            }
        }
        dataOutput.close();
        return noExiste != 0;
    }

    /**
     * Modifica la información de un jugador en el archivo binario según su ID.
     * Reemplaza al jugador con el ID especificado con los nuevos datos del jugador modificado.
     *
     * @param id ID del jugador a modificar.
     * @param jugadorModificado Objeto de la clase Jugador con los nuevos datos del jugador.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    @Override
    public void modificarJugador(int id, Jugador jugadorModificado) throws IOException {
        List<Jugador> jugadores = listarJugadores();
        dataOutput = new DataOutputStream(new FileOutputStream(archivo));
        for (Jugador j : jugadores) {
            if (j.getId() == id) {
                escribirJugador(dataOutput, jugadorModificado);
            } else {
                escribirJugador(dataOutput, j);
            }
        }
        dataOutput.close();
    }

    /**
     * Obtiene la información de un jugador según su ID.
     * Busca en el archivo binario y devuelve el jugador con el ID especificado.
     *
     * @param id ID del jugador que se desea obtener.
     * @return Un objeto Jugador con la información del jugador encontrado, o null si no se encontró.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Override
    public Jugador obtenerJugador(int id) throws IOException {
        dataInput = new DataInputStream(new FileInputStream(archivo));
        Jugador jugador;
        try {
            while (true) {
                jugador = leerJugador(dataInput);
                if (jugador.getId() == id) {
                    dataInput.close();
                    return jugador;
                }
            }
        } catch (EOFException e) {
            dataInput.close();
        }
        return null;
    }

    /**
     * Lista todos los jugadores almacenados en el archivo binario.
     * Si el archivo está vacío, retorna una lista vacía.
     *
     * @return Una lista de objetos Jugador con todos los jugadores almacenados.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Override
    public List<Jugador> listarJugadores() throws IOException {
        List<Jugador> jugadores = new ArrayList<>();
        dataInput = new DataInputStream(new FileInputStream(archivo));
        try {
            while (true) {
                Jugador jugador = leerJugador(dataInput);
                jugadores.add(jugador);
            }
        } catch (EOFException e) {
            dataInput.close();
        }
        return jugadores;
    }

    /**
     * Cierra los recursos asociados a la gestión de archivos.
     * Cierra los streams de entrada y salida de datos si están abiertos.
     *
     * @throws IOException Si ocurre un error al cerrar los recursos.
     */
    @Override
    public void cerrar() throws IOException {
        if (dataOutput != null) dataOutput.close();
        if (dataInput != null) dataInput.close();
    }

    /**
     * Escribe un jugador en el archivo binario utilizando DataOutputStream.
     * Escribe cada atributo del jugador de forma secuencial en el archivo.
     *
     * @param dataOutput El stream de salida.
     * @param jugador    El jugador a escribir.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    private void escribirJugador(DataOutputStream dataOutput, Jugador jugador) throws IOException {
        dataOutput.writeInt(jugador.getId());
        dataOutput.writeUTF(jugador.getNick());
        dataOutput.writeInt(jugador.getExperience());
        dataOutput.writeInt(jugador.getLifeLevel());
        dataOutput.writeInt(jugador.getCoins());
    }

    /**
     * Lee un jugador del archivo binario utilizando DataInputStream.
     * Lee cada atributo del jugador de forma secuencial en el archivo y lo almacena en un nuevo objeto Jugador.
     *
     * @param dataInput El stream de entrada.
     * @return El jugador leído del archivo binario.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    private Jugador leerJugador(DataInputStream dataInput) throws IOException {
        int id = dataInput.readInt();
        String nick = dataInput.readUTF();
        int experience = dataInput.readInt();
        int lifeLevel = dataInput.readInt();
        int coins = dataInput.readInt();
        return new Jugador(id, nick, experience, lifeLevel, coins);
    }
}
