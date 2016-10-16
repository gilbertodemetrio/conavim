package mx.conavim.servicios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;





import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.TabChangeEvent;

import mx.conavim.control.Tbl_lineasaccionDAO;
import mx.conavim.modelo.Tbl_Estrategia;
import mx.conavim.modelo.Tbl_Producto;
import mx.conavim.modelo.tbl_lineasaccion;

public class MbTbl_LineasAccion {

	Tbl_lineasaccionDAO consultas;
	private List<tbl_lineasaccion> lineasaccion = new ArrayList<tbl_lineasaccion>();
	private List<tbl_lineasaccion> tempLineas = new ArrayList<tbl_lineasaccion>();
	private List<Tbl_Estrategia>  estrategias = new ArrayList<Tbl_Estrategia>();
	private List<Tbl_Estrategia>  tempEstrategias = new ArrayList<Tbl_Estrategia>();
	private List<String> productos = new ArrayList<String>();
	private int estrategia=1;
	public int getEstrategia() {
		return estrategia;
	}

	private int objetivo=1;
	private String idDinamico;
	private int iddina=0;
	private int iddina2=0;
	
	
	
	public int getIddina2() {
//		if(this.iddina>30){
//			this.iddina=0;
//		}
		
		this.iddina2=this.iddina-1;
		return iddina2;
	}

	public int getIddina() {
		
		iddina+=1;
		return iddina;
	}

	public int getObjetivo() {
		return objetivo;
	}



	public void setObjetivo(int objetivo) {
		this.objetivo = objetivo;
	}



	public String getIdDinamico() {
		return idDinamico;
	}



	public void setIdDinamico(String idDinamico) {
		this.idDinamico = idDinamico;
	}



	public List<String> getProductos() {
		return productos;
	}



	public void setProductos(List<String> productos) {
		this.productos = productos;
	}



	public List<tbl_lineasaccion> getLineasaccion() {
		return lineasaccion;
	}



	public void setLineasaccion(List<tbl_lineasaccion> lineasaccion) {
		this.lineasaccion = lineasaccion;
	}



	@PostConstruct
	public void init() {
		consultas=new Tbl_lineasaccionDAO();
		tempLineas=consultas.getLineasAccion();
		productos = consultas.getProductos();
		tempEstrategias = consultas.getEstrategias();
		llenarEstrategias();
		llenarLineas();
	}
	public List<Tbl_Estrategia> getEstrategias() {
		return estrategias;
	}



	public void setEstrategias(List<Tbl_Estrategia> estrategias) {
		this.estrategias = estrategias;
	}



	public void llenarLineas(){
		System.out.println("ejecutando metodo llenar lineas para estrategia-->"+estrategia);
			lineasaccion.clear();
			for(tbl_lineasaccion val:tempLineas){
			//System.out.println("----->"+val.getId_estrategia());
				if(val.getId_estrategia() == estrategia){
					//System.out.println("sacando nuevas listas----->"+val.getId_estrategia()+"--nombre-->"+val.getNombre_linea());
					lineasaccion.add(val);
				}
				
			}
			
//			for(tbl_lineasaccion val:lineasaccion){
//				System.out.println("sacando nuevas listas----->"+val.getId_estrategia()+"--nombre-->"+val.getNombre_linea());
//			}
	}
	
	public void llenarEstrategias(){
		System.out.println("ejecutando metodo llenar estrategias para objetivo-->"+objetivo);
			estrategias.clear();
			for(Tbl_Estrategia val:tempEstrategias){
			//System.out.println("----->"+val.getId_estrategia());
				if(val.getId_objetivo() == objetivo){
					//System.out.println("sacando nuevas listas----->"+val.getId_estrategia()+"--nombre-->"+val.getNombre_linea());
					estrategias.add(val);
				}
				
			}
			
//			for(tbl_lineasaccion val:lineasaccion){
//				System.out.println("sacando nuevas listas----->"+val.getId_estrategia()+"--nombre-->"+val.getNombre_linea());
//			}
	}
	
	public void onTabChangeLineas(TabChangeEvent event) {
		
        FacesMessage msg = new FacesMessage(event.getTab().getTitle());
        String est = (msg.getDetail()).replace("Estrategia ", "");
        estrategia = Integer.parseInt(est);
        System.out.println("accediedo a metodo-->"+estrategia);
        //estrategia=Integer.parseInt(msg.toString());
        llenarLineas();
       FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void onTabChangeEstrategias(TabChangeEvent event) {
		
        FacesMessage msg = new FacesMessage(event.getTab().getTitle());
        String est = (msg.getDetail()).replace("Objetivo ", "");
        objetivo = Integer.parseInt(est);
        System.out.println("accediedo a metodo-->"+objetivo);
        //estrategia=Integer.parseInt(msg.toString());
        llenarEstrategias();
       FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	
}
