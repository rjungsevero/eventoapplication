package com.eventoapp.eventoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.eventoapp.model.Convidado;
import com.eventoapp.eventoapp.model.Evento;
import com.eventoapp.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.eventoapp.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired
	private ConvidadoRepository convidadoRepository;
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
	
	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST )
	public String eventoForm(Evento evento) {
		eventoRepository.save(evento);
		return "redirect:/cadastrarEvento";
	}
	
	@RequestMapping(value = "/listaEventos", method = RequestMethod.GET)
	public ModelAndView listaEventos() {
		ModelAndView model = new ModelAndView("evento/listaEvento");
		Iterable<Evento> eventos = eventoRepository.findAll();
		model.addObject("eventos", eventos);
		return model;
	}
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		ModelAndView model = new ModelAndView("evento/detalhesEvento");
		Evento detalhesEvento = eventoRepository.findByCodigo(codigo);
		model.addObject("detalhesEvento", detalhesEvento);
		return model;
	}
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, Convidado convidado) {
		Evento evento = eventoRepository.findByCodigo(codigo);
		convidado.setEvento(evento);
		convidadoRepository.save(convidado);
		return "redirect:/{codigo}";
	}
}
