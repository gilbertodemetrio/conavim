package mx.conavim.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.conavim.modelo.TblEstrategia;
import mx.conavim.modelo.TblLineaAccion;
import mx.conavim.modelo.TblObjetivo;
import mx.conavim.modelo.TblRespuesta;
import mx.conavim.modelo.Tbl_EntidadSecre;

public class Tbl_reportDAO extends Conexion {
	
	private String msjError;
	
	private Tbl_EntidadSecre oEntidadSecre;
	private List<TblObjetivo> lstObjetivos;
	private List<TblEstrategia> lstEstrategias;
	private List<TblLineaAccion> lstLineasAccion;
	private List<TblRespuesta> lstTblRespuesta = new ArrayList<TblRespuesta>();
	
	public boolean findEntidades(String idInforme){		
		try
		{String query = "select informe.periodo, informe.id_informe, " +
				"entidad.nombre_entidadsece, entidad.siglas_entidad, " +
				"objetivo.id_objetivo, objetivo.nombre_objetivo, objetivo.descripcion_obj, " +
				"estrategia.id_estrategia, estrategia.nombre_estrategia, estrategia.descripcion, " +
				"linea.id_estrategia, linea.nombre_linea, linea.descripcion as descripcionlinea, " +
				"respuesta.id_respuesta, respuesta.id_informe, respuesta.dependentsist,respuesta.activicumpla, respuesta.tipoactivi,respuesta.fechainactv, respuesta.fechatermactv, respuesta.producto, respuesta.tipoproduc, respuesta.linkproducto, respuesta.metaprgmdlinacc, respuesta.unidadmedlinacc, respuesta.periodometaprglinacc, respuesta.metaproglinacc, respuesta.metaprogactv, respuesta.periodometaprgactiv, respuesta.avance, respuesta.explicacionavance, respuesta.observaciones, respuesta.otrasinsticolaboran, respuesta.presupuesto, respuesta.fuentefinacia, respuesta.noserviciosotrg, respuesta.totalpoblbenfm, respuesta.poblabenfmujing, respuesta.totalpoblbenfh, respuesta.poblabenfhombing, respuesta.ninasbenifi0a12pobltot, respuesta.ninasbenifi0a12poblindig, respuesta.ninasbenifi12a17pobltot, respuesta.ninasbenifi12a17poblindig, respuesta.ninosbenifi0a12pobltot, respuesta.ninosbenifi0a12poblindig, respuesta.ninosbenifi12a17pobltot, respuesta.ninpsbenifi12a17poblindig, respuesta.status " +
				"from tbl_respuestas respuesta, tbl_informes informe, tbl_entidata_secretaria entidad, tbl_lineasaccion linea, tbl_estrategias estrategia, tbl_objetivos objetivo " +
				"where  respuesta.id_informe like '"+ idInforme + "'"+
				" and respuesta.id_informe = informe.id_informe " +
				"and entidad.id_secretaria = informe.id_secretaria " +
				"and linea.id_lineaaccion = respuesta.id_lineaaccion " +
				"and estrategia.id_estrategia = linea.id_estrategia " +
				"and objetivo.id_objetivo = estrategia.id_objetivo";
			consulta = getConnection().prepareStatement(query);
			System.out.println("query export" + query);
			
			res = consulta.executeQuery();
			int i=0;
			while(res.next())
			{
				i++;
				TblRespuesta oTblRespuesta = new TblRespuesta();	
				oEntidadSecre = new Tbl_EntidadSecre();
				oEntidadSecre.setNombre_entidadsec(res.getString("nombre_entidadsece"));
				oEntidadSecre.setSiglas_entidad(res.getString("siglas_entidad"));
				oEntidadSecre.setTipo(res.getString("periodo"));
				oTblRespuesta.setObjetivo(res.getString("nombre_objetivo"));
				oTblRespuesta.setEstrategia(res.getString("nombre_estrategia"));
				oTblRespuesta.setDescripcionLinea(res.getString("descripcionlinea"));
				oTblRespuesta.setDescripcionObjetivo(res.getString("descripcion_obj"));
				oTblRespuesta.setDescripcionEstrategia(res.getString("descripcion"));
				oTblRespuesta.setNombreLinea(res.getString("nombre_linea"));
				oTblRespuesta.setActivicumpla(res.getString("activicumpla"));			
				oTblRespuesta.setDependentsist(res.getString("dependentsist"));
				oTblRespuesta.setTipoactivi(res.getString("tipoactivi"));
				oTblRespuesta.setFechainactv(res.getDate("Fechainactv"));
				oTblRespuesta.setFechatermactv(res.getDate("fechatermactv"));
				oTblRespuesta.setProducto(res.getString("producto"));
				oTblRespuesta.setTipoproduct(res.getString("tipoproduc"));
				oTblRespuesta.setLinkproducto(res.getString("linkproducto"));
				oTblRespuesta.setMetaprgmdlinacc(res.getString("metaprgmdlinacc"));
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
				//oTblRespuesta.setFuentefinacia(res.getString("fuentefinacia")); pendiente por setear para ver si es más conveniente crear uno tipo string solo para pintar
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
				oTblRespuesta.setStatus(res.getInt("status"));
				
				
				lstTblRespuesta.add(oTblRespuesta);
			}
			if(i>0)
			return true;
			else return false;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			desconectar();
			msjError = "No fue posible obtener el informe.";
			return false;
		}
	}
	
	public boolean findEntidad(String id_secretaria)
	{		
		try
		{
			consulta = getConnection().prepareStatement("select * from tbl_entidata_secretaria where id_secretaria = " + id_secretaria);
			
			res = consulta.executeQuery();
			
			while(res.next())
			{
				oEntidadSecre = new Tbl_EntidadSecre();
				oEntidadSecre.setId_secretaria(res.getInt("id_secretaria"));
				oEntidadSecre.setNombre_entidadsec(res.getString("nombre_entidadsece"));
				oEntidadSecre.setSiglas_entidad(res.getString("siglas_entidad"));
				oEntidadSecre.setTipo(res.getString("tipo"));
				oEntidadSecre.setStatus(res.getString("status"));
			}
			
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			desconectar();
			msjError = "No fue posible obtener la entidad.";
			return false;
		}
	}
	
	public boolean findObjetivos()
	{		
		try
		{
			consulta = getConnection().prepareStatement("select * from tbl_objetivos");
			
			res = consulta.executeQuery();
			
			lstObjetivos = new ArrayList<TblObjetivo>();
			
			while(res.next())
			{
				TblObjetivo oTblObjetivo = new TblObjetivo();
				oTblObjetivo.setId_objetivo(res.getInt("id_objetivo"));
				oTblObjetivo.setNombre_objetivo(res.getString("nombre_objetivo"));
				oTblObjetivo.setDescripcion_obj(res.getString("descripcion_obj"));
				oTblObjetivo.setStatus(res.getString("status"));
				lstObjetivos.add(oTblObjetivo);
			}
			
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			msjError = "No fue posible obtener los objetivos.";
			desconectar();
			return false;
		}
	}
	
	public boolean findEstrategias(int id_objetivo)
	{		
		try
		{
			consulta = getConnection().prepareStatement("select * from tbl_estrategias where id_objetivo = " + id_objetivo);
			
			res = consulta.executeQuery();
			
			lstEstrategias = new ArrayList<TblEstrategia>();
			
			while(res.next())
			{
				TblEstrategia oTblEstrategia = new TblEstrategia();
				oTblEstrategia.setId_estrategia(res.getInt("id_estrategia"));
				oTblEstrategia.setId_objetivo(res.getInt("id_objetivo"));
				oTblEstrategia.setNombre_estrategia(res.getString("nombre_estrategia"));
				oTblEstrategia.setDescripcion(res.getString("descripcion"));
				oTblEstrategia.setStatus(res.getString("status"));
				lstEstrategias.add(oTblEstrategia);
			}
			
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			msjError = "No fue posible obtener las estartegias.";
			desconectar();
			return false;
		}
	}
	
	public boolean findLineas(int id_estrategia)
	{		
		try
		{
			consulta = getConnection().prepareStatement("select * from tbl_lineasaccion where id_estrategia = " + id_estrategia);
			
			res = consulta.executeQuery();
			
			lstLineasAccion = new ArrayList<TblLineaAccion>();
			
			while(res.next())
			{
				TblLineaAccion oTblLineaAccion = new TblLineaAccion();
				oTblLineaAccion.setId_estrategia(res.getInt("id_lineaaccion"));
				oTblLineaAccion.setId_estrategia(res.getInt("id_estrategia"));
				oTblLineaAccion.setNombre_linea(res.getString("nombre_linea"));
				oTblLineaAccion.setDescripcion(res.getString("descripcion"));
				oTblLineaAccion.setStatus(res.getString("status"));
				lstLineasAccion.add(oTblLineaAccion);
			}
			
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			msjError = "No fue posible obtener las líneas.";
			desconectar();
			return false;
		}
	}

	public String getMsjError() {
		return msjError;
	}

	public Tbl_EntidadSecre getoEntidadSecre() {
		return oEntidadSecre;
	}

	public List<TblObjetivo> getLstObjetivos() {
		return lstObjetivos;
	}

	public List<TblEstrategia> getLstEstrategias() {
		return lstEstrategias;
	}

	public List<TblLineaAccion> getLstLineasAccion() {
		return lstLineasAccion;
	}
	
	public List<TblRespuesta> getLstTblRespuesta() {
		return lstTblRespuesta;
	}

	public void setLstTblRespuesta(List<TblRespuesta> lstTblRespuesta) {
		this.lstTblRespuesta = lstTblRespuesta;
	}

	public void dispose()
	{
		oEntidadSecre = null;
		lstObjetivos = null;
		lstEstrategias = null;
		lstLineasAccion = null;
		try {
			res.close();
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		desconectar();
	}

}
