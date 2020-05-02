package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.controller.client.Client;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.model.Vector2d;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.InputMismatchException;
import java.util.Scanner;


public class CLI implements View {

    private Client client;
    private boolean isPositionWorkersPhase;
    private boolean isPowerActive;
    private Vector2d lastSelectedTile = null;
    private int numberOfPlayers;
    private Scanner in = new Scanner(System.in);
    private boolean activatePower = false;
    public final static int WORKER = 3;

    public CLI(Client client) {
        this.client = client;
        printLogo();
        isPositionWorkersPhase = true;
    }


    public void printLogo() {

        System.out.println(ANSI_CYAN);
        //System.out.println(ANSI_BLACK_BACKGROUND);
        System.out.println("  ██████  ▄▄▄       ███▄    █ ▄▄▄█████▓ ▒█████   ██▀███   ██▓ ███▄    █  ██▓");
        System.out.println("▒██    ▒ ▒████▄     ██ ▀█   █ ▓  ██▒ ▓▒▒██▒  ██▒▓██ ▒ ██▒▓██▒ ██ ▀█   █ ▓██▒");
        System.out.println("░ ▓██▄   ▒██  ▀█▄  ▓██  ▀█ ██▒▒ ▓██░ ▒░▒██░  ██▒▓██ ░▄█ ▒▒██▒▓██  ▀█ ██▒▒██▒");
        System.out.println("  ▒   ██▒░██▄▄▄▄██ ▓██▒  ▐▌██▒░ ▓██▓ ░ ▒██   ██░▒██▀▀█▄  ░██░▓██▒  ▐▌██▒░██░");
        System.out.println("▒██████▒▒ ▓█   ▓██▒▒██░   ▓██░  ▒██▒ ░ ░ ████▓▒░░██▓ ▒██▒░██░▒██░   ▓██░░██░");
        System.out.println("▒ ▒▓▒ ▒ ░ ▒▒   ▓▒█░░ ▒░   ▒ ▒   ▒ ░░   ░ ▒░▒░▒░ ░ ▒▓ ░▒▓░░▓  ░ ▒░   ▒ ▒ ░▓  ");
        System.out.println("░ ░▒  ░ ░  ▒   ▒▒ ░░ ░░   ░ ▒░    ░      ░ ▒ ▒░   ░▒ ░ ▒░ ▒ ░░ ░░   ░ ▒░ ▒ ░");
        System.out.println("░  ░  ░    ░   ▒      ░   ░ ░   ░      ░ ░ ░ ▒    ░░   ░  ▒ ░   ░   ░ ░  ▒ ░");
        System.out.println("      ░        ░  ░         ░              ░ ░     ░      ░           ░  ░  ");
        System.out.println(ANSI_RESET);
    }


    @Override
    public void notifyNickname(AuthenticationMessage authenticationMessage) {
        client.getNetworkHandler().sendMessage(authenticationMessage);
    }

    @Override
    public void notifyGameSetUp(GameSetUpMessage gameSetUpMessage) {


        //first player select 3 card from deck
        client.getNetworkHandler().sendMessage(gameSetUpMessage);


    }

    @Override
    public void notifyCard(CardMessage cardMessage) {

        //invio carta scelta dal giocatore
        client.getNetworkHandler().sendMessage(cardMessage);

    }

    @Override
    public void notifyPlacement(PlacementMessage placementMessage) {
        client.getNetworkHandler().sendMessage(placementMessage);

    }

    @Override
    public void notifySelection(SelectionMessage selectionMessage) {

        client.getNetworkHandler().sendMessage(selectionMessage);

    }

    @Override
    public void notifyIsPowerActive(PowerActiveMessage powerActiveMessage) {

        client.getNetworkHandler().sendMessage(powerActiveMessage);

    }

    @Override
    public void notifyAction(ActionMessage actionMessage) {

        client.getNetworkHandler().sendMessage(actionMessage);

    }

    @Override
    public void notifyDisconnection(DisconnectionMessage disconnectionMessage) {
        client.getNetworkHandler().closeConnection();
    }

    @Override
    public void updateGameCreation() {

        client.setPlayerNumber(1);

        System.out.println("dentro updateGameCreation");

        //scegli il numero di giocatori
        numberOfPlayers = getNumberOfPlayers();

        //create arrayList from enum
        ArrayList<GodName> cards = new ArrayList<>(EnumSet.allOf(GodName.class));
        ArrayList<GodName> selectedCards = new ArrayList<>();


        for(int i = 0; i <numberOfPlayers; i++)
        {
            selectedCards.add(chooseGodCard(cards));
            cards.remove(selectedCards.get(i));
        }

        System.out.println(ANSI_CYAN_BACKGROUND + "selected cards :" + selectedCards);
        System.out.println("not Selected cards :" + cards + ANSI_RESET);


        notifyGameSetUp(new GameSetUpMessage(selectedCards));  //arraylist delle carte scelte));



    }


    @Override
    public void updateLobby(LobbyMessage lobbyMessage) {

        System.out.println("dentro updateLobby");




        //salvataggio index del giocatore
        client.setPlayerNumber(lobbyMessage.players.indexOf(client.getNickname()) + 1);


        //stampa i player dal messaggio  <<<<<<<<<<<<<<<<da fare



        //chooseGodCard(lobbyMessage.cards);
        // lobbyMessage.cards; //stampa le carte che puo scegliere
        //scelta di una sola carta dal array in input
        //invia carta scelta
        notifyCard(new CardMessage(chooseGodCard(lobbyMessage.availableCards))); //é correto available?'


    }

    @Override
    public void updateWaitingRoom(WaitMessage waitMessage) {

        System.out.println("dentro updateWaitingRoom");

        //gioatore in attessa di una nuova partita
        System.out.println(ANSI_CYAN_BACKGROUND +"Number of players in waitingRoom : " + waitMessage.queueLength);

        ansi_reset();

    }



    @Override
    public void updateBoard(BoardMessage boardMessage) {

        System.out.println("dentro a updateBoard");

        printBoard(boardMessage.board);

        //se e il suo turno
        System.out.println("client playerNumber is:" + client.getPlayerNumber());
        if (boardMessage.activePlayerNumber == client.getPlayerNumber())//identificatore intero nuova variabile)
        {

            System.out.println("ACTION MESSAGE : " + boardMessage.action);
            if(boardMessage.action == ActionNames.PLACE_WORKERS)
            {
                Vector2d[] temp = new Vector2d[2];


                System.out.println("workers placement");
                //utente sceglie 2 posizioni
                printBoard(boardMessage.board);

                /*
                //salvo 2 posizioni
                for(int i = 0; i <2; i++)
                {
                    System.out.println("where do you want to position worker number "+(i+1)+"?");
                    temp[i] = getTargetTile(boardMessage.action,boardMessage.board);


                    //da aggiungere : non far scegliere la stessa posizione oppure posizione occupata dagli avversari


                }
                 */
                temp = setWorkerPosition(boardMessage.action,boardMessage.board);
                //invio
                notifyPlacement(new PlacementMessage(temp[0], temp[1]));

            }
            else if(boardMessage.action == ActionNames.SELECT_WORKER)
            {
                //scelta del worker da utilizzare

                //da fare : obbligo di scegliere un worker
                notifySelection(new SelectionMessage(getTargetTile(boardMessage.action, boardMessage.board)));

            }
            else if(boardMessage.hasChoice) //possibilita di attivare il potere
            {

                //attivare il potere

                //activatePower = getChoice();  ?? serve

                //si o no

                notifyIsPowerActive(new PowerActiveMessage(getChoice()));




            }


            else if(boardMessage.action == ActionNames.MOVE || boardMessage.action == ActionNames.BUILD)//condizione senza poteri muovi costruisci
            {
                //salva posizione scelta lastWorkerposition == scegli worker

                printValidTiles(boardMessage.validTiles);
                notifyAction(new ActionMessage(getTargetTile(boardMessage.action, boardMessage.board)));

            }
            else{
                System.out.println("invalid action NONE");
            }



        }

        //CommandLineInterface.printBoard(boardMessage.board); //da cambiare in viewTile


    }

    public void updateLoginScreen(InvalidNameMessage invalidNameMessage) {
        if (invalidNameMessage != null) {
            System.out.println("Invalid Nickname");
        }
        String nickname = CommandLineInterface.getNickname();
        client.setNickname(nickname);
        AuthenticationMessage am = new AuthenticationMessage(nickname);
        notifyNickname(am);
    }



    /**
     * first player has to choose the number of players
     * @return a number [1-3]
     */

    public int getNumberOfPlayers()
    {

        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "How many players [2-3] ? " + ANSI_RESET);
        int answer = 0;

        do {

            if (answer != 0)
                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "please insert a valid number [2-3]" + ANSI_RESET);
            if (!in.hasNextInt()) {
                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________this is not a number____________>>>>>>>>>> " + ANSI_RESET);
                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "please insert a valid number [2-3]" + ANSI_RESET);
                try {
                    answer = in.nextInt();
                } catch (InputMismatchException e) {
                    in.nextLine();
                    answer = 0;
                }
            } else {
                answer = in.nextInt();
            }

        } while (answer > 3 || answer < 1);


        ansi_reset();
        return answer;
    }


    /**
     * player choose a card from the deck
     * @param cards arraylist of card
     * @return an allowed card
     */
    public  GodName chooseGodCard(ArrayList<GodName> cards)
    {
        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Printing Gods card :"+ANSI_RESET);

        printEnumGodName(cards);
        System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Please select a card :"+ANSI_RESET);

        int answer= 0;
        do {

            if (answer != 0)
            {
                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "please select a valid card" + ANSI_RESET);
                printEnumGodName(cards);
            }
            if (!in.hasNextInt()) {

                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________this is not a number____________>>>>>>>>>> " + ANSI_RESET);
                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "please insert a valid card number" + ANSI_RESET);
                printEnumGodName(cards);
                try {
                    answer = in.nextInt();
                } catch (InputMismatchException e) {
                    in.nextLine();
                    answer = 0;
                }
            } else {
                answer = in.nextInt();
            }

        } while (answer >= cards.size() || answer < 0);

        return cards.get(answer);
    }


    /**
     * print cards with arrays position
     * @param cards arraylist of card
     */
    public  void printEnumGodName(ArrayList<GodName> cards)
    {
        for(int i = 0; i < cards.toArray().length;i++)
        {
            System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK +cards.get(i) + "[" +i+ "],  "+ANSI_RESET);
        }
        System.out.println();
    }


    /**
     * print game board
     * @param board game board
     */
    public  void printBoard(BoardMessage.PrintableTile[][] board) {




        int line = 2;
        System.out.println(ANSI_CYAN);
        System.out.print("███████████████████████████████████████████████████████████████");
        option(0);
        System.out.print(ANSI_YELLOW_BACKGROUND + "██"+ ANSI_RED+ "    ►►►►0◄◄◄◄  ►►►►1◄◄◄◄  ►►►►2◄◄◄◄  ►►►►3◄◄◄◄  ►►►►4◄◄◄◄  " +ANSI_CYAN +"██"+ANSI_RESET+ ANSI_CYAN  );
        option(1);
        System.out.print(ANSI_CYAN);
        for (int i = 0; i < 5; i++) {
            System.out.println("██  ■ ═════════  ═════════  ═════════  ═════════  ═════════  ██                                          ██                                                           ██");
            //System.out.println("██    ═════════  ═════════  ═════════  ═════════  ═════════  ██");
            System.out.print("██"+ANSI_YELLOW_BACKGROUND + ANSI_RED+"  "+ i + " ");

            ansi_reset();
            for (int j = 0; j < 5; j++)
            {


                if(board[i][j].dome)
                {
                    System.out.print("║");
                    System.out.print(ANSI_RED_BACKGROUND+ "       ");
                    ansi_reset();
                    System.out.print("║  ");
                    //System.out.print("  ");;

                }else{
                    System.out.print("║");
                    System.out.print(ANSI_RED +"▲"+ board[i][j].level);
                    System.out.print(ANSI_GREEN_BACKGROUND);

                    System.out.print(ANSI_GREEN_BACKGROUND);
                    System.out.print("   ");
                    switch (board[i][j].playerNumber -1)
                    {

                        case 0 :
                            System.out.print(ANSI_BLUE_BACKGROUND + ANSI_BLACK + " ");
                            break;
                        case 1 :
                            System.out.print(ANSI_BLUE_BACKGROUND + ANSI_BLACK + "♠");
                            break;
                        case 2 :
                            System.out.print(ANSI_BRIGHT_BG_YELLOW + ANSI_RED + "♣");
                            break;
                        case 3 :
                            System.out.print(ANSI_BRIGHT_BG_PURPLE + "♦");
                            break;
                        default: break;
                    }
                    System.out.print(board[i][j].playerNumber);
                    ansi_reset();
                    System.out.print("║  ");
                }





            }
            System.out.print("██");
            option(line);
            line++;
        }
        System.out.println("██  ■ ═════════  ═════════  ═════════  ═════════  ═════════  ██                                          ██  ■ ═════════  ═════════  ═════════  ═════════  ═════════  ██");
        //System.out.println("██    ═════════  ═════════  ═════════  ═════════  ═════════  ██");
        System.out.println("███████████████████████████████████████████████████████████████                                          ███████████████████████████████████████████████████████████████" +ANSI_RESET);

    }





    /**
     * connected with printBoard for additional information
     *
     * @param i raw of printboard line
     */
    public void option(int i) {

        System.out.print("                                          ██");
        switch (i) {
            case 2:
                System.out.print(ANSI_RED + "     ████████ ==  DOME");
                ansi_reset();
                System.out.println(ANSI_GREEN + "              ████████ ==  FREE      " + ANSI_CYAN + "██");
                ansi_reset();
                break;
            case 1:
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + ITALIC + UNDERLINE + "                           LEGEND :                        " + ANSI_RESET + ANSI_CYAN + "██");
                ansi_reset();
                break;

            case 3:

                System.out.print(ITALIC + ANSI_BLUE_BACKGROUND + ANSI_BLACK + "PLAYER ONE           ");
                System.out.print(ANSI_BRIGHT_BG_YELLOW + ANSI_RED + "PLAYER TWO         ");
                System.out.println(ANSI_BRIGHT_BG_PURPLE + "PLAYER THREE       " + ANSI_RESET + ANSI_CYAN + "██");
                ansi_reset();
                break;

            case 4:

                System.out.print(ITALIC + ANSI_BLUE_BACKGROUND + ANSI_BLACK + "God's name :         ");
                System.out.print(ANSI_BRIGHT_BG_YELLOW + ANSI_RED + "God's name :       ");
                System.out.println(ANSI_BRIGHT_BG_PURPLE + "God's name :       " + ANSI_RESET + ANSI_CYAN + "██");
                ansi_reset();
                break;
            case 5:
                System.out.println("Tile :  " + ANSI_RED + "▲level" + ANSI_RESET + "     <<<<" + ANSI_RED + "DOME //" + ANSI_GREEN + "   FREE>>>>" + ANSI_CYAN + " tile's owner{♠♣♦}██");
                ansi_reset();
                break;

            case 6:
                System.out.println("SELECTED TILE :                                           ");
                ansi_reset();
                break;


            case 0:
                System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██");
                break;
            default:
                System.out.println("                                                           ██");

        }


    }



    public void optionValidTiles(int i) {

        System.out.print("                                          ██");
        switch (i) {
            case 2:
                System.out.print(ANSI_RED + "     ████████ ==  NO  ");
                ansi_reset();
                System.out.println(ANSI_GREEN + "              ████████ ==  OK        " + ANSI_CYAN + "██");
                ansi_reset();
                break;
            case 1:
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + ITALIC + UNDERLINE + "                           LEGEND :                        " + ANSI_RESET + ANSI_CYAN + "██");
                ansi_reset();
                break;

            case 3:

                System.out.print(ITALIC + ANSI_BLUE_BACKGROUND + ANSI_BLACK + "PLAYER ONE           ");
                System.out.print(ANSI_BRIGHT_BG_YELLOW + ANSI_RED + "PLAYER TWO         ");
                System.out.println(ANSI_BRIGHT_BG_PURPLE + "PLAYER THREE       " + ANSI_RESET + ANSI_CYAN + "██");
                ansi_reset();
                break;

            case 4:

                System.out.print(ITALIC + ANSI_BLUE_BACKGROUND + ANSI_BLACK + "God's name :         ");
                System.out.print(ANSI_BRIGHT_BG_YELLOW + ANSI_RED + "God's name :       ");
                System.out.println(ANSI_BRIGHT_BG_PURPLE + "God's name :       " + ANSI_RESET + ANSI_CYAN + "██");
                ansi_reset();
                break;
            case 5:
                System.out.println("Tile :  " + ANSI_RED + "▲level" + ANSI_RESET + "     <<<<" + ANSI_RED + "DOME //" + ANSI_GREEN + "   FREE>>>>" + ANSI_CYAN + " tile's owner{♠♣♦}██");
                ansi_reset();
                break;

            case 6:
                System.out.println("SELECTED TILE :                                           ");
                ansi_reset();
                break;


            case 0:
                System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██");
                break;
            default:
                System.out.println("                                                           ██");

        }


    }


    public void printValidTiles(boolean[][] board) {


        int line = 2;
        System.out.println(ANSI_CYAN);
        System.out.print("███████████████████████████████████████████████████████████████");
        optionValidTiles(0);
        System.out.print(ANSI_YELLOW_BACKGROUND + "██" + ANSI_RED + "    ►►►0◄◄◄  ►►►1◄◄◄  ►►►2◄◄◄  ►►►3◄◄◄  ►►►4◄◄◄  " + ANSI_CYAN + "██" + ANSI_RESET + ANSI_CYAN);
        optionValidTiles(1);
        System.out.print(ANSI_CYAN);
        for (int i = 0; i < 5; i++) {
            System.out.println("██  ■ ═════════  ═════════  ═════════  ═════════  ═════════  ██                                          ██                                                           ██");
            //System.out.println("██    ═════════  ═════════  ═════════  ═════════  ═════════  ██");
            System.out.print("██" + ANSI_YELLOW_BACKGROUND + ANSI_RED + "  " + i + " ");

            ansi_reset();
            for (int j = 0; j < 5; j++) {


                System.out.print("║");
                if(!board[i][j])
                {
                    System.out.print(ANSI_RED_BACKGROUND + "       ");

                }
                else{
                    System.out.print(ANSI_GREEN_BACKGROUND + "       ");
                }

                ansi_reset();
                System.out.print("║  ");



            }
            System.out.print("██");
            optionValidTiles(line);
            line++;
        }
        System.out.println("██  ■ ═════════  ═════════  ═════════  ═════════  ═════════  ██                                          ██  ■ ═════════  ═════════  ═════════  ═════════  ═════════  ██");
        //System.out.println("██    ═════════  ═════════  ═════════  ═════════  ═════════  ██");
        System.out.println("███████████████████████████████████████████████████████████████                                          ███████████████████████████████████████████████████████████████" + ANSI_RESET);

    }



    public Vector2d getTargetTile(ActionNames action, BoardMessage.PrintableTile[][] board) {


        if(action == ActionNames.MOVE){
            System.out.println(ANSI_PURPLE_BACKGROUND + "███████████████████████████████████████████████████████████████\n");
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< MOVE >>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("███████████████████████████████████████████████████████████████");
        }
        else if (action == ActionNames.BUILD)
        {
            System.out.println(ANSI_BRIGHT_BG_YELLOW + "███████████████████████████████████████████████████████████████");
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<< BUILD >>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("███████████████████████████████████████████████████████████████");
        }
        else if(action == ActionNames.SELECT_WORKER){
            System.out.println(ANSI_BRIGHT_BG_YELLOW + "███████████████████████████████████████████████████████████████");
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<SELECT WORKER  >>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("███████████████████████████████████████████████████████████████");
        }
        else if(action == ActionNames.PLACE_WORKERS){
            System.out.println(ANSI_BRIGHT_BG_YELLOW + "███████████████████████████████████████████████████████████████");
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<PLACE WORKER  >>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("███████████████████████████████████████████████████████████████");
        }

        int xPosition = -1, yPosition = -1;

        System.out.println(ANSI_BLACK_BACKGROUND + ANSI_BLUE + "Please enter the target tile coordinates: [x,y]" + ANSI_RESET);

        do {
            if (xPosition != -1) {
                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________please insert a valid range input____________>>>>>>>>>> " + ANSI_RESET);
                if (action == ActionNames.SELECT_WORKER)
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________you're supposed to select one of your workers____________>>>>>>>>>> " + ANSI_RESET);
            }

            try {

                System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Row : " + ANSI_RESET);
                xPosition = in.nextInt();

                System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Column : " + ANSI_RESET);
                yPosition = in.nextInt();

            } catch (InputMismatchException e) {

                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________this is not a number____________>>>>>>>>>> " + ANSI_RESET);
                in.nextLine();
            }


        } while (!(xPosition > -1 && xPosition < 5 && yPosition > -1 && yPosition < 5)
                || action == ActionNames.SELECT_WORKER && board[xPosition][yPosition].playerNumber != client.getPlayerNumber());



        return new Vector2d(xPosition,yPosition);

    }










    public static void ansi_reset()
    {
        System.out.print(ANSI_RESET + ANSI_CYAN);
    }


    /**
     * get players choiche about god power activation
     * @return boolean choice
     */
    public boolean getChoice() {

        System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Do you want to activate your God's power ? ");
        System.out.print("[y/n} :");
        String answer = in.nextLine();

        while (!"N".equalsIgnoreCase(answer) && !"Y".equalsIgnoreCase(answer)) {
            System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "Please try again" + ANSI_RESET);
            System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Do you want to activate your God's power ? :");
            answer = in.nextLine();
        }


        ansi_reset();
        return !"N".equalsIgnoreCase(answer);

    }



    public  Vector2d[] setWorkerPosition(ActionNames action, BoardMessage.PrintableTile[][] board)
    {
        Vector2d[] temp = new Vector2d[2];

        int count = 0;




        do{

            //salvo 2 posizioni

            System.out.println(ANSI_BLACK_BACKGROUND + ANSI_BLUE + "where do you want to place your "+(count+1)+" worker ?" + ANSI_RESET);

            temp[count] = getTargetTile(ActionNames.PLACE_WORKERS,board);
            count++;
            if (count == 2)
            {
                if(isSameTargetTile(temp))
                {
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________THIS IS YOUR PREVIOUS WORKERS POSITION____________>>>>>>>>>> " + ANSI_RESET);
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________please try again____________>>>>>>>>>> " + ANSI_RESET);
                    count--;
                }



            }

        }while (count < 2);



        return temp;
    }

    private  boolean isSameTargetTile(Vector2d[] temp) {

        for(int i =0; i <temp.length-1;i++)
        {
            if(temp[i].x == temp[i+1].x &&temp[i].y == temp[i+1].y) return  true;

        }


        return false;

    }


    //ANSI COLOR
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String Bright_Black = "\u001b[30;1m";

    //ANSI BACKGROUND
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    //ANSI BRIGHT
    public static final String ANSI_BRIGHT_BG_BLACK = "\u001B[100m";
    public static final String ANSI_BRIGHT_BG_RED = "\u001B[101m";
    public static final String ANSI_BRIGHT_BG_GREEN = "\u001B[102m";
    public static final String ANSI_BRIGHT_BG_YELLOW = "\u001B[103m";
    public static final String ANSI_BRIGHT_BG_BLUE = "\u001B[104m";
    public static final String ANSI_BRIGHT_BG_PURPLE = "\u001B[105m";
    public static final String ANSI_BRIGHT_BG_CYAN = "\u001B[106m";
    public static final String ANSI_BRIGHT_BG_WHITE = "\u001B[107m";

    //ANSI
    public static final String HIGH_INTENSITY = "\u001B[1m";
    public static final String LOW_INTENSITY = "\u001B[2m";
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String BLINK = "\u001B[5m";
}