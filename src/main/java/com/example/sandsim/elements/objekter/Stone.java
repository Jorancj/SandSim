package com.example.sandsim.elements.objekter;

import com.example.sandsim.elements.abstraksjon.Element;
import com.example.sandsim.elements.abstraksjon.NonMovableSolid;
import javafx.scene.paint.Color;

public class Stone extends NonMovableSolid {

    private Element[][] matrise;
    public Color color = Color.DARKGRAY;
    public Stone(int x,int y,Element[][] matrise) {
        super(x,y,matrise);
    }

    @Override
    public Color getColor() {
        return color;
    }


}
