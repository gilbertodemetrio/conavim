package mx.conavim.control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.conavim.modelo.Tbl_Estrategia;
import mx.conavim.modelo.Tbl_Producto;
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

	public void dispose() {
		desconectar();
	}

}
