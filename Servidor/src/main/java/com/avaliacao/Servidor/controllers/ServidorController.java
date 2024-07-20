package com.avaliacao.Servidor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacao.Servidor.services.Sensoriamento;


@RestController
@RequestMapping("/servidor")
public class ServidorController {

    @Autowired
    private Sensoriamento sensoriamento;

    // chama uma versao simplificada do metodo d.4 da avaliacao 1
    @PostMapping("/{entropia}")
    public void mediaConsumoSemanaAnterior(@RequestBody String dadoEncriptado, @PathVariable("entropia") Integer entropia) throws Exception {
        //sensoriamento.desencriptar(dadoEncriptado);
        sensoriamento.mediaConsumoSemanaAnterior(sensoriamento.desencriptar(dadoEncriptado, entropia));
    }

}
