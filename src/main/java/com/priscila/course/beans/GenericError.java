package com.priscila.course.beans;

public class GenericError {

	private int codigo;
	private String mensagem;
	
	public GenericError() {
		super();
	}

	public GenericError(int codigo, String mensagem) {
		super();
		this.codigo = codigo;
		this.mensagem = mensagem;
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

	@Override
	public String toString() {
		return "GenericError [codigo=" + codigo + ", mensagem=" + mensagem + "]";
	}
	
	
}
