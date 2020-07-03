package it.polimi.ing.sw.psp017.client.view;

import it.polimi.ing.sw.psp017.client.Client;
import it.polimi.ing.sw.psp017.client.PlayersInfo;
import it.polimi.ing.sw.psp017.client.view.GraphicUserInterface.GodView;
import it.polimi.ing.sw.psp017.server.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.server.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.server.model.Vector2d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class CLI implements View {

    private static final int MAX_NUMBER_OF_WORKERS = 2 ;
    private static final int NO_PLAYER = 0;
    private int NUMBER_OF_PLAYERS = 0;
    private  boolean isUndoPossible = false;
    private boolean timeOut = false;
    private ActionNames previousAction;




    private Client client;





    private Scanner in = new Scanner(System.in);
    private boolean hasAskedPowerActive = false;


    public CLI(Client client) {
        this.client = client;
        printLogo();

    }


    public void printLogo() {

        ansi_reset();
        System.out.println("  ██████  ▄▄▄       ███▄    █ ▄▄▄█████▓ ▒█████   ██▀███   ██▓ ███▄    █  ██▓");
        System.out.println("▒██    ▒ ▒████▄     ██ ▀█   █ ▓  ██▒ ▓▒▒██▒  ██▒▓██ ▒ ██▒▓██▒ ██ ▀█   █ ▓██▒");
        System.out.println("░ ▓██▄   ▒██  ▀█▄  ▓██  ▀█ ██▒▒ ▓██░ ▒░▒██░  ██▒▓██ ░▄█ ▒▒██▒▓██  ▀█ ██▒▒██▒");
        System.out.println("  ▒   ██▒░██▄▄▄▄██ ▓██▒  ▐▌██▒░ ▓██▓ ░ ▒██   ██░▒██▀▀█▄  ░██░▓██▒  ▐▌██▒░██░");
        System.out.println("▒██████▒▒ ▓█   ▓██▒▒██░   ▓██░  ▒██▒ ░ ░ ████▓▒░░██▓ ▒██▒░██░▒██░   ▓██░░██░");
        System.out.println("▒ ▒▓▒ ▒ ░ ▒▒   ▓▒█░░ ▒░   ▒ ▒   ▒ ░░   ░ ▒░▒░▒░ ░ ▒▓ ░▒▓░░▓  ░ ▒░   ▒ ▒ ░▓  ");
        System.out.println("░ ░▒  ░ ░  ▒   ▒▒ ░░ ░░   ░ ▒░    ░      ░ ▒ ▒░   ░▒ ░ ▒░ ▒ ░░ ░░   ░ ▒░ ▒ ░");
        System.out.println("░  ░  ░    ░   ▒      ░   ░ ░   ░      ░ ░ ░ ▒    ░░   ░  ▒ ░   ░   ░ ░  ▒ ░");
        System.out.println("      ░        ░  ░         ░              ░ ░     ░      ░           ░  ░  ");
        ansi_reset();
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
    public void notifySelectedTile(SelectedTileMessage selectedTileMessage) {
        client.getNetworkHandler().sendMessage(selectedTileMessage);
    }


    @Override
    public void notifyIsPowerActive(PowerActiveMessage powerActiveMessage) {

        client.getNetworkHandler().sendMessage(powerActiveMessage);

    }

    @Override
    public void notifyRestart(RestartMessage restartMessage) {

        client.getNetworkHandler().sendMessage( restartMessage);

    }





    /*
    @Override
    public void notifyDisconnection(DisconnectionMessage disconnectionMessage) {
        client.getNetworkHandler().closeConnection();
    }
     */

    @Override
    public void notifyUndo(UndoMessage undoMessage) {

    }

    /**
     * firstPlayer,whose created the game, set his playerNumber to 1,and select number of players
     * and cards
     */
    @Override
    public void updateGameCreation() {

        client.setPlayerNumber(1);

        //scegli il numero di giocatori
        NUMBER_OF_PLAYERS = getNUMBER_OF_PLAYERS();

        //create arrayList from enum
        ArrayList<GodName> cards = new ArrayList<>(EnumSet.allOf(GodName.class));
        ArrayList<GodName> selectedCards = new ArrayList<>();


        for(int i = 0; i < NUMBER_OF_PLAYERS; i++)
        {
            selectedCards.add(chooseGodCard(cards));
            cards.remove(selectedCards.get(i));
        }

        System.out.println(ANSI_CYAN_BACKGROUND + "selected cards :" + selectedCards);
        System.out.println("not Selected cards :" + cards + ANSI_RESET);


        notifyGameSetUp(new GameSetUpMessage(selectedCards));  //arraylist delle carte scelte));

    }


    @Override
    public void updateLobby( LobbyMessage lobbyMessage) {


        GodName choosenCard;
        if (client.getPlayerNumber() == 0) {
            client.setPlayerNumber(lobbyMessage.players.indexOf(client.getNickname()) + 1);

        }



        //store info in playerInfo when all players ,except the first one ,has selected a card
        /**
         * if (lobbyMessage.availableCards.size() == 1) {
         *
         *
         *             PlayersInfo[] playersInfos = new PlayersInfo[lobbyMessage.players.size()];
         *             playersInfos[0] = new PlayersInfo(lobbyMessage.players.get(0), 1, lobbyMessage.availableCards.get(0));
         *             client.playersInfo.add(playersInfos[0]);
         *             for (int i = 1; i < lobbyMessage.players.size(); i++) {
         *                 playersInfos[i] = new PlayersInfo(lobbyMessage.players.get(i), i + 1, lobbyMessage.chosenCards.get(i-1));
         *
         *                 client.playersInfo.add(playersInfos[i]);
         *             }
         *
         *
         *
         *
         *
         *
         *
         *         }
         */

        if (lobbyMessage.availableCards.size() == 1) {
            lobbyMessage.chosenCards.add(lobbyMessage.availableCards.get(0));

            client.playersInfo = new ArrayList<>();

            for(int i = 0; i < lobbyMessage.players.size();i++)
            {
                PlayersInfo playersInfos = new PlayersInfo(lobbyMessage.players.get(i), i+1, lobbyMessage.chosenCards.get(lobbyMessage.chosenCards.size()-1-i));
                client.playersInfo.add(playersInfos);
            }
        }



        //if it is my turn ,select my card from the available cards
        if(client.getPlayerNumber()==lobbyMessage.choosingPlayerNumber)
        {


            choosenCard = chooseGodCard(lobbyMessage.availableCards); // versione con eliminazione lato server
            client.setCard(choosenCard);
            notifyCard(new CardMessage(choosenCard));
        }







    }




    @Override
    public void updateBoard(BoardMessage boardMessage) {


        boolean answer; //answer used for undo

        //if it is my turn
        if (boardMessage.activePlayerNumber == client.getPlayerNumber())
        {

            //if can undo last move
            if(isUndoPossible)
            {

                final Timer timerThread = new Timer();
                timerThread.schedule(new TimerTask() {
                    int second = 5;
                    @Override
                    public void run() {
                        second--;
                        if (second == 0 ) {
                            timeOut = true;
                            timerThread.cancel();
                        }

                    }
                }, 0, 1000);

                answer = getUndoAnswer();
                if(!timeOut&&answer){
                    client.getNetworkHandler().sendMessage(new UndoMessage());
                    timerThread.cancel();
                    isUndoPossible =false;
                    return;

                }
                if(timeOut&&answer)
                {
                    System.out.println(ANSI_RED_BACKGROUND + ANSI_WHITE + "it is  too late for undo my dear....");
                    ansi_reset();
                }


                timeOut = false;

            }

            if( boardMessage.validTiles!= null && !validTiles(boardMessage))
            {


                if(previousAction == ActionNames.SELECT_WORKER)
                {
                    boardMessage.action = ActionNames.SELECT_WORKER;
                }


            }
        /*
        if(isUndoPossible){

            final Timer timerThread = new Timer();
            timerThread.schedule(new TimerTask() {
                int second = 5;
                @Override
                public void run() {
                    second--;
                    if (second == 0 ) {
                        noMoreTime = true;
                        timerThread.cancel();
                    }

                }
            }, 0, 1000);
            answer = getUndoAnswer();
            if(!noMoreTime && answer){
                client.getNetworkHandler().sendMessage(new UndoMessage());
                return;
            }
            if(noMoreTime) return;
            //se si undo mando messaggio  e metto isundopossible a false

        }
         */


            if(boardMessage.action == ActionNames.PLACE_WORKERS)
            {
                notifySelectedTile(new SelectedTileMessage(setWorkerPositionFinal(boardMessage)));

            }
            else if(boardMessage.action == ActionNames.SELECT_WORKER)
            {
                hasAskedPowerActive = false;
                notifySelectedTile(new SelectedTileMessage(selectWorker(boardMessage)));
            }
            else if(boardMessage.hasChoice&&!hasAskedPowerActive) //possibility to activate god power
            {
                //if player has choose to activate gods power (answer == yes)
                if(getChoice()) {
                    notifyIsPowerActive(new PowerActiveMessage(true));
                    return;
                }

                hasAskedPowerActive = true;
            }
            if(boardMessage.action == ActionNames.MOVE)
            {

                isUndoPossible = true;
                notifySelectedTile(new SelectedTileMessage(getMoveTargetTile(boardMessage)));
            }

            //case with no power
            if(boardMessage.action == ActionNames.BUILD)
            {

                isUndoPossible = true;

                //if true meas that players has asked undo in the previous move,only red tiles
                if(!validTiles(boardMessage)){
                    return;
                }
                notifySelectedTile(new SelectedTileMessage(getBuildTargetTile(boardMessage)));

                //store previous action used for undo function
                previousAction = boardMessage.action;
            }
            else{
                System.out.println(ANSI_CYAN);
                System.out.println("██████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████");
            }

        }

        //if is not my turn , i cannot undo anymore
        else isUndoPossible = false;

    }

    @Override
    public void updateDefeat(NoMovesMessage noMovesMessage) {

        ansi_reset();
        if(noMovesMessage.playerNumber == client.getPlayerNumber())
        {
            printLoser();
            restartGame();
        }
        else
        {
            System.out.println(ANSI_BLACK + client.playersInfo.get(noMovesMessage.playerNumber).card +
                    " has lost the game");
        }

    }

    public void restartGame()
    {
        String answer;
        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Do you want to play another match ? :");
        do {


            answer = in.nextLine();


        } while (!"N".equalsIgnoreCase(answer) && !"Y".equalsIgnoreCase(answer));


        ansi_reset();
        if(answer.equalsIgnoreCase("y")) {


            NUMBER_OF_PLAYERS = 0;
            isUndoPossible = false;
            timeOut = false;
            previousAction = null;
            notifyRestart(new RestartMessage());
        }
        else System.exit(0);

    }






    @Override
    public void updateVictory(VictoryMessage victoryMessage) {
        if(victoryMessage.winnerNumber == client.getPlayerNumber()) printWinner();
        else printLoser();
        printGameOver();

       restartGame();
    }

    @Override
    public void updateDisconnection(ServerDisconnectionMessage disconnectionMessage) {

        ansi_reset();
        if(disconnectionMessage.disconnectedPlayerNumber== client.getPlayerNumber())
        {
            System.out.println(ANSI_RED_BACKGROUND +"you have been disconnected");

        }
        else
        {
            System.out.println(ANSI_BLACK + client.playersInfo.get(disconnectionMessage.disconnectedPlayerNumber-1).card +
                    "has been disconnected");
            restartGame();
        }

    }

    public void updateLoginScreen(InvalidNameMessage invalidNameMessage) {
        if (invalidNameMessage != null) {
            System.out.println("Invalid Nickname");
        }
        String nickname = getNickname();
        client.setNickname(nickname);
        AuthenticationMessage am = new AuthenticationMessage(nickname);
        notifyNickname(am);
    }



    /**
     * first player has to choose the number of players
     * @return a number [1-3]
     */

    public int getNUMBER_OF_PLAYERS()
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
                } catch (InputMismatchException e ) {
                    in.nextLine();
                    answer = 0;
                }
            } else {
                answer = in.nextInt();
            }

        } while (answer > 3 || answer < 2);


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


    public void printBoard(BoardMessage.PrintableTile[][] board) {

        int line = 2;
        System.out.println(ANSI_CYAN);
        System.out.print("███████████████████████████████████████████████████████████████");
        option(0);
        System.out.print(ANSI_YELLOW_BACKGROUND + "██"+ ANSI_RED+ "    ►►►►0◄◄◄◄  ►►►►1◄◄◄◄  ►►►►2◄◄◄◄  ►►►►3◄◄◄◄  ►►►►4◄◄◄◄  " +ANSI_CYAN +"██"+ANSI_RESET+ ANSI_CYAN  );
        option(1);
        System.out.print(ANSI_CYAN);
        for (int i = 0; i < 5; i++) {
            System.out.println("██  ■ ═════════  ═════════  ═════════  ═════════  ═════════  ██                                          ██                                                           ");
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
                    switch (board[i][j].playerNumber)
                    {

                        case 0 :
                            System.out.print(ANSI_GREEN_BACKGROUND + ANSI_BLACK + " ");
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
        System.out.print("██  ■ ═════════  ═════════  ═════════  ═════════  ═════════  ██");
        option(7);

        //System.out.println("██    ═════════  ═════════  ═════════  ═════════  ═════════  ██");
        System.out.println("███████████████████████████████████████████████████████████████                                          ██████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████" +ANSI_RESET);

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
                System.out.println(ANSI_GREEN + "              ████████ ==  FREE      " + ANSI_CYAN );
                ansi_reset();
                break;
            case 1:
                System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + ITALIC + UNDERLINE + "                           LEGEND :                        ");
                ansi_reset();
                break;

            case 3:

                System.out.println(ITALIC + ANSI_BLUE_BACKGROUND + ANSI_BLACK + "PLAYER ONE : "+client.playersInfo.get(0).name +
                        "CARD : "+ client.playersInfo.get(0).card);

                ansi_reset();
                break;

            case 4:
                System.out.println(ITALIC + ANSI_BRIGHT_BG_YELLOW + ANSI_RED + "PLAYER TWO  "+client.playersInfo.get(1).name +
                        "CARD : "+ client.playersInfo.get(1).card);

                ansi_reset();
                break;
            case 6:
                System.out.println("Tile :  " + ANSI_RED + "▲level" + ANSI_RESET + "     <<<<" + ANSI_RED + "DOME //" + ANSI_GREEN + "   FREE>>>>" + ANSI_CYAN + " tile's owner{♠♣♦}");
                ansi_reset();
                break;

            case 5:
                if(client.playersInfo.size() == 3) {
                    System.out.print(ITALIC + ANSI_PURPLE_BACKGROUND + ANSI_BLACK + "PLAYER THREE : "+client.playersInfo.get(2).name +
                            "CARD : "+ client.playersInfo.get(2).card);
                }
                System.out.println();
                ansi_reset();
                break;
            case 7 :
            {
                System.out.println();
                //System.out.println(ITALIC + ANSI_BRIGHT_BG_GREEN+ "              IS YOUR TURN" );
                ansi_reset();
                break;

                //System.out.println(ANSI_BRIGHT_BG_RED+ "ENEMY'S TURN" );
            }


            case 0:
                System.out.println("██████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████");
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
        System.out.print(ANSI_YELLOW_BACKGROUND + "██"+ ANSI_RED+ "    ►►►►0◄◄◄◄  ►►►►1◄◄◄◄  ►►►►2◄◄◄◄  ►►►►3◄◄◄◄  ►►►►4◄◄◄◄  " +ANSI_CYAN +"██"+ANSI_RESET+ ANSI_CYAN  );
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


    private Vector2d selectWorker(BoardMessage boardMessage) {

        System.out.println("dentro select worker'");

            System.out.println(ANSI_BRIGHT_BG_BLACK + "███████████████████████████████████████████████████████████████\n" +"<<<<<<<<<<<<<<<<<<<<<<<<<<<<< SELECT WORKER >>>>>>>>>>>>>>>>>>>>>>>>>>>\n"+"███████████████████████████████████████████████████████████████" );

            ansi_reset();

            printBoard(boardMessage.board);

            Vector2d selectedWorkerTile = getTargetTileUnified();
            while(!checkSelectedOwnWorker(boardMessage,selectedWorkerTile)){

                printBoard(boardMessage.board);

                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________please select your own worker [GREEN]____________>>>>>>>>>> " + ANSI_RESET);
                selectedWorkerTile = getTargetTileUnified();

            }
            return selectedWorkerTile;




    }

    private boolean checkSelectedOwnWorker(BoardMessage boardMessage,Vector2d selectedWorkerTile) {


        return boardMessage.board[selectedWorkerTile.x][selectedWorkerTile.y].playerNumber == client.getPlayerNumber();
    }



     /**
     * this method is used to perform move and build steps
     * @param boardMessage game information
     * @return correctly selected target tile
     */

    public Vector2d getMoveTargetTile(BoardMessage boardMessage)
    {
        System.out.println(ANSI_PURPLE_BACKGROUND + "███████████████████████████████████████████████████████████████\n" +"<<<<<<<<<<<<<<<<<<<<<<<<<<<<< MOVE >>>>>>>>>>>>>>>>>>>>>>>>>>>\n"+"███████████████████████████████████████████████████████████████" );
        ansi_reset();



        return performMoveOrBuildGetTargetTile(boardMessage);


    }

    /**
     * this method is used to perform move and build steps
     * @param boardMessage game information
     * @return correctly selected target tile
     */
    private Vector2d performMoveOrBuildGetTargetTile(BoardMessage boardMessage)
    {
        printValidTiles(boardMessage.validTiles);

        Vector2d moveTargetTile = getTargetTileUnified();
        while(!checkValidTileSelection(boardMessage.validTiles,moveTargetTile)){

            printValidTiles(boardMessage.validTiles);

            System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________please select a valid tile [GREEN]____________>>>>>>>>>> " + ANSI_RESET);
            moveTargetTile = getTargetTileUnified();

        }
        return moveTargetTile;
    }

    /**
     * check if the player selected a valid target tile
     * @param validTiles allowed target tile
     * @param moveTargetTile user target tile
     * @return true if it is allowed
     */
    private boolean checkValidTileSelection(boolean[][] validTiles,Vector2d moveTargetTile) {


        /*
        if(validTiles[moveTargetTile.x][moveTargetTile.y]) return true;
        else return  false;
         */
        return validTiles[moveTargetTile.x][moveTargetTile.y];

    }


    /**
     *
     * @param boardMessage game information
     * @return vector2d selected target tile for build
     */
    private Vector2d getBuildTargetTile(BoardMessage boardMessage) {


        System.out.println(ANSI_BRIGHT_BG_GREEN + ANSI_RED+ "███████████████████████████████████████████████████████████████\n" +"<<<<<<<<<<<<<<<<<<<<<<<<<<<<< BUILD >>>>>>>>>>>>>>>>>>>>>>>>>>>\n"+"███████████████████████████████████████████████████████████████" );
        ansi_reset();

        return performMoveOrBuildGetTargetTile(boardMessage);


    }


    /**
     * this method is used every time user has to choose a target tile, which happens in every steps
     *
     * @return Vector2d selected target tile from player
     */
    public Vector2d getTargetTileUnified()
    {


        int xPosition = -1, yPosition = -1;

        System.out.println(ANSI_BLACK_BACKGROUND + ANSI_BLUE + "Please enter the target tile coordinates: [x,y]" + ANSI_RESET);

        do {
            if (xPosition != -1) {
                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________please insert a valid range input____________>>>>>>>>>> " + ANSI_RESET);

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


        } while (!(xPosition > -1 && xPosition < 5 && yPosition > -1 && yPosition < 5));

        return new Vector2d(xPosition,yPosition);

    }


    /**
     * cleaning text after ANSI colors
     */
    public static void ansi_reset()
    {
        System.out.print(ANSI_RESET + ANSI_CYAN);
    }


    /**
     * get players choice about god power activation
     * @return boolean choice
     */
    public boolean getChoice() {


        String answer;
        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + GodView.getCard(client.getCard()).getPowerDescription());
        do {


            answer = in.nextLine();


        } while (!"N".equalsIgnoreCase(answer) && !"Y".equalsIgnoreCase(answer));


        ansi_reset();
        return !"N".equalsIgnoreCase(answer);

    }




    /**
     * this method set workers position at the beginning of the game
     * @param boardMessage game information
     * @return Vector2d of two workers position
     */
    public  Vector2d setWorkerPositionFinal(BoardMessage boardMessage)
    {

        Vector2d selectedWorkersPosition = null ;

        System.out.println(ANSI_BRIGHT_BG_BLUE +ANSI_BLACK+ "███████████████████████████████████████████████████████████████\n" +"<<<<<<<<<<<<<<<<<<<<<<<<<<<<< PLACE WORKER >>>>>>>>>>>>>>>>>>>>>>>>>>>\n"+"███████████████████████████████████████████████████████████████" );

        ansi_reset();

        boolean done = false;
        printBoard(boardMessage.board);


            do {
                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_BLUE + "where do you want to place your  worker ?" + ANSI_RESET);

                if(selectedWorkersPosition!=null){
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________OCCUPIED TILE____________>>>>>>>>>> " + ANSI_RESET);
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________please try again____________>>>>>>>>>> " + ANSI_RESET);

                }

                selectedWorkersPosition= getTargetTileUnified();
                //if(checkFreeTargetTile(boardMessage,selectedWorkersPosition)) done = true;


            }while(!checkFreeTargetTile(boardMessage,selectedWorkersPosition));


        return selectedWorkersPosition;
    }

    /**
     * check if the target tile is occupied by an opponent workers
     * @param boardMessage board game
     * @param workerPosition Vector2d position in game board
     * @return true if target tile is empty
     */
    private boolean checkFreeTargetTile(BoardMessage boardMessage, Vector2d workerPosition) {


        return boardMessage.board[workerPosition.x][workerPosition.y].playerNumber == NO_PLAYER;
    }


    /**
     *check if user set workers in the same place
     * @param workersPosition Vector2d position in game board
     * @return true if it is the same target tile selected
     */
    private  boolean checkSameWorkerPlacement(Vector2d[] workersPosition ){

        for(int i =0; i <workersPosition.length-1;i++)
        {
            if(workersPosition[i].x == workersPosition[i+1].x &&workersPosition[i].y == workersPosition[i+1].y) return  true;

        }

       return false;


    }

    public static void printWinner()
    {
        ansi_reset();
        System.out.println(ANSI_GREEN+"\n" +
                "██╗██╗██╗██╗   ██╗ ██████╗ ██╗   ██╗     █████╗ ██████╗ ███████╗    ████████╗██╗  ██╗███████╗    ██╗    ██╗██╗███╗   ██╗███╗   ██╗███████╗██████╗ ██╗██╗██╗\n" +
                "██║██║██║╚██╗ ██╔╝██╔═══██╗██║   ██║    ██╔══██╗██╔══██╗██╔════╝    ╚══██╔══╝██║  ██║██╔════╝    ██║    ██║██║████╗  ██║████╗  ██║██╔════╝██╔══██╗██║██║██║\n" +
                "██║██║██║ ╚████╔╝ ██║   ██║██║   ██║    ███████║██████╔╝█████╗         ██║   ███████║█████╗      ██║ █╗ ██║██║██╔██╗ ██║██╔██╗ ██║█████╗  ██████╔╝██║██║██║\n" +
                "╚═╝╚═╝╚═╝  ╚██╔╝  ██║   ██║██║   ██║    ██╔══██║██╔══██╗██╔══╝         ██║   ██╔══██║██╔══╝      ██║███╗██║██║██║╚██╗██║██║╚██╗██║██╔══╝  ██╔══██╗╚═╝╚═╝╚═╝\n" +
                "██╗██╗██╗   ██║   ╚██████╔╝╚██████╔╝    ██║  ██║██║  ██║███████╗       ██║   ██║  ██║███████╗    ╚███╔███╔╝██║██║ ╚████║██║ ╚████║███████╗██║  ██║██╗██╗██╗\n" +
                "╚═╝╚═╝╚═╝   ╚═╝    ╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝       ╚═╝   ╚═╝  ╚═╝╚══════╝     ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝╚═╝\n" +
                "                                                                                                                                                           \n");
    }

    public static void printLoser()
    {
        ansi_reset();
        System.out.println(ANSI_RED+"\n" +
                "██╗   ██╗ ██████╗ ██╗   ██╗    ██╗      ██████╗ ███████╗███████╗    \n" +
                "╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║     ██╔═══██╗██╔════╝██╔════╝    \n" +
                " ╚████╔╝ ██║   ██║██║   ██║    ██║     ██║   ██║███████╗█████╗      \n" +
                "  ╚██╔╝  ██║   ██║██║   ██║    ██║     ██║   ██║╚════██║██╔══╝      \n" +
                "   ██║   ╚██████╔╝╚██████╔╝    ███████╗╚██████╔╝███████║███████╗    \n" +
                "   ╚═╝    ╚═════╝  ╚═════╝     ╚══════╝ ╚═════╝ ╚══════╝╚══════╝    \n" +
                "                                                                    \n");

    }

    public static void printGameOver()
    {

        System.out.println(ANSI_BRIGHT_BG_BLUE +ANSI_WHITE+ "██████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████");
        ansi_reset();
        System.out.println("\n" +
                " ██████╗  █████╗ ███╗   ███╗███████╗     ██████╗ ██╗   ██╗███████╗██████╗ \n" +
                "██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔═══██╗██║   ██║██╔════╝██╔══██╗\n" +
                "██║  ███╗███████║██╔████╔██║█████╗      ██║   ██║██║   ██║█████╗  ██████╔╝\n" +
                "██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗\n" +
                "╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║\n" +
                " ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝     ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝\n" +
                "                                                                          \n");
        System.out.println(ANSI_BRIGHT_BG_BLUE +ANSI_WHITE+ "██████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████");
        ansi_reset();
    }

    public boolean getUndoAnswer() {

        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "undo your last step?  ? :");
        Scanner answerScan = new Scanner(System.in);
        String answer = null;

        /**
         * do {
         *
         *
         *
         *             answer = answerScan.nextLine();
         *
         *
         *
         *         } while (!"N".equalsIgnoreCase(answer) && !"Y".equalsIgnoreCase(answer));
         */
        //String result = in.hasNext() ? in.next() : "";
       // answer = answerScan.nextLine();
        InputStreamReader inputStream = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(inputStream);
        do {
            try {
                answer = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!"N".equalsIgnoreCase(answer) && !"Y".equalsIgnoreCase(answer));

        ansi_reset();
        return "Y".equalsIgnoreCase(answer);

    }
    public boolean validTiles(BoardMessage b){
        for(int x = 0; x <5 ; x++)
            for (int y = 0 ; y <5; y++){
                if(b.validTiles[x][y])return true;
            }
        return false;
    }
    /**
     * get a valid nickname from the user
     * @return string name
     */
    public String getNickname() {

        System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Please insert your nickname :");

        while (!in.hasNext()) {
            in.nextLine();
            System.out.print("Please insert a valid name :");
        }
        String nickname = in.nextLine();
        System.out.print(ANSI_RESET);
        return nickname;
    }


    //ANSI COLOR
    public static final String ANSI_RESET =                  "\u001B[0m";
    public static final String ANSI_BLACK =                  "\u001B[30m";
    public static final String ANSI_RED =                    "\u001B[31m";
    public static final String ANSI_GREEN =                  "\u001B[32m";
    public static final String ANSI_YELLOW =                 "\u001B[33m";
    public static final String ANSI_BLUE =                   "\u001B[34m";
    public static final String ANSI_PURPLE =                 "\u001B[35m";
    public static final String ANSI_CYAN =                   "\u001B[36m";
    public static final String ANSI_WHITE =                  "\u001B[37m";
    public static final String Bright_Black =                "\u001b[30;1m";

    //ANSI BACKGROUND
    public static final String ANSI_BLACK_BACKGROUND =       "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND =         "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND =       "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND =      "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND =        "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND =      "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND =        "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND =       "\u001B[47m";

    //ANSI BRIGHT
    public static final String ANSI_BRIGHT_BG_BLACK =       "\u001B[100m";
    public static final String ANSI_BRIGHT_BG_RED =         "\u001B[101m";
    public static final String ANSI_BRIGHT_BG_GREEN =       "\u001B[102m";
    public static final String ANSI_BRIGHT_BG_YELLOW =      "\u001B[103m";
    public static final String ANSI_BRIGHT_BG_BLUE =        "\u001B[104m";
    public static final String ANSI_BRIGHT_BG_PURPLE =      "\u001B[105m";
    public static final String ANSI_BRIGHT_BG_CYAN =        "\u001B[106m";
    public static final String ANSI_BRIGHT_BG_WHITE =       "\u001B[107m";

    //ANSI
    public static final String HIGH_INTENSITY =             "\u001B[1m";
    public static final String LOW_INTENSITY =              "\u001B[2m";
    public static final String ITALIC =                     "\u001B[3m";
    public static final String UNDERLINE =                  "\u001B[4m";
    public static final String BLINK =                      "\u001B[5m";
}