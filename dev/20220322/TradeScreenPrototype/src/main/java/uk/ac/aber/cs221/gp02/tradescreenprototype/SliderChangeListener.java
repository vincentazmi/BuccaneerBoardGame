package uk.ac.aber.cs221.gp02.tradescreenprototype;

import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;


public class SliderChangeListener implements ChangeListener<Number> {

    private final Slider tradeSlider;
    private final Label tradeLabel;
    private final IntegerProperty totalChangeValue;
    private final int totalMultiplier;


    public SliderChangeListener(Slider tradeSlider, Label tradeLabel, IntegerProperty totalChangeValue, int totalMultiplier) {
        this.tradeSlider = tradeSlider;
        this.tradeLabel = tradeLabel;
        this.totalChangeValue = totalChangeValue;
        this.totalMultiplier = totalMultiplier;
    }

    /**
     * Called when the value of an {@link ObservableValue} changes.
     * <p>
     * In general, it is considered bad practice to modify the observed value in
     * this method.
     *
     * @param observable The {@code ObservableValue} which value changed
     * @param oldValue   The old value
     * @param newValue   The new value
     */
    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        tradeSlider.setValue(Math.round((Double) newValue));
        tradeLabel.setText(Math.round((Double) newValue) + " /");
        totalChangeValue.setValue(totalChangeValue.getValue() + ((Math.round((Double) newValue) - Math.round((Double) oldValue)) * totalMultiplier));
    }
}
