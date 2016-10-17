package mx.conavim.modelo;

import java.io.Serializable;
import java.sql.Date;

public class Tbl_Informes implements Serializable{

	private String id_informe;
	private int secretaria;
	private String anio;
	private String periodo;
	private Date fecha_captura;
	private Date fecha_modifi;
	private String programa_presupe;
	private String unidad_enlaceResp;
	private int status;
	private String nombreEntidad;
	private String siglasEntidad;
	
	public Tbl_Informes()
	{}

	public String getId_informe() {
		return id_informe;
	}

	public void setId_informe(String id_informe) {
		this.id_informe = id_informe;
	}

	public int getSecretaria() {
		return secretaria;
	}

	public void setSecretaria(int  secretaria) {
		this.secretaria = secretaria;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Date getFecha_captura() {
		return fecha_captura;
	}

	public void setFecha_captura(Date fecha_captura) {
		this.fecha_captura = fecha_captura;
	}

	public Date getFecha_modifi() {
		return fecha_modifi;
	}

	public void setFecha_modifi(Date fecha_modifi) {
		this.fecha_modifi = fecha_modifi;
	}

	public String getPrograma_presupe() {
		return programa_presupe;
	}

	public void setPrograma_presupe(String programa_presupe) {
		this.programa_presupe = programa_presupe;
	}

	public String getUnidad_enlaceResp() {
		return unidad_enlaceResp;
	}

	public void setUnidad_enlaceResp(String unidad_enlaceResp) {
		this.unidad_enlaceResp = unidad_enlaceResp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public String getSiglasEntidad() {
		return siglasEntidad;
	}

	public void setSiglasEntidad(String siglasEntidad) {
		this.siglasEntidad = siglasEntidad;
	}
	
}