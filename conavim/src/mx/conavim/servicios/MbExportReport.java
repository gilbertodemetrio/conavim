package mx.conavim.servicios;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

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
import mx.conavim.modelo.Tbl_EntidadSecre;
import mx.conavim.modelo.Tbl_Informes;

public class MbExportReport {
	
	private HSSFWorkbook workbook;
	
	private Tbl_reportDAO oTbl_reportDAO;
	
	private String temp="";
	private String[] tempArr;

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
	

	
	public void exportExcel(String idInforme){
		oTbl_reportDAO = new Tbl_reportDAO();
		int rowIndex = 0;
		HSSFFont headerFont;
		HSSFCellStyle headerStyle;
		
		try{
				
				
				String nombre="temp.xls";				
				String nombreDocumentoDownload=  "";
				String tempFile = getPath() + nombre;
				OutputStream out = new FileOutputStream(tempFile);
				
				if(oTbl_reportDAO.findEntidades(idInforme,true))
				{
					workbook = new HSSFWorkbook();
					HSSFSheet    sheet    = workbook.createSheet("Reporte Total");
					// Generate fonts
					headerFont  = createFont(HSSFColor.WHITE.index, (short)12, true);
					// Generate styles
					headerStyle  = createStyle(headerFont,  HSSFCellStyle.ALIGN_CENTER, HSSFColor.BLUE_GREY.index,       true, HSSFColor.WHITE.index);
					// Table header
					HSSFRow      headerRow    = sheet.createRow( rowIndex++ );					
					//creamos nombre del pdf
					nombreDocumentoDownload = "ReporteTotal.xls";
//					if (oTbl_reportDAO.getoEntidadSecre().getNombre_entidadsec().length() > 60)
//						nombreDocumentoDownload = oTbl_reportDAO.getoEntidadSecre().getNombre_entidadsec().substring(0, 59)+".xls";
//					else
//						nombreDocumentoDownload = oTbl_reportDAO.getoEntidadSecre().getNombre_entidadsec()+".xls";
//					nombreDocumentoDownload = nombreDocumentoDownload.replaceAll("[\r\n\t]", " ").trim();
					
					//lista de header
					List<String> headerValues = getHeaders();
					
					HSSFCell headerCell = null;
					for (int i = 0; i < headerValues.size(); i++) {
						headerCell = headerRow.createCell(i);
							
							if(i==34){
								headerCell.setCellValue( headerValues.get(i) );
								sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(rowIndex, rowIndex, i, i++));
							}else{								
								headerCell.setCellValue( headerValues.get(i) );
								sheet.autoSizeColumn(i);
							}
							headerCell.setCellStyle(headerStyle);
					}
					
					// Table content
					HSSFRow  contentRow  = null;
					HSSFCell contentCell = null;
					List<Tbl_Informes> lstTblInformes = oTbl_reportDAO.getLstTblInformes();
					List<Tbl_EntidadSecre> lstEntidadSecre = oTbl_reportDAO.getLstTblEntidadSecre();
					List<TblRespuesta> lstTblRespuesta = oTbl_reportDAO.getLstTblRespuesta();
					
					
					for(int i=0;i<lstTblInformes.size();i++){
							int posicionCelda=0;
							contentRow = sheet.createRow( rowIndex++ );
							
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblInformes.get(i).getId_informe());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblInformes.get(i).getAnio());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblInformes.get(i).getPeriodo());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblInformes.get(i).getFecha_captura().toString());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblInformes.get(i).getFecha_modifi().toString());
							sheet.autoSizeColumn(posicionCelda);
							////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstEntidadSecre.get(i).getSiglas_entidad());
							sheet.autoSizeColumn(posicionCelda);
							//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getDescripcionObjetivo());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getDescripcionEstrategia());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getDescripcionLinea());
							sheet.autoSizeColumn(posicionCelda);
//							////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstEntidadSecre.get(i).getSiglas_entidad());
							sheet.autoSizeColumn(posicionCelda);
							//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////							
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getActivicumpla());
							sheet.autoSizeColumn(posicionCelda);							
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getOtroactivicumpla());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getDescripactivida());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getTipoactivi());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getOtrotipoactivi());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue((lstTblRespuesta.get(i).getFechainactv()!=null?lstTblRespuesta.get(i).getFechainactv().toString():""));
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue((lstTblRespuesta.get(i).getFechatermactv()!=null?lstTblRespuesta.get(i).getFechatermactv().toString():""));
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getProducto());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getTipoproduct());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getLinkproducto());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getMetaprgmdlinacc());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getOtrometaprgmdlinacc());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getUnidadmedinacc());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getPeriodometaprglinacc());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getMetaproglinacc());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getMetaprogactv());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getPeriodometaprgactiv());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getAvance());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getExplicacionavance());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getObservaciones());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getOtrasinsticolaboran());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getPresupuesto());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(concatRespCombos(lstTblRespuesta.get(i).getFuentefinacia()));
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getOtrofuentefinacia());
							sheet.autoSizeColumn(posicionCelda);
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getNoserviciootrg());
							sheet.autoSizeColumn(posicionCelda);
							
							
							//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					}																					
					
					workbook.write(out);
					//((Closeable) workbook).close();
					out.flush();
					out.close();
					downloadFile(tempFile, nombreDocumentoDownload);
				}else{
					
				}
				
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
	public String concatRespCombos(String[] valores){
		String temp="";
			for(String valor : valores)
				temp+=valor;
		
		return temp;
	}
	
	public void exportPDF2(String idInforme)
	{
		try
		{
			oTbl_reportDAO = new Tbl_reportDAO();
			
			String nombre = "temp.pdf";
			String nombreDocumentoDownload=  "";
			String tempFile = getPath() + nombre;
			
			if(oTbl_reportDAO.findEntidades(idInforme,false))
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
		
		//reponsePath = reponsePath + "eclipseApps\\conavim\\temp\\";//comentar
		reponsePath = reponsePath + "eclipseApps\\temp\\";//comentar
		//reponsePath = reponsePath + "applications\\conavim\\temp\\";//descomentar
		
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
	
	
	private HSSFFont createFont(short fontColor, short fontHeight, boolean fontBold) {
		
		HSSFFont font = workbook.createFont();		
		font.setColor(fontColor);
		font.setFontName("Arial");
		font.setFontHeightInPoints(fontHeight);
		
		return font;
	}

	private HSSFCellStyle createStyle(HSSFFont font, short cellAlign, short cellColor, boolean cellBorder, short cellBorderColor) {

		HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		style.setAlignment(cellAlign);
		style.setFillForegroundColor(cellColor);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		if (cellBorder) {
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			
			style.setTopBorderColor(cellBorderColor);
			style.setLeftBorderColor(cellBorderColor);
			style.setRightBorderColor(cellBorderColor);
			style.setBottomBorderColor(cellBorderColor);
		}
		
		return style;
	}
	
	public static List<String> getHeaders() {
		List<String> tableHeader = new ArrayList<String>();
		tableHeader.add("ID");		
		tableHeader.add("Año");
		tableHeader.add("Periodo");
		tableHeader.add("Fecha Alta");
		tableHeader.add("Fecha Modificación");
		tableHeader.add("Dependencia");
		tableHeader.add("Objetivo");
		tableHeader.add("Estrategia");
		tableHeader.add("Linea");
		tableHeader.add("1. Dependencia/ Entidad/ Sistema");
		tableHeader.add("2. Actividades para el Cumplimiento de la línea de acción");
		tableHeader.add("Otra actividad");
		tableHeader.add("Detalles");
		tableHeader.add("3. Tipo de actividad");
		tableHeader.add("Otro tipo Actividad");
		tableHeader.add("4. Fecha de inicio de actividades");
		tableHeader.add("5. Fecha de termino de actividades");
		tableHeader.add("6. Productos generados y/o evidencias (documento probatorio, liga o hipervínculo)");
		tableHeader.add("Tipo");
		tableHeader.add("Documento");
		tableHeader.add("7. Meta programada para la línea de acción");
		tableHeader.add("Otra meta");
		tableHeader.add("8. Unidad de medida de la Meta");
		tableHeader.add("9. Periodicidad de la Meta programada para línea de acción");
		tableHeader.add("10. Meta Programada para línea de acción");
		tableHeader.add("11. Meta Programada para actividades");
		tableHeader.add("12. Periodicidad de la Meta programada para actividades");
		tableHeader.add("13. Avance (enero - diciembre");
		tableHeader.add("14. Explicación de la variación y/o justificación en caso que el cumplimiento no sea satisfactorio (100%)");
		tableHeader.add("15. Observaciones");
		tableHeader.add("16. ¿Otras instituciones colaboraron con ustedes para el cumplimiento de la línea de acción?");
		tableHeader.add("17. Presupuesto destinado a estas actividades o línea de acción");
		tableHeader.add("18. Fuente de financiamiento");
		tableHeader.add("Otra fuente");		
		tableHeader.add("19. Número de Servicios otorgados mediante estas actividades o acciones");
		tableHeader.add("20. Número de personas beneficiadas con estas actividades o acciones (sólo registrar beneficiarias/os de 18 años y más y para el periodo correspondiente)");// 32
		tableHeader.add("Mujeres");
		tableHeader.add("Hombres");
		tableHeader.add("20.1. Población beneficiaria total (1)");
		tableHeader.add("20.2. Población beneficiaria ind&iacute;gena (1)");
		tableHeader.add("20.3. Población beneficiaria total (1)");
		tableHeader.add("20.4. Población beneficiaria ind&iacute;gena (1)");
		tableHeader.add("21. Niñas, niños y adolescentes beneficiadas(os) (sólo registrar para el periodo correspondiente)");
		tableHeader.add("");
		tableHeader.add("");
		tableHeader.add("");
		tableHeader.add("");
		tableHeader.add("");
		
		
		return tableHeader;
	}
}
