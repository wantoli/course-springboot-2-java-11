package com.priscila.course.beans;

public class Campo {

	private String campo;
	private String mensagem;
	private Object valor;
	
	public Campo() {
		super();
	}
	
	public Campo(String campo, String mensagem, Object valor) {
		super();
		this.campo = campo;
		this.mensagem = mensagem;
		this.valor = valor;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Campo [campo=" + campo + ", mensagem=" + mensagem + ", valor=" + valor + "]";
	}
	
	
}
