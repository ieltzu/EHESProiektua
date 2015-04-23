package src;

import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
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
		
		Instances filtratzekoInstantziak [] = new Instances [2];
		filtratzekoInstantziak[0] = Irakurtzailea.getIrakurtzailea().instantziakIrakurri(args[0]);
		filtratzekoInstantziak[1] = Irakurtzailea.getIrakurtzailea().instantziakIrakurri(args[1]);
		
		// Crear filtro
		Integer moztu;
		try {
		 moztu= Integer.parseInt(args[4]);
		 moztu = (moztu>100) ? 100:moztu;
		 moztu = (moztu<0) ? 0:moztu;
		} catch (Exception e) {
			moztu=100;
		}
		
		int trainSize = (int) Math.round(filtratzekoInstantziak[0].numInstances() * moztu/100);
	    filtratzekoInstantziak[0] = new Instances(filtratzekoInstantziak[0], 0, trainSize);
	    System.out.println("Train: "+filtratzekoInstantziak[0].numInstances()+" instantzia");
	    System.out.println("Development: "+filtratzekoInstantziak[1].numInstances()+" instantzia");
	    trainSize = (int) Math.round(filtratzekoInstantziak[1].numInstances() * moztu/100);
	    filtratzekoInstantziak[1] = new Instances(filtratzekoInstantziak[1], 0, trainSize);
	    
		InterquartileRange interquartile = new InterquartileRange();
		interquartile.setExtremeValuesFactor(6.0);
		interquartile.setOutlierFactor(3.0);
		try {
			interquartile.setInputFormat(filtratzekoInstantziak[0]);
			filtratzekoInstantziak[0] = Filter.useFilter(filtratzekoInstantziak[0], interquartile);
			filtratzekoInstantziak[1] = Filter.useFilter(filtratzekoInstantziak[1], interquartile);
			
			RemoveWithValues removeWithValues = new RemoveWithValues();
			removeWithValues.setAttributeIndex("last");
			removeWithValues.setNominalIndices("last");
			removeWithValues.setInputFormat(filtratzekoInstantziak[0]);
			filtratzekoInstantziak[0] = Filter.useFilter(filtratzekoInstantziak[0], removeWithValues);
			filtratzekoInstantziak[1] = Filter.useFilter(filtratzekoInstantziak[1], removeWithValues);
			removeWithValues = new RemoveWithValues();
			removeWithValues.setAttributeIndex(""+(filtratzekoInstantziak[0].numAttributes()-1));
			removeWithValues.setNominalIndices("last");
			removeWithValues.setInputFormat(filtratzekoInstantziak[0]);
			filtratzekoInstantziak[0] = Filter.useFilter(filtratzekoInstantziak[0], removeWithValues);
			filtratzekoInstantziak[1] = Filter.useFilter(filtratzekoInstantziak[1], removeWithValues);
			
			Remove remove = new Remove();
			remove.setInputFormat(filtratzekoInstantziak[0]);
			remove.setAttributeIndicesArray(new int[] {filtratzekoInstantziak[0].numAttributes()-1,filtratzekoInstantziak[0].numAttributes()});
			filtratzekoInstantziak[0] = Filter.useFilter(filtratzekoInstantziak[0], remove);
			filtratzekoInstantziak[1] = Filter.useFilter(filtratzekoInstantziak[1], remove);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		
	    
	    //multiFilter
		MultiFilter multiFilter = new MultiFilter();
		Filter[] filtroSorta = new Filter[3];
		Discretize discretize = new Discretize();
		Randomize randomize = new Randomize();
		RemoveUseless removeUseless = new RemoveUseless();
		
		filtroSorta[0] = discretize;
		filtroSorta[1] = randomize;
		filtroSorta[2] = removeUseless;


		
		multiFilter.setFilters(filtroSorta);	
		Instances filtratzekoInstantziak2 = filtratzekoInstantziak[0];
		try {
			multiFilter.setInputFormat(filtratzekoInstantziak2);
			filtratzekoInstantziak2 = Filter.useFilter(filtratzekoInstantziak2, multiFilter);
		} catch (Exception e1) {
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
