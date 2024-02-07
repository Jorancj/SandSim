package com.example.sandsim;

import com.example.sandsim.elements.abstraksjon.Element;
import com.example.sandsim.elements.abstraksjon.Liquid;
import com.example.sandsim.elements.abstraksjon.MovableSolid;
import com.example.sandsim.elements.objekter.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


//laget av Jøran Clausen Jøsang
public class Controller {
    //JavaFX elementer, koblet fra src/main/resources/com/example/sandsim/view.fxml
    public Button delete;
    @FXML
    public ComboBox comboBox;
    @FXML
    public Canvas canvas;
    GraphicsContext gc;
    private AnimationTimer animationTimer;

    //gammel position fra musepeker, for å tegne linjer
    private int lastMouseX;
    private int lastMouseY;
    private boolean isDrawing = false;
    //matrise
    private Element[][] matrise;
    public static final int WIDTH = 200;
    public static final int HEIGHT = 200;
    public static final int PIXEL_SIZE = 4;

    @FXML
    public void initialize() {
        comboBox.getItems().addAll("Sand", "Stone", "Water", "Dirt","Grass", "Remove");
        comboBox.getSelectionModel().select(1);
        gc = canvas.getGraphicsContext2D();
        fyllKart();
        startAnimationLoop();
    }


    @FXML //fyller matrisen med Air, brukes til å tilbakestille kartert
    public void fyllKart() {
        matrise = new Element[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                matrise[i][j] = new Air(i, j, matrise);
            }
        }
    }

    //leser fra matrisen og genererer ruter i Canvas, hver frame
    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                gc.setFill(matrise[i][j].getColor());
                gc.fillRect(i * PIXEL_SIZE, j * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }

    //animasjons løkke
    private void startAnimationLoop() {
        animationTimer = new AnimationTimer() {
            long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 16_666_666) { //16ms (60 FPS)
                    lastUpdate = now;

                    //tungdekraft flytter elementene en posisjon hver frame
                    applyGravity();
                    render(gc);
                }
            }
        };
        animationTimer.start();
    }

    private void applyGravity() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = HEIGHT - 1; j >= 0; j--) {
                //finner alle objektene av typene MovableSolid og Liquid.
                if (matrise[i][j] instanceof MovableSolid) {
                    ((MovableSolid) matrise[i][j]).applyGravity();
                }
                if (matrise[i][j] instanceof Liquid) {
                    ((Liquid) matrise[i][j]).applyGravity();
                }
            }
        }
    }

    //håndterer mus-input
    private void drawElement(MouseEvent event) {
        //henter mus-posisjon og oversetter til matrise kordinater
        double mouseX = event.getX();
        double mouseY = event.getY();

        int matrixX = (int) (mouseX / PIXEL_SIZE);
        int matrixY = (int) (mouseY / PIXEL_SIZE);

        //henter element valg fra combobox
        String selectedElement = (String) comboBox.getValue();

        //egdecase for matrise grenser
        if (matrixX >= 0 && matrixX < WIDTH && matrixY >= 0 && matrixY < HEIGHT) {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                // tilbakestiller LastMouseX,Y
                lastMouseX = matrixX;
                lastMouseY = matrixY;
                drawLine(matrixX, matrixY, matrixX, matrixY, selectedElement);
            } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                // tegner linje mellom start og slutt posisjon
                drawLine(lastMouseX, lastMouseY, matrixX, matrixY, selectedElement);

                // oppdaterer lastMouseX,Y for neste linje
                lastMouseX = matrixX;
                lastMouseY = matrixY;
            }
        }
    }

    //Bresenham's line drawing algorithm
    private void drawLine(int x0, int y0, int x1, int y1, String selectedElement) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        //henter valgt element og oppdaterer matrisen
        while (true) {
            if (x0 >= 0 && x0 < WIDTH && y0 >= 0 && y0 < HEIGHT) {
                //tillater å slette elementer
                if (selectedElement.equals("Remove")) {
                    matrise[x0][y0] = new Air(x0, y0, matrise);
                } else if (matrise[x0][y0] instanceof Air) {
                    switch (selectedElement) {
                        case "Sand":
                            matrise[x0][y0] = new Sand(x0, y0, matrise);
                            break;
                        case "Stone":
                            matrise[x0][y0] = new Stone(x0, y0, matrise);
                            break;
                        case "Water":
                            matrise[x0][y0] = new Water(x0, y0, matrise);
                            break;
                        case "Dirt":
                            matrise[x0][y0] = new Dirt(x0, y0, matrise);
                            break;
                        case "Grass":
                            matrise[x0][y0] = new Grass(x0, y0, matrise);
                            break;
                        default:
                            System.out.println("Broken");
                    }
                }
            }

            if (x0 == x1 && y0 == y1) {
                break; // Exit the loop after drawing a single line segment
            }

            //Bresenham's line
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }



    @FXML
    protected void mousePressed(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            isDrawing = true;
            drawElement(event);
        }
    }

    @FXML
    protected void mouseDragged(MouseEvent event) {
        if (isDrawing) {
            drawElement(event);
        }
    }

    @FXML
    protected void mouseReleased(MouseEvent event) {
        isDrawing = false;
    }

    public void drawSquare() {

    }
}
