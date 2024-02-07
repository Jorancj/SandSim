package com.example.sandsim.elements.abstraksjon;

import javafx.scene.paint.Color;

public abstract class Gas implements Element {
    int x;
    int y;
    private Element[][] matrise;
    public Gas(int x, int y,Element[][] matrise) {
        this.x = x;
        this.y = y;
        this.matrise = matrise;
    }

    @Override
    public Color getColor() {
        return null;
    }

    public void applyGravity() {
        System.out.println("not implemented (gravity)");
    }
}
