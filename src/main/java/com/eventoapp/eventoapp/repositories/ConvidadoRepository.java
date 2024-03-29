package com.eventoapp.eventoapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.eventoapp.eventoapp.models.Convidado;
import com.eventoapp.eventoapp.models.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {
	
	Iterable<Convidado> findByEvento(Evento evento);
	
	Convidado findByRg(String rg);

}
