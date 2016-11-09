///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package model;

import java.util.ArrayList;
import java.util.List;

//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.lang.reflect.Type;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author robertwaters
// */
public class Facade {
    private static  Facade instance = new Facade();
//    private List<Account> accounts = new ArrayList<>();
//    private List<Report> reports = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();
//
    public static Facade getInstance() { return instance; }
//
    private Facade() { }
//
//    public void dumpData() {
//        System.out.println("Dumping Data from Model");
//        System.out.println("Account List:");
//        for (Account a : accounts) {
//            System.out.println(a);
//        }
//    }
//
//    public void addAccount(Account account) {
//        accounts.add(account);
//    }
//
//    public void saveAccountJson(File file) {
//        PersistenceManager pm = new PersistenceManager(accounts);
//        pm.saveToJson(file);
//    }
//
//    public void loadAccountJson(File file) {
//        PersistenceManager pm = new PersistenceManager(accounts);
//        pm.loadFromJsonfile(file);
//    }
//
//    public void saveReportJson(File file) {
//        PersistenceManager pm = new PersistenceManager(reports);
//        pm.saveToJson(file);
//    }
//
//    public void loadReportJson(File file) {
//        PersistenceManager pm = new PersistenceManager(reports);
//        pm.loadFromJsonfile(file);
//    }
//
//    public List<Account> getAccounts() {
//        return accounts;
//    }
//
//    public List<Report> getReports() {
//        return reports;
//    }
//
    public List<Location> getLocations() {
        return locations;
    }
}
