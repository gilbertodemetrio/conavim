package mx.conavim.servicios;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Session {
	
	
	public static void close()
	{
		try
	  	{
			System.out.println("cerro sesion");
			removeBeans();
			getSession().invalidate();
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("secure/");
	  	}catch(Exception ex)
	  	{
	  		ex.printStackTrace();
	  	}
	}
	
	private static HttpSession getSession() {
        return (HttpSession)
          FacesContext.
          getCurrentInstance().
          getExternalContext().
          getSession(false);
      }
	
	private static void removeBeans() throws Exception
	{
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("prueba");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("mbTbl_LineasAccion");
	}

}
