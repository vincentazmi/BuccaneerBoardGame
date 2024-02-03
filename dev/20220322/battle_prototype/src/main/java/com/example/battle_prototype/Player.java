package com.example.battle_prototype;

import java.util.ArrayList;

public class Player {

    private final String name; // Name of the player
    private final int color;
    private ArrayList<Treasure> treasures; // The treasure the player owns
    private ArrayList<CrewCard> cards; // The cards the player owns


    public Player(String name, int color)  {

        this.color = color;
        this.name = name;

    }


    public int getColor() {
        return color;
    }

    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }

    public void setTreasures(ArrayList<Treasure> treasures) {
        this.treasures = treasures;
    }

    public ArrayList<CrewCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<CrewCard> cards) {
        this.cards = cards;
    }

    public String getName() {
        return name;
    }


}
