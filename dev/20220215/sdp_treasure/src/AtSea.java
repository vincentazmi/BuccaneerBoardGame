public class AtSea implements TreasureState {
    @Override
    public void take(TreasureStateContext ctx){
        System.out.println("You pick up the treasure at sea.");
    }

}
