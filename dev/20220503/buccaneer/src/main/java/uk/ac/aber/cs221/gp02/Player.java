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
    private int playerOrder;

    private Port homePort;

    private ArrayList<Treasure> treasures;
    private ArrayList<CrewCard> cards;


    public Player(int x, int y) {
        this(x, y, "unknown", 0);
    }

    Player(String name, int color) {
        this(1, 1, name, color);
    }


    Player(int x, int y, String name, int color) {
        xCor = x - 1;
        yCor = 20 - y;

        this.name = name;
        this.color = color;
        direction = Direction.NORTH;


        treasures = new ArrayList<>();
        cards = new ArrayList<>();
        calculateSteps();
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

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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

    public void setHomePort(Port port) {
        homePort = port;
    }

    public Port getHomePort(){
        return homePort;
    }

    public int[] cardsDescription() {
        int[] desc = new int[6];

        // System.out.println(cards);
        for (CrewCard c : cards)
            desc[c.getColor() * 3 + 3 - c.getValue()]++;

        return desc;
    }
    public int[] treasureDescription(){
        int[] desc = new int [5];

        for (Treasure t: treasures)
            switch (t.getType()) {
                case DIAMOND -> desc[0]++;
                case RUBIN -> desc[1]++;
                case GOLD -> desc[2]++;
                case PEARL -> desc[3]++;
                case BARREL -> desc[4]++;
            }
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

    public void removeCard(Card card){
        cards.remove(card);
        sortCards();
        calculateSteps();
    }


    public void addTreasure(Treasure treasure) {treasures.add(treasure);}

    public void removeTreasure(Treasure treasure) {
        treasures.remove(treasure);
    }


    public ArrayList<CrewCard> getCards() {
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


    public void setTreasures(ArrayList<Treasure> treasures) {
        this.treasures = treasures;
    }

    public void setCards(ArrayList<CrewCard> cards) {
        this.cards = cards;
    }

    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }

    public String directionToString() {
        return direction.toString();
    }

    public void setPlayerOrder(int i) {
        this.playerOrder = playerOrder;
    }

    public int getPlayerOrder() {
        return playerOrder;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }
}

