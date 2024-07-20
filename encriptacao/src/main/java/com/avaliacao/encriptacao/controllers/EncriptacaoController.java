package com.avaliacao.encriptacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacao.encriptacao.services.EncriptacaoService;

@RestController
@RequestMapping("/encriptacao")
public class EncriptacaoController {
    
    @Autowired
    private EncriptacaoService encriptacaoService;

    @GetMapping("/chavePublica/{entropia}")
    public ResponseEntity<String> getPublicKey(@PathVariable("entropia") Integer entropia) throws Exception {
        return ResponseEntity.ok(encriptacaoService.getPublicKey(entropia));
    }

    @GetMapping("/chavePrivada/{entropia}")
    public ResponseEntity<String> getPrivateKey(@PathVariable("entropia") Integer entropia) throws Exception {
        return ResponseEntity.ok(encriptacaoService.getPrivateKey(entropia));
    }
}
