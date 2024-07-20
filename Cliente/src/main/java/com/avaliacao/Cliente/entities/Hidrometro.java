package com.avaliacao.Cliente.entities;

import java.util.Random;

public class Hidrometro {
    private Casa casa;
    private double consumoTotalM3;

    // construtor com complexidade O(N), pois o laço for depende de quantos moradores a casa tem
    public Hidrometro(Random rd){
        this.casa = new Casa(rd);

        this.consumoTotalM3 = 0;

        for (Morador morador : casa.getMoradores()) {
            this.consumoTotalM3 += morador.getConsumoAtual();
        }

        this.consumoTotalM3 = this.consumoTotalM3/1000;
    }

    //encapsulamento
    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public double getConsumoTotalM3() {
        return consumoTotalM3;
    }

    public void setConsumoTotalM3(double consumoTotalM3) {
        this.consumoTotalM3 = consumoTotalM3;
    }

    //toString
    @Override
    public String toString(){
        return casa.getNumero()+ ": " + consumoTotalM3 + " m³";
    }
}
