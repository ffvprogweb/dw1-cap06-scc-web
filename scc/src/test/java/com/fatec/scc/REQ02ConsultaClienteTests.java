package com.fatec.scc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.scc.model.Cliente;

import com.fatec.scc.ports.MantemCliente;
@SpringBootTest
class REQ02ConsultaClienteTests {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	MantemCliente servico;
   
	@Test
	void ct01_quando_existem_dois_clientes_cadastrados_consulta_todos_retorna2() {

		// Dado que existem 2 clientes cadastrados (LoadDatabase)
		// Quando consulta todos
		List<Cliente> listaDeClientes = servico.consultaTodos();
		// Entao
		assertEquals(2, listaDeClientes.size());
	}
	@Test
	void ct02_quando_id_do_cliente_esta_cadastrado_consulta_por_id_retorna_detalhes_do_cliente() {

		// Dado que o cliente esta cadastrado
		// Quando consulta 
		Optional<Cliente> cliente = servico.consultaPorId(1L);
		// Entao retorna os detalhes
		assertTrue(cliente.isPresent());

	}
	@Test
	void ct03_quando_cpf_do_cliente_esta_cadastrado_consulta_por_cpf_retorna_detalhes_do_cliente() {

		// Dado que o cliente esta cadastrado
		// Quando consulta 
		Cliente cliente = servico.consultaPorCpf("43011831084");
		// Entao retorna os detalhes
		assertNotNull(cliente);

	}
	@Test
	void ct04_quando_cpf_valido_não_esta_cadastrado_consulta_por_cpf_retorna_cliente_null() {

		// Dado que o cliente esta cadastrado
		// Quando consulta 
		Cliente cliente = servico.consultaPorCpf("52825849219");
		// Entao retorna os detalhes
		assertNull(cliente);

	}
	@Test
	void ct05_quando_cpf_invalido_consulta_por_cpf_retorna_cliente_null() {

		// Dado que o cliente esta cadastrado
		// Quando consulta 
		Cliente cliente = servico.consultaPorCpf("5282584921");
		// Entao retorna os detalhes
		assertNull(cliente);

	}
}
