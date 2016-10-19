package mx.conavim.control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import mx.conavim.modelo.TblRespuesta;
import mx.conavim.modelo.Tbl_Estrategia;
import mx.conavim.modelo.Tbl_Producto;
import mx.conavim.modelo.tblRespuestas;
import mx.conavim.modelo.tbl_lineasaccion;

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
			System.out.println("no se pudo consultar la Persona\n" + e);
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

	
	public List<Tbl_Estrategia> insertarRespuestas(TblRespuesta oTblRespuesta) {
		//Statement consulta = null;
		PreparedStatement consulta=null;
		String [] tipoctividad=oTblRespuesta.getSeleCheckPregunta3();
		String tipoactivity="";
		for(String val: tipoctividad){tipoactivity+=val+",";}
		tipoactivity+=oTblRespuesta.getTipoactivi();
		//Obtenemos fuente financia
		String fuenteFinancia="-";
		if(oTblRespuesta.getFuentefinacia().length>0){
			for(String val: oTblRespuesta.getFuentefinacia()){fuenteFinancia+=val;}
		}else{
			fuenteFinancia=oTblRespuesta.getFuentefinacia2();
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
			
//			String query="INSERT INTO public.tbl_respuestas( id_informe, id_lineaaccion, dependentsist, activicumpla, "
//					+ "tipoactivi, fechainactv, fechatermactv, producto, tipoproduc, "
//					+ "linkproducto, metaprgmdlinacc, unidadmedlinacc, periodometaprglinacc, "
//					+ "metaproglinacc, metaprogactv, periodometaprgactiv, avance, explicacionavance, "
//					+ "observaciones, otrasinsticolaboran, presupuesto, fuentefinacia, "
//					+ "noserviciosotrg, totalpoblbenfm, poblabenfmujing, totalpoblbenfh, "
//					+ "poblabenfhombing, ninasbenifi0a12pobltot, ninasbenifi0a12poblindig, "
//					+ "ninasbenifi12a17pobltot, ninasbenifi12a17poblindig, ninosbenifi0a12pobltot, "
//					+ "ninosbenifi0a12poblindig, ninosbenifi12a17pobltot,ninpsbenifi12a17poblindig,"
//					+ "status)"
//					+ "VALUES ( "+oTblRespuesta.getId_informe()+","+oTblRespuesta.getId_lineaaccion()+",'"+oTblRespuesta.getDependentsist()+"', '-', "
//					+ "'"+oTblRespuesta.getTipoactivi()+"','"+oTblRespuesta.getFechainactv()+"','"+oTblRespuesta.getFechatermactv()+"', "+oTblRespuesta.getProducto()+", '-', "
//					+ "'-', '-', '-', '-', "
//					+ "'-', '-', '-', '-', '-', "
//					+ "'-', '-', 0, '-', "
//					+ "0, 0, 0, 1, "
//					+ "0, 0, 0, "
//					+ "0, 0, 0, "
//					+ "0, 0, 0, "
//					+ "0);";
			//String temp= SimpleDateFormat.format(oTblRespuesta.getFechainactv());
			System.out.println(query);	
			java.sql.Date sqlDate=new java.sql.Date(2016,02,10);
			consulta = getConnection().prepareStatement(query);
					consulta.setString(1, oTblRespuesta.getId_informe());
					consulta.setInt(2, oTblRespuesta.getId_lineaaccion());
					consulta.setString(3,oTblRespuesta.getDependentsist());
					consulta.setString(4, oTblRespuesta.getActivicumpla());
					consulta.setString(5, tipoactivity);
					consulta.setDate(6,   sqlDate);
					consulta.setDate(7,  sqlDate);
					consulta.setString(8,oTblRespuesta.getProducto());
					consulta.setString(9, oTblRespuesta.getTipoproduct());
					consulta.setString(10, oTblRespuesta.getLinkproducto());
					consulta.setString(11, oTblRespuesta.getMetaprgmdlinacc());
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
					consulta.setString(22,fuenteFinancia);
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
	
	public void verificarExisteLinea(String idInforme, int idLinea){
		ResultSet res = null;
		PreparedStatement consulta = null;
		try {
			consulta = getConnection().prepareStatement("SELECT count(*) total FROM tbl_respuestas where id_informe='"+idInforme+"' and id_lineaaccion="+idLinea+";");
			res = consulta.executeQuery();
			System.out.println("numero de Registros-->"+res.getRow());
			if(res.getRow()>0){
				
			}
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
		
		
	}
		
	public void dispose() {
		desconectar();
	}
	
	

}
