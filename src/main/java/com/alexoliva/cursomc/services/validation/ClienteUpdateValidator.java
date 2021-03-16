package com.alexoliva.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.alexoliva.cursomc.domain.Cliente;
import com.alexoliva.cursomc.dto.ClienteDTO;
import com.alexoliva.cursomc.repositories.ClienteRepository;
import com.alexoliva.cursomc.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO>
{
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ClienteRepository repo;
	@Override
	public void initialize(ClienteUpdate ann) {
	}
	
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
			List<FieldMessage> list = new ArrayList<>();
			
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			Integer uriId = Integer.parseInt(map.get("id"));
			
			Cliente aux = repo.findByEmail(objDto.getEmail());
			if (aux != null && !aux.getId().equals(uriId)) {
				list.add(new FieldMessage("email", "Email já existente"));
			}
			
			for (FieldMessage e : list) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFielName())
				.addConstraintViolation();
			}
			return list.isEmpty();
		}
}