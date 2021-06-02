package centroVacunacion;

public class Pfizer extends Vacuna {
	//protected Fecha vencimiento;
	//int espacioEnHeladera;
	private final int tiempoDeVencimiento;
	
	public Pfizer (String nombre, Fecha fi) {
		
		super(nombre, fi);
		this.tiempoDeVencimiento=30;
		super.getIngreso().avanzarDias(getTiempoDeVencimiento());
			
		
		//setVencimiento(fi.avanzarDias(getTiempoDeVencimiento()));
		
		
	}
	
	//public void asignarVencimiento() {
		//this.vencimiento=super.ingreso.avanzarDias(venc);
	//}

	

	

	public int getTiempoDeVencimiento() {
		return tiempoDeVencimiento;
	}

	//public void setTiempoDeVencimiento(int tiempoDeVencimiento) {
		//this.tiempoDeVencimiento = tiempoDeVencimiento;
	//}

}
