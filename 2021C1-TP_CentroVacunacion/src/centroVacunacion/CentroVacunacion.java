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
		
		Turno nuevo = new Turno(vac, f, per);
		turnos.add(nuevo);
		
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
				vacunasDieciocho.agregarVacunas(new Moderna (nombreVacuna, fechaIngreso), cantidad);
				setStock(getStock() + cantidad);
			}
			else if (nombre.equals("pfizer")) {
				vacunasDieciocho.agregarVacunas(new Pfizer (nombreVacuna, fechaIngreso), cantidad);
				setStock(getStock() + cantidad);
			}
			else if (nombre.equals("sinopharm")) {
				vacunasTres.agregarVacunas(new Sinopharm (nombreVacuna, fechaIngreso) , cantidad);
				setStock(getStock() + cantidad);
			}
			else if (nombre.equals("astrazeneca")) {
				vacunasTres.agregarVacunas(new Astrazeneca (nombreVacuna, fechaIngreso), cantidad);
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
		return vacunasDieciocho.cantVacunas() + vacunasTres.cantVacunas();
	}
	
	public int vacunasDisponibles(String nombre) {
		vacunasDieciocho.actualizarVencidas();		
		return vacunasDieciocho.cantVacunas(nombre) + vacunasTres.cantVacunas(nombre);
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
			//generarPrioridad();
			retirarVacunasVencidas();
			System.out.println("entro a fecha");
			//Fecha hoy = new Fecha();
			int porDia;
			boolean bandera = true;
		
		
			//Falta armar los turnos
			Iterator<Persona> iterador = inscriptos.iterator();
		
			while(bandera) {
				
				System.out.println("entro bandera");
			 
				porDia = getCapacidad(); //Renueva
			
				while (iterador.hasNext() && porDia>0 && vacunasDisponibles()>0) {
				
					Vacuna nueva = dameVacunaPorPrioridad(iterador.next().getPrioridad());
					//System.out.println(iterador.next().getPrioridad());
					System.out.println(nueva!=null);
					
					if (nueva!=null && vacunasDisponibles() != 0 ) {
						agendarTurno(nueva, fechaInicial, new Persona(iterador.next()));	
						//Falta eliminar a la persona??? o lo hace vacunarInscriptos???
						eliminarPersona(iterador.next());
						porDia--;
						System.out.println("Entro eliminar");
						
					}
					else {
						bandera = false;
					}
				}
				fechaInicial.avanzarUnDia();			
				//NO sirve, revisar el ciclo while
				//COmplica
			}
		}
		else {
			throw new RuntimeException();
		}
		//retirarAsignadas();
		
	}
	private void retirarAsignadas() {
		
		Iterator<Vacuna> iterador = vacunasTres.getVacunas().iterator();
		
		while (iterador.hasNext()) {
			
			Vacuna nueva = iterador.next();
			
			if (nueva.isAsignada()) {
				
				vacunasTres.quitarVacuna(nueva);
			}
			
		}
		
		Iterator<Vacuna> otroIterador = vacunasDieciocho.getVacunas().iterator();
		
		while(otroIterador.hasNext()) {
			
			Vacuna nueva = otroIterador.next();
			
			if(nueva.isAsignada()) {
				
				vacunasDieciocho.quitarVacuna(nueva);
			}
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
		
		while (iterador.hasNext()) {
			
			if(iterador.next().getFecha().compareTo(fecha) == 0) {
				lista.add(iterador.next().persona.dni);
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
		for (Turno tur: turnos) {
			if(tur.persona.getDni()==dni && tur.fecha.equals(fechaVacunacion)) {
				tur.persona.isEstaVacunado();
				if(tur.vacuna.getNombre()=="Moderna" || tur.vacuna.getNombre()=="Pfizer") {
					vacunasDieciocho.quitarVacuna(tur.vacuna);}
				else { 
					vacunasTres.quitarVacuna(tur.vacuna);
					
				
			}
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
		
		HashMap<String, Integer> nuevo = new HashMap<>();
		
		nuevo.put("pfizer", vacunasDieciocho.dameVencidas("pfizer"));
		nuevo.put("moderna", vacunasDieciocho.dameVencidas("moderna"));
		
		return nuevo;		
		
	}
	
	private void retirarTurnosVencidos(Fecha f) {
		Iterator<Turno> iterador = turnos.iterator();
		while (iterador.hasNext() && iterador.next().getFecha().compareTo(f)<0) {			
			
			if(iterador.next().getVacuna().getNombre().equals("pfizer") || 
					iterador.next().getVacuna().getNombre().equals("moderna")) {
				
				vacunasDieciocho.agregarVacunas(iterador.next().getVacuna());	
				turnos.remove(iterador.next());
			}
			else {
				vacunasTres.agregarVacunas(iterador.next().getVacuna());
				turnos.remove(iterador.next());
			}			
		}
	}
		
	
	private void retirarVacunasVencidas() {
		vacunasDieciocho.actualizarVencidas();
		//vacunasTres.actualizarVencidas();
	}
	
private Vacuna dameVacunaPorPrioridad(int prio) {
		
		//System.out.println(prio);
		
		if (prio==2 && vacunasDisponibles() > 0 && vacunasDieciocho != null) {
			
			Iterator<Vacuna> todas = vacunasDieciocho.getVacunas().iterator();
						
			while(todas.hasNext() && todas != null) {
				if (todas.next().getNombre().equals("pfizer")) {
					Vacuna nueva = todas.next();
					//vacunasDieciocho.quitarVacuna(todas.next());
					todas.next().setAsignada(true);
					todas.remove();
					return nueva;
				}
			}
		}
			
		else if (prio==2 && vacunasDisponibles() > 0 && vacunasTres != null) {
			
			Iterator<Vacuna> tres = vacunasTres.getVacunas().iterator();
			
			while (tres.hasNext() && tres != null) {
				if (tres.next().getNombre().equals("sputnik")) {
					//Vacuna nueva = new Vacuna();
					//nueva = tres.next();
					//vacunasTres.quitarVacuna(tres.next());
					tres.next().setAsignada(true);
					tres.remove();
					return tres.next();
				}
			}
		}
	
		
		else if (vacunasDisponibles() > 0 && vacunasDieciocho != null) {
			
			Iterator<Vacuna> ocho = vacunasDieciocho.getVacunas().iterator();
			
			while(ocho.hasNext() && ocho != null) {
				if (!ocho.next().getNombre().equals("pfizer")) {
					//Vacuna nueva = new Vacuna();
					//nueva = ocho.next();
					//vacunasDieciocho.quitarVacuna(ocho.next());
					ocho.next().setAsignada(true);
					ocho.remove();
					return ocho.next();
				}
			}
		}
			
		if (vacunasDisponibles() > 0 && vacunasDieciocho != null) {
			
			Iterator<Vacuna> tresS = vacunasTres.getVacunas().iterator();
			
			while (tresS != null && tresS.hasNext() ) {
				if (!tresS.next().getNombre().equals("sputnik")) {
					//Vacuna nueva = new Vacuna();
					//nueva = tresS.next();
					//vacunasTres.quitarVacuna(tresS.next());
					tresS.next().setAsignada(true);
					tresS.remove();
					return tresS.next();
				}
			}
		}	
		return null;				
	}
	
	public void agregarAlReporte(int dni, Vacuna vac ) {
		
		reporte.put((Integer) dni, vac.getNombre());
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

	@Override
	public String toString() {
		return "CentroVacunacion [capacidad=" + capacidad + ", nombre=" + nombre + ", turnos=" + turnos
				+ ", inscriptos=" + inscriptos + ", reporte=" + reporte + ", stock=" + stock + "]";
	}
	

	

}
