/* 
 * Leks13
 * GPL v3
 */
package ru.leks13.jabbertimer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import org.jivesoftware.smack.XMPPException;

public class Main implements Runnable {
    public static long id;
    public static void main(String[] args) throws IOException, FileNotFoundException, XMPPException, InterruptedException {
        id = System.currentTimeMillis();
        System.out.println("Starting...");
        Runnable r = new Main();
        Thread th = new Thread(r);
        th.start();
        while (true) {
            try {
                Thread.sleep(300);
                try {
                    Timer.start();
                } catch (SQLException ex) {
                    System.out.println("SQL error \n"+ ex);
                } catch (Exception ex) {
                    System.out.println("Error \n"+ ex);
                }
            } catch (InterruptedException ex) {
                System.out.println("Interrupted error \n"+ ex);
            }
        }
    }

    @Override
    public void run() {
        try {
            Parse.ParseConfigFile();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (IOException ex) {
            System.out.println("Error");
        }
    }
}