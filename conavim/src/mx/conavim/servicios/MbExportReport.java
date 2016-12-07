package mx.conavim.servicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import mx.conavim.control.Tbl_reportDAO;
import mx.conavim.modelo.TblEstrategia;
import mx.conavim.modelo.TblLineaAccion;
import mx.conavim.modelo.TblObjetivo;
import mx.conavim.modelo.TblRespuesta;

public class MbExportReport {
	
	private Tbl_reportDAO oTbl_reportDAO;

	@PostConstruct
	public void init() 
	{
		
	}
	
	public void exportPDF(String idEntidad)
	{
		try
		{
			oTbl_reportDAO = new Tbl_reportDAO();
			
			String nombre = "temp.pdf";
			String nombreDocumentoDownload=  "";
			String tempFile = getPath() + nombre;
			
			if(oTbl_reportDAO.findEntidad(idEntidad))
			{	
				Document document = new Document(PageSize.LETTER);
				
				Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(4, 4, 4));
				Font fontBasicTitulo = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(65, 65, 65));
				Font fontBasicInfo = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(65, 65, 65));
				
				PdfWriter.getInstance(document, new FileOutputStream(tempFile));
				document.open();
				
				Paragraph textNombreEntidad = new Paragraph(oTbl_reportDAO.getoEntidadSecre().getNombre_entidadsec(), fontTitulo);
				textNombreEntidad.setAlignment(Element.ALIGN_CENTER);
				
				document.add(textNombreEntidad);
				document.add(new Paragraph(" "));
				if (oTbl_reportDAO.getoEntidadSecre().getNombre_entidadsec().length() > 60)
					nombreDocumentoDownload = oTbl_reportDAO.getoEntidadSecre().getNombre_entidadsec().substring(0, 59)+".pdf";
				else
					nombreDocumentoDownload = oTbl_reportDAO.getoEntidadSecre().getNombre_entidadsec()+".pdf";
				nombreDocumentoDownload = nombreDocumentoDownload.replaceAll("[\r\n\t]", " ").trim();
				if(oTbl_reportDAO.findObjetivos())
				{
					for(TblObjetivo objetivo : oTbl_reportDAO.getLstObjetivos())
					{
						Paragraph textNombreObjetivo = new Paragraph("Objetivo " + objetivo.getDescripcion_obj(), fontBasicTitulo);
						textNombreObjetivo.setAlignment(Element.ALIGN_JUSTIFIED);
						
						document.add(textNombreObjetivo);
						document.add(new Paragraph(" "));
						
						if(oTbl_reportDAO.findEstrategias(objetivo.getId_objetivo()))
						{
							for(TblEstrategia estrategia : oTbl_reportDAO.getLstEstrategias())
							{	
								Paragraph textNombreEstrategia = new Paragraph("Estrategia " + estrategia.getDescripcion(), fontBasicTitulo);
								textNombreEstrategia.setAlignment(Element.ALIGN_JUSTIFIED);
								
								document.add(textNombreEstrategia);
								document.add(new Paragraph(" "));
								
								if(oTbl_reportDAO.findLineas(estrategia.getId_estrategia()))
								{
									for(TblLineaAccion linea : oTbl_reportDAO.getLstLineasAccion())
									{	
										Paragraph textNombreLinea = new Paragraph(linea.getDescripcion(), fontBasicInfo);
										textNombreLinea.setAlignment(Element.ALIGN_JUSTIFIED);
										
										document.add(textNombreLinea);	
									}
									
									document.add(new Paragraph(" "));
								}
							}
						}
					}	
				}
				else
				{
					
				}
				
				document.close();				
				downloadFile(tempFile, nombreDocumentoDownload);
			}
			else
			{
				
			}			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			oTbl_reportDAO.dispose();
		}
	}
	
	public void exportPDF2(String idInforme)
	{
		try
		{
			oTbl_reportDAO = new Tbl_reportDAO();
			
			String nombre = "temp.pdf";
			String nombreDocumentoDownload=  "";
			String tempFile = getPath() + nombre;
			
			if(oTbl_reportDAO.findEntidades(idInforme))
			{	
				Document document = new Document(PageSize.LETTER);
				
				Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(4, 4, 4));
				Font fontBasicTitulo = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(65, 65, 65));
				Font fontBasicInfo = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(65, 65, 65));
				
				PdfWriter.getInstance(document, new FileOutputStream(tempFile));
				document.open();
				
				Paragraph textPeriodo = new Paragraph("Periodo: " + oTbl_reportDAO.getoEntidadSecre().getTipo(), fontBasicInfo);
				textPeriodo.setAlignment(Element.ALIGN_RIGHT);
				document.add(textPeriodo);
				
				Paragraph textNombreEntidad = new Paragraph(oTbl_reportDAO.getoEntidadSecre().getNombre_entidadsec() + " - " + oTbl_reportDAO.getoEntidadSecre().getSiglas_entidad(), fontTitulo);
				textNombreEntidad.setAlignment(Element.ALIGN_CENTER);				
				document.add(textNombreEntidad);
				
				if (oTbl_reportDAO.getoEntidadSecre().getNombre_entidadsec().length() > 60)
					nombreDocumentoDownload = oTbl_reportDAO.getoEntidadSecre().getNombre_entidadsec().substring(0, 59)+".pdf";
				else
					nombreDocumentoDownload = oTbl_reportDAO.getoEntidadSecre().getNombre_entidadsec()+".pdf";
				nombreDocumentoDownload = nombreDocumentoDownload.replaceAll("[\r\n\t]", " ").trim();
				for(TblRespuesta oTblRespuesta: oTbl_reportDAO.getLstTblRespuesta())
				{
					
						document.add(new Paragraph(""));
						Paragraph textNombreObjetivo = new Paragraph("Objetivo " + oTblRespuesta.getDescripcionObjetivo(), fontBasicTitulo);
						textNombreObjetivo.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textNombreObjetivo); 						
						Paragraph textEstrategia = new Paragraph( "Estrategia " +oTblRespuesta.getDescripcionEstrategia(), fontBasicTitulo);
						textEstrategia.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textEstrategia);
						document.add(new Paragraph(""));
						Paragraph textDescripcionLinea = new Paragraph("Linea " + oTblRespuesta.getDescripcionLinea(), fontBasicInfo);
						textDescripcionLinea.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textDescripcionLinea);
						Paragraph textRespuesta1 = new Paragraph("1. Dependencia/ Entidad/ Sistema: R." + oTbl_reportDAO.getoEntidadSecre().getNombre_entidadsec(), fontBasicInfo);
						textRespuesta1.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta1);
						
						Paragraph textRespuesta2 = new Paragraph("2. Actividades para el Cumplimiento de la línea de acción: R." + oTblRespuesta.getActivicumpla(), fontBasicInfo);
						textRespuesta2.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta2);
						
						Paragraph textRespuesta3 = new Paragraph("3. Tipo de actividad: R." + oTblRespuesta.getTipoactivi(), fontBasicInfo);
						textRespuesta3.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta3);
						
						Paragraph textRespuesta4 = new Paragraph("4. Fecha de inicio de actividades: R." + oTblRespuesta.getFechainactv(), fontBasicInfo);
						textRespuesta4.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta4);
						
						Paragraph textRespuesta5 = new Paragraph("5. Fecha de termino de actividades: R." + oTblRespuesta.getFechatermactv(), fontBasicInfo);
						textRespuesta5.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta5);
						
						document.add(new Paragraph(" "));
						Paragraph textRespuesta6 = new Paragraph(".....después aquí van a aparecer el resto de las preguntas con sus respuestas. " , fontBasicTitulo);
						textRespuesta6.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta6);
						document.add(new Paragraph(" "));
						
						
						
				}
				
				
				document.close();				
				downloadFile(tempFile, nombreDocumentoDownload);
			}
			else
			{
				Document document = new Document(PageSize.LETTER);
				
				Font fontBasicTitulo = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(65, 65, 65));				
				
				PdfWriter.getInstance(document, new FileOutputStream(tempFile));
				document.open();
				
				Paragraph textNombreEntidad = new Paragraph("La entidad y periodo seleccionado aún no tiene líneas de acción.", fontBasicTitulo);
				textNombreEntidad.setAlignment(Element.ALIGN_CENTER);				
				document.add(textNombreEntidad);
								
				nombreDocumentoDownload = "EntidadSinLineasDeAccion.pdf".replaceAll("[\r\n\t]", " ").trim();
				
				document.close();				
				downloadFile(tempFile, nombreDocumentoDownload);
			}			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			oTbl_reportDAO.dispose();
		}
	}
	
	public String getPath() throws UnsupportedEncodingException 
	{
		String path = this.getClass().getClassLoader().getResource("").getPath();
		String fullPath = URLDecoder.decode(path, "UTF-8");
		String pathArr[] = fullPath.split("lib/classes/");
		fullPath = pathArr[0];
		
		String reponsePath = "";
		reponsePath = new File(fullPath).getPath() + File.separatorChar;
		//reponsePath = reponsePath + "eclipseApps\\conavim\\temp\\";
		reponsePath = reponsePath + "applications\\conavim\\temp\\";
		
		return reponsePath;
	}
	
	public void downloadFile(String tempRute,String nombre) 
	{
		File file = new File(tempRute);
		FacesContext facesContext = FacesContext.getCurrentInstance();		
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.reset();
		response.setContentType("application/force-download");
        response.setCharacterEncoding("UTF-8");
        try {
			nombre = URLEncoder.encode(nombre,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		response.setHeader("Content-Disposition", "attachment;filename=" + nombre );		
		
		response.setContentLength((int) file.length());
		
		FileInputStream input= null;
	
		int buffer;
		
		try 
		{			
			input = new FileInputStream(file);
			
			while ((buffer = input.read()) != -1) 
			{  
				response.getOutputStream().write(buffer);  
				response.getOutputStream().flush();  
			}
			
			facesContext.responseComplete();
			facesContext.renderResponse();
			input.close();
		} 
		catch (Exception ex) 
		{
			try{
				facesContext.responseComplete();
				facesContext.renderResponse();
				input.close();
			}catch(Exception ex1){
				ex.printStackTrace();
			}
			ex.printStackTrace();
		}
		finally
		{
			if(file.exists())
				file.delete();
		}
	}

}
