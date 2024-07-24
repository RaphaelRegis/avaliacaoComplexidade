package com.avaliacao.Servidor.services;

public interface Sensoriamento {

    void mediaConsumoSemanaAnterior(String[] resultado);
    String[] desencriptar(String dadoEncriptado) throws Exception;
    String coletarChave();
    
}
