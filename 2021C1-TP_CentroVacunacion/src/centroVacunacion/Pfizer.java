package centroVacunacion;

public class Pfizer extends Vacuna {
	
	private Fecha vencimiento;	
	private final int tiempoDeVencimiento;
	
	public Pfizer (String nombre, Fecha fi) {
		
		super(nombre, fi);
		this.tiempoDeVencimiento=30;
		
		Fecha otra = new Fecha(fi);
		otra.avanzarDias(getTiempoDeVencimiento());				
		setVencimiento(otra);	
	}

	public Fecha getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Fecha vencimiento) {
		this.vencimiento = vencimiento;
	}

	public int getTiempoDeVencimiento() {
		return tiempoDeVencimiento;
	}
	

}
