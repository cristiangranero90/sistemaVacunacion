package centroVacunacion;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Iterator;

public class Deposito {
	
	protected ArrayList<Vacuna> vacunas;
	protected ArrayList<Vacuna> vencidas;
	protected int cantidad;
	protected int temperatura;
	private int vacunasVencidas;
		
	Deposito(int temperatura){
		
		setCantidad(0);
		setTemperatura(temperatura);
		setVacunasVencidas(0);
		vacunas = new ArrayList<Vacuna>();
		vencidas = new ArrayList<Vacuna>();
		
	}
	
	public void agregarVacunas(Vacuna vac, int cantidad) {
		
		//Fecha otra = new Fecha(); //Fecha de hoy
		
		for (int i = 0; i < cantidad; i++) {
			
			if (vacunas.add(vac)) {
				setCantidad(getCantidad() + 1);
			}					
	}
}
	
	public void agregarVacunas(Vacuna vac) {
		
		if(vacunas.add(vac)) {
			setCantidad(getCantidad() + 1);
		}
	}
	
	public void quitarVacuna(Vacuna nueva) {
		//Vacuna nueva = new Vacuna(tipo, new Fecha());
		if(vacunas.remove(nueva)) {
			setCantidad(getCantidad() - 1);
		}
	}
	
	public void agregarVencida(Vacuna vac) {
		if (vencidas.add(vac)) {
			setVacunasVencidas(getVacunasVencidas() + 1);
		}
		
	}
	public void quitarVencida(Vacuna vac) {
		
		if (vencidas.remove(vac)) {
			setVacunasVencidas(getVacunasVencidas() - 1);
		}		
	}
	
	public int cantVacunasNombre(String nombre) {
		int contador = 0;
		
		for(Vacuna vac : vacunas) {
			if (vac.getNombre().equals(nombre.toLowerCase())) {
				contador++;
			}
		}
		return contador;
	}
	
	public void actualizarVencidas() {
		
		Iterator<Vacuna> iterador = vacunas.iterator();
		Fecha otra = new Fecha();
		
		while (iterador.hasNext()) {
			
			if (iterador.next().getNombre().equals("pfizer") ){
				if(iterador.next().getIngreso().posterior(otra));	{	
					Vacuna nueva = new Pfizer(iterador.next().getNombre() , iterador.next().getIngreso());
					agregarVencida(nueva);
					quitarVacuna(iterador.next());
				}
			}
			else if(iterador.next().getNombre().equals("moderna")) {
				if(iterador.next().getIngreso().posterior(otra));	{	
					Vacuna nueva = new Moderna(iterador.next().getNombre() , iterador.next().getIngreso());
					agregarVencida(nueva);
					quitarVacuna(iterador.next());
				}
			}
		}
	}
	
	

	
//Getters and setters ---------------------------------------------------
	
	public int cantVacunas() {		
		return cantidad;
	}
	
	public int cantVencidas() {		
		return vencidas.size();
	}

	public int getCantidad() {
		return cantidad;
	}

	public int getVacunasVencidas() {
		return vacunasVencidas;
	}

	public void setVacunasVencidas(int vacunasVencidas) {
		this.vacunasVencidas = vacunasVencidas;
	}

	public void setCantidad(int cantidad) {
		
		if (cantidad > 0) {
			this.cantidad = cantidad;
		}
		
	}

	public int getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}

	


	
}
