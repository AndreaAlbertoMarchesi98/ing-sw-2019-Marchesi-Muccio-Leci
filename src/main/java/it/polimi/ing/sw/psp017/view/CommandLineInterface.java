package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.model.Game;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class CommandLineInterface {


    private static Scanner in = new Scanner(System.in);


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


    public static void main(String[] args) {

        ViewTile[][] board = new ViewTile[5][5];

        printLogo();
        Random random = new Random();
        //System.out.println(getNickname());
        //System.out.println(getChoice());
        int answer = getNumberOfPlayers();


        for(int i = 0;i <5;i++)
        {
            for(int j = 0;j <5;j++)
            {
                board[i][j]= new ViewTile(random.nextBoolean(),(int) (Math.random()*3),(int) (Math.random()*3 + 1));

            }


        }
        printBoard(board);

    }







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


    public static String getNickname() {
        Scanner in = new Scanner(System.in);
        System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Please insert your nickname :");

        while (!in.hasNext()) {
            in.nextLine();
            System.out.print("Please insert a valid name :");
        }
        String nickname = in.nextLine();
        System.out.print(ANSI_RESET);
        //in.close();
        return nickname;
    }

    public static boolean getChoice() {
        Scanner in = new Scanner(System.in);
        System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Do you want to activate your God's power ? ");
        System.out.print("[y/n} :");
        String answer = in.nextLine();

        while (!"N".equalsIgnoreCase(answer) && !"Y".equalsIgnoreCase(answer)) {
            System.out.println(ANSI_BLACK_BACKGROUND + ANSI_RED + "Please try again" + ANSI_RESET);
            System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Do you want to activate your God's power ? :");
            answer = in.nextLine();
        }

        in.close();
        ansi_reset();
        return !"N".equalsIgnoreCase(answer);

    }

    /*
    public static ViewTile getTile(ViewTile[][] board)
    {
        int [] positions = {0,0,0}; // x,y,counter
        printBoard(board);
        int xPosition,yPosition;
        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Please enter the target tile coordinates: [x,y]");
        int answer = -1;
        do {
            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "example : tile [0,3] = 03");
            String position = in.next();
            xPosition = position.charAt(0);
            yPosition = position.charAt(2);

        }while((xPosition < 0 || xPosition > 4)&&(yPosition < 0 || yPosition > 4));


        System.out.println();
        return board;




    }
     */



    public static int getNumberOfPlayers()
    {
        Scanner in = new Scanner(System.in);
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

        in.close();
        ansi_reset();
        return answer;
    }

    public static void printBoard(ViewTile[][] board) {


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
                    switch (board[i][j].player)
                    {

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
                    System.out.print(board[i][j].player);
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


    public static void ansi_reset()
    {
        System.out.print(ANSI_RESET + ANSI_CYAN);
    }

    public static void option(int i){

        System.out.print("                                          ██");
        switch(i)
        {
            case 2 :
                System.out.print(ANSI_RED+ "     ████████ ==  DOME");
                ansi_reset();
                System.out.println(ANSI_GREEN+ "              ████████ ==  FREE      "+ANSI_CYAN+"██");
                ansi_reset();
                break;
            case 1 :
                System.out.println(ANSI_YELLOW_BACKGROUND+ANSI_RED + ITALIC + UNDERLINE +"                           LEGEND :                        "+ANSI_RESET +ANSI_CYAN+"██");
                ansi_reset();
                break;

            case 3 :

                System.out.print(ITALIC+ANSI_BLUE_BACKGROUND +ANSI_BLACK+ "PLAYER ONE           ");
                System.out.print(ANSI_BRIGHT_BG_YELLOW + ANSI_RED+ "PLAYER TWO         ");
                System.out.println(ANSI_BRIGHT_BG_PURPLE+ "PLAYER THREE       "+ANSI_RESET+ANSI_CYAN+"██");
                ansi_reset();
                break;

            case 4 :

                System.out.print(ITALIC+ANSI_BLUE_BACKGROUND + ANSI_BLACK + "God's name :         ");
                System.out.print(ANSI_BRIGHT_BG_YELLOW + ANSI_RED+ "God's name :       ");
                System.out.println(ANSI_BRIGHT_BG_PURPLE+ "God's name :       "+ANSI_RESET+ANSI_CYAN+"██");
                ansi_reset();
                break;
            case 5 :
                System.out.println("Tile :  "+ANSI_RED+"▲level"+ANSI_RESET+"     <<<<"+ANSI_RED+"DOME //"+ANSI_GREEN+ "   FREE>>>>" +ANSI_CYAN+" tile's owner{♠♣♦}██");
                ansi_reset();
                break;

            case 6 :
                System.out.println("SELECTED TILE :                                           ");
                ansi_reset();
                break;


            case 0 :
                System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██");
                break;
            default: System.out.println("                                                           ██");

        }



    }


}