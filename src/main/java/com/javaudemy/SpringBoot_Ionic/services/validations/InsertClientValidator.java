package com.javaudemy.SpringBoot_Ionic.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.javaudemy.SpringBoot_Ionic.domain.dto.Client2DTO;
import com.javaudemy.SpringBoot_Ionic.domain.enums.ClientType;
import com.javaudemy.SpringBoot_Ionic.resources.exceptions.FieldMessage;
import com.javaudemy.SpringBoot_Ionic.services.validations.utils.BR;

public class InsertClientValidator implements ConstraintValidator<InsertClient, Client2DTO> {
	@Override
	public void initialize(InsertClient ann) {
	}

	@Override
	public boolean isValid(Client2DTO objDTO, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(objDTO.getType().equals(ClientType.PESSOAFISICA.getDescription()) && !BR.isValidCPF(objDTO.getCpfOrCnpj())) {
			list.add(new FieldMessage("cpfOrCnpj", "CPF inválido"));
		}
		
		if(objDTO.getType().equals(ClientType.PESSOAJURIDICA.getDescription()) && !BR.isValidCNPJ(objDTO.getCpfOrCnpj())) {
			list.add(new FieldMessage("cpfOrCnpj", "CNPJ inválido"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}