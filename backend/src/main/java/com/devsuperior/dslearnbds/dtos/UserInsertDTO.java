package com.devsuperior.dslearnbds.dtos;

import javax.validation.constraints.NotBlank;

import com.devsuperior.dslearnbds.services.validation.UserInsertValid;


// @UserInsertValid - Annotation customizada para validar e-mail duplicado
@UserInsertValid
public class UserInsertDTO extends UserDTO {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "A senha do usuário é um campo obrigatório")
	private String password;

	public UserInsertDTO() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
