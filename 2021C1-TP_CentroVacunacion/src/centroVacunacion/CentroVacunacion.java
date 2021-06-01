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
	ArrayList <Vacuna> vacunas;
	ArrayList<Turno> turnos;
	HashSet <Persona> inscriptos;
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
	public void ingresarVacunas(String nombreVacuna, int cantidad, Fecha fechaIngreso) throws Exception {
		if (nombreVacuna.toLowerCase()=="sputnik")
				vacunas.add(new Sputnik (nombreVacuna, cantidad, fechaIngreso));
				this.stock=this.stock+cantidad;
		if (nombreVacuna.toLowerCase()=="moderna")
				vacunas.add(new Moderna (nombreVacuna, cantidad, fechaIngreso));
				this.stock=this.stock+cantidad;	
		if (nombreVacuna.toLowerCase()=="pfizer")
				vacunas.add(new Pfizer (nombreVacuna, cantidad, fechaIngreso));
				this.stock=this.stock+cantidad;
		if (nombreVacuna.toLowerCase()=="sinopharm")
				vacunas.add(new Sinopharm (nombreVacuna, cantidad, fechaIngreso));
				this.stock=this.stock+cantidad;
		if (nombreVacuna.toLowerCase()=="astrazeneca")
				vacunas.add(new Astrazeneca (nombreVacuna, cantidad, fechaIngreso));
				this.stock=this.stock+cantidad;
		if (nombreVacuna.toLowerCase()!="sputnik" && nombreVacuna.toLowerCase()!="moderna" &&
				nombreVacuna.toLowerCase()!="pfizer" && nombreVacuna.toLowerCase()!="sinopharm"
				&& nombreVacuna.toLowerCase()!="astrazeneca")
			throw new Exception ("el nombre ingresado no corresponde a una vacuna");
		
		if (cantidad<1)
			throw new Exception ("el numero ingresado debe ser mayor que uno");
	}
	/**
	* total de vacunas disponibles no vencidas sin distinci�n por tipo.
	*/
	public int vacunasDisponibles() {
		
		return vacunasVencidas;
	}
	/**
	* total de vacunas disponibles no vencidas que coincida con el nombre de
	* vacuna especificado.
	*/
	public int vacunasDisponibles(String nombreVacuna) {
		return 0;
	}
	/**
	* Se inscribe una persona en lista de espera.
	* Si la persona ya se encuentra inscripta o es menor de 18 a�os, se debe
	* generar una excepci�n.
	* Si la persona ya fue vacunada, tambi�n debe generar una excepci�n.
	*/
	public void inscribirPersona(int dni, Fecha nacimiento,
	boolean tienePadecimientos, boolean esEmpleadoSalud) {
		Persona paciente= new Persona(dni, nacimiento,
				tienePadecimientos, esEmpleadoSalud);
		inscriptos.add(paciente);
		
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
	/**
	* Devuelve en O(1) un Diccionario:
	* - clave: nombre de la vacuna
	* - valor: cantidad de vacunas vencidas conocidas hasta el momento.
	*/
	public Map<String, Integer> reporteVacunasVencidas(){
		Map<String, Integer> lista= new HashMap <String,Integer> ();
		return lista;
	}

}
