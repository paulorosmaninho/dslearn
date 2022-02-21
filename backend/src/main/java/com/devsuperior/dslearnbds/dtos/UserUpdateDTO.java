package com.devsuperior.dslearnbds.dtos;

import com.devsuperior.dslearnbds.services.validation.UserUpdateValid;

// @UserUpdateValid - Annotation customizada para validar e-mail duplicado
@UserUpdateValid
public class UserUpdateDTO extends UserDTO {

	private static final long serialVersionUID = 1L;


}
