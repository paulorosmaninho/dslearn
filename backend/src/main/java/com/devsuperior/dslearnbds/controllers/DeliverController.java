package com.devsuperior.dslearnbds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dslearnbds.dtos.DeliverRevisionDTO;
import com.devsuperior.dslearnbds.services.DeliverService;

@RestController
@RequestMapping(value = "/deliveries")
public class DeliverController {

	@Autowired
	DeliverService deliverService;

	@PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody DeliverRevisionDTO deliverRevisionDTO) {

		deliverService.saveRevision(id, deliverRevisionDTO);

		return ResponseEntity.noContent().build();

	}

}
