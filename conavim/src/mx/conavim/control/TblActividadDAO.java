package mx.conavim.control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.conavim.modelo.TblActividad;

public class TblActividadDAO extends Conexion{
	
	TblActividad oTblActividad = new TblActividad();
	
	public List<String> getAllActividades(){

		List<String> actividades = new ArrayList<String>();
		ResultSet res = null;
		PreparedStatement consulta = null;

		try {
			consulta = getConnection().prepareStatement("SELECT nombre_actividad FROM tbl_actividades");
			res = consulta.executeQuery();
			while (res.next()) {
				actividades.add(res.getString("nombre_actividad"));
			}
			res.close();
			consulta.close();

		} catch (Exception e) {
			desconectar();
			System.out.println("no se pudo consultar la tabla Actividades\n" + e);
		} finally {
			try {
				res.close();
				consulta.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return actividades;
	}
}
