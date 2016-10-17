package mx.conavim.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.conavim.modelo.TblEstrategia;
import mx.conavim.modelo.TblLineaAccion;
import mx.conavim.modelo.TblObjetivo;
import mx.conavim.modelo.Tbl_EntidadSecre;

public class Tbl_reportDAO extends Conexion {
	
	private String msjError;
	
	private Tbl_EntidadSecre oEntidadSecre;
	private List<TblObjetivo> lstObjetivos;
	private List<TblEstrategia> lstEstrategias;
	private List<TblLineaAccion> lstLineasAccion;
	
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
