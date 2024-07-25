package com.avaliacao.Servidor.services;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class SensoriamentoImpl implements Sensoriamento {

    private static String CHAVE_PRIVADA_PATH = "src\\main\\java\\com\\avaliacao\\Servidor\\chavePrivada.txt";

    // metodo d.4 da avaliacao 1 simplificado
    // recebe apenas os resultados do que o cliente processou e os imprime
    // portanto, possui complexidade O(1)
    @Override
    public void mediaConsumoSemanaAnterior(String[] resultado) {
        String idRua = resultado[0];
        String mediaSemanaMoradores = resultado[1];

        System.out.println(idRua + " - Média do consumo total dos moradores ao longo da SEMANA anterior: "
                + mediaSemanaMoradores + " litros\n");
    }

    // metodo para desencriptar o json da requisicao
    // a desencriptacao com RSA possui complexidade O(N^3)
    @Override
    public String[] desencriptar(String dadoEncriptado) throws Exception {

        String privateKeyBase64 = coletarChave();
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);

        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        byte[] encryptedJsonBytes = Base64.getDecoder().decode(dadoEncriptado);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedJsonBytes = cipher.doFinal(encryptedJsonBytes);
        String decryptedJson = new String(decryptedJsonBytes);

        Gson gson = new Gson();
        String[] resultado = gson.fromJson(decryptedJson, String[].class);

        return resultado;
    }

    // metodo para coletar a chave privada de arquivo externo
    // a leitura itera sobre os caracteres, conferindo ao metodo a complexidade O(N)
    @Override
    public String coletarChave() throws Exception {
        String chavePrivada = "";
        chavePrivada = new String(Files.readAllBytes(Paths.get(CHAVE_PRIVADA_PATH)));
        return chavePrivada;
    }

}
