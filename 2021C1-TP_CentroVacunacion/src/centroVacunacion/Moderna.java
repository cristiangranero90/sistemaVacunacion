package centroVacunacion;

public class Moderna extends Vacuna {
	Fecha vencimiento;
	int espacioEnHeladera;
	int tiempoDeVencimiento;
	
	public Moderna (String nombre, Fecha fi) {
		super(nombre, fi);
		this.tiempoDeVencimiento=60;
		
		super.ingreso.avanzarDias(tiempoDeVencimiento);
				
	}
	
}
