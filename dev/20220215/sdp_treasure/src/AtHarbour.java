public class AtHarbour implements TreasureState {
    @Override
    public void take(TreasureStateContext ctx){
        System.out.println("You trade your crew cards for the treasure.");
    }
}
