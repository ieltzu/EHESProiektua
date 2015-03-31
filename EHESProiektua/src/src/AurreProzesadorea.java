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
		
		Instances filtratzekoInstantziak = Irakurtzailea.getIrakurtzailea().instantziakIrakurri(args[0]);
		// Crear filtro
		MultiFilter multiFilter = new MultiFilter();
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
		
		multiFilter.setFilters(filtroSorta);	
		Instances filtratzekoInstantziak2 = null;
		try {
			filtratzekoInstantziak2 = Filter.useFilter(filtratzekoInstantziak, multiFilter);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		AttributeSelection filter= new AttributeSelection();
		// Crear tipo de filtro
		CfsSubsetEval eval = new CfsSubsetEval();
		// Crear metodo de busqueda
		GreedyStepwise search=new GreedyStepwise();
		// añadir lo creado al filtro
		filter.setEvaluator(eval);
		filter.setSearch(search);
				
		Instances filtratzekoInstantziak3=null;
				
		try {
			filter.setInputFormat(filtratzekoInstantziak2);
			filtratzekoInstantziak3 = Filter.useFilter(filtratzekoInstantziak2, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Idazlea.getIdazlea().idatziInstantziak(filtratzekoInstantziak3, args[1]);
	}

}
