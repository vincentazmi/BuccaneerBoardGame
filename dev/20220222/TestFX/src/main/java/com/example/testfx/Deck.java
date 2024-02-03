package com.example.testfx;

import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;
import java.util.*;

public class Deck extends StackPane {
    private ArrayList<Card> deck;
    private int xCor, yCor;

    public Deck(int x, int y) throws FileNotFoundException {
        xCor = x;
        yCor = y;

        deck = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 1; j < 4; j++) {
                for (int k = 0; k < 6; k++) {
                    Card card = new Card(0, 0, j, i);
                    deck.add(card);
                    getChildren().add(card);
                }
            }
        }
    }

    void printDeck(int x, int y) {
        System.out.println(deck.size());
        for(int i = deck.size() - 1; i >=0; i--){
            deck.get(i).relocate(x - (deck.size() - i) * 10, y);
           // deck.get(i).relocate(200, 100);
        }
    }
}
