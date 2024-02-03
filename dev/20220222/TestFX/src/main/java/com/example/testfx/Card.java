package com.example.testfx;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.example.testfx.BuccaneerApplication.PATH_TO_TEXTURES;

public class Card extends StackPane {

    private final int pirateSize = 54;
    private final int cardSize = 30;

    private int color;
    private int value;
    private int xCor, yCor;


    public Card(int x, int y, int value, int color) throws FileNotFoundException {
        xCor = x;
        yCor = y;

        relocate(xCor, yCor);
        this.value = Math.min(value, 3);
        this.color = Math.min(color, 1);

        Rectangle border = new Rectangle(5 * cardSize, 7 * cardSize);
        border.setFill(Color.BLACK);
        border.setArcHeight(20);
        border.setArcWidth(20);

        Rectangle textureBox = new Rectangle(5 * cardSize * 0.95, 7 * cardSize * 0.95);
        textureBox.setFill(Color.LIGHTGREY);
        textureBox.setArcHeight(20);
        textureBox.setArcWidth(20);





        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < value; i++) {
            Rectangle rec = new Rectangle(pirateSize, pirateSize);
            rec.setFill(new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + (color == 0 ? "PirateBlack.png" : "PirateRed.png")))));
            vBox.getChildren().add(rec);
        }
        getChildren().addAll(border, textureBox, vBox);
    }
}
