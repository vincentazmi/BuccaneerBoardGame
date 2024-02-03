package uk.ac.aber.cs221.gp02;

import java.util.*;
import java.util.stream.IntStream;

public class TreasureIsland extends Island {

    private ArrayList<Treasure> treasures;
    private Deck chanceCards;


    public void setTreasures(ArrayList<Treasure> treasures) {
        this.treasures = treasures;
    }

    public void setChanceCards(Deck chanceCards) {
        this.chanceCards = chanceCards;
    }

    public TreasureIsland(String name) {
        super(name);
        chanceCards = new Deck();
        treasures = new ArrayList<>();

    }

    public void addCard(ChanceCard card) {
        chanceCards.addCard(card);
    }

    public Card getCard() {
        return chanceCards.getCard();
    }


//
//    public String[] cardsDescription(){
//        String[] desc = new String[chanceCards.size()];
//
//        System.out.println(chanceCards);
//
//        int count = 0;
//        for (ChanceCard c : chanceCards)
//            desc[count] = c.getId() + c.getDescription();
//            count++;
//        return desc;
//
//    }

    public void shuffle() {
        chanceCards.shuffleDeck();
    }

    public void addTreasure(Treasure treasure) {
        treasures.add(treasure);
    }

    public ArrayList<Treasure> getTreasures(){
        return treasures;
    }

    public Treasure getTreasure(TreasureType type) {
        Treasure result = null;
        for (int i = 0; i < treasures.size(); i++) {
            if (treasures.get(i).getType() == type) {
                result = treasures.get(i);
                treasures.remove(i);
                break;
            }
        }
        return result;
    }


    public List<Card> getChanceCards() {
        return chanceCards.getCards();
    }


    public int[] treasureDescription() {
        int[] desc = new int[5];

        for (Treasure t : treasures)
            switch (t.getType()) {
                case DIAMOND -> desc[0]++;
                case RUBIN -> desc[1]++;
                case GOLD -> desc[2]++;
                case PEARL -> desc[3]++;
                case BARREL -> desc[4]++;
            }
        return desc;

    }


}
