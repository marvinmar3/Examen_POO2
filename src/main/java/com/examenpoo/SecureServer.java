package com.examenpoo;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyStore;

public class SecureServer {

    public static final int PORT= 5000;

    public static void main(String[] args) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try(FileInputStream fis = new FileInputStream("server.jks")) {
            keyStore.load(fis, "contrasena123".toCharArray());
        }

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keyStore, "contrasena123".toCharArray());

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(kmf.getKeyManagers(),null,null );

        SSLServerSocketFactory socketFactory = context.getServerSocketFactory();
        SSLServerSocket serverSocket = (SSLServerSocket) socketFactory.createServerSocket(PORT);

        System.out.println("Servido seguro iniciado en el puerto " + PORT);

        while(true) {
            try(Socket socket = (SSLSocket) serverSocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                System.out.println("Cliente conectoado de forma segura");

                Object receivedObject = in.readObject();
                System.out.println("Objeto recibido: " + receivedObject.toString());

                out.writeObject("Objeto recibido con exito.");

            }
        }
    }
}
