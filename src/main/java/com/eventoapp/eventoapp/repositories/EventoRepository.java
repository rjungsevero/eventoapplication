package com.eventoapp.eventoapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.eventoapp.eventoapp.models.Evento;

public interface EventoRepository extends CrudRepository<Evento, String>{
	
	Evento findById(long id);
	
}
