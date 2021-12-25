/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.mycompany.multichat;
package clientchat;

import java.io.*;
import java.net.*;

/**
 *
 * @author yabok
 */
public class clientThread{
    private static Socket socket = null;
    private String nameSV = "";
    private String mssvSV = "";
    private static DataInputStream din;
    private static DataOutputStream dout;
    private static clientThread instance = null; //singleton
    private String codeRoom = "";

    public clientThread() {
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    

    public String getNameSV() {
        return nameSV;
    }

    public void setNameSV(String nameSV) {
        this.nameSV = nameSV;
    }

    public String getMssvSV() {
        return mssvSV;
    }

    public void setMssvSV(String mssvSV) {
        this.mssvSV = mssvSV;
    }

    public static clientThread getInstance() {
        if (instance == null)
            return instance = new clientThread();
        return instance;
    }

    public static void setInstance(clientThread instance) {
        clientThread.instance = instance;
    }

    public DataInputStream getDin() {
        return din;
    }

    public void setDin(DataInputStream din) {
        this.din = din;
    }

    public DataOutputStream getDout() {
        return dout;
    }

    public void setDout(DataOutputStream dout) {
        this.dout = dout;
    }    

    public String getCodeRoom() {
        return codeRoom;
    }

    public void setCodeRoom(String codeRoom) {
        this.codeRoom = codeRoom;
    }

    
    
}
