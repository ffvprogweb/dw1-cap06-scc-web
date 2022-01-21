package com.fatec.scc.services;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import com.fatec.scc.model.Cliente;
import com.fatec.scc.model.Endereco;
import com.fatec.scc.ports.ClienteRepository;
import com.fatec.scc.ports.ClienteServico;

@Service
public class ClienteServicoI implements ClienteServico {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	ClienteRepository repository;

	public List<Cliente> consultaTodos() {
		logger.info(">>>>>> servico consultaTodos chamado");
		return repository.findAll();
	}
	@Override
	public Cliente consultaPorCpf(String cpf) {
		logger.info(">>>>>> servico consultaPorCpf chamado");
		return repository.findByCpf(cpf);
	}
	@Override
	public Optional<Cliente> consultaPorId(Long id) {
		logger.info(">>>>>> servico consultaPorId chamado");
		return repository.findById(id);
	}
	@Override
	public Cliente save(Cliente cliente) {
		logger.info(">>>>>> servico save chamado ");
		Optional<Cliente> umCliente = Optional.ofNullable(consultaPorCpf(cliente.getCpf()));
		
		Endereco endereco = obtemEndereco(cliente.getCep());
		if (umCliente.isEmpty() & endereco != null) {
			logger.info(">>>>>> controller create - dados validos");
			cliente.obtemDataAtual(new DateTime());
			cliente.setEndereco(endereco.getLogradouro());
			return repository.save(cliente);
		} else {
			return null;

		}
	}
	@Override
	public void delete(Long id) {
		logger.info(">>>>>> servico delete por id chamado");
		repository.deleteById(id);
	}
	@Override
	public Cliente altera(Cliente clienteModificado) {
		logger.info(">>>>>> servico altera cliente chamado");
		Optional<Cliente> cliente = consultaPorId(clienteModificado.getId());
		Endereco endereco = obtemEndereco(clienteModificado.getCep());
		if (cliente.isPresent() & endereco != null) {
			clienteModificado.obtemDataAtual(new DateTime());
			clienteModificado.setEndereco(endereco.getLogradouro());
			logger.info(">>>>>> servico altera cliente cep valido para o id => " + clienteModificado.getId());
			return repository.save(clienteModificado);
		} else {
			return null;
		}

	}
	public Endereco obtemEndereco(String cep) {
		RestTemplate template = new RestTemplate();
		String url = "https://viacep.com.br/ws/{cep}/json/";
		logger.info(">>>>>> servico consultaCep - " + cep);
		ResponseEntity<Endereco> resposta = null;
	
		try {
			resposta = template.getForEntity(url, Endereco.class, cep);
			return resposta.getBody();
		} catch (ResourceAccessException e) {
			logger.info(">>>>>> consulta CEP erro nao esperado ");
			return null;
		} catch (HttpClientErrorException e) {
			logger.info(">>>>>> consulta CEP inválido erro HttpClientErrorException =>" + e.getMessage());
			return null;
		}
	}
}

