package com.avaliacao.Cliente.entities;

import java.util.ArrayList;
import java.util.Random;

public class Casa {
    private String numero;
    private ArrayList<Morador> moradores;

    // construtor com complexidade O(N), pois o for depende do numero de moradores
    public Casa(Random rd){
        this.moradores = new ArrayList<>();
        this.numero = "Casa nº " + rd.nextInt(1, 1000);

        int nMoradores = rd.nextInt(1, 6);

        for(int i=0; i< nMoradores; i++) {
            this.moradores.add(new Morador(rd));
        }
    }

    //encapsulamento
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ArrayList<Morador> getMoradores() {
        return moradores;
    }

    public void setMoradores(ArrayList<Morador> moradores) {
        this.moradores = moradores;
    }

    //toString com complexidade O(N), pois depende do numero de iteracoes dos moradores
    @Override
    public String toString(){
        String consumoTotal = "";
        int i = 1;

        for (Morador morador : moradores) {
            consumoTotal += "Morador " + i + ": " +  morador.getConsumoAtual() + " litros; \n";
            i++;
        }

        return numero + ":\n" + consumoTotal;
    }
}
