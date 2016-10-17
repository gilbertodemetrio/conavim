package mx.conavim.modelo;

public class TblObjetivo {
	
	private int id_objetivo;
	private String nombre_objetivo;
	private String descripcion_obj;
	private String status;
	
	public TblObjetivo() { }
	
	public int getId_objetivo() {
		return id_objetivo;
	}
	public void setId_objetivo(int id_objetivo) {
		this.id_objetivo = id_objetivo;
	}
	public String getNombre_objetivo() {
		return nombre_objetivo;
	}
	public void setNombre_objetivo(String nombre_objetivo) {
		this.nombre_objetivo = nombre_objetivo;
	}
	public String getDescripcion_obj() {
		return descripcion_obj;
	}
	public void setDescripcion_obj(String descripcion_obj) {
		this.descripcion_obj = descripcion_obj;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Tbl_Objetivo [id_objetivo=" + id_objetivo + ", nombre_objetivo=" + nombre_objetivo
				+ ", descripcion_obj=" + descripcion_obj + ", status=" + status + "]";
	}

}
