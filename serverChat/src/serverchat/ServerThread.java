/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.mycompany.multichat;
package serverchat;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yabok
 */
public class ServerThread extends Thread{
    private Socket socket;
    private DataOutputStream dout;
    private DataInputStream din;
    private User user;
    FileInputStream inFile = null;
    FileOutputStream outFile = null;
    
    String[] welcomeNote = {" trượt vào phòng.", " vừa mới xuất hiện!", " vừa mới đến.", " đang ở đây.", "Đã tham gia!", ". Chúng tôi hy vọng là bạn có mang theo pizza."};
    
    public ServerThread() {
        
    }

    public ServerThread(Socket socket, DataOutputStream dout, DataInputStream din) {
        this.socket = socket;
        this.dout = dout;
        this.din = din;
    }        

    public ServerThread(Socket socket, User user) {
        this.socket = socket;
        this.user = user;
    }

    public ServerThread(Socket socket) {
        this.socket = socket;
    }
    
    
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getDout() {
        return dout;
    }

    public void setDout(DataOutputStream dout) {
        this.dout = dout;
    }
    
    @Override
    public void run()
    {
        String content = "";
        File x = new File("logs.dat");
        try {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            user = new User();
            
            user.setMssv(new String(Base64.getDecoder().decode(din.readUTF().trim())));
            Thread.sleep(100);
            user.setName(new String(Base64.getDecoder().decode(din.readUTF().trim())));
            Thread.sleep(100);
            if (x.exists())
            {
                    Scanner scan = new Scanner(x);
                    
                    while(scan.hasNextLine()) {
                        content = scan.nextLine()+"\r\n";
                        dout.writeUTF(content);
                    }                    
                    scan.close();
            }
        
            //dout.writeUTF(content);
        
            Random rand = new Random();
            int n = rand.nextInt();
            if (n < 0 ) n *= -1;            
            n %= welcomeNote.length;
            
            sendToAllClients("\n ------------> Chào mừng "+ user.getName() + " (MSSV: " + user.getMssv() + ")"+ welcomeNote[n] + "\n");
            writeLogs("\n ------------> Chào mừng "+ user.getName() + " (MSSV: " + user.getMssv() + ")"+ welcomeNote[n] + "\n");
            while (true)
            {
                Thread.sleep(200);
                String msg = din.readUTF().trim();
                sendToAllClients(user.getName()+ ": " + msg);
                writeLogs(user.getName()+ ": " + msg + "\n");
            }
        } catch (IOException ex) {
            //Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            Server.getInstance().getThreads().remove(this);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sendToAllClients(String msg) throws IOException
    {
        for (ServerThread serverThread : Server.getInstance().getThreads())
                if (!serverThread.equals(this))
                    serverThread.dout.writeUTF(msg);
    }
    
    private void writeLogs(String msg) throws IOException
    {
        try {
            BufferedWriter writer = new BufferedWriter
                                        (new OutputStreamWriter(new FileOutputStream("logs.dat", true), StandardCharsets.UTF_8));
            writer.write(msg);
            writer.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
        
    }
}
