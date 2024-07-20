package com.avaliacao.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.avaliacao.Cliente.clientes.ClienteImpl;
import com.github.javafaker.Faker;

public class ClienteApplication {

	// define quantos clientes faraum requisicoes
	private static Integer NUMERO_CLIENTES = 10;

	// metodo para gerar clientes com complexidade O(N)
	public static List<ClienteImpl> gerarClientes() {
		List<ClienteImpl> clientes = new ArrayList<>();

		Locale locale = new Locale.Builder().setLanguage("pt").setRegion("BR").build();
		Faker faker = new Faker(locale);

		for (int i = 0; i < NUMERO_CLIENTES; i++) {
			clientes.add(new ClienteImpl(faker.name().fullName()));
		}

		return clientes;
	}

	// metodo para iniciar as threads com complexidade O(N)
	public static List<Thread> iniciarRequisicoes(List<ClienteImpl> clientes) {
		List<Thread> processos = new ArrayList<>();

		for (ClienteImpl cliente : clientes) {
			Thread processo = new Thread(cliente);
			processos.add(processo);
			processo.start();
		}

		return processos;
	}

	// metodo principal
	public static void main(String[] args) {
		iniciarRequisicoes(gerarClientes());
	}

}
