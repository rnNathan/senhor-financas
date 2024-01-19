package model.dto;

public class RelatorioDTO {

	private String tipo;
	private int ano;
	private double total;
	public RelatorioDTO(String tipo, int ano, double total) {
		super();
		this.tipo = tipo;
		this.ano = ano;
		this.total = total;
	}
	
	
	public RelatorioDTO() {
		super();
	}


	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	} 
	
	
}

