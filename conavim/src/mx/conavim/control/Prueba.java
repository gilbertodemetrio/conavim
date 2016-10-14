package mx.conavim.control;

import org.primefaces.event.TabChangeEvent;

public class Prueba {
	private String mensaje="Esperando a conectar..";


	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public void probarConexion(){
		Conexion con = new Conexion();
		con.conectar();
		mensaje="Conexion Lograda!!!!!...";
		System.out.println("Conexion lograda!!..");
		
	}
	

}
