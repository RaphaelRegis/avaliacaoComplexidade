package com.avaliacao.Servidor.entities;

import java.util.ArrayList;
import java.util.Random;

public class Morador {
    private int consumoAtual;
    private ArrayList<Integer> consumoSemanaAnterior;

    // construtor com complexidade O(1), pois o numero de entradas do for eh constante
    public Morador(Random rd){
        this.consumoAtual = rd.nextInt(75, 100);
        this.consumoSemanaAnterior = new ArrayList<>();
        
        for(int i=0; i<7; i++) {
            consumoSemanaAnterior.add(rd.nextInt(75, 100));
        }
    }
    
    // encapsulamento
    public int getConsumoAtual() {
        return consumoAtual;
    }
    
    public void setConsumoAtual(int consumoAtual) {
        this.consumoAtual = consumoAtual;
    }

    public ArrayList<Integer> getConsumoSemanaAnterior() {
        return consumoSemanaAnterior;
    }
    
    public void setConsumoSemanaAnterior(ArrayList<Integer> consumoSemanaAnterior) {
        this.consumoSemanaAnterior = consumoSemanaAnterior;
    }
}
