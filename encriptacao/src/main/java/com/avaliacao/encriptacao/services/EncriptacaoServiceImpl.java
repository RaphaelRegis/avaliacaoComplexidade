package com.avaliacao.encriptacao.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Base64;

public class EncriptacaoServiceImpl implements EncriptacaoService {

    private KeyPair keyPair;
    private static String VIDEO_PATH = "src\\main\\java\\com\\avaliacao\\encriptacao\\video\\meuVideo.mp4";

    // CONSTRUTOR
    public EncriptacaoServiceImpl() throws Exception {
        byte[] videoBytes = Files.readAllBytes(Paths.get(
                VIDEO_PATH));

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(videoBytes);

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, secureRandom);
        this.keyPair = keyPairGenerator.generateKeyPair();
    }

    // METODO DA INTERFACE
    @Override
    public void salvarParDeChaves() {
        /* esse metodo salvara o par de chaves em arquivos fora daqui */

        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

        try {

            /*as chaves devem ter seu caminho alterado para ficarem em seus respectivos programas */
            BufferedWriter escritorChavePrivada = new BufferedWriter(new FileWriter("chavePrivada.txt"));
            escritorChavePrivada.write(privateKey);
            escritorChavePrivada.close();

            BufferedWriter escritorChavePublica = new BufferedWriter(new FileWriter("chavePublica.txt"));
            escritorChavePublica.write(publicKey);
            escritorChavePublica.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}