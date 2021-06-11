package centroVacunacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DepositoConVencidas extends Deposito {
	protected Map<String, Integer> vencidas;
	private int vacunasVencidas;
		
	DepositoConVencidas(int temp){
		
		super(temp);
		setCantidad(0);
		setVacunasVencidas(0);
		vencidas = new HashMap<String, Integer>();
		
	}
	
	public void agregarVacunas(Vacuna vac, int cantidad) {		
				
		for (int i = 0; i < cantidad; i++) {		
			agregarVacunas(vac);
		}
		
	}
	
	public void agregarVacunas(Vacuna vac) {
		
		super.agregarVacunas(vac);
	}
	
	public void agregarVencida(Vacuna vac) {
		
		if (vencidas.containsKey(vac.getNombre())) {
			int numero = vencidas.get(vac.getNombre()).intValue();
			vencidas.put(vac.getNombre(), numero + 1);
			setVacunasVencidas(getVacunasVencidas() + 1);
			setCantidad(getCantidad() - 1);
		}
		else {
			vencidas.put(vac.getNombre(), 1);
			setVacunasVencidas(getVacunasVencidas() + 1);
			setCantidad(getCantidad() - 1);
		}
		
	}
	
	public void actualizarVencidas() {
		
		Iterator<Vacuna> iterador = super.getVacunas().iterator();
		ArrayList<Vacuna> otraLista = new ArrayList<Vacuna>();
		
		while (iterador.hasNext()) {
			Vacuna otra = iterador.next();			
			if(otra.getIngreso().anterior(Fecha.hoy())) {	
				otraLista.add(otra);
			}	
		}
		
		Iterator<Vacuna> itLista = otraLista.iterator();
		
		while(itLista.hasNext()) {	
			Vacuna nueva = itLista.next();
			agregarVencida(nueva);
			quitarVacuna(nueva);			
		}
	}
	
	public int dameVencidas(String tipo) {
		
		if (vencidas.containsKey(tipo)) {
			return (int) vencidas.get(tipo);	
		}
		else {
			return 0;
		}		
	}
	
	

	
//Getters and setters ---------------------------------------------------
	
	
	public int cantVencidas() {		
		return getCantidad();
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