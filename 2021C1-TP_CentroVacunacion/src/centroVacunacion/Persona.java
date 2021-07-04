package centroVacunacion;

public class Persona {
	private String nombre;
	private int edad;
	private int dni;
	private boolean enfermedadPre;
	private boolean personalSalud;
	private Fecha fechaNacimiento;
	private boolean estaVacunado;
	private int prioridad;
	
	public Fecha getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Fecha fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public boolean isEstaVacunado() {
		return estaVacunado;
	}

	public void setEstaVacunado(boolean estaVacunado) {
		this.estaVacunado = estaVacunado;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}
	
	public Persona (Persona n) {
		this.edad = n.getEdad();
		this.dni = n.getDni();
		this.fechaNacimiento = n.getFechaNacimiento();
		this.setPrioridad(n.getPrioridad());
		
	}

	public Persona (int dni, Fecha fechaNac, boolean tienePadecimientos, boolean esEmpleadoSalud){
		this.edad=Fecha.diferenciaAnios(Fecha.hoy(), fechaNac);
		this.dni=dni;
		this.enfermedadPre=tienePadecimientos;
		this.personalSalud=esEmpleadoSalud;
		this.fechaNacimiento=fechaNac;
		esPrioridad();
		
	}
	
	public boolean mayorDe60 (){
		if (this.edad>60)
			return true;
		else
			return false;
			
	}
	
	public boolean enfermedadPreexistente (){
		if (this.enfermedadPre)
			return true;
		else
			return false;
	}
	
	public boolean esPersonalSalud (){
		if (this.personalSalud)
			return true;
		else
			return false;
	}
	
	public void esPrioridad () {
		
		int prio = 4;
		if (personalSalud)
			prio = Math.min(prio, 1);
		if(enfermedadPre)
			prio = Math.min(prio, 2);
		if (mayorDe60())
			prio = Math.min(prio, 3);
		
		
		
		this.prioridad = prio;
	}
	
	public Persona damePersona (Persona persona) throws Exception{
		if(this.dni==persona.getDni())
			return persona;
		else
			throw new Exception("La persona no se encuentra ingresada");
	
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", edad=" + edad + ", dni=" + dni + ", estaVacunado=" + estaVacunado
				+ ", prioridad=" + prioridad + "]";
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public boolean isEnfermedadPre() {
		return enfermedadPre;
	}

	public void setEnfermedadPre(boolean enfermedadPre) {
		this.enfermedadPre = enfermedadPre;
	}

	public boolean isPersonalSalud() {
		return personalSalud;
	}

	public void setPersonalSalud(boolean personalSalud) {
		this.personalSalud = personalSalud;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dni;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = new Persona((Persona) obj);
		if (this.dni != other.dni)
			return false;
		return true;
	}

}
