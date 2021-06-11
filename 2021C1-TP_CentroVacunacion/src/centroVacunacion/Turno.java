package centroVacunacion;

public class Turno {
	Vacuna vacuna;
	Fecha fecha;
	Persona persona;	
	
	public Turno(Vacuna vac, Fecha fec, Persona per) {
		vacuna=vac;
		fecha=fec;
		persona=per;
	}
	
	//Getters and Setters--------------------------------------------------------
	
	public Vacuna getVacuna() {
		return vacuna;
	}
	public void setVacuna(Vacuna vacuna) {
		this.vacuna = vacuna;
	}
	public Fecha getFecha() {
		return fecha;
	}
	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Override
	public String toString() {
		return "Turno [vacuna=" + vacuna.toString() + ", fecha=" + fecha.toString() + ", persona=" + persona.toString() + "]";
	}
	

}
