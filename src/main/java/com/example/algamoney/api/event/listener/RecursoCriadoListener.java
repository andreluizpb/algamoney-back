package com.example.algamoney.api.event.listener;
/*
 * Listener de eventos
 * Recebe os eventos criados no pacote event
 * e executa rotina ligadas aos eventos 
 */
import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent>{

	@Override
	public void onApplicationEvent(RecursoCriadoEvent event) {

		HttpServletResponse response = event.getResponse();
		Long codigo = event.getCodigo();
		
		//Monta uri da pessoa existente e inclui no header
		adicionaHeaderLocation(response, codigo);
		
	}

	private void adicionaHeaderLocation(HttpServletResponse response, Long codigo) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(codigo).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}
