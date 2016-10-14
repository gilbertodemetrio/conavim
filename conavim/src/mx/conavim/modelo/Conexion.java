package mx.conavim.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;





public class Conexion {
	
	   static String bd = "prueba";
	   static String user = "gil";
	   //static String user = "postgres";
	   static String password = "gilberto_1993";
	   //static String password = "";
	   static String url = "jdbc:postgresql://node21778-conavim.jl.serv.net.mx/"+bd;
	   //static String url = "jdbc:postgresql://148.215.24.212:5432/"+bd;
	   
	   Connection connection = null;
	   
	   public Conexion(){
		   try{
		         //obtenemos el driver de para postgresql
		         Class.forName("org.postgresql.Driver");
		         //obtenemos la conexión
		         //String URL =  "jdbc:postgresql://node21771-conavimprueba.jl.serv.net.mx/{your_database_name}";
		         //DriverManager.getConnection(URL, user_name, user_password);
		         connection = DriverManager.getConnection(url,user,password);
		 
		         if (connection!=null){
		            System.out.println("Conexión a base de datos "+bd+" OK\n");
		         }
		      }
		      catch(SQLException e){
		         System.out.println(e);
		      }catch(ClassNotFoundException e){
		         System.out.println(e);
		      }catch(Exception e){
		         System.out.println(e);
		      }
	   }
	   
	   /**Permite retornar la conexión*/
	   public Connection conectar(){
	      return connection;
	   }
	 
	   public void desconectar(){
	      
	      try {
	    	//connection = null;
			conectar().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }	   
}
	