package com.example.sandsim.elements.abstraksjon;

import com.example.sandsim.Controller;
import com.example.sandsim.elements.objekter.Air;
import com.example.sandsim.elements.objekter.Water;
import javafx.scene.paint.Color;

import java.util.Random;

public abstract class MovableSolid extends Solid {

    public MovableSolid(int x, int y,Element[][] matrise) {
        super(x, y, matrise);
    }

    @Override
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    //tyngdekraft metode for MovableSolids, Burde evt ha brukt switchElements()
    public void applyGravity(){
        //sjekker at Y er innforbi Y høyde
        if (getY() < Controller.HEIGHT - 1) {
            Element targetCell = getElementAtCell(getX(),getY()+1);

            //sjekker om targetCell er Air (eller evt Gas)
            if (targetCell instanceof Air) {//flytter this ned, oppdaterer forrige posisjon, og oppdaterer egene kordinater
                matrise[getX()][getY() + 1] = this;
                matrise[getX()][getY()] = new Air(getX(), getY(),matrise);
                setY(getY() + 1);

            } else if (targetCell instanceof Water) {//sjekker om targetCell er Water, burde kanskje vært Liquid
                matrise[getX()][getY() + 1] = this;
                matrise[getX()][getY()] = new Water(getX(), getY(),matrise);
                setY(getY() + 1);

            } else if (targetCell instanceof Solid) {//sjekker om targetCell er Solid

                boolean isBelowSolid = isBelowSolid(targetCell);

                if (isBelowSolid) {
                    return;
                }

                // If the cell below is not solid, the dirt particle falls randomly left or right
                Random random = new Random();
                if (random.nextBoolean()) {//50% sjans om den sjekker venstre eller høyre celle

                    //sjekker at nye cellen er innforbi matrisen
                    if (getX() > 0 && (matrise[getX() - 1][getY()] instanceof Air || matrise[getX() - 1][getY()] instanceof Liquid)) {
                        Element adj = matrise[getX() - 1][getY()];
                        // flytter cellen til venstre
                        matrise[getX() - 1][getY()] = this;

                        //ikke helt optimalt, men fikset en bug, kunne sikkert ha blitt løst med swapPositions
                        if (adj instanceof Air)
                            matrise[getX()][getY()] = new Air(getX(), getY(), matrise); //fjerne gamle celle, oppdaterer cellens kordinat
                        else if (adj instanceof Water)
                            matrise[getX()][getY()] = new Water(getX(), getY(), matrise); //fjerne gamle celle, oppdaterer cellens kordinat
                        setX(getX() - 1);
                    }
                } else {

                    //sjekker at nye cellen er innforbi matrisen
                    if (getX() < Controller.WIDTH - 1 && (matrise[getX() + 1][getY()] instanceof Air || matrise[getX() + 1][getY()] instanceof Liquid)) {
                        Element adj = matrise[getX() + 1][getY()];
                        // flytter cellen til høyre
                        matrise[getX() + 1][getY()] = this;

                        if (adj instanceof Air)
                            matrise[getX()][getY()] = new Air(getX(), getY(), matrise); //fjerne gamle celle, oppdaterer cellens kordinat
                        else if (adj instanceof Water)
                            matrise[getX()][getY()] = new Water(getX(), getY(), matrise); //fjerne gamle celle, oppdaterer cellens kordinat
                        setX(getX() + 1);
                    }
                }
            }
        }
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
}
