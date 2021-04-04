package com.priscila.course.delivery;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.priscila.course.annotation.PlacaCarro;

public class UserDto {

	@NotNull(message = "Nome precisa ser preenchido")
	private String name;
	
	@Email
	private String email;
	
	@NotNull(message = "Telefone eh obrigatorio")
	private String phone;
	
	@NotNull(message = "Senha precisa ser preenchida")
	private String password;
	
	private UUID idUnico;
	
	@PlacaCarro(message = "Placa carro precisa estar presente")
	private String placaCarro;
	
	public UserDto() {
		super();
	}
	
	public UserDto(String name, String email, String phone, String password, UUID idUnico, String placaCarro) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.idUnico = idUnico;
		this.placaCarro = placaCarro;
	}

	public String getPlacaCarro() {
		return placaCarro;
	}

	public void setPlacaCarro(String placaCarro) {
		this.placaCarro = placaCarro;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UUID getIdUnico() {
		return idUnico;
	}

	public void setIdUnico(UUID idUnico) {
		this.idUnico = idUnico;
	}

	@Override
	public String toString() {
		return "UserDto [name=" + name + ", email=" + email + ", phone=" + phone + ", password=" + password
				+ ", idUnico=" + idUnico + "]";
	}
		
	
}
