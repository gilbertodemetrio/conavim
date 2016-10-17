package mx.conavim.control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.conavim.modelo.Tbl_EntidadSecre;
import mx.conavim.modelo.Tbl_Informes;

public class ConsultasDAO extends Conexion{

	Conexion con;
	public ConsultasDAO()
	{
	}
	public List<Tbl_Informes> obtenerInforme() throws SQLException
	{
		List<Tbl_Informes> informes = new ArrayList< Tbl_Informes>();
		Tbl_Informes informe=null;
		ResultSet informeResult=null;
		try {
			 informeResult=getConnection().prepareStatement("Select * from Tbl_Informes").executeQuery();
			while(informeResult.next())
			{
				informe= new Tbl_Informes();
				informe.setId_informe(informeResult.getString("Id_Informe"));
				informe.setAnio(informeResult.getString("ano"));
				informe.setFecha_captura(informeResult.getDate("fecha_captura"));
				informe.setFecha_modifi(informeResult.getDate("fecha_nodifi"));
				informe.setPeriodo(informeResult.getString("periodo"));
				informe.setPrograma_presupe(informeResult.getString("programa_presupuestario"));
				informe.setSecretaria(informeResult.getInt("id_secretaria"));
				informe.setUnidad_enlaceResp(informeResult.getString("unidad_enlace_responsable"));
				informes.add(informe);
			}
			return informes;
		} catch (Exception e) {
			desconectar();
			return null;
		}finally{
			informeResult.close();
		}
	}
	public boolean insertInforme( Tbl_Informes informe) throws SQLException
	{
		String sql="insert into tbl_informes(id_secretaria,periodo,"
				+ "programa_presupuestario,unidad_enlace_responsable) values(?,?,?,?)";
		PreparedStatement sqlCon = null;
		
	 	try {
	 		sqlCon=getConnection().prepareStatement(sql);
	 		sqlCon.setInt(1, informe.getSecretaria());
	 		sqlCon.setString(2, informe.getPeriodo());
	 		sqlCon.setString(3, informe.getPrograma_presupe());
	 		sqlCon.setString(4, informe.getUnidad_enlaceResp());
	 		sqlCon.executeUpdate();
			return true;
		} catch (SQLException e) {
			desconectar();
			return false;
			// TODO: handle exception
		}finally{
			sqlCon.close();
		}
		
	}
	public List<Tbl_EntidadSecre> obtenerEntindadSec() throws SQLException
	{
		ResultSet entidadResul=null;
		Tbl_EntidadSecre entidad=null;
		List<Tbl_EntidadSecre> entidades= new ArrayList< Tbl_EntidadSecre>();
		try {
			
			entidadResul=getConnection().prepareStatement("Select id_secretaria,nombre_entidadsece,siglas_entidad"
					+ " from tbl_entidata_secretaria").executeQuery();
			while (entidadResul.next()) {
			entidad=new Tbl_EntidadSecre();
			entidad.setId_secretaria(entidadResul.getInt("id_secretaria"));
			entidad.setNombre_entidadsec(entidadResul.getString("nombre_entidadsece"));
			entidad.setSiglas_entidad(entidadResul.getString("siglas_entidad"));
			entidades.add(entidad);				
			}
			return entidades;			
		} catch (Exception e) {
			desconectar();
			return entidades;
		}finally{		
			entidadResul.close();
		}		
	}	
	
	public void dispose() {
		desconectar();
	}
}