package centroVacunacion;

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
		}
		setCantidad(getCantidad() + cantidad);
	}
	
	public void agregarVacunas(Vacuna vac) {
		
		super.agregarVacunas(new Vacuna(vac));
	}
	
	public void quitarVacuna(Vacuna nueva) {
		//Vacuna nueva = new Vacuna(tipo, new Fecha());
		super.quitarVacuna(nueva);
		setCantidad(getCantidad() - 1);
	}
	
	public void agregarVencida(Vacuna vac) {
		
		if (vencidas.containsKey(vac.getNombre())) {
			vencidas.replace(vac.getNombre(), vencidas.get(vac.getNombre()) + 1);
			setVacunasVencidas(getVacunasVencidas() + 1);
		}
		else {
			vencidas.put(vac.getNombre(), 1);
		}
		
	}
	//public void quitarVencida(Vacuna vac) {
		
	//	if (vencidas.remove(vac)) {
	//		setVacunasVencidas(getVacunasVencidas() - 1);
	//	}		
	//}
	
	
	public void actualizarVencidas() {
		
		Iterator<Vacuna> iterador = super.getVacunas().iterator();
		//Fecha otra = new Fecha();
		System.out.println("empieza");
		
		while (iterador.hasNext()) {
			Vacuna otra = iterador.next();
			//System.out.println(Fecha.hoy().toString());

			System.out.println("while");
			
			if (otra.getNombre().equals("pfizer") ){
				if(otra.getIngreso().anterior(Fecha.hoy()));	{	
					//Vacuna nueva = new Pfizer(iterador.next().getNombre() , iterador.next().getIngreso());
					setVacunasVencidas(getVacunasVencidas() + 1);
					agregarVencida(new Vacuna(otra));
					iterador.remove();
					super.setCantidad(getCantidad()-1);
					System.out.println("pfizer");
				}
			}
			else if(otra.getNombre().equals("moderna")) {
				if(otra.getIngreso().anterior(Fecha.hoy()));	{	
					//Vacuna nueva = new Moderna(iterador.next().getNombre() , iterador.next().getIngreso());
					setVacunasVencidas(getVacunasVencidas() + 1);
					agregarVencida(new Vacuna (otra));
					iterador.remove();
					super.setCantidad(getCantidad() - 1);
					System.out.println("moderna");
				}
			}
		}
	}
	
	public int dameVencidas(String tipo) {
		
		if (vencidas.containsKey(tipo)) {
			return vencidas.get(tipo);	
		}
		else {
			return 0;
		}		
	}
	
	

	
//Getters and setters ---------------------------------------------------
	
	
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