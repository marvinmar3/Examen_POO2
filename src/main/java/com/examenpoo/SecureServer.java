package com.examenpoo;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Scanner;

import com.examenpoo.Mensaje;


public class SecureServer {

    private static final int PORT = 5050;


    public static void main(String[] args) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try(FileInputStream fis = new FileInputStream("server.keystore")) {
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

                while(true) {
                    Object obj = in.readObject();
                    if(obj instanceof Mensaje) {
                        Mensaje mensaje = (Mensaje) obj;
                        System.out.println("Objeto recibido: "+mensaje);
                        
                        if(mensaje.getContenido().equalsIgnoreCase("Cliente se ha desconectado.")){
                            System.out.println("El cliente se ha desconectado.");
                            break;
                        }

                        out.writeObject("Servidor recibió correctamente");
                    }
                }

            }
        }
    }
}
