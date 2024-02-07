package com.example.sandsim.elements.objekter;

import com.example.sandsim.elements.abstraksjon.Element;
import com.example.sandsim.elements.abstraksjon.MovableSolid;
import javafx.scene.paint.Color;

public class Sand extends MovableSolid {
    public Sand(int x, int y, Element[][] matrise) {
        super(x, y, matrise);
        setBaseColor(Color.KHAKI);
        generateRandomColor();
    }

    public Color getColor() {
        return color;
    }

}

