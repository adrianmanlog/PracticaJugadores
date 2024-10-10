/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jugadorarchivosadriangalilea;

import java.io.Serializable;

/**
 *
 * @author Vespertino
 */
public class Jugador implements Serializable{
     private int id;
    private String nick;
    private int experience;
    private int lifeLevel;
    private int coins;

    // Constructor
    public Jugador(int id, String nick, int experience, int lifeLevel, int coins) {
        this.id = id;
        this.nick = nick;
        this.experience = experience;
        this.lifeLevel = lifeLevel;
        this.coins = coins;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLifeLevel() {
        return lifeLevel;
    }

    public void setLifeLevel(int lifeLevel) {
        this.lifeLevel = lifeLevel;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    // toString
    @Override
    public String toString() {
        return String.format("[USER_ID = %d, NICK_NAME = %s, EXPERIENCE = %d, LIFE_LEVEL = %d, COINS = %d]",
                id, nick, experience, lifeLevel, coins);
    }
}
