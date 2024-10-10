/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jugadorarchivosadriangalilea;
import java.io.IOException;
import java.util.List;
/**
 *
 * @author Vespertino
 */
public abstract class GestionFichero {
    public abstract Boolean agregarJugador(Jugador jugador) throws IOException;

    public abstract Boolean eliminarJugador(int id) throws IOException;

    public abstract void modificarJugador(int id, Jugador jugadorModificado) throws IOException;

    public abstract Jugador obtenerJugador(int id) throws IOException;

    public abstract List<Jugador> listarJugadores() throws IOException;

    public abstract void cerrar() throws IOException;
}
