package centroVacunacion;

public class Vacuna {
	
	private boolean conservacion;
	private Fecha ingreso;
	private String nombre;
	private boolean asignada;
	private boolean vencida;	
	
	public Vacuna(Vacuna vac) {
		
		this.nombre=vac.getNombre();
		this.ingreso=vac.getIngreso();
		
	}
	
	public Vacuna (String nombre, Fecha fi) {
		this.nombre=nombre;
		
		ingreso=fi;
		conservacion=esRefrigerada(nombre);		
		
	}
	
	public boolean esRefrigerada (String nombre) {
		return (nombre.toLowerCase()=="moderna" || nombre.toLowerCase()=="sputnic" );
			
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	public boolean isConservacion() {
		return conservacion;
	}

	public void setConservacion(boolean conservacion) {
		this.conservacion = conservacion;
	}

	public Fecha getIngreso() {
		return ingreso;
	}

	public void setIngreso(Fecha ingreso) {
		this.ingreso = ingreso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	public boolean isVencida() {
		return vencida;
	}

	public void setVencida(boolean vencida) {
		this.vencida = vencida;
	}
		
	public boolean isAsignada() {
		return asignada;
	}

	public void setAsignada(boolean asignada) {
		this.asignada = asignada;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vacuna other = (Vacuna) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre) &&
				!ingreso.equals(other.ingreso))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vacuna [conservacion=" + conservacion + ", nombre=" + nombre + ", asignada=" + asignada + "]";
	}
	
}
