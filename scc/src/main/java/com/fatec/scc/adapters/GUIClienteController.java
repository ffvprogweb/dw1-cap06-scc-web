package com.fatec.scc.adapters;
import java.util.Optional;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.scc.model.Cliente;
import com.fatec.scc.ports.MantemCliente;

@Controller
@RequestMapping(path = "/sig")
public class GUIClienteController {
	Logger logger = LogManager.getLogger(GUIClienteController.class);
	@Autowired
	MantemCliente servico;

	@GetMapping("/clientes")
	public ModelAndView retornaFormDeConsultaTodosClientes() {
		ModelAndView modelAndView = new ModelAndView("consultarCliente");
		modelAndView.addObject("clientes", servico.consultaTodos());
		return modelAndView;
	}

	@GetMapping("/cliente")
	public ModelAndView retornaFormDeCadastroDe(Cliente cliente) {
		logger.info(">>>>>> controller form cadastro de cliente chamado ");
		ModelAndView mv = new ModelAndView("cadastrarCliente");
		mv.addObject("cliente", cliente);
		return mv;
	}

	@GetMapping("/clientes/{cpf}") 
	public ModelAndView retornaFormParaEditarCliente(@PathVariable("cpf") String cpf) {
		ModelAndView modelAndView = new ModelAndView("atualizarCliente");
		modelAndView.addObject("cliente", servico.consultaPorCpf(cpf)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/cliente/{id}")
	public ModelAndView excluirNoFormDeConsultaCliente(@PathVariable("id") Long id) {
		servico.delete(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id =>  " + id);
		ModelAndView modelAndView = new ModelAndView("consultarCliente");
		modelAndView.addObject("clientes", servico.consultaTodos());
		return modelAndView;
	}

	@PostMapping("/clientes")
	public ModelAndView save(@Valid Cliente cliente, BindingResult result) {
		logger.info(">>>>>> controller form cadastro de cliente chamou save");
		ModelAndView modelAndView = new ModelAndView("consultarCliente");
		if (result.hasErrors()) {
			logger.info(">>>>>> controller form cadastro de cliente erro na interface =" + result.getFieldError());
			modelAndView.setViewName("cadastrarCliente");
		} else {
			logger.info(">>>>>> controller form cadastro de cliente chamou save sem erro");
			servico.save(cliente);
			modelAndView.addObject("clientes", servico.consultaTodos());
		}
		return modelAndView;
	}

	@PostMapping("/clientes/{id}")
	public ModelAndView atualizaCliente(@PathVariable("id") Long id, @Valid Cliente cliente, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarCliente");
		if (result.hasErrors()) {
			cliente.setId(id);
			return new ModelAndView("atualizarCliente");
		} else {
			servico.altera(cliente);
		}
			
		return modelAndView;
	}
}
