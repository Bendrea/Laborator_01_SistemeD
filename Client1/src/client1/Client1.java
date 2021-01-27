/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author 
 */
public class Client1 {

    public static void main(String[] args) {

        String hostname = "192.168.1.100";
        int port = Integer.parseInt("9876");
        Socket socket;
        
        // verifica portul
        try {
            System.out.println("Looking for port "+ port);
            // varianta 1
            // socket = new Socket(hostname, port);

            // varianta 2 (timeout)            
            socket = new Socket();
            socket.setSoTimeout(200);
            socket.connect(new InetSocketAddress(hostname, port), 200);
            //System.out.println("There is a server on port " + port + " of " + hostname);
         } catch (IOException e) {
            System.out.println(e);
            return;
         }
        // System.out.println("ok.start");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                String msg = reader.readLine();                
                System.out.println(msg.replace('\t','\n'));
                System.out.print("\n$ ");

                String command;
                while ((command = scanner.nextLine()) != null) {
                    ////write command
                    writer.println(command);
                    System.out.println("Message sent to server");

                    // get response
                    System.out.print("Server response: ");
                    String response=reader.readLine();
                    System.out.println(response);
                    if(response.equals("bye")){
                        socket.close();
                        return;
                    }

                    System.out.print("\n$ ");
                }

            } catch (UnknownHostException ex) {
                System.out.println("Server not found: " + ex.getMessage());
            } catch (IOException ex) {
                System.out.println("I/O error: " + ex);
            }
            //Thread.sleep(10000);
        }
    }
}