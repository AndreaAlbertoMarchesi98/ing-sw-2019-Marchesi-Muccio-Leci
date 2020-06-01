package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.controller.server.Server;
import it.polimi.ing.sw.psp017.view.CLI;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class provaClient {
    private static class Cli {
        public String variable;
    }

    private static class Boh implements Runnable {
        private final Cli cli;

        public Boh(Cli cli) {
            this.cli = cli;
        }

        public void run() {
            Scanner ciao= new Scanner(System.in);
            String string = ciao.nextLine();
            cli.variable = string;
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {

        Cli cli = new Cli();
        Thread t = new Thread(new Boh(cli));
        t.start();
        Thread.sleep(10000);
        t.interrupt();

        System.out.println(cli.variable);
    }
}
