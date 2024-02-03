package com.example.battle_prototype;



public class Treasure {
    private String name;
    private TreasureType type; // The type of treasure (e.g. diamond, ruby etc)


    public Treasure(String name, TreasureType type) {
        this.type = type;
        this.name = name;

    }

    public TreasureType getType() {
        return type;
    }



}
