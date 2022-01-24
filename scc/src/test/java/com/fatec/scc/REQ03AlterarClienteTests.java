package com.fatec.scc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.scc.model.Cliente;
import com.fatec.scc.ports.ClienteRepository;
import com.fatec.scc.ports.MantemCliente;
import com.fatec.scc.services.MantemClienteI;
@SpringBootTest
class REQ03AlterarClienteTests {
	private Logger logger = LogManager.getLogger(this.getClass());;
	@Autowired
	ClienteRepository repository;
	@Autowired
	MantemClienteI servico;
	
	@Test
	void ct01_quando_cliente_esta_cadastrado_retorna_cliente_alterado() {
		Cliente clienteModificado = new Cliente("Jose da Silva", "10/02/1960", "M", "99504993052", "04280130", "2983");
		clienteModificado.setDataCadastro("18/05/2020");
		clienteModificado.setId(1L);
		
		Cliente cliente = servico.altera(clienteModificado);
		assertEquals("Jose da Silva", cliente.getNome());
	}
	@Test
	void ct02_quando_cep_invalido_servico_altera_cliente_retorna_nulo() {
		Cliente clienteModificado = new Cliente("Jose da Silva", "10/02/1960", "M", "99504993052", "00", "2983");
		clienteModificado.setDataCadastro("18/05/2020");
		clienteModificado.setId(1L);
		Cliente cliente = servico.altera(clienteModificado);
		assertNull(cliente);
	}
}

