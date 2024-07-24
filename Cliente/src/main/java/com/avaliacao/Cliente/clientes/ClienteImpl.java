package com.avaliacao.Cliente.clientes;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
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
    private static String CHAVE_PUBLICA_PATH = "D:\\R.R. Araújo\\IFBA\\5º SEMESTRE\\06- Complex Algo\\Unidade 3\\Avaliação\\Cenario 2\\avaliacaoComplexidade\\encriptacao\\chavePublica.txt";

    // construtor com complexidade O(1)
    public ClienteImpl(String nome) {
        this.rua = new Rua("Rua " + nome);
    }

    // ao iniciar a thread, ele fará a requisicao - complexidade O(1)
    @Override
    public void run() {

        Gson gson = new Gson();
        String ruaJson = gson.toJson(mediaConsumoSemanaAnterior());
        String encryptedJsonBytesBase64 = "";

        // metodo para obter a chave publica *
        String publicKeyBase64 = coletarChave();

        // codigo para encriptar o json
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);

        try {
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedJsonBytes = cipher.doFinal(ruaJson.getBytes());
            encryptedJsonBytesBase64 = Base64.getEncoder().encodeToString(encryptedJsonBytes);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // chama o metodo para fazer as requisicoes
        enviar(encryptedJsonBytesBase64);

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

    // coleta a chave publica a partir de arquivo externo
    @Override
    public String coletarChave() {
        String chavePublica = "";

        try {
            chavePublica = new String(Files.readAllBytes(Paths.get(CHAVE_PUBLICA_PATH)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chavePublica;
    }

    // metodo que encripta o json
    /*public String encriptar() {
        
    }*/

    // metodo que faz a requisicao HTTP POST atraves do Unirest
    private void enviar(String encryptedJsonBytesBase64) {
        HttpResponse<JsonNode> response = Unirest
                .post(URL_REQUISICAO)
                .header("Content-Type", "application/json")
                .body(encryptedJsonBytesBase64)
                .asJson();

        // imprime o codigo de resposta da requisicao
        System.out.println("Response Code: " + response.getStatus());
    }
}
