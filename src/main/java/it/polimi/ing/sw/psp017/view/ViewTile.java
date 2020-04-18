package it.polimi.ing.sw.psp017.view;

public class ViewTile {
    int level;
    boolean dome;
    int player;

    public ViewTile()
    {
        this.level = 0;
        this.dome = false;
        this.player = 0;
    }

    public ViewTile(boolean dome, int level, int player) {
        this.level = level;
        this.dome = dome;
        this.player = player;
    }

}
