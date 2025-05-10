package com.examenpoo;

import javax.net.ssl.*;
import java.io.*;

public class SecureClient {

    private static final String HOST = "localhost";
    private static final int PORT = 5050;

    public static void main(String[] args)  throws Exception {
        System.setProperty("javax.net.ssl.trustStore", "server.keystore");
        System.setProperty("javax.net.ssl.trustStorePassword", "contrasena123");

        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try (SSLSocket socket = (SSLSocket) socketFactory.createSocket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){

            System.out.println("Conexion segura establecida con el servidor.");

            //enviando mensaje serializado
            Mensaje mensaje = new Mensaje("Hola desde el cliente jeje", "🫡");
            out.writeObject(mensaje);

            //out.writeObject("Hola desde el cliente🫡");
            System.out.println("Objeto enviado.");

            Object response = in.readObject();
            System.out.println("Respuesta del servidor: "+response.toString());
        }
    }
}
