package src;

import weka.core.Instances;


public class ScanParamsJ48{

	public static void main(String[] args){
		Cargador cg = new Cargador();
		Instances data = cg.cargarArchivo(args[0]);
		Instances data2 = cg.cargarArchivo(args[1]);
		Filtrador fl = new Filtrador();
		Instances newData = fl.conseguirDatosFiltrados(data);
		Instances newData2 = fl.conseguirDatosFiltrados(data2);
		Clasificador cl = new Clasificador();
		try {
			cl.clasificar(newData,newData2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

