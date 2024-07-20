package com.avaliacao.encriptacao.services;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class EncriptacaoServiceImpl implements EncriptacaoService {

    private KeyPair keyPair;

    public EncriptacaoServiceImpl() throws Exception {
        byte[] videoBytes = Files.readAllBytes(Paths.get(
                "src\\main\\java\\com\\avaliacao\\encriptacao\\video\\meuVideo.mp4"));

        System.out.println("Meu rolão" + Arrays.asList(videoBytes));
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(videoBytes);

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, secureRandom);
        this.keyPair = keyPairGenerator.generateKeyPair();

    }

    @Override
    public String getPublicKey() {
        PublicKey publicKey = keyPair.getPublic();
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    @Override
    public String getPrivateKey() {
        PrivateKey privateKey = keyPair.getPrivate();
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /*
     * @Autowired
     * private ChavePrivadaRepository chavePrivadaRepository;
     * 
     * private final String videoPath =
     * "src\\main\\java\\com\\avaliacao\\encriptacao\\video\\meuVideo.mp4";
     * 
     * public String getPublicKey() throws Exception {
     * KeyPair keyPair = generateKeyPair();
     * PublicKey publicKey = keyPair.getPublic();
     * 
     * String chavePublica =
     * Base64.getEncoder().encodeToString(publicKey.getEncoded());
     * 
     * ChavePrivada chavePrivada = ChavePrivada.builder()
     * .chavePrivada(Base64.getEncoder().encodeToString(keyPair.getPrivate().
     * getEncoded()))
     * .build();
     * 
     * chavePrivadaRepository.save(chavePrivada);
     * 
     * //return Base64.getEncoder().encodeToString(publicKey.getEncoded());
     * return chavePrivada.getId()+ " " + chavePublica;
     * }
     * 
     * public String getPrivateKey(Integer id) throws Exception {
     * /*KeyPair keyPair = generateKeyPair();
     * PrivateKey privateKey = keyPair.getPrivate();
     * return Base64.getEncoder().encodeToString(privateKey.getEncoded());
     * return chavePrivadaRepository.findById(id).get().getChavePrivada();
     * 
     * }
     * 
     * private KeyPair generateKeyPair() throws Exception {
     * byte[] entropy = getEntropyFromVideo();
     * 
     * // Gere chave pública e privada usando os bytes de entropia como semente
     * SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
     * secureRandom.setSeed(entropy);
     * 
     * KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
     * keyPairGenerator.initialize(2048, secureRandom);
     * return keyPairGenerator.generateKeyPair();
     * }
     * 
     * private byte[] getEntropyFromVideo() throws IOException {
     * byte[] videoBytes = Files.readAllBytes(Paths.get(videoPath));
     * Random random = new Random();
     * 
     * // Escolher um segmento aleatório do vídeo
     * int segmentSize = 128; // Tamanho do segmento de bytes para a entropia
     * int randomStart = random.nextInt(videoBytes.length - segmentSize);
     * byte[] entropy = new byte[segmentSize];
     * System.arraycopy(videoBytes, randomStart, entropy, 0, segmentSize);
     * 
     * return entropy;
     * }
     */
}
