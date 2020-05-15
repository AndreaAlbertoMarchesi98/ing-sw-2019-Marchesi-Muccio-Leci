package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.controller.client.Client;
import it.polimi.ing.sw.psp017.controller.client.PlayersInfo;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.model.Vector2d;

import java.util.*;


public class CLI implements View {

    private static final int MAX_NUMBER_OF_WORKERS = 2 ;
    private static final int NO_PLAYER = 0;
    private int NUMBER_OF_PLAYERS;


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
    public void updateLobby(LobbyMessage lobbyMessage) {

        System.out.println("PRIMO:");
        System.out.println("choosen cards : "+lobbyMessage.chosenCards);

        System.out.println("available cards : " +lobbyMessage.availableCards);



        System.out.println("dentro updateLobby");
        client.playersInfo = new ArrayList<>();
        System.out.println("player number : " + client.getPlayerNumber() + " choosing player number : "+ lobbyMessage.choosingPlayerNumber);
        GodName choosenCard;



        //ogni player ottiene il suo playerNumber

        if(client.getPlayerNumber() == 0)
        {
            System.out.println("memorizzo playerNumber");
            client.setPlayerNumber(lobbyMessage.players.indexOf(client.getNickname())+1);
            System.out.println("playerNumber settato : "+client.getPlayerNumber());

        }



        //turno del giocatore
        if(client.getPlayerNumber()==lobbyMessage.choosingPlayerNumber)
        {
            ArrayList<GodName> copy = new ArrayList<>();
            copy = lobbyMessage.availableCards;


            for (GodName godname : lobbyMessage.chosenCards
                 ) {
                copy.remove(godname);

            }
            System.out.println("choosen cards : "+lobbyMessage.chosenCards);

            System.out.println(lobbyMessage.availableCards);
            choosenCard = chooseGodCard(copy); // versione con eliminazione lato server
            notifyCard(new CardMessage(choosenCard));
        }



        if(lobbyMessage.availableCards.size() == 1)
        {
            System.out.println("dentro a size == 1");
            //salvo informazioni players
            PlayersInfo[] playersInfos = new PlayersInfo[lobbyMessage.players.size()];
            playersInfos[0] = new PlayersInfo(lobbyMessage.players.get(0),1,lobbyMessage.availableCards.get(0));
            client.playersInfo.add(playersInfos[0]);
            for(int i = 1; i < lobbyMessage.players.size();i++)
            {
                //occhio a i che è playerNumber ma lo usi come index
                playersInfos[i] = new PlayersInfo(lobbyMessage.players.get(i),i+1,lobbyMessage.chosenCards.get(i));

                client.playersInfo.add(playersInfos[i]);
                //else playersInfos[i] = new PlayersInfo(lobbyMessage.players.get(i),i+1,lobbyMessage.chosenCards.get())
            }

            for(int i = 0; i < client.playersInfo.size();i++)
            {
                System.out.println("nickname :"+client.playersInfo.get(i).name+
                                    "card name : "+ client.playersInfo.get(i).cards+
                                    "playerNumber  : "+ client.playersInfo.get(i).playerNumber);
            }




        }



        System.out.println("choosen cards : "+lobbyMessage.chosenCards);

        System.out.println("available cards : " +lobbyMessage.availableCards);

    }

    public PlayersInfo savePlayersInfo(LobbyMessage lobbyMessage)
    {
        String name = null;
        int playerNumber = 0;
        GodName cards = null;

        return new PlayersInfo(name,playerNumber,cards);


    }

    //last player added
    public PlayersInfo savePlayersInfo(LobbyMessage lobbyMessage,GodName choosenCard)
    {

        return new PlayersInfo(client.getNickname(),lobbyMessage.choosingPlayerNumber,choosenCard);


    }
/*
    private void saveOpponentPlayers(LobbyMessage lobbyMessage) {


        System.out.println("dentro saveOpponentplayers");

        client.playersInfo = new ArrayList<>();

        for(int i = 0; i < lobbyMessage.players.size();i++)
        {
            //client.playersInformation = new PlayersInformation(lobbyMessage.players.get(i),i);
            client.playersInfo.add(new PlayersInfo(lobbyMessage.players.get(i),i));
            //client.playersInformation[i] = new PlayersInformation(lobbyMessage.players.get(i),i+1);
        }


        for(int i = 0; i < client.playersInformation.size();i++)
        {
            //System.out.println("nome : "+ client.playersInformation[i].);
            client.playersInformation.toString();
        }
    }
*/
    @Override
    public void updateWaitingRoom(WaitMessage waitMessage) {

        System.out.println("dentro updateWaitingRoom");

        //gioatore in attessa di una nuova partita
        System.out.println("Number of players in waitingRoom : " + waitMessage.queueLength);

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
                temp = setWorkerPosition(boardMessage);
                //invio
                notifyPlacement(new PlacementMessage(temp[0], temp[1]));

            }
            else if(boardMessage.action == ActionNames.SELECT_WORKER)
            {
                hasAskedPowerActive = false;
                //scelta del worker da utilizzare

                //da fare : obbligo di scegliere un worker

                //Vector2d temp = selectWorker(boardMessage);
                notifySelection(new SelectionMessage(selectWorker(boardMessage)));

            }
            else if(boardMessage.hasChoice&&!hasAskedPowerActive) //possibilita di attivare il potere
            {

                //attivare il potere

                //activatePower = getChoice();  ?? serve

                //si o no

                notifyIsPowerActive(new PowerActiveMessage(getChoice()));

                hasAskedPowerActive = true;


            }
            else if(boardMessage.action == ActionNames.MOVE)
            {

                System.out.println("dentro a move  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

                notifyAction(new ActionMessage(getMoveTargetTile(boardMessage)));
            }


            else if(boardMessage.action == ActionNames.BUILD)//condizione senza poteri muovi costruisci
            {

                //   boardMessage.action == ActionNames.MOVE ||



                System.out.println("dentro a build >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

                notifyAction(new ActionMessage(getBuildTargetTile(boardMessage)));
                //salva posizione scelta lastWorkerposition == scegli worker

                /*
                printValidTiles(boardMessage.validTiles);
                notifyAction(new ActionMessage(getTargetTile(boardMessage.action, boardMessage.board)));
                 */

            }
            else{
                System.out.println("invalid action NONE");
            }



        }

        //CommandLineInterface.printBoard(boardMessage.board); //da cambiare in viewTile


    }

    @Override
    public void updateVictory(VictoryMessage victoryMessage) {
        if(victoryMessage.winnerNumber == client.getPlayerNumber()) printWinner();
        else printLoser();
        printGameOver();
    }

    @Override
    public void updateDisconnection(SDisconnectionMessage disconnectionMessage) {

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
                } catch (InputMismatchException e) {
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


        do {

            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Do you want to activate your God's power ? :");
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
    public  Vector2d[] setWorkerPosition(BoardMessage boardMessage)
    {

        Vector2d[] selectedWorkersPosition = new Vector2d[2];

        System.out.println(ANSI_BRIGHT_BG_BLUE +ANSI_BLACK+ "███████████████████████████████████████████████████████████████\n" +"<<<<<<<<<<<<<<<<<<<<<<<<<<<<< PLACE WORKER >>>>>>>>>>>>>>>>>>>>>>>>>>>\n"+"███████████████████████████████████████████████████████████████" );

        ansi_reset();

        int count = 0;
        boolean done = false;


        while(!done)
        {
            do {
                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_BLUE + "where do you want to place your " + (count + 1) + " worker ?" + ANSI_RESET);
                printBoard(boardMessage.board);

                selectedWorkersPosition[count] = getTargetTileUnified();

                if(!checkOccupiedTargetTile(boardMessage,selectedWorkersPosition[count])) count++;
                else{
                    printBoard(boardMessage.board);
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________OCCUPIED TILE____________>>>>>>>>>> " + ANSI_RESET);
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________please try again____________>>>>>>>>>> " + ANSI_RESET);

                }

            }while(count < MAX_NUMBER_OF_WORKERS);

            if(checkSameWorkerPlacement(selectedWorkersPosition))
            {
                printBoard(boardMessage.board);
                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________NOT ALLOWED PLACEMENT____________>>>>>>>>>> " + ANSI_RESET);
                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________please try again____________>>>>>>>>>> " + ANSI_RESET);
                count--;
            }
            else done = true;

        }
        return selectedWorkersPosition;
    }

    /**
     * check if the target tile is occupied by an opponent workers
     * @param boardMessage board game
     * @param workerPosition Vector2d position in game board
     * @return true if target tile is occupied
     */
    private boolean checkOccupiedTargetTile(BoardMessage boardMessage, Vector2d workerPosition) {


        return boardMessage.board[workerPosition.x][workerPosition.y].playerNumber != NO_PLAYER;
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