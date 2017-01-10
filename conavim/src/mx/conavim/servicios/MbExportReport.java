package mx.conavim.servicios;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.itextpdf.text.Chunk;
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
		String nombre="temp.xls";				
		String nombreDocumentoDownload=  "";
		String tempFile;
		OutputStream out = null;
		
		try{
				
			tempFile = getPath() + nombre;
			out = new FileOutputStream(tempFile);
								
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
					
					//lista de header
					List<String> headerValues = getHeaders();
					rowIndex+=3;
					HSSFCell headerCell = null;
					for (int i = 0; i < headerValues.size(); i++) {
						
							
							if(i==35){
								//preunta 20
								headerCell = headerRow.createCell(i);
								headerCell.setCellValue( headerValues.get(i) );								
								sheet.addMergedRegion( new org.apache.poi.ss.util.CellRangeAddress(rowIndex-4, rowIndex-4, i, (i=i+3)));
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(i);
								
								HSSFRow      headerRow1    = sheet.createRow( 1 );	
								headerCell = headerRow1.createCell(i-3);
								headerCell.setCellValue("Mujeres");
								sheet.addMergedRegion( new org.apache.poi.ss.util.CellRangeAddress(1, 1, 35,36 ));
								headerCell.setCellStyle(headerStyle);
								
								headerCell = headerRow1.createCell(i-1);
								headerCell.setCellValue("Hombres");
								sheet.addMergedRegion( new org.apache.poi.ss.util.CellRangeAddress(1, 1, 37,38 ));
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(37);
								
								HSSFRow      headerRow2    = sheet.createRow( 2 );	
								headerCell = headerRow2.createCell(i-3);
								headerCell.setCellValue("20.1. Población beneficiaria total (1)");
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(i-3);
								
								headerCell = headerRow2.createCell(i-2);								
								headerCell.setCellValue("20.2. Población beneficiaria indígena (1)");							
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(i-2);
								
								headerCell = headerRow2.createCell(i-1);
								headerCell.setCellValue("20.3. Población beneficiaria total (1)");	
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(i-1);
								
								headerCell = headerRow2.createCell(i);								
								headerCell.setCellValue("20.4. Población beneficiaria indígena (1)");
								sheet.autoSizeColumn(i);
								headerCell.setCellStyle(headerStyle);
								
//								//pregunta 21
								headerCell = headerRow.createCell(i+1);
								headerCell.setCellValue( "21. Niñas, niños y adolescentes beneficiadas(os) (sólo registrar para el periodo correspondiente) Mujeres");								
								sheet.addMergedRegion( new org.apache.poi.ss.util.CellRangeAddress(rowIndex-4, rowIndex-4, ++i, (i=i+7)));
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(39);
								
								headerCell = headerRow1.createCell(i-7);
								headerCell.setCellValue("Mujeres");
								sheet.addMergedRegion( new org.apache.poi.ss.util.CellRangeAddress(1, 1, 39,42 ));
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(i-7);
								
								headerCell = headerRow1.createCell(i-3);
								headerCell.setCellValue("Hombres");
								sheet.addMergedRegion( new org.apache.poi.ss.util.CellRangeAddress(1, 1, 43,46 ));
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(i-3);
								
								headerCell = headerRow2.createCell(i-7);
								headerCell.setCellValue("De 0 meses hasta 11 años con 11 meses de edad (un día antes de cumplir los 12 años)");
								sheet.addMergedRegion( new org.apache.poi.ss.util.CellRangeAddress(2, 2, 39,40 ));
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(39);
								
								headerCell = headerRow2.createCell(i-5);
								headerCell.setCellValue("De 12 años cumplidos hasta 17 años con 11 meses de edad (un día antes de cumplir los 18 años )");
								sheet.addMergedRegion( new org.apache.poi.ss.util.CellRangeAddress(2, 2, 41,42 ));
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(41);
//								
								headerCell = headerRow2.createCell(i-3);
								headerCell.setCellValue("De 0 meses hasta 11 años con 11 meses de edad (un día antes de cumplir los 12 años )");
								sheet.addMergedRegion( new org.apache.poi.ss.util.CellRangeAddress(2, 2, 43,44 ));
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(43);
								
								headerCell = headerRow2.createCell(i-1);
								headerCell.setCellValue("De 12 años cumplidos hasta 17 años con 11 meses de edad (un día antes de cumplir los 18 años )");
								sheet.addMergedRegion( new org.apache.poi.ss.util.CellRangeAddress(2, 2, 45,46 ));
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(45);
								
								HSSFRow      headerRow3    = sheet.createRow(3);	
								headerCell = headerRow3.createCell(i-7);
								headerCell.setCellValue("21.1. Población total");								
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(i-7);
								
								headerCell = headerRow3.createCell(i-6);
								headerCell.setCellValue("21.2 Población indígena ");								
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(i-6);
								
								headerCell = headerRow3.createCell(i-5);
								headerCell.setCellValue("21.3. Población total");								
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(i-5);
								
								headerCell = headerRow3.createCell(i-4);
								headerCell.setCellValue("21.4 Población indígena");								
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(i-4);
								
								headerCell = headerRow3.createCell(i-3);
								headerCell.setCellValue("21.5. Población total ");								
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(i-3);
								
								headerCell = headerRow3.createCell(i-2);
								headerCell.setCellValue("21.6 Población indígena ");								
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(i-2);
								
								headerCell = headerRow3.createCell(i-1);
								headerCell.setCellValue("21.7. Población total ");								
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(i-1);
								
								headerCell = headerRow3.createCell(i);
								headerCell.setCellValue("21.8 Población indígena ");								
								headerCell.setCellStyle(headerStyle);
								sheet.autoSizeColumn(i);
								
							}else if(i<35){	
								headerCell = headerRow.createCell(i);
								headerCell.setCellValue( headerValues.get(i) );	
								//sheet.addMergedRegion( new org.apache.poi.ss.util.CellRangeAddress(0, 3, i, i));
								//if(1!=0)
									sheet.autoSizeColumn(i);
								headerCell.setCellStyle(headerStyle);															
								
							}
							
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
							//preunta 20
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getTotalpoblbenfm());
							sheet.autoSizeColumn(posicionCelda);
							
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getPoblabenfmujing());
							sheet.autoSizeColumn(posicionCelda);
							
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getTotalpoblbenfh());
							sheet.autoSizeColumn(posicionCelda);
							
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getPoblabenfhobing());
							sheet.autoSizeColumn(posicionCelda);
							
							//preunta 21
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getNinasbenifi0a12pobltot());
							sheet.autoSizeColumn(posicionCelda);
							
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getNinasbenifi0a12poblindig());
							sheet.autoSizeColumn(posicionCelda);
							
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getNinasbenifi12a17pobltot());
							sheet.autoSizeColumn(posicionCelda);
							
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getNinasbenifi12a17poblingdig());
							sheet.autoSizeColumn(posicionCelda);
							
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getNinosbenifi0a12pobltot());
							sheet.autoSizeColumn(posicionCelda);
							
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getNinosbenifi0a12poblingid());
							sheet.autoSizeColumn(posicionCelda);
							
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getNinosbenifi12a17pobltot());
							sheet.autoSizeColumn(posicionCelda);
							
							contentCell = contentRow.createCell(posicionCelda++);
							contentCell.setCellValue(lstTblRespuesta.get(i).getNinpsbenifi12a17poblindig());
							sheet.autoSizeColumn(posicionCelda);
							//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					}																					
					
					workbook.write(out);
					//((Closeable) workbook).close();
					out.flush();
					out.close();
					workbook=null;
					downloadFile(tempFile, nombreDocumentoDownload);
				}else{
					
				}
				
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			
			try {
				out.close();
				out.flush();
			} catch (IOException e) {
				
			}
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
				Font fontNumerosLinea = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(65, 65, 65));
				Font fontBasicInfo = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(65, 65, 65));
				
				PdfWriter.getInstance(document, new FileOutputStream(tempFile));
				document.open();
				
				Paragraph textPeriodo = new Paragraph("Periodo: " + oTbl_reportDAO.getoEntidadSecre().getTipo(), fontBasicInfo);
				textPeriodo.setAlignment(Element.ALIGN_RIGHT);
				document.add(textPeriodo);
				
				Paragraph textNombreEntidad = new Paragraph(oTbl_reportDAO.getoEntidadSecre().getNombre_entidadsec() , fontTitulo);
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
						
						String[] descripcionLinea = oTblRespuesta.getDescripcionLinea().split(" ", 2);
						String descripcionNumero = "";
						String descripcionTexto = "";
						if(descripcionLinea[0] != null)
							descripcionNumero = descripcionLinea[0];
						if(descripcionLinea[1] != null)
							descripcionTexto = descripcionLinea[1];
						
						Paragraph textDescripcionLinea = new Paragraph();
						textDescripcionLinea.add(new Chunk("Linea ", fontBasicInfo));
						textDescripcionLinea.add(new Chunk(descripcionNumero, fontNumerosLinea));
						textDescripcionLinea.add(new Chunk(" "+descripcionTexto, fontBasicInfo));												
						textDescripcionLinea.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textDescripcionLinea);
						Paragraph textRespuesta1 = new Paragraph("1. Dependencia/ Entidad/ Sistema: R. " 
						+ oTbl_reportDAO.getoEntidadSecre().getNombre_entidadsec(), fontBasicInfo);
						textRespuesta1.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta1);
						
						Paragraph textRespuesta2 = new Paragraph("2. Actividades para el Cumplimiento de la línea de acción: R. " 
						+ oTblRespuesta.getActivicumpla() + oTblRespuesta.getOtroactivicumpla() + ", Detalles: "+oTblRespuesta.getDescripactivida(), fontBasicInfo);
						textRespuesta2.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta2);
						
						Paragraph textRespuesta3 = new Paragraph("3. Tipo de actividad: R. " 
						+ (oTblRespuesta.getTipoactivi().equals(null) ? "" :oTblRespuesta.getTipoactivi() + oTblRespuesta.getOtrotipoactivi() !=null ? ", "+ oTblRespuesta.getOtrotipoactivi() :"" ), fontBasicInfo);
						textRespuesta3.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta3);
						
						Paragraph textRespuesta4 = new Paragraph("4. Fecha de inicio de actividades: R. " +
						(oTblRespuesta.getFechainactv()==null? " ":oTblRespuesta.getFechainactv()), fontBasicInfo);
						textRespuesta4.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta4);
						
						Paragraph textRespuesta5 = new Paragraph("5. Fecha de termino de actividades: R. " + 
						(oTblRespuesta.getFechatermactv()==null ? " ": oTblRespuesta.getFechatermactv()), fontBasicInfo);
						textRespuesta5.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta5);
						
						Paragraph textRespuesta6 = new Paragraph("6. Productos generados y/o evidencias (documento probatorio, liga o hipervínculo): R. " +
						(oTblRespuesta.getProducto()==null ? "":oTblRespuesta.getProducto()) + oTblRespuesta.getLinkproducto(), fontBasicInfo);
						textRespuesta6.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta6);
						
						Paragraph textRespuesta7 = new Paragraph("7. Meta programada para la línea de acción: R. " +
						(oTblRespuesta.getMetaprgmdlinacc()==null ? "":oTblRespuesta.getMetaprgmdlinacc() + oTblRespuesta.getOtrometaprgmdlinacc() !=null ? ", "+oTblRespuesta.getOtrometaprgmdlinacc() :""), fontBasicInfo);
						textRespuesta7.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta7);
						
						Paragraph textRespuesta8 = new Paragraph("8. Unidad de medida de la Meta: R. " +
						(oTblRespuesta.getUnidadmedinacc()==null ? "":oTblRespuesta.getUnidadmedinacc()), fontBasicInfo);
						textRespuesta8.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta8);
						
						Paragraph textRespuesta9 = new Paragraph("9. Periodicidad de la Meta programada para línea de acción: R. " + 
						(oTblRespuesta.getPeriodometaprglinacc()==null ? " ":oTblRespuesta.getPeriodometaprglinacc() ), fontBasicInfo);
						textRespuesta9.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta9);
						
						Paragraph textRespuesta10 = new Paragraph("10. Meta Programada para línea de acción: R. " +
						(oTblRespuesta.getMetaproglinacc()==null ? " " :oTblRespuesta.getMetaproglinacc()), fontBasicInfo);
						textRespuesta10.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta10);

						Paragraph textRespuesta11 = new Paragraph("11. Meta Programada para actividades: R. " +
						(oTblRespuesta.getMetaprogactv()!=null ? oTblRespuesta.getMetaprogactv() :""), fontBasicInfo);
						textRespuesta11.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta11);
						
						Paragraph textRespuesta12 = new Paragraph("12. Periodicidad de la Meta programada para actividade: R. " +
						(oTblRespuesta.getPeriodometaprgactiv() !=null ? oTblRespuesta.getPeriodometaprgactiv() : ""), fontBasicInfo);
						textRespuesta12.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta12);
						
						Paragraph textRespuesta13 = new Paragraph("13. Avance (enero - diciembre): R. " + oTblRespuesta.getAvance(), fontBasicInfo);
						textRespuesta13.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta13);
						
						Paragraph textRespuesta14 = new Paragraph("14. Explicación de la variación y/o justificación en caso que el cumplimiento no sea satisfactorio (100%): R. " +
						(oTblRespuesta.getExplicacionavance() != null ? oTblRespuesta.getExplicacionavance():"" ), fontBasicInfo);
						textRespuesta14.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta14);
						
						Paragraph textRespuesta15 = new Paragraph("15. Observaciones: R. " +( oTblRespuesta.getObservaciones() !=null ? oTblRespuesta.getObservaciones() :"" ), fontBasicInfo);
						textRespuesta15.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta15);
						
						Paragraph textRespuesta16 = new Paragraph("16. ¿Otras instituciones colaboraron con ustedes para el cumplimiento de la línea de acción?: R. " + 
						(oTblRespuesta.getOtrasinsticolaboran() !=null ? oTblRespuesta.getOtrasinsticolaboran():""), fontBasicInfo);
						textRespuesta16.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta16);
						
						Paragraph textRespuesta17 = new Paragraph("17. Presupuesto destinado a estas actividades o línea de acción: R. " + 
						oTblRespuesta.getPresupuesto(), fontBasicInfo);
						textRespuesta17.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta17);
						
						Paragraph textRespuesta18 = new Paragraph("18. Fuente de financiamiento: R. " +
						concatRespCombos(oTblRespuesta.getFuentefinacia())	+ (oTblRespuesta.getOtrofuentefinacia()!=null ? ", "+ oTblRespuesta.getOtrofuentefinacia():"" ), fontBasicInfo );
						textRespuesta18.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta18);
						
						Paragraph textRespuesta19 = new Paragraph("19. Número de Servicios otorgados mediante estas actividades o acciones: R. " 
						+ oTblRespuesta.getNoserviciootrg(), fontBasicInfo);
						textRespuesta19.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta19);
						
						Paragraph textRespuesta20 = new Paragraph("20. Número de personas beneficiadas con estas actividades o acciones (sólo registrar beneficiarias/os de 18 años y más y para el periodo correspondiente):", fontBasicInfo);
						textRespuesta20.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta20);		
						
						Paragraph textMsjMujeres = new Paragraph("Mujeres", fontBasicInfo);
						textMsjMujeres.setAlignment(Element.ALIGN_CENTER);
						document.add(textMsjMujeres);
						
						Paragraph textRespuesta27 = new Paragraph("20.1. Población beneficiaria total: R. " + oTblRespuesta.getTotalpoblbenfm(), fontBasicInfo);
						textRespuesta27.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta27);
						
						Paragraph textRespuesta28 = new Paragraph("20.2. Población beneficiaria indígena (1): R. " + oTblRespuesta.getPoblabenfmujing(), fontBasicInfo);
						textRespuesta28.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta28);
						
						Paragraph textMsjHombres = new Paragraph("Hombres", fontBasicInfo);
						textMsjHombres.setAlignment(Element.ALIGN_CENTER);
						document.add(textMsjHombres);						
												
						Paragraph textRespuesta29 = new Paragraph("20.3. Población beneficiaria total (1): R. " + oTblRespuesta.getTotalpoblbenfh(), fontBasicInfo);
						textRespuesta29.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta29);
						
						Paragraph textRespuestaC = new Paragraph("20.4. Población beneficiaria indígena: R. " + oTblRespuesta.getPoblabenfhobing(),  fontBasicInfo);
						textRespuestaC.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuestaC);
						
						Paragraph textRespuesta21 = new Paragraph("21. Niñas, niños y adolescentes beneficiadas(os) (sólo registrar para el periodo correspondiente):" , fontBasicInfo);
						textRespuesta21.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta21);

						document.add(textMsjMujeres);
						
						Paragraph textRespuesta22 = new Paragraph("De 0 meses hasta 11 años con 11 meses de edad (un día antes de cumplir los 12 años ): R.  Población total " + 
						oTblRespuesta.getNinasbenifi0a12pobltot() + ", Población indígena "+ oTblRespuesta.getNinasbenifi0a12poblindig(), fontBasicInfo);
						textRespuesta22.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta22);
						
						Paragraph textRespuesta23 = new Paragraph("De 12 años cumplidos hasta 17 años con 11 meses de edad (un día antes de cumplir los 18 años ): R.  Población total " + 
						oTblRespuesta.getNinasbenifi12a17pobltot()+ ", Población indígena "+ + oTblRespuesta.getNinasbenifi12a17poblingdig(), fontBasicInfo);
						textRespuesta23.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta23);
						

						document.add(textMsjHombres);						
						
						Paragraph textRespuesta24 = new Paragraph("De 0 meses hasta 11 años con 11 meses de edad (un día antes de cumplir los 12 años ): R.  Población total " +
						oTblRespuesta.getNinosbenifi0a12pobltot() + ", Población indígena "+ oTblRespuesta.getNinosbenifi0a12poblingid(), fontBasicInfo);
						textRespuesta24.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta24);
						
						Paragraph textRespuesta25 = new Paragraph("De 12 años cumplidos hasta 17 años con 11 meses de edad (un día antes de cumplir los 18 años ): R.  Población total " +
						oTblRespuesta.getNinosbenifi12a17pobltot() + ", Población indígena "+ oTblRespuesta.getNinpsbenifi12a17poblindig(), fontBasicInfo);
						textRespuesta25.setAlignment(Element.ALIGN_JUSTIFIED);
						document.add(textRespuesta25);
						
;
						


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
		
		//reponsePath = reponsePath + "eclipseApps\\conavim\\";
		reponsePath = reponsePath + "eclipseApps\\conavim\\temp\\";//comentar
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
