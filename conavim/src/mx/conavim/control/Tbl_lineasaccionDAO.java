package mx.conavim.control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import mx.conavim.modelo.TblRespuesta;
import mx.conavim.modelo.Tbl_Estrategia;
import mx.conavim.modelo.Tbl_Producto;
import mx.conavim.modelo.tblRespuestas;
import mx.conavim.modelo.tbl_lineasaccion;
import mx.conavim.servicios.MbTbl_LineasAccion;

public class Tbl_lineasaccionDAO extends Conexion {

	public List<String> getProductos() {
		// List<Tbl_Productos> productos = new ArrayList<Tbl_Productos>();
		List<String> productos = new ArrayList<String>();
		ResultSet res = null;
		PreparedStatement consulta = null;

		try {
			consulta = getConnection().prepareStatement("SELECT nombre_producto FROM tbl_productos");
			res = consulta.executeQuery();
			while (res.next()) {
				productos.add(res.getString("nombre_producto"));
			}
			res.close();
			consulta.close();

		} catch (Exception e) {
			desconectar();
			System.out.println("no se pudo consultar Producto\n" + e);
		} finally {
			try {
				res.close();
				consulta.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return productos;
	}

	public List<tbl_lineasaccion> getLineasAccion() {
		List<tbl_lineasaccion> lineasaccion = new ArrayList<tbl_lineasaccion>();

		ResultSet res = null;
		PreparedStatement consulta = null;

		try {
			consulta = getConnection().prepareStatement("SELECT * FROM tbl_lineasaccion");
			res = consulta.executeQuery();
			while (res.next()) {
				tbl_lineasaccion linea = new tbl_lineasaccion();
				linea.setId_lineaaccion(res.getInt("id_lineaaccion"));
				linea.setId_estrategia(res.getInt("id_estrategia"));
				linea.setNombre_linea(res.getString("nombre_linea"));
				linea.setDescripcion((res.getString("descripcion")));
				linea.setStatus(res.getInt("status"));
				;

				lineasaccion.add(linea);
			}
			res.close();
			consulta.close();

		} catch (Exception e) {
			System.out.println("no se pudo consultar la Linea\n" + e);
			desconectar();
		} finally {
			try {
				res.close();
				consulta.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return lineasaccion;
	}

	public List<Tbl_Estrategia> getEstrategias() {
		List<Tbl_Estrategia> estrategias = new ArrayList<Tbl_Estrategia>();

		ResultSet res = null;
		PreparedStatement consulta = null;

		try {
			consulta = getConnection().prepareStatement("SELECT * FROM tbl_estrategias");
			res = consulta.executeQuery();
			while (res.next()) {
				Tbl_Estrategia estrategia = new Tbl_Estrategia();
				estrategia.setId_estrategia(res.getInt("id_estrategia"));
				estrategia.setId_objetivo(res.getInt("id_objetivo"));
				estrategia.setNombre_estrategia(res.getString("nombre_estrategia"));
				estrategia.setDescripcion(res.getString("descripcion"));
				estrategia.setStatus(res.getInt("status"));

				estrategias.add(estrategia);
			}
			res.close();
			consulta.close();

		} catch (Exception e) {
			System.out.println("no se pudo consultar Estrategias\n" + e);
			desconectar();
		} finally {
			try {
				res.close();
				consulta.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return estrategias;
	}

	
	
	public List<Tbl_Estrategia> insertarRespuestas(TblRespuesta oTblRespuesta) throws ParseException {
		//Statement consulta = null;
		PreparedStatement consulta=null;
		String [] tipoctividad=oTblRespuesta.getSeleCheckPregunta3();
		String tipoactivity="";
		for(String val: tipoctividad){tipoactivity+=val+",";}
		//tipoactivity+=oTblRespuesta.getTipoactivi();
		//Obtenemos fuente financia
		String fuenteFinancia="";
		if(oTblRespuesta.getFuentefinacia().length>0){
			for(String val: oTblRespuesta.getFuentefinacia()){fuenteFinancia+=val;}
		}else{
			//fuenteFinancia=oTblRespuesta.getFuentefinacia2();
		}
		String query="INSERT INTO public.tbl_respuestas("
				+ "id_informe, id_lineaaccion, dependentsist, activicumpla, "
				+ "tipoactivi, fechainactv, fechatermactv, producto, tipoproduc, "
				+ "linkproducto, metaprgmdlinacc, unidadmedlinacc, periodometaprglinacc, "
				+ "metaproglinacc, metaprogactv, periodometaprgactiv, avance, explicacionavance, "
				+ "observaciones, otrasinsticolaboran, presupuesto, fuentefinacia, "
				+ "noserviciosotrg, totalpoblbenfm, poblabenfmujing, totalpoblbenfh, "
				+ "poblabenfhombing, ninasbenifi0a12pobltot, ninasbenifi0a12poblindig, "
				+ "ninasbenifi12a17pobltot, ninasbenifi12a17poblindig, ninosbenifi0a12pobltot, "
				+ "ninosbenifi0a12poblindig, ninosbenifi12a17pobltot, ninpsbenifi12a17poblindig, "
				+ "status)"
				+ "VALUES (?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, "
				+ "?, ?, ?, ?, "
				+ "?, ?, ?, "
				+ "?, ?, ?, "
				+ "?, ?, ?, "
				+ "?)";
		
		try {
			
			//String fechainactv = (format.format(oTblRespuesta.getFechainactv()).isEmpty())?format.format(oTblRespuesta.getFechainactv()):"";
			//String fechatermactv = (format.format(oTblRespuesta.getFechatermactv()).isEmpty())?format.format(oTblRespuesta.getFechatermactv()):"";
			//System.out.println("fecha formateada-->"+fechainactv);
//			SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
//			String fechaOrdenada=formateador.format(oTblRespuesta.getFechainactv());
//			java.sql.Date fechainactv = java.sql.Date.valueOf(fechaOrdenada);
//			System.out.println("fecha insertada"+oTblRespuesta.getFechainactv());			
//			System.out.println("fecha formateada-->"+fechainactv);
			
			
			System.out.println(query);	
			//java.sql.Date sqlDate=new java.sql.Date(2016,02,10);
			consulta = getConnection().prepareStatement(query);
					consulta.setString(1, oTblRespuesta.getId_informe());
					consulta.setInt(2, oTblRespuesta.getId_lineaaccion());
					consulta.setString(3,oTblRespuesta.getDependentsist());
					consulta.setString(4, oTblRespuesta.getActivicumpla());
					consulta.setString(5, tipoactivity+"--"+oTblRespuesta.getTipoactivi());
					consulta.setDate(6,   new MbTbl_LineasAccion().validarFecha(oTblRespuesta, oTblRespuesta.getFechainactv()));
					consulta.setDate(7,  new MbTbl_LineasAccion().validarFecha(oTblRespuesta, oTblRespuesta.getFechatermactv()));
					consulta.setString(8,oTblRespuesta.getProducto());
					consulta.setString(9, oTblRespuesta.getTipoproduct());
					consulta.setString(10, oTblRespuesta.getLinkproducto());
					consulta.setString(11, oTblRespuesta.getMetaprgmdlinacc()+"--"+oTblRespuesta.getMetaprgmdlinacc2());
					consulta.setString(12, oTblRespuesta.getUnidadmedinacc());
					consulta.setString(13, oTblRespuesta.getPeriodometaprgactiv());
					consulta.setString(14, oTblRespuesta.getMetaproglinacc());
					consulta.setString(15, oTblRespuesta.getMetaprogactv());
					consulta.setString(16, oTblRespuesta.getPeriodometaprgactiv());
					consulta.setString(17, oTblRespuesta.getAvance());
					consulta.setString(18, oTblRespuesta.getExplicacionavance());
					consulta.setString(19, oTblRespuesta.getObservaciones());
					consulta.setString(20, oTblRespuesta.getOtrasinsticolaboran());
					consulta.setInt(21, oTblRespuesta.getPresupuesto());
					consulta.setString(22,fuenteFinancia+"--"+oTblRespuesta.getFuentefinacia2());
					consulta.setInt(23, oTblRespuesta.getNoserviciootrg());
					consulta.setInt(24, oTblRespuesta.getTotalpoblbenfm());
					consulta.setInt(25, oTblRespuesta.getPoblabenfmujing());
					consulta.setInt(26, oTblRespuesta.getTotalpoblbenfh());
					consulta.setInt(27, oTblRespuesta.getPoblabenfhobing());
					consulta.setInt(28, oTblRespuesta.getNinasbenifi0a12pobltot());
					consulta.setInt(29, oTblRespuesta.getNinasbenifi0a12poblindig());
					consulta.setInt(30, oTblRespuesta.getNinasbenifi12a17pobltot());
					consulta.setInt(31, oTblRespuesta.getNinasbenifi12a17poblingdig());
					consulta.setInt(32, oTblRespuesta.getNinosbenifi0a12pobltot());
					consulta.setInt(33, oTblRespuesta.getNinosbenifi0a12poblingid());
					consulta.setInt(34, oTblRespuesta.getNinosbenifi12a17pobltot());
					consulta.setInt(35, oTblRespuesta.getNinpsbenifi12a17poblindig());
					consulta.setInt(36, oTblRespuesta.getStatus());
			consulta.executeUpdate();
			System.out.println("\n\n\nREGISTRO INSERTADO CON EXITO!!!");
			consulta.close();
		} catch (SQLException e) {
			System.out.println("NO SE PUDO INSERTAR REGISTRO\n" + e.getMessage());
			desconectar();
		} finally {
			try {
				consulta.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public TblRespuesta actualizarRespuestas(TblRespuesta oTblRespuesta, String idInforme, int idLinea) throws ParseException {
		//Statement consulta = null;
		PreparedStatement consulta=null;
		String [] tipoctividad=oTblRespuesta.getSeleCheckPregunta3();
		String tipoactivity="";
		for(String val: tipoctividad){tipoactivity+=val+",";}
		//tipoactivity+=oTblRespuesta.getTipoactivi();
		//Obtenemos fuente financia
		String fuenteFinancia="";
		if(oTblRespuesta.getFuentefinacia().length>0){
			for(String val: oTblRespuesta.getFuentefinacia()){fuenteFinancia+=val+",";}
		}else{
			//fuenteFinancia=oTblRespuesta.getFuentefinacia2();
		}
		String query="UPDATE public.tbl_respuestas "
				+ "SET dependentsist=?, "
				+ "activicumpla=?,  tipoactivi=?, fechainactv=?, fechatermactv=?, "
				+ "producto=?, tipoproduc=?, linkproducto=?, metaprgmdlinacc=?, "
				+ "unidadmedlinacc=?, periodometaprglinacc=?, metaproglinacc=?, "
				+ "metaprogactv=?, periodometaprgactiv=?, avance=?, explicacionavance=?, "
				+ "observaciones=?, otrasinsticolaboran=?, presupuesto=?, fuentefinacia=?, "
				+ "noserviciosotrg=?, totalpoblbenfm=?, poblabenfmujing=?, totalpoblbenfh=?, "
				+ "poblabenfhombing=?, ninasbenifi0a12pobltot=?, ninasbenifi0a12poblindig=?, "
				+ "ninasbenifi12a17pobltot=?, ninasbenifi12a17poblindig=?, ninosbenifi0a12pobltot=?, "
				+ "ninosbenifi0a12poblindig=?, ninosbenifi12a17pobltot=?, ninpsbenifi12a17poblindig=?, "
				+ "status=? WHERE id_informe='"+idInforme+"' and id_lineaaccion="+idLinea+";";
		
		try {

			System.out.println(query);	
			java.sql.Date sqlDate=new java.sql.Date(2016,02,10);
			consulta = getConnection().prepareStatement(query);										
					consulta.setString(1,oTblRespuesta.getDependentsist());
					consulta.setString(2, oTblRespuesta.getActivicumpla());
					consulta.setString(3, tipoactivity+"--"+oTblRespuesta.getTipoactivi());
					consulta.setDate(4,   new MbTbl_LineasAccion().validarFecha(oTblRespuesta, oTblRespuesta.getFechainactv()));
					consulta.setDate(5,  new MbTbl_LineasAccion().validarFecha(oTblRespuesta, oTblRespuesta.getFechatermactv()));
					consulta.setString(6,oTblRespuesta.getProducto());
					consulta.setString(7, oTblRespuesta.getTipoproduct());
					consulta.setString(8, oTblRespuesta.getLinkproducto());
					//agregar metaprgmdlinacc2 a input
					consulta.setString(9, oTblRespuesta.getMetaprgmdlinacc()+"--"+oTblRespuesta.getMetaprgmdlinacc2());
					consulta.setString(10, oTblRespuesta.getUnidadmedinacc());
					consulta.setString(11, oTblRespuesta.getPeriodometaprgactiv());
					consulta.setString(12, oTblRespuesta.getMetaproglinacc());
					consulta.setString(13, oTblRespuesta.getMetaprogactv());
					consulta.setString(14, oTblRespuesta.getPeriodometaprgactiv());
					consulta.setString(15, oTblRespuesta.getAvance());
					consulta.setString(16, oTblRespuesta.getExplicacionavance());
					consulta.setString(17, oTblRespuesta.getObservaciones());
					consulta.setString(18, oTblRespuesta.getOtrasinsticolaboran());
					consulta.setInt(19, oTblRespuesta.getPresupuesto());
					consulta.setString(20,fuenteFinancia+"--"+oTblRespuesta.getFuentefinacia2());
					consulta.setInt(21, oTblRespuesta.getNoserviciootrg());
					consulta.setInt(22, oTblRespuesta.getTotalpoblbenfm());
					consulta.setInt(23, oTblRespuesta.getPoblabenfmujing());
					consulta.setInt(24, oTblRespuesta.getTotalpoblbenfh());
					consulta.setInt(25, oTblRespuesta.getPoblabenfhobing());
					consulta.setInt(26, oTblRespuesta.getNinasbenifi0a12pobltot());
					consulta.setInt(27, oTblRespuesta.getNinasbenifi0a12poblindig());
					consulta.setInt(28, oTblRespuesta.getNinasbenifi12a17pobltot());
					consulta.setInt(29, oTblRespuesta.getNinasbenifi12a17poblingdig());
					consulta.setInt(30, oTblRespuesta.getNinosbenifi0a12pobltot());
					consulta.setInt(31, oTblRespuesta.getNinosbenifi0a12poblingid());
					consulta.setInt(32, oTblRespuesta.getNinosbenifi12a17pobltot());
					consulta.setInt(33, oTblRespuesta.getNinpsbenifi12a17poblindig());
					consulta.setInt(34, oTblRespuesta.getStatus());
			consulta.executeUpdate();
			System.out.println("\n\n\nREGISTRO ACTUALIZADO CON EXITO!!!");
			consulta.close();
		} catch (SQLException e) {
			System.out.println("NO SE PUDO ACTUALIZAR REGISTRO\n" + e);
			desconectar();
		} finally {
			try {
				consulta.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return oTblRespuesta;
	}
	public TblRespuesta verificarExisteLinea(String idInforme, int idLinea){
		TblRespuesta oTblRespuesta = new TblRespuesta();
		ResultSet res = null;		
		PreparedStatement consulta = null;
		String []tempArreglo;
		String tempVar = "";
		String tempVar2 = "";
		
		//System.out.println("idInforme->"+idInforme+" ----- ");
		try {
			consulta = getConnection().prepareStatement("SELECT * FROM tbl_respuestas where id_informe='"+idInforme+"' and id_lineaaccion="+idLinea+";");
			res = consulta.executeQuery();
//			if(res.){
//				System.out.println("Existe Registro");
//			}else{
//				System.out.println("NO Existe Registro!!!!!");
//			}
			//System.out.println("linea encontrada-->idInforme="+idInforme+"--->idLinea="+idLinea+" --Numeroregistro->"+res.getRow());
			while (res.next()) {
				oTblRespuesta.setId_informe(res.getString("id_informe"));
				oTblRespuesta.setDependentsist(res.getString("dependentsist"));
				oTblRespuesta.setActivicumpla(res.getString("activicumpla"));
				//checar esta parte para campo otro
				if(res.getString("tipoactivi").contains("--")){tempArreglo=res.getString("tipoactivi").split(",");tempVar=tempArreglo[tempArreglo.length-1];tempArreglo[tempArreglo.length-1]="";}else{tempArreglo=res.getString("tipoactivi").split(",");}
				oTblRespuesta.setSeleCheckPregunta3(tempArreglo);				
				oTblRespuesta.setTipoactivi(tempVar.replace("--", ""));
				//////////////////////////////////////////////////////////
				oTblRespuesta.setFechainactv(res.getDate("fechainactv"));
				oTblRespuesta.setFechatermactv(res.getDate("fechatermactv"));
				oTblRespuesta.setProducto(res.getString("producto"));
				oTblRespuesta.setTipoproduct(res.getString("tipoproduc"));
				oTblRespuesta.setLinkproducto(res.getString("linkproducto"));
				//agregar metaprgmdlinacc2 a input
				if(res.getString("metaprgmdlinacc").contains("--")){tempArreglo=res.getString("metaprgmdlinacc").split("--");tempVar=(tempArreglo.length>1)?tempArreglo[1]:"";tempVar2=(tempArreglo.length>0)?tempArreglo[0]:"";}
				oTblRespuesta.setMetaprgmdlinacc(tempVar2);
				oTblRespuesta.setMetaprgmdlinacc2(tempVar);
				oTblRespuesta.setUnidadmedinacc(res.getString("unidadmedlinacc"));
				oTblRespuesta.setPeriodometaprglinacc(res.getString("periodometaprglinacc"));
				oTblRespuesta.setMetaproglinacc(res.getString("metaproglinacc"));
				oTblRespuesta.setMetaprogactv(res.getString("metaprogactv"));
				oTblRespuesta.setPeriodometaprgactiv(res.getString("periodometaprgactiv"));
				oTblRespuesta.setAvance(res.getString("avance"));
				oTblRespuesta.setExplicacionavance(res.getString("explicacionavance"));
				oTblRespuesta.setObservaciones(res.getString("observaciones"));
				oTblRespuesta.setOtrasinsticolaboran(res.getString("otrasinsticolaboran"));				
				oTblRespuesta.setPresupuesto(res.getInt("presupuesto"));
				//checar esta parte para campo otro
				if(res.getString("tipoactivi").contains("--")){tempArreglo=res.getString("fuentefinacia").split(",");tempVar=tempArreglo[tempArreglo.length-1];tempArreglo[tempArreglo.length-1]="";}else{tempArreglo=res.getString("fuentefinacia").split(",");}
				oTblRespuesta.setFuentefinacia(tempArreglo);
				oTblRespuesta.setFuentefinacia2(tempVar.replace("--", ""));
				////////////////////////////////////////////////////////
				oTblRespuesta.setNoserviciootrg(res.getInt("noserviciosotrg"));
				oTblRespuesta.setTotalpoblbenfm(res.getInt("totalpoblbenfm"));
				oTblRespuesta.setPoblabenfmujing(res.getInt("poblabenfmujing"));
				oTblRespuesta.setTotalpoblbenfh(res.getInt("totalpoblbenfh"));
				oTblRespuesta.setPoblabenfhobing(res.getInt("poblabenfhombing"));
				oTblRespuesta.setNinasbenifi0a12pobltot(res.getInt("ninasbenifi0a12pobltot"));
				oTblRespuesta.setNinasbenifi0a12poblindig(res.getInt("ninasbenifi0a12poblindig"));
				oTblRespuesta.setNinasbenifi12a17pobltot(res.getInt("ninasbenifi12a17pobltot"));
				oTblRespuesta.setNinasbenifi12a17poblingdig(res.getInt("ninasbenifi12a17poblindig"));
				oTblRespuesta.setNinosbenifi0a12pobltot(res.getInt("ninosbenifi0a12pobltot"));
				oTblRespuesta.setNinosbenifi0a12poblingid(res.getInt("ninosbenifi0a12poblindig"));
				oTblRespuesta.setNinosbenifi12a17pobltot(res.getInt("ninosbenifi12a17pobltot"));
				oTblRespuesta.setNinpsbenifi12a17poblindig(res.getInt("ninpsbenifi12a17poblindig"));				
			}
		} catch (Exception e) {
			System.out.println("no se pudo buscar Linea No se encontro\n" + e);
			desconectar();
		} finally {
			try {
				res.close();
				consulta.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	return oTblRespuesta; 
	}
		
	public void dispose() {
		desconectar();
	}
	
	

}
