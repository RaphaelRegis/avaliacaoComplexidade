package com.avaliacao.Cliente.clientes;

public interface Cliente {
    
    String[] mediaConsumoSemanaAnterior();
    String coletarChave() throws Exception;
    String encriptar(String publicKeyBase64, String ruaJson) throws Exception;
    void enviar(String encryptedJsonBytesBase64);
}
