package it.polimi.ing.sw.psp017.model;

public class Tile {
    private Worker worker;
    private int level;
    private boolean isDome;
    final private Vector2d position;


    public Tile(Vector2d position){
        this.position=position;
        this.worker=null;
        this.level=0;
        this.isDome=false;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDome(boolean dome) {
        isDome = dome;
    }

    public Tile[] validMove(){
        return null;
    }
    public void removeWorker(Worker worker){

    }
    public void addWorker(Worker worker){

    }
    public void addLevel(int level){

    }
    public void addDome(){

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
