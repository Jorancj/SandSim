package com.example.sandsim.elements.objekter;

import com.example.sandsim.elements.abstraksjon.Element;
import com.example.sandsim.elements.abstraksjon.Gas;
import javafx.scene.paint.Color;

public class Air extends Gas {
    //x og y
    public Color color = Color.BLACK;
    public Air(int x, int y, Element[][] matrise) {
        super(x, y,matrise);
    }

    @Override
    public Color getColor() {
        return color;
    }
}
