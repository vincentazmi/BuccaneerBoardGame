public class InSafeZone implements TreasureState {
    @Override
    public void take(TreasureStateContext ctx){
        System.out.println("You cannot take this treasure it's in the safe zone!");
    }
}
