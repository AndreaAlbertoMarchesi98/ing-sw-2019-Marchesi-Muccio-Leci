package it.polimi.ing.sw.psp017.model;

/**
 * this class is the model of a single cell that make up the game board
 */
public class Tile {
    private Worker worker;
    private int level;
    private boolean isDome;
    final private Vector2d position;


    public Tile(Vector2d position) {
        this.position = position;
        this.worker = null;
        this.level = 0;
        this.isDome = false;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setLevel(int level) {

        this.level = level;
        if (level == 4) isDome = true;
    }

    public void setDome(boolean dome) {
        isDome = dome;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public int getLevel() {
        return level;
    }

    public boolean isDome() {
        return isDome;
    }


}
