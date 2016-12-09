package mx.conavim.modelo;

import java.io.Serializable;
import java.sql.Date;

public class Tbl_EntidadSecre implements Serializable {

	private int id_secretaria;
	private String nombre_entidadsec;
	private String siglas_entidad;
	private String tipo;
	private String status;
	private String ano;
	private Date fecha_captura;
	private Date fecha_nodifi;
	
   public Tbl_EntidadSecre() {
	}

public int getId_secretaria() {
	return id_secretaria;
}

public void setId_secretaria(int id_secretaria) {
	this.id_secretaria = id_secretaria;
}

public String getNombre_entidadsec() {
	return nombre_entidadsec;
}

public void setNombre_entidadsec(String nombre_entidadsec) {
	this.nombre_entidadsec = nombre_entidadsec;
}

public String getSiglas_entidad() {
	return siglas_entidad;
}

public void setSiglas_entidad(String siglas_entidad) {
	this.siglas_entidad = siglas_entidad;
}

public String getTipo() {
	return tipo;
}

public void setTipo(String tipo) {
	this.tipo = tipo;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getAno() {
	return ano;
}

public void setAno(String ano) {
	this.ano = ano;
}

public Date getFecha_captura() {
	return fecha_captura;
}

public void setFecha_captura(Date fecha_captura) {
	this.fecha_captura = fecha_captura;
}

public Date getFecha_nodifi() {
	return fecha_nodifi;
}

public void setFecha_nodifi(Date fecha_nodifi) {
	this.fecha_nodifi = fecha_nodifi;
}



}
