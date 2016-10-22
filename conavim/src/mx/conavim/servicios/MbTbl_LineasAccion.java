package mx.conavim.servicios;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.TabChangeEvent;

import mx.conavim.control.Tbl_lineasaccionDAO;
import mx.conavim.modelo.TblRespuesta;
import mx.conavim.modelo.Tbl_Estrategia;
import mx.conavim.modelo.tbl_lineasaccion;

public class MbTbl_LineasAccion {

	Tbl_lineasaccionDAO consultas;
	private List<tbl_lineasaccion> lineasaccion = new ArrayList<tbl_lineasaccion>();
	private List<tbl_lineasaccion> tempLineas = new ArrayList<tbl_lineasaccion>();
	private List<Tbl_Estrategia>  estrategias = new ArrayList<Tbl_Estrategia>();
	private List<Tbl_Estrategia>  tempEstrategias = new ArrayList<Tbl_Estrategia>();
	private List<String> productos = new ArrayList<String>();
	private int estrategia=1;	
	private int objetivo=1;
	private int idLinea=1;
	private String idInforme;
	TblRespuesta otblRespuesta;
	private int iteamActivo=0;
	private String mensaje="Hola";
	
	public int getIteamActivo() {
		return iteamActivo;
	}

	public void setIteamActivo(int iteamActivo) {
		this.iteamActivo = iteamActivo;
	}
	private boolean pintarBotones=true;
	
	
	
	
	public boolean isPintarBotones() {
		return pintarBotones;
	}

	public void setPintarBotones(boolean pintarBotones) {
		this.pintarBotones = pintarBotones;
	}

	public String getIdInforme() {
		return idInforme;
	}

	public void setIdInforme(String idInforme) {
		System.out.println("Seteando id informe-->"+idInforme);
		this.idInforme = idInforme;
	}

	public TblRespuesta getOtblRespuesta() {
		return otblRespuesta;
	}

	public void setOtblRespuesta(TblRespuesta otblRespuesta) {
		this.otblRespuesta = otblRespuesta;
	}

	public int getEstrategia() {
		return estrategia;
	}

	public int getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}

	public int getObjetivo() {
		return objetivo;
	}


	public void setObjetivo(int objetivo) {
		this.objetivo = objetivo;
	}

	public List<String> getProductos() {
		return productos;
	}



	public void setProductos(List<String> productos) {
		//inicializar();
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
		//if(otblRespuesta ==null){
			//System.out.println("inicializando TblRespuesta");
			//otblRespuesta=new TblRespuesta();
		//}
		 //Obtenemos idInforme
		 FacesContext fc = FacesContext.getCurrentInstance();
	     Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	     this.idInforme =  params.get("idInforme");
	     System.out.println("Metodo init-->");
	     System.out.println("id informe-->"+idInforme);
		consultas=new Tbl_lineasaccionDAO();
		tempLineas=consultas.getLineasAccion();
		productos = consultas.getProductos();
		tempEstrategias = consultas.getEstrategias();
		llenarEstrategias(1);
		//verificando si existe linea con id informe
		otblRespuesta = consultas.verificarExisteLinea(idInforme,idLinea);
		validarBotones(otblRespuesta);
		//System.out.println("Valor de id Informe-->"+idInforme);
		consultas.dispose();
	}
	
	
	public List<Tbl_Estrategia> getEstrategias() {
		return estrategias;
	}



	public void setEstrategias(List<Tbl_Estrategia> estrategias) {
		this.estrategias = estrategias;
	}



	public void llenarLineas(int estrategia){
	//	System.out.println("ejecutando metodo llenar lineas para estrategia-->"+estrategia);
			lineasaccion.clear();
			for(tbl_lineasaccion val:tempLineas){								
				if(val.getId_estrategia() == estrategia){
					//System.out.println("sacando nuevas listas----->"+val.getId_estrategia()+"--nombre-->"+val.getDescripcion());
					lineasaccion.add(val);
				}				
			}
			this.idLinea = lineasaccion.get(0).getId_lineaaccion();
			System.out.println("id Linea accion->"+idLinea);
//			for(tbl_lineasaccion val:lineasaccion){
//				System.out.println("sacando nuevas Estrategi----->"+val.getId_estrategia()+"--nombre-->"+val.getNombre_linea()+"----descripcion---->"+val.getDescripcion());
//			}
	}
	
	public void llenarEstrategias(int objetivo){
		//System.out.println("ejecutando metodo llenar estrategias para objetivo-->"+objetivo);
			estrategias.clear();
			for(Tbl_Estrategia val:tempEstrategias){
				if(val.getId_objetivo() == objetivo){
					//System.out.println("sacando nuevas listas----->"+val.getId_estrategia()+"--nombre-->"+val.getDescripcion());
					estrategias.add(val);					
				}				
			}			
			int ob = estrategias.get(0).getId_estrategia();
			this.estrategia = ob;
			System.out.println("id Estrategia->"+estrategia);
			llenarLineas(ob);			
//			for(tbl_lineasaccion val:lineasaccion){
//				System.out.println("sacando nuevas listas Estrategias----->"+val.getId_estrategia()+"--nombre-->"+val.getNombre_linea()+"--->descripcion"+val.getDescripcion());
//			}
	}
	
	public void setEstrategia(int estrategia) {
		this.estrategia = estrategia;
	}

	public void onTabChangeEstrategia(TabChangeEvent event) {		
        FacesMessage msg = new FacesMessage(event.getTab().getTitletip());        
        String est = (msg.getDetail());
        estrategia = Integer.parseInt(est);
        System.out.println("id Estrategia-->"+estrategia);
        llenarLineas(estrategia);
        iteamActivo=0;
        consultas=new Tbl_lineasaccionDAO();
        otblRespuesta = consultas.verificarExisteLinea(idInforme,idLinea);
		validarBotones(otblRespuesta);
		consultas.dispose();
    }
	
	public void onTabChangeObjetivo(TabChangeEvent event) {		
        FacesMessage msg = new FacesMessage(event.getTab().getTitle());
        String est = (msg.getDetail()).replace("Objetivo ", "");
        objetivo = Integer.parseInt(est);
        System.out.println("id Objetivo-->"+objetivo);
        llenarEstrategias(objetivo);  
        
        consultas=new Tbl_lineasaccionDAO();
        otblRespuesta = consultas.verificarExisteLinea(idInforme,idLinea);
		validarBotones(otblRespuesta);
		consultas.dispose();
        //System.out.println("Linea-->"+idLinea);
    }
	
	public void onTabChangeLinea(TabChangeEvent event) {		
        FacesMessage msg = new FacesMessage(event.getTab().getTitletip());
        String est = (msg.getDetail());
        idLinea = Integer.parseInt(est);
        System.out.println("id Linea-->"+idLinea);
        
        consultas=new Tbl_lineasaccionDAO();
        otblRespuesta = consultas.verificarExisteLinea(idInforme,idLinea);
		validarBotones(otblRespuesta);
		consultas.dispose();
    }

	public void insertarRespuesta(){		
		otblRespuesta.setId_lineaaccion(idLinea);		
		//llevar este obtjeto para insercion		
	}
	
	 public void guardarLinea(String clave) throws ParseException {
		 
		 //otblRespuesta=new TblRespuesta();		
	     //Realizamos la consulta
		 consultas=new Tbl_lineasaccionDAO();
		 System.out.println("INSERTANDO REGISTRO");
		 //otblRespuesta.setFechainactv(validarFecha(otblRespuesta.getFechainactv()));
		 //otblRespuesta.setFechatermactv(validarFecha(otblRespuesta.getFechatermactv()));		 
		 otblRespuesta.setId_lineaaccion(idLinea);
		 otblRespuesta.setId_informe(idInforme);		 
		 System.out.println("Valor de informe-->"+idInforme);
		 if(pintarBotones){
		 	consultas.insertarRespuestas(otblRespuesta);
		 	otblRespuesta=null;
		 }else{
			 otblRespuesta=consultas.actualizarRespuestas(otblRespuesta, idInforme, idLinea);
			 
		 }
		 consultas.dispose();		 	     
		 
	    }
	 
	 public java.sql.Date validarFecha(TblRespuesta oTblRespuesta,Date fecha) throws ParseException{
		 java.sql.Date fechaValidada=null;
		 if(fecha!=null){
		 	SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
			String fechaOrdenada=formateador.format(fecha);
			fechaValidada = java.sql.Date.valueOf(fechaOrdenada);
			//System.out.println("fecha insertada"+oTblRespuesta.getFechainactv());			
			//System.out.println("fecha formateada-->"+fechaValidada);
		 }
		 return fechaValidada;
	 }
	 
	 public void redirectIndex(String id_informe)
		{
			FacesContext context = FacesContext.getCurrentInstance();
			try {				
				context.getExternalContext().redirect("index.conavim");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	 public void validarBotones(TblRespuesta otblRespuesta){
		 if(otblRespuesta.getId_informe()!=null){
			 pintarBotones=false;
			 System.out.println("actualizar");
			 mensaje="Registro Actualizado Exitosamente";
		 }else{
			 System.out.println("Insertar");
			 pintarBotones=true;
			 mensaje="Registro Insertado Exitosamente";
		 }
	 }
	 
	 public void notificacionMessage() {
	        FacesContext context = FacesContext.getCurrentInstance();	         
	        context.addMessage(null, new FacesMessage("COMPLETADO",  "" + mensaje) );
	        //context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
	    }
}
