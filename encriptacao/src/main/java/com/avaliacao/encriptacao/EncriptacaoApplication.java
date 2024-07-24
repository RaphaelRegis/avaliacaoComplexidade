package com.avaliacao.encriptacao;

import com.avaliacao.encriptacao.services.EncriptacaoService;
import com.avaliacao.encriptacao.services.EncriptacaoServiceImpl;

public class EncriptacaoApplication {

	public static EncriptacaoService getEncriptacaoService() throws Exception {
		return new EncriptacaoServiceImpl();
	}

	public static void main(String[] args) throws Exception {
		
		EncriptacaoService encriptacaoService = getEncriptacaoService();
		encriptacaoService.salvarParDeChaves();
	}

}
