package com.alexoliva.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.alexoliva.cursomc.domain.Cliente;
import com.alexoliva.cursomc.domain.enums.TipoCliente;
import com.alexoliva.cursomc.dto.ClienteNewDTO;
import com.alexoliva.cursomc.repositories.ClienteRepository;
import com.alexoliva.cursomc.resources.exception.FieldMessage;
import com.alexoliva.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>
{

	@Autowired
	private ClienteRepository repo;
	@Override
	public void initialize(ClienteInsert ann) {
	}
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
			List<FieldMessage> list = new ArrayList<>();
			
			if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
				list.add(new FieldMessage ("cpfOuCnpj", "CPF inválido"));
			}
			
			if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
				list.add(new FieldMessage ("cpfOuCnpj", "CNPJ inválido"));
			}
			
			Cliente aux = repo.findByEmail(objDto.getEmail());
			if (aux != null) {
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