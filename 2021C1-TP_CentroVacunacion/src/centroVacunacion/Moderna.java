package centroVacunacion;

public class Moderna extends Vacuna {
	private Fecha vencimiento;
	private int espacioEnHeladera;
	private int tiempoDeVencimiento;
	
	public Moderna (String nombre, Fecha fi) {
		
		super(nombre, fi);
		this.tiempoDeVencimiento=60;
		
		Fecha otra = new Fecha(fi);
		otra.avanzarDias(getTiempoDeVencimiento());		
		super.setIngreso(otra);		
				
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

	public void setTiempoDeVencimiento(int tiempoDeVencimiento) {
		this.tiempoDeVencimiento = tiempoDeVencimiento;
	}
	
	
	
}
