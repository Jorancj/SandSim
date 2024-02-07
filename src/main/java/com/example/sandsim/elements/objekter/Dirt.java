package com.example.sandsim.elements.objekter;

import com.example.sandsim.elements.abstraksjon.Element;
import com.example.sandsim.elements.abstraksjon.MovableSolid;
import javafx.scene.paint.Color;

public class Dirt extends MovableSolid {

    public Dirt(int x, int y, Element[][] matrise) {
        super(x, y, matrise);
        setBaseColor(Color.SANDYBROWN);
        generateRandomColor();
    }

    public Color getColor() {
        return color;
    }

}
