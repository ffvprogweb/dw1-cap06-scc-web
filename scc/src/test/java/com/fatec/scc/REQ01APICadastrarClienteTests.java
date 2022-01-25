package com.fatec.scc;

import static org.junit.jupiter.api.Assertions.*;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fatec.scc.model.Cliente;
import com.fatec.scc.ports.MantemCliente;
import com.google.gson.Gson;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class REQ01APICadastrarClienteTests {
	String urlBase = "/api/v1/clientes";
	@Autowired
	TestRestTemplate testRestTemplate;
	@Autowired
	MantemCliente servico;
	@Test
	void ct01_quando_cliente_nao_cadastrado_retorna_detalhes_do_cliente() {
		
		Cliente cliente = new Cliente("Jose", "12/02/1960", "M", "32751358136", "04280130", "2983");
		cliente.obtemDataAtual(new DateTime());
		Gson dadosDeEntrada = new Gson();
		String entity = dadosDeEntrada.toJson(cliente);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<>(entity,headers);
		ResponseEntity<Cliente> resposta = testRestTemplate.exchange(urlBase,HttpMethod.POST, httpEntity, Cliente.class);
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		System.out.println("cliente enviado ==>" + cliente.toString());
		System.out.println("cliente obtido  ==>" + resposta.getBody());
		assertEquals(cliente.getCpf(), resposta.getBody().getCpf());
	}

}
