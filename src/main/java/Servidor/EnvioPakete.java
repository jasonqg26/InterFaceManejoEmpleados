package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class EnvioPakete {
    private DatagramSocket socket;

    public EnvioPakete() {

        try { // crear objeto DatagramSocket para enviar y recibir paquetes

            socket = new DatagramSocket();

        } catch (SocketException excepcionSocket) {
            System.exit(1);
        }

    }

    public void ejecutarEnviar(String envio) throws IOException {

        try {
            String mensaje = envio;
            byte[] datos = mensaje.getBytes();
            // crear y enviar paquete
            DatagramPacket enviarPaquete = new DatagramPacket(datos, datos.length,
                    InetAddress.getByName("192.168.18.7"), 6000);

            socket.send(enviarPaquete);

        } catch (IOException excepcionES) {
        }

    }
    public static void main(String[] args) throws IOException {
        EnvioPakete aplicacion = new EnvioPakete();
        aplicacion.ejecutarEnviar("Me llamo jason un gusto en conocerte");
    }
}
