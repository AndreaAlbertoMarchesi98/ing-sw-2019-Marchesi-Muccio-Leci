package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.model.*;

import java.util.Scanner;

public class CLI {
    private Scanner input = new Scanner(System.in);
    public static void printTile(Tile tile){
        String worker=null;
        if(tile.getWorker()!=null)
            worker=tile.getWorker().toString();
        String level=String.valueOf(tile.getLevel());
        String dome=String.valueOf(tile.isDome());
        if(worker!=null)
            System.out.print("{"+worker+level+dome+"}");
        else
            System.out.print("{"+level+dome+"}");
    }
    public void printBoard(Board board){
        for(int y = 0; y< Board.size; y++){
            for(int x=0;x< Board.size;x++){
                printTile(board.getTile(new Vector2d(x,y)));
            }
            System.out.println();
        }
    }
    public static String getNickname(){
        Scanner in = new Scanner(System.in);
        System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Please insert your nickname :");

        while (!in.hasNext()) {
            in.nextLine();
            System.out.print("Please insert a valid name :");
        }
        String nickname = in.nextLine();
        System.out.println(ANSI_RESET);
        in.close();
        return nickname;
    }
    public static boolean getChoice(){
        Scanner in = new Scanner(System.in);
        System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Do you want to activate your God's power ? ");
        System.out.print("[y/n} :");
        String answer = in.nextLine();

        while (!"N".equalsIgnoreCase(answer) && !"Y".equalsIgnoreCase(answer))  {
            System.out.println(ANSI_BLACK_BACKGROUND+ ANSI_RED + "Please try again" + ANSI_RESET);
            System.out.print(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "Do you want to activate your God's power ? :");
            answer = in.nextLine();
        }

        in.close();
        return !"N".equalsIgnoreCase(answer);
    }
    public static Tile getTile(Board board){
        //controllare se è nei bouds
        return new Tile(new Vector2d(0,0));
    }
    public static void printValidTiles(ValidTiles validTiles, Board board){
        //controllare se è nei bouds
    }
    public static Tile getWorkerTile(Board board, Player player){
        //controllare se è nei bouds
        return new Tile(new Vector2d(0,0));
    }
    public static Tile getTargetTile(Board board, ValidTiles validTiles){
        //controllare se è una valid tile
        return new Tile(new Vector2d(0,0));
    }




    public static void printBoard() {


        System.out.println(ANSI_CYAN);
        System.out.println("███████████████████████████████████████████████████████████████");
        System.out.println("██    ►►►►0◄◄◄◄  ►►►►1◄◄◄◄  ►►►►2◄◄◄◄  ►►►►3◄◄◄◄  ►►►►4◄◄◄◄  ██");
        for (int i = 0; i < 5; i++) {
            System.out.println("██    ═════════  ═════════  ═════════  ═════════  ═════════  ██");
            //System.out.println("██    ═════════  ═════════  ═════════  ═════════  ═════════  ██");
            System.out.print("██    ");
            for (int j = 0; j < 5; j++)
            {
                System.out.print("║   ");
                if (i+j % 2 == 1) {
                    System.out.print(ANSI_RED_BACKGROUND);
                }
                System.out.print(j);
                System.out.print(ANSI_RESET);
                System.out.print(ANSI_CYAN);
                System.out.print("   ║  ");
                //option(i,j);
            }
            System.out.println("██");
        }
        System.out.println("██    ═════════  ═════════  ═════════  ═════════  ═════════  ██");
        System.out.println("███████████████████████████████████████████████████████████████"+ ANSI_RESET);

    }

    public static void printTile() {
        System.out.println("▬▬▬▬");
        System.out.println("▐   ▐");
        System.out.print("▬▬▬▬");
    }

    public static void printLogo(){
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


    //BACKGROUND ANSI COLOR
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";


}


