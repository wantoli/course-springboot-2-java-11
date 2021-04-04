package com.priscila.course.beans;

import java.util.List;

public class BadRequest {

	private int codigo;
	private String mensagem;
	private List<Campo> campos;

	public BadRequest(){
		super();
	}

	public BadRequest(int codigo, String mensagem, List<Campo> campos) {
		super();
		this.codigo = codigo;
		this.mensagem = mensagem;
		this.campos = campos;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}

	@Override
	public String toString() {
		return "BadRequest [codigo=" + codigo + ", mensagem=" + mensagem + ", campos=" + campos + "]";
	}
	
	
}
