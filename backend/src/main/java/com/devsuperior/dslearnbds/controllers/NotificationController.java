package com.devsuperior.dslearnbds.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dslearnbds.dtos.NotificationDTO;
import com.devsuperior.dslearnbds.services.NotificationService;

@RestController
@RequestMapping(value = "/notifications")
public class NotificationController {

	@Autowired
	NotificationService notificationService;

	@GetMapping
	public ResponseEntity<Page<NotificationDTO>> findAll(Pageable pageable) {

		Page<NotificationDTO> pageDto = notificationService.notificationsForCurrentUserPaged(pageable);

		return ResponseEntity.ok().body(pageDto);

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<NotificationDTO> findById(@PathVariable Long id) {

		NotificationDTO notificationDTO = notificationService.findById(id);

		return ResponseEntity.ok().body(notificationDTO);

	}

	@PostMapping
	public ResponseEntity<NotificationDTO> insert(@RequestBody NotificationDTO notificationDTO) {

		notificationDTO = notificationService.insert(notificationDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(notificationDTO.getId())
				.toUri();

		return ResponseEntity.created(uri).body(notificationDTO);

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<NotificationDTO> update(@PathVariable Long id, @RequestBody NotificationDTO notificationDTO) {

		notificationDTO = notificationService.update(id, notificationDTO);

		return ResponseEntity.ok().body(notificationDTO);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		notificationService.delete(id);

		return ResponseEntity.noContent().build();

	}

}
