package centroVacunacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CentroVacunacion {
	private int capacidad;
	private String nombre;
	private DepositoConVencidas vacunasDieciocho;
	private Deposito vacunasTres;
	
	//ArrayList <Vacuna> vacunas;
	private ArrayList<Turno> turnos; 
	private ArrayList <Persona> inscriptos;
	private HashMap <Integer,String> reporte; 
	private int vacunasVencidas;
	
	public  CentroVacunacion(String nombreCentro, int capacidadVacunacionDiaria) {
		this.nombre=nombreCentro;
		this.capacidad=capacidadVacunacionDiaria;
		turnos = new ArrayList<>();
		inscriptos = new ArrayList<>();
		reporte = new HashMap<>();
		vacunasDieciocho = new DepositoConVencidas(18);
		vacunasTres = new Deposito(3);
		
	}
	
	public void agendarTurno(Vacuna vac,  Fecha f, Persona per) {
				
		Turno nuevo = new Turno(new Vacuna(vac), new Fecha(f), new Persona (per));		
		
		if (!this.getTurnos().contains(nuevo)) {
			this.getTurnos().add(nuevo);
		}
				
	}
		
	public void ingresarVacunas(String nombreVacuna, int cantidad, Fecha fechaIngreso)  {
		if (cantidad<1)
			throw new RuntimeException ("el numero ingresado debe ser mayor que uno");
		else {
			String nombre = nombreVacuna.toLowerCase();
			
			if (nombre.equals("sputnik")) {
				vacunasTres.agregarVacunas(new Sputnik (nombre, new Fecha(fechaIngreso)),cantidad);
				
			}
			else if (nombre.equals("moderna")) {
				vacunasDieciocho.agregarVacunas(new Moderna (nombre, new Fecha (fechaIngreso)), cantidad);
				
			}
			else if (nombre.equals("pfizer")) {
				vacunasDieciocho.agregarVacunas(new Pfizer (nombre, new Fecha(fechaIngreso)), cantidad);
				
			}
			else if (nombre.equals("sinopharm")) {
				vacunasTres.agregarVacunas(new Sinopharm (nombre, new Fecha(fechaIngreso)) , cantidad);
				
			}
			else if (nombre.equals("astrazeneca")) {
				vacunasTres.agregarVacunas(new Astrazeneca (nombre, new Fecha(fechaIngreso)), cantidad);
				
			}
			else {
				throw new RuntimeException ("el nombre ingresado no corresponde a una vacuna");
		}
			
	}
		
		
			
	}
	public boolean estaInscripto(Persona p) {
		return inscriptos.contains(p);
	}
	/**
	* total de vacunas disponibles no vencidas sin distinci�n por tipo.
	*/
	public int vacunasDisponibles() {	
		vacunasDieciocho.actualizarVencidas();
		return getVacunasTres().cantVacunas() + getVacunasDieciocho().cantVacunas();
	}
	
	public int vacunasDisponibles(String nombre) {
		vacunasDieciocho.actualizarVencidas();	
		
		return vacunasDieciocho.cantVacunas(nombre.toLowerCase()) + vacunasTres.cantVacunas(nombre.toLowerCase());
	}
	/**
	* total de vacunas disponibles no vencidas que coincida con el nombre de
	* vacuna especificado.
	*/
	
	public void inscribirPersona(int dni,  Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		
		Persona nueva = new Persona(dni, nacimiento,tienePadecimientos, esEmpleadoSalud) ;
		
		if(Fecha.diferenciaAnios(new Fecha(), nacimiento) >= 18 &&
				!estaInscripto(nueva) && 
				!estaEnReporte(nueva.getDni())) {			
			inscriptos.add(nueva);
		}
		else {
			throw new RuntimeException("Persona no registrada");
		}		
	}
	
	public boolean estaEnReporte(int dni) {

		return getReporte().containsKey(dni);
	}

	public void eliminarPersona(Persona per) {
		System.out.println(inscriptos.remove(per) + "persona");
	}
	
	public ArrayList<Integer> listaDeEspera(){
		
		ArrayList<Integer>  lista = new ArrayList <Integer> ();
		Iterator<Persona> iterador = inscriptos.iterator();
		while (iterador.hasNext()) {
			lista.add(iterador.next().getDni());
		}			
		return lista;
	}
	
	public void generarTurnos(Fecha fechaInicial) {
		
		if (fechaInicial.posterior(Fecha.hoy())) {
			
			retirarTurnosVencidos(fechaInicial); 	
			retirarVacunasVencidas();
			ordenarInscriptosPorPrioridad();
			int porDia = getCapacidad();
			Persona otra;
			Iterator<Persona> iterador = inscriptos.iterator();
			Fecha fecha = new Fecha(fechaInicial);
			
			while (iterador.hasNext() && vacunasDisponibles() > 0) {
				
				if (porDia > 0) {
					
					otra = iterador.next();
					Vacuna nueva = dameVacunaPorPrioridad(otra.getPrioridad());
					
					if (nueva != null) {
						
						agendarTurno(new Vacuna(nueva), new Fecha(fecha), new Persona(otra));
						iterador.remove();
						quitarVacuna(nueva);
						porDia--;
					}
					
				}
				else {
					porDia = getCapacidad();
					fecha.avanzarUnDia();
					
				}
			}
		}
		else {
			throw new RuntimeException("No se pudo generar los turnos. ");
		}
	}
	
	public ArrayList<Integer> turnosConFecha(Fecha fecha){
		
		ArrayList<Integer>  lista = new ArrayList <Integer> ();
		Iterator<Turno> iterador = turnos.iterator();
		
		while (iterador.hasNext()) {
			
			Turno nueva = iterador.next();
			
			
			if(nueva.getFecha().compareTo(fecha) == 0) {
				lista.add(nueva.getPersona().getDni());
			}
		}
		return lista;
	}
	
	public void vacunarInscripto(int dni, Fecha fechaVacunacion) {
		
		Iterator<Turno> tur = this.getTurnos().iterator();
		boolean tengoPersona = false;
		
		while(tur.hasNext() && !tengoPersona) {
			Turno nuevo = tur.next();

			if(nuevo.getPersona().getDni()==dni && nuevo.getFecha().equals(fechaVacunacion)) {
				
				nuevo.getPersona().setEstaVacunado(true);
				agregarAlReporte(nuevo.getPersona().getDni(), nuevo.getVacuna().getNombre());
				tur.remove();
				tengoPersona = true;
			}
			
			
		}
		if (!tengoPersona) {
			throw new RuntimeException("La persona no esta inscripta");
		}
	}
	
	public Map<Integer, String> reporteVacunacion(){
		
		return getReporte();
	}
	
	public Map<String, Integer> reporteVacunasVencidas(){
		
		vacunasDieciocho.actualizarVencidas();
		
		HashMap<String, Integer> nuevo = new HashMap<>();
		
		nuevo.put("pfizer", vacunasDieciocho.dameVencidas("pfizer"));
		nuevo.put("moderna", vacunasDieciocho.dameVencidas("moderna"));
		
		return nuevo;		
		
	}
	private void ordenarInscriptosPorPrioridad() {
		
		ArrayList<Persona> auxiliar = new ArrayList<>();
		int prioridad = 1;
		
		while (prioridad <= 4) {
			for (Persona per : getInscriptos()) {
				if (per.getPrioridad() == prioridad) {
					auxiliar.add(auxiliar.size(), per);
				}
			}
			prioridad++;			
		}
		
		setInscriptos(auxiliar);
		
	}
	
	private void quitarVacuna(Vacuna nueva) {
		
		if (nueva.getNombre().equals("pfizer") || nueva.getNombre().equals("moderna")) {
			getVacunasDieciocho().quitarVacuna(nueva);
		}
		else {
			getVacunasTres().quitarVacuna(nueva);
		}
		
	}

	private void retirarTurnosVencidos(Fecha f) {
		Iterator<Turno> iterador = turnos.iterator();
		Turno nuevo = null;
		
		while (iterador.hasNext())  {	
			nuevo = iterador.next();
			
			if (nuevo.getFecha().compareTo(f)<0) {
				
				if(nuevo.getVacuna().getNombre().equals("pfizer") || 
						nuevo.getVacuna().getNombre().equals("moderna")) {
					
					vacunasDieciocho.agregarVacunas(nuevo.getVacuna());	
					iterador.remove();;
				}
				else {
					vacunasTres.agregarVacunas(nuevo.getVacuna());
					iterador.remove();
				}				
			}	
				
		}
	}
		
	
	private void retirarVacunasVencidas() {
		vacunasDieciocho.actualizarVencidas();
	}
	
private Vacuna dameVacunaPorPrioridad(int prio) {
	//Sacar 	eliminar vacuna
		
		boolean tengoVacuna = false;
		Vacuna nueva = null;
		
		if (vacunasDieciocho.cantVacunas() > 0) {
			
			Iterator<Vacuna> ite = vacunasDieciocho.getVacunas().iterator();
			Vacuna vacunaModi = null;
			
			while (!tengoVacuna && ite.hasNext()) {
				vacunaModi = ite.next();
				
				if (prio == 2){
					
					if (vacunaModi.getNombre().equals("pfizer")) {
						
						nueva = vacunaModi; tengoVacuna = true;
						
					}
				}
				else {						
						nueva = vacunaModi; tengoVacuna = true;
				}
			}
		}
		if (vacunasTres.cantVacunas() > 0 && !tengoVacuna) {
			
			Iterator<Vacuna> otroIte = vacunasTres.getVacunas().iterator();
			Vacuna vacunaModi = null;
			
			while (otroIte.hasNext() && !tengoVacuna) {
				vacunaModi = otroIte.next();
				if (prio == 2) {
					
					if (vacunaModi.getNombre().equals("sputnik")) {
						
						nueva = vacunaModi; tengoVacuna = true;
					}
				}
				else {						
						nueva = vacunaModi; tengoVacuna = true;
					
				}
			}
		}
		return nueva;		
	}
	
	public void agregarAlReporte(int dni, String vacuna ) {
		
		reporte.put((Integer) dni, vacuna);
	}

	//Getters and Setters----------------------------------------------------------------------------
	
	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Deposito getVacunasDieciocho() {
		return vacunasDieciocho;
	}

	public void setVacunasDiesiocho(DepositoConVencidas vacunasDiesiocho) {
		this.vacunasDieciocho = vacunasDiesiocho;
	}

	public Deposito getVacunasTres() {
		return vacunasTres;
	}

	public void setVacunasTres(Deposito vacunasTres) {
		this.vacunasTres = vacunasTres;
	}

	public ArrayList<Turno> getTurnos() {
		return turnos;
	}

	public void setTurnos(ArrayList<Turno> turnos) {
		this.turnos = turnos;
	}

	private ArrayList<Persona> getInscriptos() {
		return inscriptos;
	}

	private void setInscriptos(ArrayList<Persona> inscriptos) {
		this.inscriptos = inscriptos;
	}

	public Map<Integer, String> getReporte() {
		return reporte;
	}

	public void setReporte(HashMap<Integer, String> reporte) {
		this.reporte = reporte;
	}

	public int getVacunasVencidas() {
		return vacunasVencidas;
	}

	public void setVacunasVencidas(int vacunasVencidas) {
		this.vacunasVencidas = vacunasVencidas;
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("CentroVacunacion [Capacidad: " + capacidad);
		sb.append(", nombre: " + nombre);
		sb.append(", Vacunas disponibles: " + vacunasDisponibles());
		sb.append(", Personas en espera: " + listaDeEspera().size());
		sb.append("\nTurnos generados: " + getTurnos().size());
		sb.append(", Vacunas aplicadas: " + getReporte().size() + "]");
		
		return sb.toString();
		
	}	

}
