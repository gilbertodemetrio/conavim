package mx.conavim.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;





public abstract class Conexion {
	
	   static String bd = "conavim";
	   //parametros para base local
	   
	   static String user = "postgres";
	   static String password = "admin";
	   static String url = "jdbc:postgresql://localhost:5432/"+bd;
	   
	   //parametros para base de jelastic
	   
	   
	   //static String password = "SNHxas17182";
	   //static String url = "jdbc:postgresql://node21901-env-7716639.jl.serv.net.mx/"+bd;
	 //static String user = "webadmin";
	   
	   
	   
	   private Connection connection = null;
	   
	   protected ResultSet res = null;
	   protected PreparedStatement consulta = null;
		   
	   public Connection getConnection() {
		   return connection;
	   }

	public Conexion()
	   {
		   try
		   {
			   Class.forName("org.postgresql.Driver");
		       connection = DriverManager.getConnection(url, user, password);
		   }
		   catch(Exception ex)
		   {
			   ex.printStackTrace();
		   }
	   }
	 
	   public void desconectar()
	   {
	       try 
	       {
	    	   if(!connection.isClosed())
	    		   getConnection().close();
		   } 
	       catch (Exception ex) 
	       {
	    	   ex.printStackTrace();
		   }
	   }	   
	}
		