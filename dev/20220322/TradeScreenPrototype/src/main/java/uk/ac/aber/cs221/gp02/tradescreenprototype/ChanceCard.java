package uk.ac.aber.cs221.gp02.tradescreenprototype;

public class ChanceCard extends Card {
    int id;
    String description;

    public ChanceCard(int id) {
        new ChanceCard(id, "unknown");
    }

    public ChanceCard(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ChanceCard{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
