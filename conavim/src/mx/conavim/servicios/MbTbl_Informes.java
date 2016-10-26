package mx.conavim.servicios;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mx.conavim.control.ConsultasDAO;
import mx.conavim.modelo.Tbl_EntidadSecre;
import mx.conavim.modelo.Tbl_Informes;

public class MbTbl_Informes {
	Tbl_Informes informe;
	Tbl_EntidadSecre entidad;
	ConsultasDAO consultas;
	List<Tbl_Informes> listaInformes;
	List<Tbl_EntidadSecre> listaEntidades;
	private String mensaje="";
	String idInforme="";
	
	
	
	
	public String getIdInforme() {
		return idInforme;
	}
	public void setIdInforme(String idInforme) {
		this.idInforme = idInforme;
	}
	
	public MbTbl_Informes()
	{
		this.informe=null;
		this.entidad=null;
		this.consultas=null;
		listaInformes=null;
	}
	@PostConstruct
	public void init()
	{
	informe= new Tbl_Informes();
	entidad= new Tbl_EntidadSecre();
	listaEntidades= new ArrayList<Tbl_EntidadSecre>();
	listaInformes= new ArrayList<Tbl_Informes>();
	consultas= new ConsultasDAO();
	try {
		listaEntidades= consultas.obtenerEntindadSec();
		allInformes();
		consultas.dispose();
	} catch (SQLException e) {
		consultas.dispose();
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	public void allInformes() throws SQLException{
	
			listaInformes=consultas.obtenerInforme();
			obtNomSiglasEnt();
	}
	public void restValores()
	{
		this.informe.setAnio(null);
		this.informe.setFecha_captura(null);
		this.informe.setFecha_modifi(null);
		this.informe.setId_informe(null);
		this.informe.setNombreEntidad(null);
		this.informe.setPeriodo(null);
		this.informe.setPrograma_presupe(null);
		this.informe.setSecretaria(0);
		this.informe.setSiglasEntidad(null);
		this.informe.setStatus(0);
		this.informe.setUnidad_enlaceResp(null);
		
	}
	public void insertInforme() {
		if(informe!=null && validarInsert()==true){
			consultas=new ConsultasDAO();
			boolean insert;
			try {
				int tipoMensaje=0;
				insert = consultas.insertInforme(informe);
				if(insert==true){
					allInformes();
					mensaje="Formato agregado";
					tipoMensaje=1;
					}
				else
					mensaje="No se pudo agregar correctamente el formato, verifique que no exista en la lista";
					
				notificacionMessage(tipoMensaje);
				} catch (SQLException e) {
				consultas.dispose();
					e.printStackTrace();
			}			
		}
		restValores();
		consultas.dispose();
	}
	private void obtNomSiglasEnt() {
		if(listaEntidades.size()> 0 && listaInformes.size()>0){
			
			for(int i=0; i<listaInformes.size(); i++)
			{
				Tbl_Informes informes= listaInformes.get(i);
				for(Tbl_EntidadSecre entidad:listaEntidades){
					if(informes.getSecretaria()== entidad.getId_secretaria())
					{
						listaInformes.get(i).setNombreEntidad(entidad.getNombre_entidadsec());
						listaInformes.get(i).setSiglasEntidad(entidad.getSiglas_entidad());
						break;
					}
				}
				
			}
		}
		
	}
	
	 public void notificacionMessage(int tipoMensaje) {
	        FacesContext context = FacesContext.getCurrentInstance();
	        if(tipoMensaje==0)
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"¡¡¡ERROR!!!",  "" + mensaje) );
	        else
	         context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"COMPLETADO",  "" + mensaje) );
	        
	        mensaje="";
	    }
	 
	 private boolean validarInsert(){
		 boolean insertAcep=true;
		 if(informe.getSecretaria()==0 || informe.getUnidad_enlaceResp().equals("")
			|| informe.getPeriodo().equals("")|| informe.getPrograma_presupe().equals("")){
			 insertAcep=false;
			 mensaje="Todos los campos son requeridos";
			 notificacionMessage(0);
		 }
		 return insertAcep;
	 }
	 public void redirectLineas(String id_informe, Tbl_Informes oTbInforme)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			this.idInforme=id_informe;
			//context.getExternalContext().redirect("formato.conavim?idInforme="+id_informe+"&informe="+oTbInforme);			
			context.getExternalContext().redirect("formato.conavim");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public Tbl_Informes getInforme() {
		return informe;
	}
	public void setInforme(Tbl_Informes informe) {
		this.informe = informe;
	}
	public Tbl_EntidadSecre getEntidad() {
		return entidad;
	}
	public void setEntidad(Tbl_EntidadSecre entidad) {
		this.entidad = entidad;
	}
	public List<Tbl_Informes> getListaInformes() throws SQLException {
		return listaInformes;
	}
	public void setListaInformes(List<Tbl_Informes> listaInformes) {
		this.listaInformes = listaInformes;
	}
	public List<Tbl_EntidadSecre> getListaEntidades() {
		return listaEntidades;
	}
	public void setListaEntidades(List<Tbl_EntidadSecre> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

}