/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author alexb
 */
public class Lab1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // TODO code application logic here
        
        int port = Integer.parseInt("9876");
        int[] vector = new int[]{0, 0, 1, 0, 1, 1, 1, 0, 0, 0};

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Serverul este gata de lucru, port " + port);

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("clientul s-a conectat la server");

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                String menu = " formatul comenzii: [comanda][pozitie 0..9]\t" + 
                              "comenzi accepptate: A - ask, R - reserve, Q - quit, O - anulare\t" +
                              "           exemplu: A2";
                writer.println(menu);

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.length() == 2 || line.charAt(0)=='q' ) {
                        switch (line.charAt(0)) {
                            case 'a':
                            case 'A':
                                if (Character.isDigit(line.charAt(1))) {
                                    int index = Character.getNumericValue(line.charAt(1));
                                    if (vector[index] == 0) {
                                        writer.println("pozitia " + line.charAt(1) + " este libera");
                                    } else {
                                        writer.println("pozitia " + line.charAt(1) + " este ocupata");
                                    }
                                } else {
                                    writer.println("** pozitie innexistenta **");
                                }
                                break;
                                
                            case 'r':
                            case 'R':
                                if (Character.isDigit(line.charAt(1))) {
                                    int index = Character.getNumericValue(line.charAt(1));
                                    if (vector[index] == 0) {
                                        writer.println("ai rezervat pozitia " + line.charAt(1));
                                        vector[index] = 1;
                                    } else {
                                        writer.println("pozitia " + line.charAt(1) + " este ocupata");
                                    }
                                } else {
                                    writer.println("** pozitie innexistenta **");
                                }
                                break;
                                
                                
                            case 'o':
                            case 'O':
                                if (Character.isDigit(line.charAt(1))) {
                                    int index = Character.getNumericValue(line.charAt(1));
                                    if (vector[index] == 1) {
                                        writer.println("Ai anulat rezervarea " + line.charAt(1));
                                        vector[index] = 0;
                                    } else {
                                        writer.println("pozitia " + line.charAt(1) + " este ocupata");
                                    }
                                } else {
                                    writer.println("** pozitie innexistenta **");
                                }
                                break;
                                
                           case 'q':
                           case 'Q':
                               writer.println("bye");
                               System.out.println("\nquit.");
                               return;
                               //System.exit(0);
                        }
                    } else {
                        writer.println("** comanda incorecta **");
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println("Exceptie server: " + ex.getMessage());
            //ex.printStackTrace();
        }
    }

    }
    

