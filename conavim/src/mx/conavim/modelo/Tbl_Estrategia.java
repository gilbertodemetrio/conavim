package mx.conavim.modelo;

public class Tbl_Estrategia {
	private int id_estrategia;
	private int id_objetivo;
	private String nombre_estrategia;
	private String descripcion;
	private int status;
	
	
	
	public int getId_estrategia() {
		return id_estrategia;
	}
	public void setId_estrategia(int id_estrategia) {
		this.id_estrategia = id_estrategia;
	}
	public int getId_objetivo() {
		return id_objetivo;
	}
	public void setId_objetivo(int id_objetivo) {
		this.id_objetivo = id_objetivo;
	}
	public String getNombre_estrategia() {
		return nombre_estrategia;
	}
	public void setNombre_estrategia(String nombre_estrategia) {
		this.nombre_estrategia = nombre_estrategia;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
			
}
