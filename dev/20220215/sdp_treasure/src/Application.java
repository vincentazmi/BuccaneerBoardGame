/**
 * Prototype showing an example of state design pattern and how it can be used to represent states in our game.
 *
 * Author: Xander Davies (xad1)
 */
public class Application {
    public static void main(String[] args) {
        TreasureStateContext stateContext = new TreasureStateContext();
        // Default state is at sea
        // Trying to take the treasure at sea
        stateContext.take();
        // Trying to take the treasure in the safe zone
        stateContext.setCurrentState(new InSafeZone());
        stateContext.take();
        // Trying to take the treasure at the harbour
        stateContext.setCurrentState(new AtHarbour());
        stateContext.take();
        // Trying to take treasure from the treasure island
        stateContext.setCurrentState(new OnIsland());
        stateContext.take();
    }

}
