package src;

import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.Discretize;
import weka.filters.unsupervised.attribute.InterquartileRange;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.RemoveUseless;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemoveWithValues;

public class AurreProzesadorea {

	public static void main(String[] args) {
		
		Instances filtratzekoInstantziak [] = new Instances []{Irakurtzailea.getIrakurtzailea().instantziakIrakurri(args[0]),Irakurtzailea.getIrakurtzailea().instantziakIrakurri(args[1])};
		
		// Crear filtro
		Integer moztu;
		/*try {
		 moztu= Integer.parseInt(args[4]);
		 moztu = (moztu>100) ? 100:moztu;
		 moztu = (moztu<0) ? 0:moztu;
		 filtratzekoInstantziak = null;
		} catch (Exception e) {
			moztu=100;
		}*/
		
		//filtratzekoInstantziak.addAll(filtratzekoInstantziak.subList(0,filtratzekoInstantziak.numInstances()/100*moztu));
		
		/*MultiFilter multiFilter = new MultiFilter();
		Filter[] filtroSorta = new Filter[6];
		Discretize discretize = new Discretize();
		InterquartileRange interquartile = new InterquartileRange();
		Remove remove = new Remove();
		RemoveWithValues removeWithValues = new RemoveWithValues();
		Randomize randomize = new Randomize();
		RemoveUseless removeUseless = new RemoveUseless();
		filtroSorta[0] = discretize;
		filtroSorta[1] = interquartile;
		filtroSorta[2] = remove;
		filtroSorta[3] = removeWithValues;
		filtroSorta[4] = randomize;
		filtroSorta[5] = removeUseless;
		for (int i = 0; i < filtroSorta.length; i++) {
			try {
				filtroSorta[i].setInputFormat(filtratzekoInstantziak);
			} catch (Exception e) {
				System.out.println("eoeoeoe");
				e.printStackTrace();
			}

		}

		//remove useless y remove sirven para eliminar los atributos y instancias no deseadas TODO
		
		multiFilter.setFilters(filtroSorta);	
		Instances filtratzekoInstantziak2 = filtratzekoInstantziak;
		try {
			
			filtratzekoInstantziak2 = Filter.useFilter(filtratzekoInstantziak, multiFilter);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		AttributeSelection filter= new AttributeSelection();
		// Crear tipo de filtro
		CfsSubsetEval eval = new CfsSubsetEval();
		// Crear metodo de busqueda
		GreedyStepwise search=new GreedyStepwise();
		// añadir lo creado al filtro
		filter.setEvaluator(eval);
		filter.setSearch(search);
				
		Instances filtratzekoInstantziak3 [] =new Instances [2];
				
		try {
			filter.setInputFormat(filtratzekoInstantziak[0]);
			filtratzekoInstantziak3[0] = Filter.useFilter(filtratzekoInstantziak[0], filter);
			filtratzekoInstantziak3[1] = Filter.useFilter(filtratzekoInstantziak[1], filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Idazlea.getIdazlea().idatziInstantziak(filtratzekoInstantziak3[0], args[2]);
		Idazlea.getIdazlea().idatziInstantziak(filtratzekoInstantziak3[1], args[3]);
	}

}
