package hardware;




import arduino.Arduino;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tuyen
 */
public class FingerHW extends Thread{
    final public int REG_CMD=1;
    final public int ROLL_CMD=2;
    final public String port_name="/cu.SLAB_USBtoUART";
    final public int baud_rate=9600;

    public Arduino serial;
    public FingerHW() {
        serial = new Arduino(port_name, baud_rate); //enter the port name here, and ensure that Arduino is connected, otherwise exception will be thrown.
    }
    
    public void connect(){
        serial.openConnection();

    }
    public void sendCommand(Command c){
        Gson gson = new Gson();
        writeln(gson.toJson(c));
    }
    
    public void writeln(String s){
        serial.serialWrite(s);
        serial.serialWrite("\n");
    }
    
    public void run(){
       System.out.println("MyThread running");
        try {
            serial.getSerialPort().setComPortTimeouts(serial.getSerialPort().TIMEOUT_READ_BLOCKING, 50, 0);
            InputStreamReader is = new InputStreamReader(serial.getSerialPort().getInputStream());
            BufferedReader reader = new BufferedReader(is);
            int i=0;
            while (true) {
                String nextLine;
                try {
                      nextLine = reader.readLine();
                      System.out.println(nextLine);
                      System.out.println(i++);
                } catch (IOException e) {}
            }
        } 
        catch (Exception e) { e.printStackTrace(); }
    }
}
