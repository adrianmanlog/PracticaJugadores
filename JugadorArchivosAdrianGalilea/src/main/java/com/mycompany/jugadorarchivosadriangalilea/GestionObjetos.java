package com.mycompany.jugadorarchivosadriangalilea;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase GestionObjetos extiende de la clase abstracta GestionFichero y proporciona una implementación
 * para gestionar jugadores utilizando archivos de objetos serializables.
 * Esta clase permite realizar operaciones como agregar, eliminar, modificar, obtener y listar jugadores
 * almacenados en un archivo binario, utilizando la serialización de Java para manejar los datos.
 */
public class GestionObjetos extends GestionFichero {

    private File archivo;

    /**
     * Constructor de la clase GestionObjetos.
     * Inicializa el archivo binario especificado y lo crea si no existe.
     *
     * @param nombreArchivo Nombre del archivo binario donde se almacenarán los datos de los jugadores.
     * @throws IOException Si ocurre un error al crear o acceder al archivo.
     */
    public GestionObjetos(String nombreArchivo) throws IOException {
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
        jugadores.add(jugador);
        guardarJugadores(jugadores);
        return true;
    }

    /**
     * Elimina un jugador del archivo binario según su ID.
     * Elimina al jugador con el ID especificado de la lista y la guarda nuevamente en el archivo.
     *
     * @param id ID del jugador que se desea eliminar.
     * @return true si el jugador fue eliminado correctamente, false si no se encontró el jugador con el ID especificado.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    @Override
    public Boolean eliminarJugador(int id) throws IOException {
        List<Jugador> jugadores = listarJugadores();
        int noExiste = 0;

        for (Jugador j : jugadores) {
            if (j.getId() == id) {
                noExiste++;
                break;
            }
        }
        jugadores.removeIf(j -> j.getId() == id);
        guardarJugadores(jugadores);
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
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getId() == id) {
                jugadores.set(i, jugadorModificado);
                break;
            }
        }
        guardarJugadores(jugadores);
    }

    /**
     * Obtiene la información de un jugador según su ID.
     * Busca en la lista de jugadores almacenada en el archivo y devuelve el jugador con el ID especificado.
     *
     * @param id ID del jugador que se desea obtener.
     * @return Un objeto Jugador con la información del jugador encontrado, o null si no se encontró.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Override
    public Jugador obtenerJugador(int id) throws IOException {
        List<Jugador> jugadores = listarJugadores();
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == id) {
                return jugador;
            }
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
        if (archivo.length() == 0) {
            return jugadores;
        }

        ObjectInputStream objectInput = null;
        try {
            objectInput = new ObjectInputStream(new FileInputStream(archivo));
            jugadores = (List<Jugador>) objectInput.readObject();
        } catch (EOFException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectInput != null) {
                objectInput.close();
            }
        }
        return jugadores;
    }

    /**
     * Cierra los recursos asociados a la gestión de archivos.
     * En esta clase no es necesario cerrar recursos específicamente ya que se usan streams locales en cada método.
     *
     * @throws IOException Si ocurre un error al cerrar los recursos.
     */
    @Override
    public void cerrar() throws IOException {

    }

    /**
     * Guarda la lista de jugadores en el archivo usando ObjectOutputStream.
     * Sobrescribe el contenido del archivo con la lista completa de jugadores.
     *
     * @param jugadores La lista de jugadores a guardar.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    private void guardarJugadores(List<Jugador> jugadores) throws IOException {
        ObjectOutputStream objectOutput = null;
        try {
            objectOutput = new ObjectOutputStream(new FileOutputStream(archivo));
            objectOutput.writeObject(jugadores);
        } finally {
            if (objectOutput != null) {
                objectOutput.close();
            }
        }
    }
}

