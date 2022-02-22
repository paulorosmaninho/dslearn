package com.devsuperior.dslearnbds.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslearnbds.dtos.NotificationDTO;
import com.devsuperior.dslearnbds.entities.Notification;
import com.devsuperior.dslearnbds.entities.User;
import com.devsuperior.dslearnbds.repositories.NotificationRepository;
import com.devsuperior.dslearnbds.repositories.UserRepository;
import com.devsuperior.dslearnbds.services.exceptions.DatabaseException;
import com.devsuperior.dslearnbds.services.exceptions.ResourceNotFoundException;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private AuthService authService;

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	public Page<NotificationDTO> notificationsForCurrentUserPaged(Pageable pageable) {

		User user = authService.authenticated();
		
		Page<Notification> pageNotificationEntity = notificationRepository.findByUser(user, pageable);
		
		Page<NotificationDTO> pageNotificationDTO = pageNotificationEntity
				.map(elementNotificationEntity -> new NotificationDTO(elementNotificationEntity));

		return pageNotificationDTO;

	}

	@Transactional(readOnly = true)
	public NotificationDTO findById(Long id) {

		Optional<Notification> objOptional = notificationRepository.findById(id);

		Notification entity = objOptional
				.orElseThrow(() -> new ResourceNotFoundException("Notification " + id + " não encontrado"));

		return new NotificationDTO(entity);

	}

	@Transactional
	public NotificationDTO insert(NotificationDTO notificationDTO) {

		Notification entity = new Notification();

		User user = userRepository.getOne(notificationDTO.getUserId());

		copyDtoToEntity(notificationDTO, entity);

		entity.setUser(user);

		entity = notificationRepository.save(entity);

		return new NotificationDTO(entity);

	}

	@Transactional
	public NotificationDTO update(Long id, NotificationDTO notificationDTO) {

		try {
			Notification entity = notificationRepository.getOne(id);

			User user = userRepository.getOne(notificationDTO.getUserId());

			copyDtoToEntity(notificationDTO, entity);

			entity.setUser(user);

			entity = notificationRepository.save(entity);

			return new NotificationDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Notification " + id + " não encontrado");
		}

	}

	public void delete(Long id) {
		try {

			notificationRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Notification " + id + " não encontrado");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	private void copyDtoToEntity(NotificationDTO dto, Notification entity) {

		entity.setMoment(dto.getMoment());
		entity.setRead(dto.isRead());
		entity.setRoute(dto.getRoute());
		entity.setText(dto.getText());

	}

}
