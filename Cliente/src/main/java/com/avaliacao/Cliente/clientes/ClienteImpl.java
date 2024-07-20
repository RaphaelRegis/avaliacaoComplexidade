package com.avaliacao.Cliente.clientes;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.crypto.Cipher;

import com.avaliacao.Cliente.entities.Hidrometro;
import com.avaliacao.Cliente.entities.Morador;
import com.avaliacao.Cliente.entities.Rua;
import com.google.gson.Gson;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class ClienteImpl implements Cliente, Runnable {

    // campos da classe
    private Rua rua;
    private final String URL_REQUISICAO = "http://localhost:8081/servidor";

    // construtor com complexidade O(1)
    public ClienteImpl(String nome) {
        this.rua = new Rua("Rua " + nome);
    }

    // ao iniciar a thread, ele fará a requisicao - complexidade O(1)
    @Override
    public void run() {

        Gson gson = new Gson();
        String ruaJson = gson.toJson(mediaConsumoSemanaAnterior());
        String encryptedJsonBytesBase64 = encriptarJson(ruaJson);

        //System.out.println("\nMinha chave Cliente: " + encryptedJsonBytesBase64);

        String milli = encryptedJsonBytesBase64.substring(0, encryptedJsonBytesBase64.indexOf(" "));
        encryptedJsonBytesBase64 = encryptedJsonBytesBase64.substring(encryptedJsonBytesBase64.indexOf(" ") +1);

        /* código para obter a chave publica */
        /*
         * String publicKeyBase64 =
         * Unirest.get("http://localhost:8080/encriptacao/chavePublica").asString().
         * getBody();
         * 
         * System.out.println("\n" + publicKeyBase64);
         * 
         * 
         * byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
         * 
         * try {
         * PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new
         * X509EncodedKeySpec(publicKeyBytes));
         * 
         * Cipher cipher = Cipher.getInstance("RSA");
         * cipher.init(Cipher.ENCRYPT_MODE, publicKey);
         * byte[] encryptedJsonBytes = cipher.doFinal(ruaJson.getBytes());
         * encryptedJsonBytesBase64 =
         * Base64.getEncoder().encodeToString(encryptedJsonBytes);
         * 
         * } catch (Exception e) {
         * System.out.println(e.getMessage());
         * }
         */

        // metodo que faz a requisicao HTTP POST atraves do Unirest
        /*
         * HttpResponse<JsonNode> response = Unirest
         * .post(URL_REQUISICAO)
         * .header("Content-Type", "application/json")
         * .body(encryptedJsonBytesBase64)
         * .asJson();
         */

        HttpResponse<JsonNode> response = fazerRequisicaoAoServidor(encryptedJsonBytesBase64, milli);

        // imprime o codigo de resposta da requisicao
        System.out.println("Response Code: " + response.getStatus());
    }

    // ira processar os dados e retornar um array com os parametros da url
    // complexidade O(N^3), pois possui 3 lacos "for" aninhados
    @Override
    public String[] mediaConsumoSemanaAnterior() {

        int nMoradoresTotal = 0;
        double somatorioConsumoSemanaMoradores = 0;

        for (Hidrometro hidrometro : rua.getHidrometros()) {
            for (Morador morador : hidrometro.getCasa().getMoradores()) {
                for (Integer consumo : morador.getConsumoSemanaAnterior()) {
                    somatorioConsumoSemanaMoradores += consumo;
                }
                nMoradoresTotal++;
            }
        }

        String mediaSemanaMoradores = String.format("%." + 2 + "f", somatorioConsumoSemanaMoradores / nMoradoresTotal);
        return new String[] { rua.getIdRua(),
                mediaSemanaMoradores };

    }

    public String encriptarJson(String json) {

        String milli = String.valueOf(LocalDateTime.now().getSecond());

        String publicKeyBase64 = Unirest.get("http://localhost:8080/encriptacao/chavePublica/{entropia}")
                .routeParam("entropia", milli)
                .asString()
                .getBody();

        String encryptedJsonBytesBase64 = "";

        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);

        try {
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedJsonBytes = cipher.doFinal(json.getBytes());
            encryptedJsonBytesBase64 = Base64.getEncoder().encodeToString(encryptedJsonBytes);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return milli+ " " + encryptedJsonBytesBase64;
    }

    public HttpResponse<JsonNode> fazerRequisicaoAoServidor(String jsonEncriptado, String milli) {
        return Unirest
                .post(URL_REQUISICAO+ "/{entropia}")
                .routeParam("entropia", milli.toString())
                .header("Content-Type", "application/json")
                .body(jsonEncriptado)
                .asJson();
    }
}
