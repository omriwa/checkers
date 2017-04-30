package Model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Controller.Manager;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author omri
 */
public class Server {

    public static void main(String[] args) {

        try {
            Manager manager = new Manager();
            GameManager skeleton = (GameManager) UnicastRemoteObject.exportObject(manager, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("gameManager", manager);
        } catch (Exception e) {
            System.out.println("cannot set up the server");
        }
        System.out.println("server is up");
    }

}
