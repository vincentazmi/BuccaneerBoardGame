package uk.ac.aber.cs221.gp02;

/**
 * This class is a representation of chanceCard in the Buccaneer Game
 *
 * @version 1.0
 * @author Adrian
 */
public class ChanceCard extends Card {

    private int id;
    private String description;

    /**
     * This is a second constructor, without card's description, it can be used for testing
     * @param id is ID of the chance Card
     */
    public ChanceCard(int id) {
        new ChanceCard(id, "unknown");
    }

    /**
     * This is the standard constructor
     * @param id is ID of the chance Card
     * @param description is the text representation of card's effect
     */
    public ChanceCard(int id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * Just getter
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Just setter
     * @param description new description of chance card
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * ID getter
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Standard toString()
     * @return String representation
     */
    @Override
    public String toString() {
        return "ChanceCard{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
