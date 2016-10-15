package mx.conavim.servicios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;





import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.TabChangeEvent;

import mx.conavim.control.Tbl_lineasaccionDAO;
import mx.conavim.modelo.tbl_lineasaccion;

public class MbTbl_LineasAccion {

	Tbl_lineasaccionDAO lineasacc;
	private List<tbl_lineasaccion> lineasaccion = new ArrayList<tbl_lineasaccion>();
	private List<tbl_lineasaccion> temp = new ArrayList<tbl_lineasaccion>();
	private int estrategia=1;
	
	
	
	public List<tbl_lineasaccion> getLineasaccion() {
		return lineasaccion;
	}



	public void setLineasaccion(List<tbl_lineasaccion> lineasaccion) {
		this.lineasaccion = lineasaccion;
	}



	@PostConstruct
	public void init() {
		lineasacc=new Tbl_lineasaccionDAO();
		temp=lineasacc.getLineasAccion();
//		//Iterator<tbl_lineasaccion> i = lineasaccion.iterator();
//		for(tbl_lineasaccion val:temp){
//			System.out.println("----->"+val.getId_estrategia());
//			if(val.getId_estrategia() == estrategia){
//				System.out.println("----->"+val.getId_estrategia());
//				lineasaccion.add(val);
//			}
//		}
//		
		llenarLineas();
	}
	public void llenarLineas(){
		System.out.println("ejecutando metodo llenar lineas");
			lineasaccion.clear();
			for(tbl_lineasaccion val:temp){
			//System.out.println("----->"+val.getId_estrategia());
				if(val.getId_estrategia() == estrategia){
					System.out.println("sacando nuevas listas----->"+val.getId_estrategia());
					lineasaccion.add(val);
				}
			}
	}
	
//	public void llenarLineaAccion(int estrategia){
//		this.estrategia=estrategia;
//	}
	public void onTabChange(TabChangeEvent event) {
		
        FacesMessage msg = new FacesMessage(event.getTab().getTitle());
        String est = (msg.getDetail()).replace("Estrategia ", "");
        estrategia = Integer.parseInt(est);
        System.out.println("accediedo a metodo-->"+estrategia);
        //estrategia=Integer.parseInt(msg.toString());
        llenarLineas();
       FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	
}
