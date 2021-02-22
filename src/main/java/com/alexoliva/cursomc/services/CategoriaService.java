package com.alexoliva.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexoliva.cursomc.domain.Categoria;
import com.alexoliva.cursomc.repositories.CategoriaRepository;
import java.util.Optional;
@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
		
	}
}
