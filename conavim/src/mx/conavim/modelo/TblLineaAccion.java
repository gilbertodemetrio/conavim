package mx.conavim.modelo;

public class TblLineaAccion {
	
	private int id_lineaaccion;
	private int id_estrategia;
	private String nombre_linea;
	private String descripcion;
	private String status;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
