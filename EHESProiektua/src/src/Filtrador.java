package src;

import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.instance.Randomize;


public class Filtrador {
	
	public Filtrador(){
		
	}
	
	public static Instances conseguirDatosFiltrados(Instances data){
    	// Crear filtro
		AttributeSelection filter= new AttributeSelection();
		// Crear tipo de filtro
		CfsSubsetEval eval = new CfsSubsetEval();
		// Crear metodo de busqueda
		BestFirst search=new BestFirst();
		// añadir lo creado al filtro
		filter.setEvaluator(eval);
		filter.setSearch(search);
		
		Instances newData = null, newData2=null;
		
		try {
			filter.setInputFormat(data);
			newData = Filter.useFilter(data, filter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Randomize ra = new Randomize();
		
		try {
			ra.setInputFormat(newData);
			newData2 = Filter.useFilter(newData, ra);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newData2;
	}
 
}
