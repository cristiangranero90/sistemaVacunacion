package centroVacunacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DepositoConVencidas extends Deposito {
	
	//protected ArrayList<Vacuna> vacunas;
	protected Map<String, Integer> vencidas;
	//protected int cantidad;
	//protected int temperatura;
	private int vacunasVencidas;
		
	DepositoConVencidas(int temp){
		
		super(temp);
		setCantidad(0);
		//setTemperatura(temperatura);
		setVacunasVencidas(0);
		//vacunas = new ArrayList<Vacuna>();
		vencidas = new HashMap<String, Integer>();
		
	}
	
	public void agregarVacunas(Vacuna vac, int cantidad) {		
		//Fecha otra = new Fecha(); //Fecha de hoy		
		for (int i = 0; i < cantidad; i++) {
			//Vacuna nueva = new Vacuna(tipo, this.temperatura, Fecha f);			
			agregarVacunas(vac);
			//setCantidad(getCantidad() + 1);
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
			//System.out.println(vencidas.get(vac.getNombre()).intValue());
			//System.out.println("actualiza");
		}
		else {
			vencidas.put(vac.getNombre(), 1);
			setVacunasVencidas(getVacunasVencidas() + 1);
			setCantidad(getCantidad() - 1);
			//System.out.println("agrega");
		}
		
	}
	//public void quitarVencida(Vacuna vac) {
		
	//	if (vencidas.remove(vac)) {
	//		setVacunasVencidas(getVacunasVencidas() - 1);
	//	}		
	//}
	
	
	public void actualizarVencidas() {
		
		Iterator<Vacuna> iterador = super.getVacunas().iterator();
		ArrayList<Vacuna> otraLista = new ArrayList<Vacuna>();
		//Fecha otra = new Fecha();
		//System.out.println("empieza");
		
		while (iterador.hasNext()) {
			Vacuna otra = iterador.next();			
			if(otra.getIngreso().anterior(Fecha.hoy())) {	
				otraLista.add(otra);
				//System.out.println(otra.getIngreso().anterior(Fecha.hoy()) + otra.getIngreso().toString());
			}	
		}
		//System.out.println(Fecha.hoy().toString());
		Iterator<Vacuna> itLista = otraLista.iterator();
		//int i = 0;
		while(itLista.hasNext()) {	
			Vacuna nueva = itLista.next();
			agregarVencida(nueva);
			quitarVacuna(nueva);			
			//System.out.println(nueva.getNombre() + i++ +nueva.getIngreso().toString());
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