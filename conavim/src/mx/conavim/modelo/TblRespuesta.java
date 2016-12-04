package mx.conavim.modelo;



import java.text.ParseException;
import java.util.Date;




public class TblRespuesta {
	
	private int id_respuesta;
	private String id_informe ;
	private int id_lineaaccion;
	private String dependentsist ;
	private String activicumpla ;
	private String descripactivida;
	private String tipoactivi ;
	private Date fechainactv;
	private Date fechatermactv;
	private String producto ;
	private String tipoproduct ;
	private String linkproducto ;
	private String metaprgmdlinacc ;
	private String metaprgmdlinacc2 ;
	private String unidadmedinacc ;
	private String periodometaprglinacc ;
	private String metaproglinacc ;
	private String metaprogactv ;
	private String periodometaprgactiv ;
	private String avance ;
	private String explicacionavance ;
	private String observaciones ;
	private String otrasinsticolaboran ;
	private int presupuesto;
	private String [] fuentefinacia;
	private String fuentefinacia2 ;
	private int noserviciootrg;
	private int totalpoblbenfm;
	private int poblabenfmujing;
	private int totalpoblbenfh;
	private int poblabenfhobing;
	private int ninasbenifi0a12pobltot;
	private int ninasbenifi0a12poblindig;
	private int ninasbenifi12a17pobltot;
	private int ninasbenifi12a17poblingdig;
	private int ninosbenifi0a12pobltot;
	private int ninosbenifi0a12poblingid;
	private int ninosbenifi12a17pobltot;
	private int ninpsbenifi12a17poblindig;
	private int status=1;
	
	//obtener seleccion manyCheckbox
	private String[] seleCheckPregunta3;
	
	
	

	
	
	public String getDescripactivida() {
		return descripactivida;
	}
	public void setDescripactivida(String descripactivida) {
		this.descripactivida = descripactivida;
	}
	public String getMetaprgmdlinacc2() {
		return metaprgmdlinacc2;
	}
	public void setMetaprgmdlinacc2(String metaprgmdlinacc2) {
		this.metaprgmdlinacc2 = metaprgmdlinacc2;
	}
	public String getFuentefinacia2() {
		return fuentefinacia2;
	}
	public void setFuentefinacia2(String fuentefinacia2) {
		this.fuentefinacia2 = fuentefinacia2;
	}
	
	public String[] getSeleCheckPregunta3() {		
		return seleCheckPregunta3;
	}
	public void setSeleCheckPregunta3(String[] seleCheckPregunta3) {
		this.seleCheckPregunta3 = seleCheckPregunta3;
	}
	public TblRespuesta(){
		//System.out.println("Accediendo a modelo Tbl Respuesta");
	}
	public int getId_respuesta() {
		return id_respuesta;
	}
	public void setId_respuesta(int id_respuesta) {
		this.id_respuesta = id_respuesta;
	}
	public String getId_informe() {
		return id_informe;
	}
	public void setId_informe(String id_informe) {
		this.id_informe = id_informe;
	}
	public int getId_lineaaccion() {
		return id_lineaaccion;
	}
	public void setId_lineaaccion(int id_lineaaccion) {
		this.id_lineaaccion = id_lineaaccion;
	}
	public String getDependentsist() {
		return dependentsist;
	}
	public void setDependentsist(String dependentsist) {
		this.dependentsist = dependentsist;
	}
	public String getActivicumpla() {
		return activicumpla;
	}
	public void setActivicumpla(String activicumpla) {
		this.activicumpla = activicumpla;
	}
	public String getTipoactivi() {
		return tipoactivi;
	}
	public void setTipoactivi(String tipoactivi) {
		this.tipoactivi = tipoactivi;
	}
	public Date getFechainactv() {
		return fechainactv;
	}
	public void setFechainactv(Date fechainactv) throws ParseException {
		this.fechainactv=fechainactv;				
	}
	public Date getFechatermactv() {
		return fechatermactv;
	}
	public void setFechatermactv(Date fechatermactv) {
		this.fechatermactv = fechatermactv;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		//System.out.println("seteando producto->");
		this.producto = producto;
	}
	public String getTipoproduct() {
		return tipoproduct;
	}
	public void setTipoproduct(String tipoproduct) {
		this.tipoproduct = tipoproduct;
	}
	public String getLinkproducto() {
		return linkproducto;
	}
	public void setLinkproducto(String linkproducto) {
		this.linkproducto = linkproducto;
	}
	public String getMetaprgmdlinacc() {
		return metaprgmdlinacc;
	}
	public void setMetaprgmdlinacc(String metaprgmdlinacc) {
		this.metaprgmdlinacc = metaprgmdlinacc;
	}
	public String getUnidadmedinacc() {
		return unidadmedinacc;
	}
	public void setUnidadmedinacc(String unidadmedinacc) {
		this.unidadmedinacc = unidadmedinacc;
	}
	public String getPeriodometaprglinacc() {
		return periodometaprglinacc;
	}
	public void setPeriodometaprglinacc(String periodometaprglinacc) {
		this.periodometaprglinacc = periodometaprglinacc;
	}
	public String getMetaproglinacc() {
		return metaproglinacc;
	}
	public void setMetaproglinacc(String metaproglinacc) {
		this.metaproglinacc = metaproglinacc;
	}
	public String getMetaprogactv() {
		return metaprogactv;
	}
	public void setMetaprogactv(String metaprogactv) {
		this.metaprogactv = metaprogactv;
	}
	public String getPeriodometaprgactiv() {
		return periodometaprgactiv;
	}
	public void setPeriodometaprgactiv(String periodometaprgactiv) {
		this.periodometaprgactiv = periodometaprgactiv;
	}
	public String getAvance() {
		return avance;
	}
	public void setAvance(String avance) {
		this.avance = avance;
	}
	public String getExplicacionavance() {
		return explicacionavance;
	}
	public void setExplicacionavance(String explicacionavance) {
		this.explicacionavance = explicacionavance;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getOtrasinsticolaboran() {
		return otrasinsticolaboran;
	}
	public void setOtrasinsticolaboran(String otrasinsticolaboran) {
		this.otrasinsticolaboran = otrasinsticolaboran;
	}
	public int getPresupuesto() {
		return presupuesto;
	}
	public void setPresupuesto(int presupuesto) {
		this.presupuesto = presupuesto;
	}
	public String[] getFuentefinacia() {
		return fuentefinacia;
	}
	public void setFuentefinacia(String[] fuentefinacia) {
		this.fuentefinacia = fuentefinacia;
	}
	public int getNoserviciootrg() {
		return noserviciootrg;
	}
	public void setNoserviciootrg(int noserviciootrg) {
		this.noserviciootrg = noserviciootrg;
	}
	public int getTotalpoblbenfm() {
		return totalpoblbenfm;
	}
	public void setTotalpoblbenfm(int totalpoblbenfm) {
		this.totalpoblbenfm = totalpoblbenfm;
	}
	public int getPoblabenfmujing() {
		return poblabenfmujing;
	}
	public void setPoblabenfmujing(int poblabenfmujing) {
		this.poblabenfmujing = poblabenfmujing;
	}
	public int getTotalpoblbenfh() {
		return totalpoblbenfh;
	}
	public void setTotalpoblbenfh(int totalpoblbenfh) {
		this.totalpoblbenfh = totalpoblbenfh;
	}
	public int getPoblabenfhobing() {
		return poblabenfhobing;
	}
	public void setPoblabenfhobing(int poblabenfhobing) {
		this.poblabenfhobing = poblabenfhobing;
	}
	public int getNinasbenifi0a12pobltot() {
		return ninasbenifi0a12pobltot;
	}
	public void setNinasbenifi0a12pobltot(int ninasbenifi0a12pobltot) {
		this.ninasbenifi0a12pobltot = ninasbenifi0a12pobltot;
	}
	public int getNinasbenifi0a12poblindig() {
		return ninasbenifi0a12poblindig;
	}
	public void setNinasbenifi0a12poblindig(int ninasbenifi0a12poblindig) {
		this.ninasbenifi0a12poblindig = ninasbenifi0a12poblindig;
	}
	public int getNinasbenifi12a17pobltot() {
		return ninasbenifi12a17pobltot;
	}
	public void setNinasbenifi12a17pobltot(int ninasbenifi12a17pobltot) {
		this.ninasbenifi12a17pobltot = ninasbenifi12a17pobltot;
	}
	public int getNinasbenifi12a17poblingdig() {
		return ninasbenifi12a17poblingdig;
	}
	public void setNinasbenifi12a17poblingdig(int ninasbenifi12a17poblingdig) {
		this.ninasbenifi12a17poblingdig = ninasbenifi12a17poblingdig;
	}
	public int getNinosbenifi0a12pobltot() {
		return ninosbenifi0a12pobltot;
	}
	public void setNinosbenifi0a12pobltot(int ninosbenifi0a12pobltot) {
		this.ninosbenifi0a12pobltot = ninosbenifi0a12pobltot;
	}
	public int getNinosbenifi0a12poblingid() {
		return ninosbenifi0a12poblingid;
	}
	public void setNinosbenifi0a12poblingid(int ninosbenifi0a12poblingid) {
		this.ninosbenifi0a12poblingid = ninosbenifi0a12poblingid;
	}
	public int getNinosbenifi12a17pobltot() {
		return ninosbenifi12a17pobltot;
	}
	public void setNinosbenifi12a17pobltot(int ninosbenifi12a17pobltot) {
		this.ninosbenifi12a17pobltot = ninosbenifi12a17pobltot;
	}
	public int getNinpsbenifi12a17poblindig() {
		return ninpsbenifi12a17poblindig;
	}
	public void setNinpsbenifi12a17poblindig(int ninpsbenifi12a17poblindig) {
		this.ninpsbenifi12a17poblindig = ninpsbenifi12a17poblindig;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
