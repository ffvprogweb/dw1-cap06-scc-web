package com.fatec.scc.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fatec.scc.model.Cliente;
import com.fatec.scc.model.Endereco;
import com.fatec.scc.ports.ClienteRepository;

@Configuration
public class LoadDatabase {
	Logger log = LogManager.getLogger(this.getClass());

	@Bean
	CommandLineRunner initDatabase(ClienteRepository repository) {
		RestTemplate template = new RestTemplate();
		
		return args -> {
			Cliente cliente1 = new Cliente("Jose Antonio", "10/02/1960", "M", "99504993052", "04280130", "2983");
			cliente1.setDataCadastro("18/05/2020");
			ResponseEntity<Endereco> response = template.getForEntity("https://viacep.com.br/ws/{cep}/json/", Endereco.class, cliente1.getCep());
			cliente1.setEndereco(response.getBody().getLogradouro());
			log.info("Preloading " + repository.save(cliente1));
			
			Cliente cliente2 = new Cliente("Marcos Silva", "04/10/1974", "M", "43011831084", "08545160", "2983");
			cliente2.setDataCadastro("18/07/2019");
			response = template.getForEntity("https://viacep.com.br/ws/{cep}/json/", Endereco.class, cliente2.getCep());
			cliente2.setEndereco(response.getBody().getLogradouro());
			log.info("Preloading " + repository.save(cliente2));

		};
	}
	
}
