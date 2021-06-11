package centroVacunacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CentroVacunacion {
	int capacidad;
	String nombre;
	DepositoConVencidas vacunasDieciocho;
	Deposito vacunasTres;
	
	//ArrayList <Vacuna> vacunas;
	ArrayList<Turno> turnos; 
	ArrayList <Persona> inscriptos;
	HashMap <Integer,String> reporte; 
	int vacunasVencidas;
	int stock;
	
	//* Constructor.
	//* recibe el nombre del centro y la capacidad de vacunaci�n diaria.
	//* Si la capacidad de vacunaci�n no es positiva se debe generar una excepci�n.
	//* Si el nombre no est� definido, se debe generar una excepci�n.
	//*/
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
		if (!this.turnos.contains(nuevo)) {
			//System.out.println(nuevo.getPersona().getDni());
			this.getTurnos().add(nuevo);
		}
				
	}
		
	/**
	* Solo se pueden ingresar los tipos de vacunas planteados en la 1ra parte.
	* Si el nombre de la vacuna no coincidiera con los especificados se debe generar
	* una excepci�n.
	* Tambi�n se genera excepci�n si la cantidad es negativa.
	* La cantidad se debe
	* sumar al stock existente, tomando en cuenta las vacunas ya utilizadas.
	 * @throws Exception 
	*/
	public void ingresarVacunas(String nombreVacuna, int cantidad, Fecha fechaIngreso)  {
		if (cantidad<1)
			throw new RuntimeException ("el numero ingresado debe ser mayor que uno");
		else {
			String nombre = nombreVacuna.toLowerCase();
			
			if (nombre.equals("sputnik")) {
				vacunasTres.agregarVacunas(new Sputnik (nombre, fechaIngreso),cantidad);
				setStock(getStock() + cantidad);
			}
			else if (nombre.equals("moderna")) {
				vacunasDieciocho.agregarVacunas(new Moderna (nombre, fechaIngreso), cantidad);
				setStock(getStock() + cantidad);
			}
			else if (nombre.equals("pfizer")) {
				vacunasDieciocho.agregarVacunas(new Pfizer (nombre, fechaIngreso), cantidad);
				setStock(getStock() + cantidad);
			}
			else if (nombre.equals("sinopharm")) {
				vacunasTres.agregarVacunas(new Sinopharm (nombre, fechaIngreso) , cantidad);
				setStock(getStock() + cantidad);
			}
			else if (nombre.equals("astrazeneca")) {
				vacunasTres.agregarVacunas(new Astrazeneca (nombre, fechaIngreso), cantidad);
				setStock(getStock() + cantidad);
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
		//vacunasTres.actualizarVencidas();
		return getStock();
	}
	
	public int vacunasDisponibles(String nombre) {
		//System.out.println(Fecha.hoy().toString());
		vacunasDieciocho.actualizarVencidas();	
		
		return vacunasDieciocho.cantVacunas(nombre.toLowerCase()) + vacunasTres.cantVacunas(nombre.toLowerCase());
	}
	/**
	* total de vacunas disponibles no vencidas que coincida con el nombre de
	* vacuna especificado.
	*/
	
	/**
	* Se inscribe una persona en lista de espera.
	* Si la persona ya se encuentra inscripta o es menor de 18 a�os, se debe
	* generar una excepci�n.
	* Si la persona ya fue vacunada, tambi�n debe generar una excepci�n.
	*/
	public void inscribirPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		
		Persona nueva = new Persona(dni, nacimiento,tienePadecimientos, esEmpleadoSalud) ;
		
		if(Fecha.diferenciaAnios(new Fecha(), nacimiento) >= 18 &&
				!estaInscripto(nueva) && 
				!estaEnReporte(nueva.getDni())) {			
			inscriptos.add(nueva);
		}
		else {
			throw new RuntimeException();
		}		
	}
	
	public boolean estaEnReporte(int dni) {

		return reporte.containsKey(dni);
	}

	public void eliminarPersona(Persona per) {
		System.out.println(inscriptos.remove(per) + "persona");
	}
	/**
	* Devuelve una lista con los DNI de todos los inscriptos que no se vacunaron
	* y que no tienen turno asignado.
	* Si no quedan inscriptos sin vacunas debe devolver una lista vac�a.
	*/
	public ArrayList<Integer> listaDeEspera(){
		
		ArrayList<Integer>  lista = new ArrayList <Integer> ();
		Iterator<Persona> iterador = inscriptos.iterator();
		while (iterador.hasNext()) {
			lista.add(iterador.next().getDni());
		}			
		return lista;
	}
	/**
	* Primero se verifica si hay turnos vencidos. En caso de haber turnos
	* vencidos, la persona que no asisti� al turno debe ser borrada del sistema
	* y la vacuna reservada debe volver a estar disponible.
	*
	* Segundo, se deben verificar si hay vacunas vencidas y quitarlas del sistema.
	*
	* Por �ltimo, se procede a asignar los turnos a partir de la fecha inicial
	* recibida seg�n lo especificado en la 1ra parte.
	* Cada vez que se registra un nuevo turno, la vacuna destinada a esa persona
	* dejar� de estar disponible. Dado que estar� reservada para ser aplicada
	* el d�a del turno.
	*
	*
	*/
	public void generarTurnos(Fecha fechaInicial) {
		
		if (fechaInicial.posterior(Fecha.hoy())) {
			
			retirarTurnosVencidos(fechaInicial); 	
			retirarVacunasVencidas();
			int porDia = getCapacidad();
			Persona otra = null;
			Iterator<Persona> iterador = inscriptos.iterator();
			Fecha fecha = new Fecha(fechaInicial);
			
			while (iterador.hasNext() && vacunasDisponibles() > 0) {
				
				if (porDia > 0) {
					
					otra = iterador.next();
					Vacuna nueva = dameVacunaPorPrioridad(otra.getPrioridad());
					
					if (nueva != null) {
						
						agendarTurno(new Vacuna(nueva), new Fecha(fecha), new Persona (otra));	
						iterador.remove();
						//System.out.println(otra.getDni());
						porDia--;
					}
					else {
						break;
					}
				}
				else {
					porDia = getCapacidad();
					fecha.avanzarUnDia();
					
				}
			}
		}
		else {
			throw new RuntimeException();
		}
	}
	


	/**
	* Devuelve una lista con los dni de las personas que tienen turno asignado
	* para la fecha pasada por par�metro.
	* Si no hay turnos asignados para ese d�a, se debe devolver una lista vac�a.
	* La cantidad de turnos no puede exceder la capacidad por d�a de la ungs.
	*/
	public ArrayList<Integer> turnosConFecha(Fecha fecha){
		
		ArrayList<Integer>  lista = new ArrayList <Integer> ();
		Iterator<Turno> iterador = turnos.iterator();
		//System.out.println("fecha mandada" + fecha.toString());
		
		while (iterador.hasNext()) {
			
			Turno nueva = iterador.next();
			
			
			if(nueva.getFecha().compareTo(fecha) == 0) {
				lista.add(nueva.persona.dni);
				//System.out.println(nueva.getFecha().toString());
			}
		}
		return lista;
	}
	/**
	* Dado el DNI de la persona y la fecha de vacunaci�n
	* se valida que est� inscripto y que tenga turno para ese dia.
	* - Si tiene turno y est� inscripto se debe registrar la persona como
	* vacunada y la vacuna se quita del dep�sito.
	* - Si no est� inscripto o no tiene turno ese d�a, se genera una Excepcion.
	*/
	public void vacunarInscripto(int dni, Fecha fechaVacunacion) {
		
		for (Turno tur: this.getTurnos()) {
			//System.out.println(tur.getPersona().getDni());
			if(tur.getPersona().getDni()==dni && tur.getFecha().equals(fechaVacunacion)) {
				
				tur.getPersona().setEstaVacunado(true);
				agregarAlReporte(tur.getPersona().getDni(), tur.getVacuna().getNombre());
				vacunasDieciocho.quitarVacuna(tur.getVacuna());
				 
				//agregarAlReporte(tur.getPersona().getDni(), tur.getVacuna().getNombre());
				//vacunasTres.quitarVacuna(tur.getVacuna());
			
			}
			else{
				throw new RuntimeException ("El paciente no esta inscripto o no tiene turno este d�a");}
			}
		
		
		//Este ni puta idea mai fren
	}
	/**
	* Devuelve un Diccionario donde
	* - la clave es el dni de las personas vacunadas
	* - Y, el valor es el nombre de la vacuna aplicada.
	*/
	public Map<Integer, String> reporteVacunacion(){
		
		return getReporte();
	}
	/** Devuelve en O(1) un Diccionario:
	* - clave: nombre de la vacuna
	* - valor: cantidad de vacunas vencidas conocidas hasta el momento.
	*/
	public Map<String, Integer> reporteVacunasVencidas(){
		
		vacunasDieciocho.actualizarVencidas();
		
		HashMap<String, Integer> nuevo = new HashMap<>();
		
		nuevo.put("pfizer", vacunasDieciocho.dameVencidas("pfizer"));
		nuevo.put("moderna", vacunasDieciocho.dameVencidas("moderna"));
		
		return nuevo;		
		
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
		//vacunasTres.actualizarVencidas();
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
						
						nueva = vacunaModi; tengoVacuna = true; ite.remove();
						
					}
				}
				else {
					
					//if (!vacunaModi.getNombre().equals("pfizer")) {
						
						nueva = vacunaModi; tengoVacuna = true; ite.remove();
					//}
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
						
						nueva = vacunaModi; tengoVacuna = true; otroIte.remove();
					}
				}
				else {
					
					//if (!vacunaModi.getNombre().equals("sputnik")) {
						
						nueva = vacunaModi; tengoVacuna = true; otroIte.remove();
					//}
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

	public ArrayList<Persona> getInscriptos() {
		return inscriptos;
	}

	public void setInscriptos(ArrayList<Persona> inscriptos) {
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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	

	

}
