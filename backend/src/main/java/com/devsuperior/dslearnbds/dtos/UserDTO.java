package com.devsuperior.dslearnbds.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.devsuperior.dslearnbds.entities.User;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotBlank(message = "Primeiro nome do usuário é um campo obrigatório")
	private String name;

	@Email(message = "Informe um e-mail válido")
	private String email;

	Set<RoleDTO> rolesDTO = new HashSet<>();

	public UserDTO() {
	}

	public UserDTO(Long id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public UserDTO(User entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.email = entity.getEmail();

		// Solução EAGER para obtenção das lista de roles
		// O Fetch.type da entidade está como EAGER
		entity.getRoles().forEach(role -> this.rolesDTO.add(new RoleDTO(role)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleDTO> getRolesDTO() {
		return rolesDTO;
	}

}
