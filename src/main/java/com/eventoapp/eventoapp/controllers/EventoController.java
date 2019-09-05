package com.eventoapp.eventoapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.eventoapp.models.Convidado;
import com.eventoapp.eventoapp.models.Evento;
import com.eventoapp.eventoapp.repositories.ConvidadoRepository;
import com.eventoapp.eventoapp.repositories.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired
	private ConvidadoRepository convidadoRepository;
	
	@Autowired
	private EventoRepository eventoRepository;

	@GetMapping("/cadastrarEvento")
	public String formEvento() {
		return "evento/formEvento";
	}
	
	@PostMapping("/cadastrarEvento")
	public String formEvento(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Preencha os campos obrigatórios");
			return "redirect:/cadastrarEvento";
		}
		eventoRepository.save(evento);
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
		return "redirect:/cadastrarEvento";
	}
	
	@GetMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView model = new ModelAndView("evento/listaEventos");
		Iterable<Evento> eventos = eventoRepository.findAll();
		model.addObject("eventos", eventos);
		return model;		
	}
	
	@GetMapping("/eventos/{id}")
	public ModelAndView detalhesEvento(@PathVariable("id") long id) {
		Evento evento = eventoRepository.findById(id);
		Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
		ModelAndView model = new ModelAndView("evento/detalhesEvento");
		model.addObject("evento", evento);
		model.addObject("convidados", convidados);		
		return model;	
	}
	
	@GetMapping("/eventos/{id}/excluirEvento")
	public String excluirEvento(long id) {
		Evento evento = eventoRepository.findById(id);
		eventoRepository.delete(evento);
		return "redirect:/eventos";
	}
	
	@PostMapping("/eventos/{id}")
	public String salvaConvidado(@PathVariable("id") long id, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos obrigatórios!");
			return "redirect:/eventos/{id}";
		}	
		Evento evento = eventoRepository.findById(id);
		convidado.setEvento(evento);
		convidadoRepository.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
		return "redirect:/eventos/{id}";	
	}
	
	@GetMapping("/eventos/{id}/excluirConvidado")
	public String excluirConvidado(String rg) {
		Convidado convidado = convidadoRepository.findByRg(rg);
		convidadoRepository.delete(convidado);
		Evento evento = convidado.getEvento();
		Object auxiliar = evento.getId();
		String id = auxiliar.toString();
		return "redirect:/eventos/" + id;
	}
	
}