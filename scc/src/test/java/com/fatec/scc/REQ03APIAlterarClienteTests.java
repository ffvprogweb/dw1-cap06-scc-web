package com.fatec.scc;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fatec.scc.model.Cliente;
import com.fatec.scc.ports.ClienteRepository;
import com.fatec.scc.services.MantemClienteI;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class REQ03APIAlterarClienteTests {

	Logger logger = LogManager.getLogger(this.getClass());
	String urlBase = "/api/v1/clientes";
	@Autowired
	TestRestTemplate testRestTemplate;
	@Autowired
	MantemClienteI servico;
	
	//@Test
	void ct01_quando_cliente_esta_cadastrado_retorna_cliente_alterado() {
		Cliente clienteModificado = new Cliente("Jose da Silva", "10/02/1960", "M", "99504993052", "04280130", "2983");
		clienteModificado.setDataCadastro("18/05/2020");
		clienteModificado.setId(1L);
		System.out.println(clienteModificado.toString());

		HttpEntity<Cliente> httpEntity = new HttpEntity<>(clienteModificado);
		ResponseEntity<Cliente> resposta = testRestTemplate.exchange(urlBase + "/id/{id}",HttpMethod.PUT, httpEntity, Cliente.class, clienteModificado.getId());
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		Cliente cliente = resposta.getBody();
		System.out.println(cliente.toString());
		assertEquals("Jose da Silva", resposta.getBody().getNome());
	}

}
