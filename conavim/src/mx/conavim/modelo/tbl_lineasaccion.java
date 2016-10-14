package mx.conavim.modelo;

public class tbl_lineasaccion {
	private int id_lineaaccion;
	private int id_estrategia;
	private String nombre_linea;
	private String descripcion;
	
	
	public int getId_lineaaccion() {
		return id_lineaaccion;
	}
	public void setId_lineaaccion(int id_lineaaccion) {
		this.id_lineaaccion = id_lineaaccion;
	}
	public int getId_estrategia() {
		return id_estrategia;
	}
	public void setId_estrategia(int id_estrategia) {
		this.id_estrategia = id_estrategia;
	}
	public String getNombre_linea() {
		return nombre_linea;
	}
	public void setNombre_linea(String nombre_linea) {
		this.nombre_linea = nombre_linea;
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
	private int status;

	
}
