package com.example.reptepsp_uf3_mario_molina_app.sockets;

import com.example.reptepsp_uf3_mario_molina_app.datamodels.Usuari;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MySocket {

    private Socket socket;
    public static final int PORT = 5432;

    public MySocket(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void sendInt(int num) throws Exception {
        try{
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(num);
        }catch(Exception e){
            throw e;
        }
    }
    public int recieveInt() throws Exception {
        int num;
        try{
            DataInputStream is = new DataInputStream(socket.getInputStream());
            num = is.readInt();
        }catch(Exception e){
            throw e;
        }
        return num;
    }

    public void sendString(String msg) throws Exception {
        try{
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(msg);
        }catch(Exception e){
            throw e;
        }
    }

    public String recieveString() throws Exception {
        String msg;
        try{
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            msg = (String) ois.readObject();
        }catch(Exception e){
            throw e;
        }
        return msg;
    }

    public void close() throws Exception {
        try{
            socket.close();
        }catch(Exception e){
            throw e;
        }
    }

    public void accept(ServerSocket server) throws Exception {
        try{

            socket = server.accept();
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public Usuari rebreUsuari() {
        Usuari usuari = new Usuari();
        try {
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            String usuariString = (String )ois.readObject();
            String[] parts = usuariString.split(":");
            usuari = new Usuari(parts[0],parts[5],parts[1],parts[2],parts[4],parts[3],Double.parseDouble(parts[6]));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return usuari;
    }

    public void enviarUsuari(Usuari u) throws IOException {
        try
        {
            String usuari = u.getNomUsuari()+":"+u.getNom()+":"+u.getCognoms()+":"+u.getEmail()+":"+u.getCompteCorrent()+":"+u.getPassword()+":"+u.getSaldo();
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(usuari);
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public static boolean reciveBoolean(Socket socket) {
        boolean bool = false;
        try {
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            bool = (boolean) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return bool;
    }

    public static void sendBoolean(Socket socket,boolean bool) throws IOException {
        try
        {
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(bool);
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}
