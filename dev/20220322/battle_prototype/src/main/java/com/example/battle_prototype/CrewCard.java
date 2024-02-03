package com.example.battle_prototype;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CrewCard {
    private int value;
    private int color;

    public CrewCard(int value, int color) {
        this.color = color;
        this.value = value;

    }

    public int getValue() {
        return value;
    }

    public int getColor() {
        return color;
    }
}
