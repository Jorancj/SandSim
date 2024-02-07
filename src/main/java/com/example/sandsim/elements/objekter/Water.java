package com.example.sandsim.elements.objekter;

import com.example.sandsim.elements.abstraksjon.Element;
import com.example.sandsim.elements.abstraksjon.Liquid;
import javafx.scene.paint.Color;

public class Water extends Liquid {

    public Water(int x, int y, Element[][] matrise) {
        super(x,y,matrise);
        setBaseColor(Color.BLUE);
        generateRandomColor();
    }

    public Color getColor() {
        return color;
    }




}
