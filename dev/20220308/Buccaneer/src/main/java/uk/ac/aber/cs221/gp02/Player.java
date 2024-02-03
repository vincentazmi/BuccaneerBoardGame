package uk.ac.aber.cs221.gp02;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private int xCor;
    private int yCor;
    private String name;
    private int color;
    private int steps;
    private Direction direction;

    private ArrayList<Treasure> treasures;
    private ArrayList<CrewCard> cards;


    Player(int x, int y) {
        this(x, y, "unknown", 0);
    }

    Player(int x, int y, String name, int color) {
        xCor = x - 1;
        yCor = 20 - y;

        this.name = name;
        this.color = color;
        steps = 5;
        direction = Direction.NORTH;


        treasures = new ArrayList<>();
        cards = new ArrayList<>();
    }


    public int getXCor() {
        return xCor;
    }

    public void setXCor(int xCor) {
        this.xCor = xCor;
    }

    public int getYCor() {
        return yCor;
    }

    public void setYCor(int yCor) {
        this.yCor = yCor;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public int getSteps() {
        return steps;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction dir) {
        direction = dir;
    }

    public int[] cardsDescription(){
        int[] desc = new int[6];

        for(CrewCard c: cards)
            desc[c.getColor() * 3 + c.getValue() - 1]++;

        return desc;
    }

    public void addCard(CrewCard card) {
        cards.add(card);
        sortCards();
        calculateSteps();
    }

    public Card getCard() {
        CrewCard c = cards.get(0);
        cards.remove(0);
        calculateSteps();
        return c;
    }

    public ArrayList<CrewCard> getCards(){
        return cards;
    }

    private void sortCards() {
        Collections.sort(cards);
    }

    private void calculateSteps() {
        int s = 0;
        for (CrewCard c : cards)
            s += c.getValue();

        steps = Math.max(1, s);
    }
}
