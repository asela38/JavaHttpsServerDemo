package com.asela;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class HttpsServer {

    private static final Path  KEY_STORE_PATH =
            Paths.get("C:/Asela/workspace/security/keystore20170720.jks");
    
    private static final Logger LOG = Logger.getGlobal();
    
    public static void main(String[] args) {
        new HttpsServer().start();
    }

    private void start() {
        SSLContext sslContext = createSSLContext();
        SSLServerSocketFactory serverFactory = sslContext.getServerSocketFactory();
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            ServerSocket serverSocket = serverFactory.createServerSocket(45454);
            Utility.decoratedPrint(serverSocket, "Server Started");
            IntStream.iterate(0, i -> i + 1).forEach( i -> {
                try {
                    SSLServerSocket sslServer = (SSLServerSocket) serverSocket;
            //        sslServer.setEnabledCipherSuites(sslServer.getSupportedCipherSuites());
                    
                    Socket clientSocket = sslServer.accept();
                    LOG.info(String.format("Accepted %d record : %s", i, clientSocket));
  
                    executor.submit(() -> {
                        SSLSocket sslSocket = (SSLSocket) clientSocket;
                        sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());
                        LOG.info("Supported Ciphers " + Arrays.toString(sslSocket.getSupportedCipherSuites()));
                                                
                        try {
                            sslSocket.startHandshake();
                            SSLSession session = sslSocket.getSession();
                            
                            LOG.info("SSLSession :" + session);
                        } catch (IOException e) {
                           LOG.log(Level.SEVERE, "ERROR", e);
                        }
                    });
                    
                } catch (IOException e) {
                   throw new UncheckedIOException(e);
                }
            });
            
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        
    }

    private SSLContext createSSLContext() {
        try {
            
            KeyStore keyStore = KeyStore.getInstance("JKS");
            LOG.info(String.format("KeyStore[Type=%s, privider=%s]",keyStore.getType(), keyStore.getProvider()));
            keyStore.load(Files.newInputStream(KEY_STORE_PATH), "password".toCharArray());
            
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, "password".toCharArray());
            
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            
            SSLContext sslContext = SSLContext.getInstance("TLSv1");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            
            LOG.info(String.format("SSLContext[protocol=%s, sslParameters.cipherSuites=%s]", sslContext.getProtocol(), 
                   Arrays.toString(sslContext.getDefaultSSLParameters().getCipherSuites())));
            
            return sslContext;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
