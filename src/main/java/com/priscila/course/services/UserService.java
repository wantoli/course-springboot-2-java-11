package com.priscila.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.priscila.course.entities.User;
import com.priscila.course.repositories.UserRepository;
import com.priscila.course.services.exceptions.DatabaseException;
import com.priscila.course.services.exceptions.ResourceNotFoundException;
import com.priscila.course.utils.GeradorUUID;

@Service
public class UserService {
	
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository repository;
	
	public List<User> findAll() {
		
		log.info("Buscando todos os usuarios");
		return repository.findAll();
	}
	
	public User findById(Long id) {
		
		log.info("Buscando um usuario especifico");
		Optional<User> obj = repository.findById(id);
		//return obj.get();
		return obj.orElseThrow(() -> new ResourceNotFoundException(id, " Usuario nao encontrado "));
	}
	
	public User insert(User obj) {
		log.info("Inserindo um usuario");
		UUID idUnico = generatedUUID(obj);
		obj.setIdUnico(idUnico);
		return repository.save(obj);
	}
	
	public void delete (Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			log.error("Erro ao excluir um usuario - usuario nao encontrado");
			throw new ResourceNotFoundException(id, e.getMessage());
		} catch (DataIntegrityViolationException e) {
			log.error("Erro ao excluir um usuario - problema de integridade do Data Base");
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public User update(Long id, User obj) {
		try {
			log.info("Atualizando os dados de um usuario");
			User entity = repository.getOne(id); //nao vai no banco, mas o objeto fica monitorado
			entity = findById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			log.error("Erro ao atualizar um usuario - usuario nao encontrado");
			throw new ResourceNotFoundException(id, e.getMessage());
		}
	}

	private void updateData(User entity, User obj) {
		
		if (!(obj.getName() == null)) {
			entity.setName(obj.getName());
		}
		
		if (!(obj.getEmail() == null)) {
			entity.setEmail(obj.getEmail());
		}
		
		if (!(obj.getPhone() == null)) {
			entity.setPhone(obj.getPhone());
			UUID idUnico = generatedUUID(obj);
			entity.setIdUnico(idUnico);
		}
		
		if (!(entity.getPhone() == null)) {
			UUID idUnico = generatedUUID(entity);
			entity.setIdUnico(idUnico);
		}
		
		if (!(obj.getPassword() == null)) {
			entity.setPassword(obj.getPassword());
		}
		
/*		if (entity.getIdUnico() == null) {
			UUID idUnico = generatedUUID(obj);
			entity.setIdUnico(idUnico);
		}
*/		
	}
	
	private UUID generatedUUID(User obj) {
		String id = obj.getPhone();
		return GeradorUUID.getUUIDUnico(id);
	}
}
