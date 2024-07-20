package com.avaliacao.Servidor.services;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import kong.unirest.Unirest;

@Service
public class SensoriamentoImpl implements Sensoriamento {

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

    public String[] desencriptar(String dadoEncriptado) throws Exception {

        String privateKeyBase64 = Unirest.get("http://localhost:8080/encriptacao/chavePrivada").asString().getBody();
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

}
