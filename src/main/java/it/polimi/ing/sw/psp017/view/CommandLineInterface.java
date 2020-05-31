package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.model.Game;
import it.polimi.ing.sw.psp017.model.Tile;


import java.io.Serializable;
import java.util.*;

public class CommandLineInterface{
    public static Scanner in = new Scanner(System.in);





    public static void main(String[] args) {

        PrintableTile[][] board = new PrintableTile[5][5];
        Random random = new Random();


        //create array from an enum
        ArrayList<GodName> cards = new ArrayList<>(EnumSet.allOf(GodName.class));





        for(int i = 0;i <5;i++)
        {
            for(int j = 0;j <5;j++)
            {
                board[i][j]= new PrintableTile(random.nextBoolean(),(int) (Math.random()*3),(int) (Math.random()*3 ));

            }


        }

        printBoard(board);


       // printWinner();
       // printLoser();
       // gameOver();

         // System.out.println(getChoice());
              //    System.out.println(chooseGodCard(cards));
              //    startingServerConnection();
                //ViewTile workerPosition = setWorkersPosition(board);

               //   ViewTile targetTile = getTile(board);
              //  System.out.println(getNickname());
              //  int answer = getNumberOfPlayers();
                //printBoard((BoardMessage.PrintableTileboard) board);




    }


    /**
     * player choose a card from the deck
     * @param cards arraylist of card
     * @return an allowed card
     */
    public static GodName chooseGodCard(ArrayList<GodName> cards)
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
    public static void printEnumGodName(ArrayList<GodName> cards)
    {
        for(int i = 0; i < cards.toArray().length;i++)
        {
            System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK +cards.get(i) + "[" +i+ "],  "+ANSI_RESET);
        }
        System.out.println();
    }

    /**
     * player choose an allowed tile from board when the game start
     *
     * @param board board game
     * @return selected target tile
     */
    public static ViewTile setWorkersPosition(ViewTile[][] board) {
        //printBoard(board);
        int xPosition = -1, yPosition = -1;

        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Set worker position in an empty tile \n" + ANSI_RESET);

        do {
            if (xPosition != -1) {
                if ((board[xPosition][yPosition].dome)) {
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________you have selected a dome____________>>>>>>>>>> " + ANSI_RESET);
                } else if (board[xPosition][yPosition].player != 0) {
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________this tile is not empty____________>>>>>>>>>> " + ANSI_RESET);
                }


            }


            do {
                if (xPosition != -1)
                {
                    //printBoard(board);
                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________Please try again____________>>>>>>>>>> " + ANSI_RESET);
                }


                try {

                    System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Row : " + ANSI_RESET);
                    xPosition = in.nextInt();

                    System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Column : " + ANSI_RESET);
                    yPosition = in.nextInt();

                    System.out.println(xPosition);
                    System.out.println(yPosition);


                } catch (InputMismatchException e) {

                    System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________this is not a number____________>>>>>>>>>> " + ANSI_RESET);
                    in.nextLine();
                }


            } while (!(xPosition > -1 && xPosition < 5 && yPosition > -1 && yPosition < 5));

        } while (board[xPosition][yPosition].dome || board[xPosition][yPosition].player != 0);



        return board[xPosition][yPosition];
    }


    /**
     * something to print in users CLI
     */
    public static void startingServerConnection(){
        printLogo();
        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Waiting for server connection \n" + ANSI_RESET);

    }

    /**
     * print logo of the game
     */
    public static void printLogo() {
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


    /**
     * get a valid nickname from the user
     * @return string name
     */
    public static String getNickname() {

        System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Please insert your nickname :");

        while (!in.hasNext()) {
            in.nextLine();
            System.out.print("Please insert a valid name :");
        }
        String nickname = in.nextLine();
        System.out.print(ANSI_RESET);
        return nickname;
    }

    /**
     * get players choiche about god power activation
     * @return boolean choice
     */
    public static boolean getChoice() {

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


    /**
     * get a generic tile from the board game
     * it can be used for move and build
     * @param board board game
     * @return a valid target tile
     */
    public static ViewTile getTile(ViewTile[][] board)
    {

       // printBoard(board);


        int xPosition = -1,yPosition = -1;

        System.out.println(ANSI_BLACK_BACKGROUND + ANSI_BLUE + "Please enter the target tile coordinates: [x,y]"+ ANSI_RESET);

        do {
            if(xPosition != -1) System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________please insert a valid range input____________>>>>>>>>>> " + ANSI_RESET);


            try {

                System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK +"Row : " + ANSI_RESET);
                xPosition = in.nextInt();

                System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK +"Column : " + ANSI_RESET);
                yPosition = in.nextInt();

            } catch (InputMismatchException e) {

                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________this is not a number____________>>>>>>>>>> " + ANSI_RESET);
                in.nextLine();
            }


        }while(!(xPosition>-1 && xPosition < 5 && yPosition>-1 && yPosition < 5));


        System.out.println();

        return board[xPosition][yPosition];

    }


    /**
     * first player has to choose the number of players
     * @return a number [1-3]
     */
    public static int getNumberOfPlayers()
    {

        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "How many players [1-3] ? " + ANSI_RESET);
        int answer = 0;

        do {

            if (answer != 0)
                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "please insert a valid number [1-3]" + ANSI_RESET);
            if (!in.hasNextInt()) {
                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "<<<<<<<<<<<____________this is not a number____________>>>>>>>>>> " + ANSI_RESET);
                System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "please insert a valid number [1-3]" + ANSI_RESET);
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





    public static void ansi_reset()
    {
        System.out.print(ANSI_RESET + ANSI_CYAN);
    }

    public static   void printBoard(PrintableTile[][] board) {

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
    public static void option(int i) {

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

                System.out.println(ITALIC + ANSI_BLUE_BACKGROUND + ANSI_BLACK + "PLAYER ONE :");





                ansi_reset();
                break;

            case 4:
                System.out.println(ITALIC + ANSI_BRIGHT_BG_YELLOW + ANSI_RED + "PLAYER TWO         ");

                ansi_reset();
                break;
            case 6:
                System.out.println("Tile :  " + ANSI_RED + "▲level" + ANSI_RESET + "     <<<<" + ANSI_RED + "DOME //" + ANSI_GREEN + "   FREE>>>>" + ANSI_CYAN + " tile's owner{♠♣♦}");
                ansi_reset();
                break;

            case 5:
                System.out.println(ITALIC + ANSI_PURPLE_BACKGROUND +ANSI_BLACK+ "PLAYER THREE       " );
                ansi_reset();
                break;
            case 7 :
            {
                System.out.println(ITALIC + ANSI_BRIGHT_BG_GREEN+ "              IS YOUR TURN" );
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

    public static void gameOver()
    {
        ansi_reset();
        System.out.println("\n" +
                " ██████╗  █████╗ ███╗   ███╗███████╗     ██████╗ ██╗   ██╗███████╗██████╗ \n" +
                "██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔═══██╗██║   ██║██╔════╝██╔══██╗\n" +
                "██║  ███╗███████║██╔████╔██║█████╗      ██║   ██║██║   ██║█████╗  ██████╔╝\n" +
                "██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗\n" +
                "╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║\n" +
                " ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝     ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝\n" +
                "                                                                          \n");

        ansi_reset();
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
    public static final String ANSI_BRIGHT_BG_BLACK  = "\u001B[100m";
    public static final String ANSI_BRIGHT_BG_RED    = "\u001B[101m";
    public static final String ANSI_BRIGHT_BG_GREEN  = "\u001B[102m";
    public static final String ANSI_BRIGHT_BG_YELLOW = "\u001B[103m";
    public static final String ANSI_BRIGHT_BG_BLUE   = "\u001B[104m";
    public static final String ANSI_BRIGHT_BG_PURPLE = "\u001B[105m";
    public static final String ANSI_BRIGHT_BG_CYAN   = "\u001B[106m";
    public static final String ANSI_BRIGHT_BG_WHITE  = "\u001B[107m";

    //ANSI
    public static final String	HIGH_INTENSITY		= "\u001B[1m";
    public static final String	LOW_INTENSITY		= "\u001B[2m";
    public static final String	ITALIC				= "\u001B[3m";
    public static final String	UNDERLINE			= "\u001B[4m";
    public static final String	BLINK				= "\u001B[5m";


    public static class PrintableTile implements Serializable {
        public int level;
        public boolean dome;
        public int playerNumber;



        //usata per debug prinyboard cli
        public PrintableTile(boolean dome,int level,int playerNumber)
        {
            this.level = level;
            this.dome = dome;
            this.playerNumber = playerNumber;
        }
    }

}