package mx.conavim.control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;







import mx.conavim.modelo.tbl_lineasaccion;

public class Tbl_lineasaccionDAO {
	
	
	
	
	public List< tbl_lineasaccion> getLineasAccion() {
		  List< tbl_lineasaccion> lineasaccion = new ArrayList< tbl_lineasaccion>();
		  Conexion conex= new Conexion();
		  ResultSet res = null;
		  PreparedStatement consulta = null;
		     
		  try {
		   consulta = conex.conectar().prepareStatement("SELECT * FROM tbl_lineasaccion");
		   res = consulta.executeQuery();
		   while(res.next()){
			tbl_lineasaccion linea= new tbl_lineasaccion();
			linea.setId_lineaaccion(res.getInt("id_lineaaccion"));	
			linea.setId_estrategia(res.getInt("id_estrategia"));
			linea.setNombre_linea(res.getString("nombre_linea"));
			linea.setDescripcion((res.getString("descripcion")));
			linea.setStatus(res.getInt("status"));;
			
			lineasaccion.add(linea);
		  }
		          res.close();
		          consulta.close();
		          conex.desconectar();
		    
		  } catch (Exception e) {
		   System.out.println("no se pudo consultar la Persona\n"+e);
		  }finally{
			  try {
				res.close();
				consulta.close();
		        conex.desconectar();
			} catch (SQLException e) {				
				e.printStackTrace();
			}	         
		  }
		  
		  return lineasaccion;
		 }
		 
		
	

}
