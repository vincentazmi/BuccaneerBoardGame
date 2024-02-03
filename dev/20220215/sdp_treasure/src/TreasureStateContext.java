public class TreasureStateContext {
    private TreasureState currentState;
    // By default the treasure is at sea.
    public TreasureStateContext() {
        currentState = new AtSea();
    }

    public void setCurrentState(TreasureState state){
        currentState = state;
    }

    public void take() {
        currentState.take(this);

    }
}
