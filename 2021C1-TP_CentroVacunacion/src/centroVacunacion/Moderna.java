package centroVacunacion;

public class Moderna extends Vacuna {
	Fecha vencimiento;
	int espacioEnHeladera;
	int tiempoDeVencimiento;
	
	public Moderna (String nombre, Fecha fi) {
		super(nombre, fi);
		this.tiempoDeVencimiento=60;
		
		fi.avanzarDias(getTiempoDeVencimiento());
		
		super.setIngreso(fi);
		
				
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
