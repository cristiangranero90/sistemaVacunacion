package centroVacunacion;

import java.util.ArrayList;
//import java.util.Iterator;

public class Deposito  {
	
	protected ArrayList<Vacuna> vacunas;
	protected int cantidad;
	protected int temperatura;
	//private int posicion;

	public Deposito(int temperatura) {
		
		vacunas = new ArrayList<>();
		setCantidad(0);
		setTemperatura(temperatura);
		
	}
	
	public void agregarVacunas(Vacuna vac) {
		
		
		vacunas.add(vac);
		setCantidad(getCantidad() + 1);
		//System.out.println("Entro a vacunasSimple");

		
	}
	
	public void agregarVacunas(Vacuna vac, int cantidad) {
		
		for (int i = 0; i<cantidad; i++) {
			vacunas.add(vac);
				setCantidad(getCantidad() + 1);
			//	System.out.println("Entro a vacunasM");
				
		}
	}
	
	public void quitarVacuna(Vacuna nueva) {
		//Vacuna nueva = new Vacuna(tipo, new Fecha());
		
		setCantidad(getCantidad() - 1);
		vacunas.remove(nueva);
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
	
	public int cantVacunas() {
		return vacunas.size();
	}
	
	public boolean existeVacuna(Vacuna vac) {
		
		return vacunas.contains(vac);
	}
	
	public int cantVacunas(String nombre) {
		int contador = 0;
		
		for(Vacuna vac : vacunas) {
			if (vac.getNombre().equals(nombre.toLowerCase())) {
				contador++;
			}
		}
		return contador;
	}
	
	//Getters and Setters-------------------------------------------------------
	
	public ArrayList<Vacuna> getVacunas() {
		return vacunas;
	}

	public void setVacunas(ArrayList<Vacuna> vacunas) {
		this.vacunas = vacunas;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}

	

//--------------------------------Iterable / Iterator ----------------------------
	
	
}
