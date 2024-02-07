package com.example.sandsim.elements.abstraksjon;

import javafx.scene.paint.Color;

public abstract class NonMovableSolid extends Solid {

    public Color color = Color.RED;

    public NonMovableSolid(int x, int y,Element[][] matrise) {
        super(x,y,matrise);
    }

    @Override
    public Color getColor() {
        return color;
    }

    public void applyGravity() {
        return;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
