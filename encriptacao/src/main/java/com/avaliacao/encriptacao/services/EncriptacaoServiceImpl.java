package com.avaliacao.encriptacao.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class EncriptacaoServiceImpl implements EncriptacaoService {

    /*
     * private KeyPair keyPair;
     * 
     * public EncriptacaoServiceImpl() throws Exception {
     * byte[] videoBytes = Files.readAllBytes(Paths.get(
     * "src\\main\\java\\com\\avaliacao\\encriptacao\\video\\meuVideo.mp4"));
     * 
     * System.out.println("Meu rolão" + Arrays.asList(videoBytes));
     * SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
     * secureRandom.setSeed(videoBytes);
     * 
     * KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
     * keyPairGenerator.initialize(2048, secureRandom);
     * this.keyPair = keyPairGenerator.generateKeyPair();
     * 
     * }
     * 
     * @Override
     * public String getPublicKey() {
     * PublicKey publicKey = keyPair.getPublic();
     * return Base64.getEncoder().encodeToString(publicKey.getEncoded());
     * }
     * 
     * @Override
     * public String getPrivateKey() {
     * PrivateKey privateKey = keyPair.getPrivate();
     * return Base64.getEncoder().encodeToString(privateKey.getEncoded());
     * }
     */

    

    private final String videoPath = "src\\main\\java\\com\\avaliacao\\encriptacao\\video\\meuVideo.mp4";

    public String getPublicKey(Integer entropia) throws Exception {
        KeyPair keyPair = generateKeyPair(entropia);
        PublicKey publicKey = keyPair.getPublic();
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public String getPrivateKey(Integer entropia) throws Exception {
        KeyPair keyPair = generateKeyPair(entropia);
        PrivateKey privateKey = keyPair.getPrivate();
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());

    }

    private KeyPair generateKeyPair(Integer entropia) throws Exception {
        byte[] entropy = getEntropyFromVideo(entropia);

        // Gere chave pública e privada usando os bytes de entropia como semente
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(entropy);

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, secureRandom);
        return keyPairGenerator.generateKeyPair();
    }

    private byte[] getEntropyFromVideo(Integer entropia) throws IOException {
        /*byte[] videoBytes = Files.readAllBytes(Paths.get(videoPath));
        Random random = new Random();

        // Escolher um segmento aleatório do vídeo
        int segmentSize = 128; // Tamanho do segmento de bytes para a entropia
        int randomStart = random.nextInt(videoBytes.length - segmentSize);
        byte[] entropy = new byte[segmentSize];
        System.arraycopy(videoBytes, randomStart, entropy, 0, segmentSize);

        return entropy;*/
        byte[] videoBytes = Files.readAllBytes(Paths.get(videoPath));

        // Escolher um segmento aleatório do vídeo
        int segmentSize = 128; // Tamanho do segmento de bytes para a entropia
        int randomStart = videoBytes[0 + entropia];//random.nextInt(videoBytes.length - segmentSize);
        byte[] entropy = new byte[segmentSize];
        System.arraycopy(videoBytes, randomStart, entropy, 0, segmentSize);

        return entropy;
    }

}
