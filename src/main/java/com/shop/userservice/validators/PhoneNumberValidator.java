package com.shop.userservice.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.shop.userservice.dto.PhoneDTO;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNo, PhoneDTO>
{

	@Override
	public boolean isValid(PhoneDTO phone, ConstraintValidatorContext context) 
	{
		if(phone.getCountryCode() == null || phone.getNumber() == null) // if country code or number is null, 
			return false;												// return false.	
		
		else if(phone.getCountryCode().trim().isEmpty() || phone.getNumber().isEmpty()) // or if the country code or number is empty
			return false;																// return false.
		
		return true;
	}

}
