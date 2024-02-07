package com.example.sandsim.elements.abstraksjon;

import com.example.sandsim.Controller;
import com.example.sandsim.elements.objekter.Air;
import javafx.scene.paint.Color;

import java.util.Random;

public abstract class Liquid implements Element {
    int x;
    int y;

    Color baseColor = Color.RED;

    public Color color;

    //referanse for matrisen
    Element[][] matrise;

    public Liquid(int x, int y, Element[][] matrise) {
        this.x = x;
        this.y = y;
        this.matrise = matrise;
    }

    //tyngdekraft metode for Liquids, Burde evt ha brukt switchElements()
    public void applyGravity() {
        //sjekker at Y er innforbi Y høyde
        if (getY() < Controller.HEIGHT - 1) {
            Element targetCell = getElementAtCell(getX(), getY() + 1);

            //sjekker om targetCell er Air (eller evt Gas)
            if (targetCell instanceof Air) {//flytter this ned, oppdaterer forrige posisjon, og oppdaterer egene kordinater
                matrise[getX()][getY() + 1] = this;
                matrise[getX()][getY()] = new Air(getX(), getY(), matrise);
                setY(getY() + 1);

            } else if (targetCell instanceof Liquid ) { //sjekker om targetCell er Liquid
                Random random = new Random();
                if (random.nextBoolean()) {//50% sjans om den sjekker venstre eller høyre celle
                    //sjekker at nye cellen er innforbi matrisen, og erstatter Air
                    if (getX() > 0 && (matrise[getX() - 1][getY()] instanceof Air)) {
                        // Flytter Liquid til venstre
                        matrise[getX() - 1][getY()] = this;
                        matrise[getX()][getY()] = new Air(getX(), getY(), matrise); //fjerne gamle celle, oppdaterer cellens kordinat
                        setX(getX() - 1);
                    }
                } else {
                    //sjekker at nye cellen er innforbi matrisen
                    if (getX() < Controller.WIDTH - 1 && (matrise[getX() + 1][getY()] instanceof Air)) {
                        // Flytter Liquid til høyre
                        matrise[getX() + 1][getY()] = this;
                        matrise[getX()][getY()] = new Air(getX(), getY(), matrise); //fjerne gamle celle, oppdaterer cellens kordinat
                        setX(getX() + 1);
                    }
                }
            } else if (targetCell instanceof Solid) { //sjekker om targetCell er Solid
                Random random = new Random();
                if (random.nextBoolean()) { //50%
                    if (getX() > 0 && (matrise[getX() - 1][getY()] instanceof Air)) {
                        // Flytter Liquid til venstre, dersom den er over Solid
                        matrise[getX() - 1][getY()] = this;
                        matrise[getX()][getY()] = new Air(getX(), getY(), matrise);
                        setX(getX() - 1);
                    }
                } else {
                    if (getX() < Controller.WIDTH - 1 && (matrise[getX() + 1][getY()] instanceof Air)) {
                        // Flytter Liquid til høyre, dersom den er over Solid
                        matrise[getX() + 1][getY()] = this;
                        matrise[getX()][getY()] = new Air(getX(), getY(), matrise);
                        setX(getX() + 1);
                    }
                }
            }
        }
    }

    public Element getElementAtCell(int x, int y) {
        return matrise[x][y];
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

    //metode som sjekker om underlaget til cellen er stabil, forhindrer cellen i å skli ned eller bortover
    private boolean isBelowSolid(Element targetCell) {
        boolean isBelowSolid = true;

        //sjekker om cellen under er Solid
        if (!(targetCell instanceof Solid)) {
            isBelowSolid = false;
        } else {
            // sjekker venstre og høyre diagonal
            if (getX() > 0 && !(matrise[getX() - 1][getY() + 1] instanceof Solid)) {
                isBelowSolid = false;
            }
            if (getX() < Controller.WIDTH - 1 && !(matrise[getX() + 1][getY() + 1] instanceof Solid)) {
                isBelowSolid = false;
            }
        }
        return isBelowSolid;
    }

    public void switchElements(int x1, int y1, int x2, int y2) {
        Element temp = matrise[x1][y1];

        matrise[x1][y1] = matrise[x2][y2];

        matrise[x2][y2] = temp;
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

    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }
}
