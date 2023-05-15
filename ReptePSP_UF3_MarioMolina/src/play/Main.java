package play;

import datamodels.GamesBuyed;
import datamodels.Usuari;
import utilities.Blowfish;
import utilities.MySocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class Main {
    private MySocket client;
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(MySocket.PORT);
        try {
            System.out.println("Esperant una nova conexió d'un client");

            while (true) {
                MySocket client = new MySocket(server.accept());
                System.out.println("Client conectat");

                Thread thread = new Play(client);
                thread.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}