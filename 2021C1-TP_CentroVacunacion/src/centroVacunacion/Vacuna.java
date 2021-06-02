package centroVacunacion;

public class Vacuna {
	boolean conservacion;
	//private int cantidad;
	Fecha ingreso;
	String nombre;
	//private int vencidas;
	//boolean vencida;
	
	public Vacuna (String nombre, Fecha fi) {
		this.nombre=nombre;
		//cantidad=cantidad+cant;
		ingreso=fi;
		conservacion=esRefrigerada(nombre);
		//vencida=false;
		
		
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
	
	//public int dameTotal () {
		//return cantidad;
	//}
	
	//public int dameVencidad () {
	//	return vencidas;
	//}
	
	public boolean estaVencida () {
		
		return isVencida();
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
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
}
