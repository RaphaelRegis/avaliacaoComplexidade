package com.avaliacao.Cliente.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rua {

    private String idRua;
    private List<Hidrometro> hidrometros;

    // define o numero de hidrometros em cada rua
    private final Integer NUMERO_HIDROMETROS = 10;
    
    // construtor com complexidade O(N^2), pois inclui laco "for" que chama outros laco "for"
    // atraves do construtor de Hidrometro
    public Rua(String idRua) {
        this.idRua = idRua;
        this.hidrometros = new ArrayList<>();

        // instancia os hidrometros
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
