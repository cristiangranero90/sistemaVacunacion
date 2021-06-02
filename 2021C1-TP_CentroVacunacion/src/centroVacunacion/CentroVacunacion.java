package centroVacunacion;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class CentroVacunacion {
	int capacidad;
	String nombre;
	Deposito vacunasDiesiocho;
	Deposito vacunasTres;
	
	//ArrayList <Vacuna> vacunas;
	ArrayList<Turno> turnos; 
	ArrayList <Persona> inscriptos;
	HashMap <Integer,Vacuna> reporte; 
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
		vacunasDiesiocho = new Deposito(18);
		vacunasTres = new Deposito(3);
		
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
				vacunasDiesiocho.agregarVacunas(new Moderna (nombreVacuna, fechaIngreso), cantidad);
				setStock(getStock() + cantidad);
			}
			else if (nombre.equals("pfizer")) {
				vacunasDiesiocho.agregarVacunas(new Pfizer (nombreVacuna, fechaIngreso), cantidad);
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
	/**
	* total de vacunas disponibles no vencidas sin distinci�n por tipo.
	*/
	public int vacunasDisponibles() {	
		vacunasDiesiocho.actualizarVencidas();
		vacunasTres.actualizarVencidas();
		return vacunasDiesiocho.cantVacunas() + vacunasTres.cantVacunas();
	}
	/**
	* total de vacunas disponibles no vencidas que coincida con el nombre de
	* vacuna especificado.
	*/
	public int vacunasDisponibles(String nombreVacuna) {	
		vacunasDiesiocho.actualizarVencidas();
		vacunasTres.actualizarVencidas();
		return vacunasDiesiocho.cantVacunasNombre(nombreVacuna) + vacunasTres.cantVacunasNombre(nombreVacuna);			
	}
	/**
	* Se inscribe una persona en lista de espera.
	* Si la persona ya se encuentra inscripta o es menor de 18 a�os, se debe
	* generar una excepci�n.
	* Si la persona ya fue vacunada, tambi�n debe generar una excepci�n.
	*/
	public void inscribirPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		
		inscriptos.add(new Persona(dni, nacimiento,tienePadecimientos, esEmpleadoSalud) );
		
	}
	/**
	* Devuelve una lista con los DNI de todos los inscriptos que no se vacunaron
	* y que no tienen turno asignado.
	* Si no quedan inscriptos sin vacunas debe devolver una lista vac�a.
	*/
	public ArrayList<Integer> listaDeEspera(){
		ArrayList<Integer>  lista = new ArrayList <Integer> ();
		Iterator<Persona> iterador = inscriptos.iterator();
		if (iterador.hasNext())
			lista.add(iterador.next().getDni());
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
		retirarTurnosVencidos(fechaInicial);
		generarPrioridad();
		retirarVacunasVencidas();
		
		//Falta armar los turnos
		
	}
	/**
	* Devuelve una lista con los dni de las personas que tienen turno asignado
	* para la fecha pasada por par�metro.
	* Si no hay turnos asignados para ese d�a, se debe devolver una lista vac�a.
	* La cantidad de turnos no puede exceder la capacidad por d�a de la ungs.
	*/
	public ArrayList<Integer> turnosConFecha(Fecha fecha){
		ArrayList<Integer>  lista = new ArrayList <Integer> ();
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
		
	}
	/**
	* Devuelve un Diccionario donde
	* - la clave es el dni de las personas vacunadas
	* - Y, el valor es el nombre de la vacuna aplicada.
	*/
	public Map<Integer, String> reporteVacunacion(){
		Map<Integer, String> lista= new HashMap <Integer,String> ();
		return lista;
	}
	/** Devuelve en O(1) un Diccionario:
	* - clave: nombre de la vacuna
	* - valor: cantidad de vacunas vencidas conocidas hasta el momento.
	*/
	public Map<String, Integer> reporteVacunasVencidas(){
		Map<String, Integer> lista= new HashMap <String,Integer> ();
		return lista;
	}
	
	private void retirarTurnosVencidos(Fecha f) {
		Iterator<Turno> iterador = turnos.iterator();
		if (iterador.hasNext() && iterador.next().getFecha().compareTo(f)<0) {			
			
			if(iterador.next().getVacuna().equals("pfizer") || iterador.next().getVacuna().equals("moderna")) {
				vacunasDiesiocho.agregarVacunas(iterador.next().getVacuna());	
				turnos.remove(iterador.next());
			}
			else {
				vacunasTres.agregarVacunas(iterador.next().getVacuna());
				turnos.remove(iterador.next());
			}			
		}
	}
		
	private void retirarVacunasVencidas() {
		vacunasDiesiocho.actualizarVencidas();
		vacunasTres.actualizarVencidas();
	}
	
	private void generarPrioridad() {
		int prio=1;
        while(prio<=4)
        {
			for (Persona per: inscriptos) {
				if (per.prioridad==prio) {
					Persona nueva=new Persona (per);
					inscriptos.remove(per);
					inscriptos.add(inscriptos.size(), nueva);}
			
			prio++;	}
			
		
	}}
	
	private Vacuna dameVacunaPorPrioridad(int prio) {
		if (prio==2) {
			
		}
			
		
	}

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

	public Deposito getVacunasDiesiocho() {
		return vacunasDiesiocho;
	}

	public void setVacunasDiesiocho(Deposito vacunasDiesiocho) {
		this.vacunasDiesiocho = vacunasDiesiocho;
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

	public HashMap<Integer, Vacuna> getReporte() {
		return reporte;
	}

	public void setReporte(HashMap<Integer, Vacuna> reporte) {
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
