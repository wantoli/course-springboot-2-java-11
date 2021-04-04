package com.priscila.course.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.priscila.course.delivery.ParseDto;
import com.priscila.course.delivery.UserDto;
import com.priscila.course.entities.User;
import com.priscila.course.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	private final Logger log = LoggerFactory.getLogger(UserResource.class);
	
	@Autowired
	private UserService service;
	
	@Autowired
	private ParseDto parse;
	
	@ApiOperation("Lista todos os usuarios")
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		
		log.info("Processando lista de usuarios");
		List<User> list = service.findAll();
		
		return ResponseEntity.ok().body(list);
		
	}
	
	@ApiOperation("Lista um determinado usuario por id")
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		
		log.info("processando lista de usuario por id");
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
		
	}

	@ApiOperation("Inserir um usuario")
	@PostMapping
	public ResponseEntity<User> insert(@Valid @RequestBody UserDto dto){
		
		log.info("Inserindo um usuario ");
		
		User obj = parse.parseDtoToUser(dto);
		
		obj = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		
		log.info("Usuario inserido com o codigo " + obj.getId().toString());
		
		return ResponseEntity.created(uri).body(obj);
		
		//return ResponseEntity.created(null).body(obj);
		
		//return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation("Excluir um usuario")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id) {
		log.info("Exclusao de usuario");
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation("Atualizar um usuario")
	@PutMapping(value= "/{id}")
	public ResponseEntity<User> update (@PathVariable Long id, @RequestBody User obj) {
		log.info("Atualizacao de usuario");
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	
}
