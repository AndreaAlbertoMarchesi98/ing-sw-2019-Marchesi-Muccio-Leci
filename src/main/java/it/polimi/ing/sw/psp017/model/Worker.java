package it.polimi.ing.sw.psp017.model;

public class Worker {
    private Player owner;
    private BoardPosition position;
    private Card card;

    public Worker(Player owner, BoardPosition boardPosition){
        this.owner = owner;
        this.position = boardPosition;
    }


    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setPosition(BoardPosition workerPosition) {
        this.position = workerPosition;
    }

    public void setCard(Card card) {
        this.card = card;
    }



    public Player getOwner() {
        return owner;
    }

    public BoardPosition getPosition() {
        return position;
    }

    public Card getCard() {
        return card;
    }

    public void move(BoardPosition direction, Worker worker){
        card.move( direction,  worker);
    };
}
