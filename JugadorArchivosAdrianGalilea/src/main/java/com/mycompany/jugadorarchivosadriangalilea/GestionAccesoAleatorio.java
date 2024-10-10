package com.mycompany.jugadorarchivosadriangalilea;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

    /**
     * La clase GestionAccesoAleatorio extiende de la clase abstracta GestionFichero y proporciona una implementación
     * para gestionar jugadores utilizando un archivo de acceso aleatorio. Esta clase permite realizar operaciones como
     * agregar, eliminar, modificar, obtener y listar jugadores almacenados en un archivo utilizando la clase RandomAccessFile.
     * Cada jugador se almacena con un tamaño de registro fijo para permitir el acceso aleatorio por posición.
     */
    public class GestionAccesoAleatorio extends GestionFichero {

        private static final int TAMAÑO_REGISTRO = 56;
        private static final int TAMAÑO_NICK = 20;
        private RandomAccessFile archivo;

        /**
         * Constructor de la clase GestionAccesoAleatorio.
         * Inicializa el archivo de acceso aleatorio para lectura y escritura.
         *
         * @param nombreArchivo Nombre del archivo donde se almacenarán los datos de los jugadores.
         * @throws IOException Si ocurre un error al acceder o crear el archivo.
         */
        public GestionAccesoAleatorio(String nombreArchivo) throws IOException {
            archivo = new RandomAccessFile(nombreArchivo, "rw");
        }

        /**
         * Agrega un nuevo jugador al archivo de acceso aleatorio.
         * Verifica que no exista un jugador con el mismo ID antes de agregarlo.
         *
         * @param jugador Objeto de la clase Jugador que representa al jugador a agregar.
         * @return true si el jugador se agrega correctamente, false si ya existe un jugador con el mismo ID.
         * @throws IOException Si ocurre un error al escribir en el archivo.
         */
        @Override
        public Boolean agregarJugador(Jugador jugador) throws IOException {
            archivo.seek(0);
            while (archivo.getFilePointer() < archivo.length()) {
                int idExistente = archivo.readInt();

                if (idExistente == jugador.getId()) {
                    return false;
                }

                archivo.skipBytes(4 * Integer.BYTES);
            }

            archivo.seek(archivo.length());
            escribirJugador(jugador);
            return true;
        }

        /**
         * Elimina un jugador del archivo de acceso aleatorio según su ID.
         * Marca el jugador como eliminado escribiendo un ID negativo.
         *
         * @param id ID del jugador que se desea eliminar.
         * @return true si el jugador fue eliminado correctamente, false si no se encontró el jugador con el ID especificado.
         * @throws IOException Si ocurre un error al modificar el archivo.
         */
        @Override
        public Boolean eliminarJugador(int id) throws IOException {
            long pos = buscarPosicionJugador(id);

            if (pos != -1) {
                archivo.seek(pos);
                archivo.writeInt(-1);
                return true;
            }

            return false;
        }

        /**
         * Modifica la información de un jugador en el archivo de acceso aleatorio según su ID.
         * Reemplaza al jugador con el ID especificado con los nuevos datos del jugador modificado.
         *
         * @param id ID del jugador a modificar.
         * @param jugadorModificado Objeto de la clase Jugador con los nuevos datos del jugador.
         * @throws IOException Si ocurre un error al escribir en el archivo.
         */
        @Override
        public void modificarJugador(int id, Jugador jugadorModificado) throws IOException {
            long pos = buscarPosicionJugador(id);
            if (pos != -1) {
                archivo.seek(pos);
                escribirJugador(jugadorModificado);
            }
        }

        /**
         * Obtiene la información de un jugador según su ID.
         * Busca en el archivo de acceso aleatorio y devuelve el jugador con el ID especificado.
         *
         * @param id ID del jugador que se desea obtener.
         * @return Un objeto Jugador con la información del jugador encontrado, o null si no se encontró.
         * @throws IOException Si ocurre un error al leer el archivo.
         */
        @Override
        public Jugador obtenerJugador(int id) throws IOException {
            long pos = buscarPosicionJugador(id);
            if (pos != -1) {
                archivo.seek(pos);
                return leerJugador();
            }
            return null;
        }

        /**
         * Lista todos los jugadores almacenados en el archivo de acceso aleatorio.
         * Ignora los jugadores marcados como eliminados (con ID negativo).
         *
         * @return Una lista de objetos Jugador con todos los jugadores almacenados.
         * @throws IOException Si ocurre un error al leer el archivo.
         */
        @Override
        public List<Jugador> listarJugadores() throws IOException {
            List<Jugador> jugadores = new ArrayList<>();
            archivo.seek(0);
            while (archivo.getFilePointer() < archivo.length()) {
                Jugador jugador = leerJugador();
                if (jugador.getId() > 0) {
                    jugadores.add(jugador);
                }
            }
            return jugadores;
        }

        /**
         * Cierra los recursos asociados a la gestión del archivo de acceso aleatorio.
         * Cierra el RandomAccessFile si está abierto.
         *
         * @throws IOException Si ocurre un error al cerrar el recurso.
         */
        @Override
        public void cerrar() throws IOException {
            archivo.close();
        }

        /**
         * Escribe un jugador en el archivo utilizando RandomAccessFile.
         * Escribe cada atributo del jugador de forma secuencial en el archivo con un tamaño fijo.
         *
         * @param jugador El jugador a escribir.
         * @throws IOException Si ocurre un error de entrada/salida.
         */
        private void escribirJugador(Jugador jugador) throws IOException {
            archivo.writeInt(jugador.getId());
            escribirString(jugador.getNick(), TAMAÑO_NICK);
            archivo.writeInt(jugador.getExperience());
            archivo.writeInt(jugador.getLifeLevel());
            archivo.writeInt(jugador.getCoins());
        }

        /**
         * Lee un jugador del archivo utilizando RandomAccessFile.
         * Lee cada atributo del jugador de forma secuencial en el archivo y lo almacena en un nuevo objeto Jugador.
         *
         * @return El jugador leído del archivo.
         * @throws IOException Si ocurre un error de entrada/salida.
         */
        private Jugador leerJugador() throws IOException {
            int id = archivo.readInt();
            String nick = leerString(TAMAÑO_NICK);
            int experience = archivo.readInt();
            int lifeLevel = archivo.readInt();
            int coins = archivo.readInt();
            return new Jugador(id, nick, experience, lifeLevel, coins);
        }

        /**
         * Busca la posición del jugador en el archivo según su ID.
         * Recorre el archivo secuencialmente hasta encontrar el ID especificado.
         *
         * @param id ID del jugador que se desea buscar.
         * @return La posición en el archivo donde se encuentra el jugador, o -1 si no se encuentra.
         * @throws IOException Si ocurre un error al leer el archivo.
         */
        private long buscarPosicionJugador(int id) throws IOException {
            archivo.seek(0);
            while (archivo.getFilePointer() < archivo.length()) {
                long posActual = archivo.getFilePointer();
                int idLeido = archivo.readInt();
                if (idLeido == id) {
                    return posActual;
                }
                archivo.skipBytes(TAMAÑO_REGISTRO - 4);
            }
            return -1;
        }

        /**
         * Escribe una cadena con un tamaño fijo en el archivo.
         * Rellena la cadena con espacios si es más corta que el tamaño especificado.
         *
         * @param str    La cadena a escribir.
         * @param tamano El tamaño fijo para la cadena.
         * @throws IOException Si ocurre un error al escribir en el archivo.
         */
        private void escribirString(String str, int tamano) throws IOException {
            StringBuilder sb = new StringBuilder(str);
            sb.setLength(tamano);
            archivo.writeChars(sb.toString());
        }

        /**
         * Lee una cadena con un tamaño fijo del archivo.
         * Rellena la cadena con espacios si es más corta que el tamaño especificado.
         *
         * @param tamano El tamaño fijo para la cadena.
         * @return La cadena leída con el tamaño fijo, eliminando espacios en blanco.
         * @throws IOException Si ocurre un error al leer el archivo.
         */
        private String leerString(int tamano) throws IOException {
            char[] chars = new char[tamano];
            for (int i = 0; i < tamano; i++) {
                chars[i] = archivo.readChar();
            }
            return new String(chars).trim();
        }
    }

