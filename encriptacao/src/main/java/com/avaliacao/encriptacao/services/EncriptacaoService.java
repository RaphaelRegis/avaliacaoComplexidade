package com.avaliacao.encriptacao.services;

public interface EncriptacaoService {
    
    String getPublicKey() throws Exception;
    String getPrivateKey() throws Exception;
}
