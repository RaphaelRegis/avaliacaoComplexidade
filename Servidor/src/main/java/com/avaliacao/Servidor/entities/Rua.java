package com.avaliacao.Servidor.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rua {

    private String idRua;
    private List<Hidrometro> hidrometros;


    private final Integer NUMERO_HIDROMETROS = 10;
    
    // construtor com complexidade O(N^2)
    public Rua(String idRua) {
        this.idRua = idRua;

        this.hidrometros = new ArrayList<>();

        for(int i=0; i< NUMERO_HIDROMETROS; i++) {
            hidrometros.add(new Hidrometro(new Random()));
        }
    }

    // encapsulamento
    public String getIdRua() {
        return idRua;
    }

    public void setIdRua(String idRua) {
        this.idRua = idRua;
    }

    public List<Hidrometro> getHidrometros() {
        return hidrometros;
    }

    public void setHidrometros(List<Hidrometro> hidrometros) {
        this.hidrometros = hidrometros;
    }
    
}
