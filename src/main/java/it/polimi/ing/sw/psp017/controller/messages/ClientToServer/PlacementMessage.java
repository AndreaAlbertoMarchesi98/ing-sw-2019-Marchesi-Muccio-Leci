package it.polimi.ing.sw.psp017.controller.messages.ClientToServer;

import it.polimi.ing.sw.psp017.model.Vector2d;

import java.io.Serializable;

public class PlacementMessage implements Serializable {

    //posizionamento worker
    public Vector2d firstWorker;
    public Vector2d secondWorker;

    public PlacementMessage(Vector2d firstWorker, Vector2d secondWorker) {

        this.firstWorker = firstWorker;
        this.secondWorker = secondWorker;
    }
}
