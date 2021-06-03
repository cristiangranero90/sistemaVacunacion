package centroVacunacion;

public class Pfizer extends Vacuna {
	public Fecha vencimiento;
	//int espacioEnHeladera;
	private final int tiempoDeVencimiento;
	
	public Pfizer (String nombre, Fecha fi) {
		
		super(nombre, fi);
		this.tiempoDeVencimiento=30;
		fi.avanzarDias(tiempoDeVencimiento);
		
		super.setIngreso(fi);
		
		//vencimiento = fi;
			
		
		//setVencimiento(fi.avanzarDias(getTiempoDeVencimiento()));
		
		
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
	
	//public void asignarVencimiento() {
		//this.vencimiento=super.ingreso.avanzarDias(venc);
	//}

	

	

	

	//public void setTiempoDeVencimiento(int tiempoDeVencimiento) {
		//this.tiempoDeVencimiento = tiempoDeVencimiento;
	//}

}
