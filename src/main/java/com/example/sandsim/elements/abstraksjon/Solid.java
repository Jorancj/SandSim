package com.example.sandsim.elements.abstraksjon;

import javafx.scene.paint.Color;

public abstract class Solid implements Element{
    int x;
    int y;
    public Element[][] matrise;

    Color baseColor = Color.RED;

    public Color color;

    public Solid(int x, int y, Element[][] matrise) {
        this.x = x;
        this.y = y;
        this.matrise = matrise;
    }

    public Element[][] getMatrise() {
        return matrise;
    }

    public void switchElements(int x1, int y1, int x2, int y2) {
        Element temp = matrise[x1][y1];

        matrise[x1][y1] = matrise[x2][y2];

        matrise[x2][y2] = temp;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }

    public void generateRandomColor() {
        // Add random variation to each RGB component
        double red = baseColor.getRed() + (Math.random() * 0.2) - 0.1; // Random value between -0.1 and 0.1
        double green = baseColor.getGreen() + (Math.random() * 0.2) - 0.1;
        double blue = baseColor.getBlue() + (Math.random() * 0.2) - 0.1;

        // Ensure color components stay within valid range (0 to 1)
        red = Math.min(1.0, Math.max(0.0, red));
        green = Math.min(1.0, Math.max(0.0, green));
        blue = Math.min(1.0, Math.max(0.0, blue));

        // Set the new color
        color = new Color(red, green, blue, 1.0); // Set opacity to 1.0 (fully opaque)
    }
}
