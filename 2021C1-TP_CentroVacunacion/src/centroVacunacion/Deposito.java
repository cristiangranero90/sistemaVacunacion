package centroVacunacion;

import java.util.HashMap;

public class Deposito {
	
	protected HashMap<String, Vacuna> vacunas;
	protected HashMap<String, Vacuna> vencidas;
	protected int cantidad;
	protected int temperatura;
		
	Deposito(int temperatura){
		
		setCantidad(0);
		setTemperatura(temperatura);
		setVencidas(0);
		vacunas = new HashMap<>();
		vencidas = new HashMap<>();
		
	}
	
	public void agregarVacunas(String tipo, int cantidad) {
		
		for (int i = 0; i < cantidad; i++) {
			
			Vacuna nueva = new Vacuna(tipo, this.temperatura, Fecha f);
			
			if (nueva.estaVencida()) {
				vencidas.put(tipo, nueva);
				setVencidas(getVencidas() + 1);
			}
			else {
				vacunas.put(tipo, nueva);
				setCantidad(getCantidad() + 1); 
			}
			
		}
	}
	
	public void quitarVacuna(String tipo) {
		
		vacunas.remove(tipo);
		setCantidad(getCantidad() - 1);
	}
	
	public int cantVacunas() {
		
		return getCantidad();
	}
	
	public int cantVencidas() {
		
		return getVencidas();
	}

	
//Getters and setters ---------------------------------------------------
	
	
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		
		if (cantidad >= 0) {
			this.cantidad = cantidad;
		}
		throw new RuntimeException();
	}

	public int getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}

	public int getVencidas() {
		return vencidas;
	}

	public void setVencidas(int vencidas) {
		this.vencidas = vencidas;
	}

	
}
