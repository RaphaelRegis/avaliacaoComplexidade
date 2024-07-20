package com.avaliacao.encriptacao.services;

public interface EncriptacaoService {
    
    String getPublicKey(Integer entropia) throws Exception;
    String getPrivateKey(Integer entropia) throws Exception;
}
