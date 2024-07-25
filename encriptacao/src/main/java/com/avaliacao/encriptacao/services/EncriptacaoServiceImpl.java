package com.avaliacao.encriptacao.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Base64;

public class EncriptacaoServiceImpl implements EncriptacaoService {

    private KeyPair keyPair;
    private static String VIDEO_PATH = "src\\main\\java\\com\\avaliacao\\encriptacao\\video\\meuVideo.mp4";
    private static String PRIVATE_KEY_PATH = "..\\Servidor\\src\\main\\java\\com\\avaliacao\\Servidor";
    private static String PUBLIC_KEY_PATH = "..\\Cliente\\src\\main\\java\\com\\avaliacao\\Cliente";

    // construtor 
    // possui notação Big O variante de acordo com o trecho do video que sera usado como semente de encriptacao
    public EncriptacaoServiceImpl() throws Exception {

        // le o video
        byte[] videoBytes = Files.readAllBytes(Paths.get(
                VIDEO_PATH));

        // coleta uma fracao do video
        SecureRandom secureRandom = new SecureRandom();
        byte[] videoFractionBytes = new byte[secureRandom.nextInt(0, videoBytes.length - 1)];

        for (int i = 0; i < videoFractionBytes.length; i++) {
            videoFractionBytes[i] = videoBytes[i];
        }

        // gera o par de chaves com a fracao do video
        secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(videoFractionBytes);

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, secureRandom);
        this.keyPair = keyPairGenerator.generateKeyPair();
    }

    // metodo que salva a chave publica no Cliente e a chave privada no Servidor
    // complexidade O(N), pois itera sobre os caracteres das strings representantes das chaves
    @Override
    public void salvarParDeChaves() throws Exception {

        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

        BufferedWriter escritorChavePrivada = new BufferedWriter(
                new FileWriter(PRIVATE_KEY_PATH + "\\chavePrivada.txt"));
        escritorChavePrivada.write(privateKey);
        escritorChavePrivada.close();

        BufferedWriter escritorChavePublica = new BufferedWriter(
                new FileWriter(PUBLIC_KEY_PATH + "\\chavePublica.txt"));
        escritorChavePublica.write(publicKey);
        escritorChavePublica.close();

    }
}