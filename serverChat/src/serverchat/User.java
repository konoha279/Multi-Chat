/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat;

import java.net.Socket;

/**
 *
 * @author yabok
 */
public class User {
    private String mssv;
    private String name;

    public User() {
    }

    public User(String mssv, String name) {
        this.mssv = mssv;
        this.name = name;
    }

    public String getMssv() {
        return mssv;
    }

    public String getName() {
        return name;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
