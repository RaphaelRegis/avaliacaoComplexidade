package com.avaliacao.encriptacao;

import com.avaliacao.encriptacao.services.EncriptacaoService;
import com.avaliacao.encriptacao.services.EncriptacaoServiceImpl;

public class EncriptacaoApplication {

	// metodo com complexidade O(1)
	public static EncriptacaoService getEncriptacaoService() throws Exception {
		return new EncriptacaoServiceImpl();
	}

	public static void main(String[] args) throws Exception {
		
		EncriptacaoService encriptacaoService = getEncriptacaoService();
		try {
			encriptacaoService.salvarParDeChaves();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Par de chaves salvo!");
	}

}
