package Servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class RecibirPakete {

    private DatagramSocket socket;

    public RecibirPakete() {
        try {
            this.socket = new DatagramSocket(6000);//Puerto al que mandar el paquete
        } catch (SocketException var2) {
            System.exit(1);
        }

    }
    public String ejecutarRecibir() {
        String mensaje = "";
        boolean recibido = false;

        while(!recibido) {

            try {

                byte[] datos = new byte[100];

                DatagramPacket recibirPaquete = new DatagramPacket(datos, datos.length);
                System.out.println("Esperando Conexión...");
                this.socket.receive(recibirPaquete);
                if (recibirPaquete != null){
                    recibido = true;
                    mensaje = new String(recibirPaquete.getData(), 0, recibirPaquete.getLength());
                }

            } catch (IOException var4) {
                var4.printStackTrace();
            }


        }

        return mensaje;




    }

    public static void main(String[] args) {
        RecibirPakete recibirPakete = new RecibirPakete();
        System.out.println(recibirPakete.ejecutarRecibir());

    }
}
