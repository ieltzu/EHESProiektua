package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.AbstractEvaluationMetric;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.ChebyshevDistance;
import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.ManhattanDistance;
import weka.core.SelectedTag;
import weka.core.neighboursearch.BallTree;
import weka.core.neighboursearch.CoverTree;
import weka.core.neighboursearch.KDTree;
import weka.core.neighboursearch.LinearNNSearch;
import weka.core.neighboursearch.NearestNeighbourSearch;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.InterquartileRange;
import weka.filters.unsupervised.instance.Randomize;


public class Clasificador {	
	
	public Clasificador(){
		
	}
	
	public static void clasificar(Instances train, Instances test) throws Exception{
    	//declarar variables
		File output= new File("TestPredictionJ48.txt");
		FileWriter fw= new FileWriter(output);
    	Evaluation evaluator=null;
    	int trainSize,devSize;
    	double confidencefactor = 0.0;
		int random=0;
		double i= 0.0,fmeasure=0.0,fmeasuredentro=0.0;
		int minnumbobj=0;
		
		// separar datos de entrenamiento
    	trainSize = (int) Math.round(train.numInstances() * 0.7);
    	devSize = train.numInstances() - trainSize;
    	Instances trainsub = new Instances(train, 0, trainSize);
    	Instances development = new Instances(test, 0, devSize);
    	
    	//crear estimador
    	J48 estimador= new J48();
    	estimador.setUnpruned(true);
    	
    	// establecer rangos
    	int min=trainsub.numInstances()*2/100;
    	int max=trainsub.numInstances()/trainsub.numClasses();
    	
    	//obtener mejores valores
    	for(int j=min;j<=max;j=j+min){
    		estimador.setMinNumObj(j);
    		while (i<=1.0) {
				estimador.setConfidenceFactor((float)i);
				estimador.buildClassifier(trainsub);
				
				//evaluar: elegir tipo de evaluacion
				evaluator = new Evaluation(trainsub);
				evaluator.evaluateModel(estimador, development);
				
				//FALTA COGER LA CLASE MINORITARIA
				
				//interpretar datos
				fmeasuredentro = evaluator.weightedFMeasure();
				if(fmeasure<fmeasuredentro){
					fmeasure=fmeasuredentro;
					minnumbobj = j;						
					confidencefactor = i;
				}
				i=i+0.05;
			}
    		i=0.0;
    	}
    	
    	//escribir datos optimos
    	escribirEstimador(fw,fmeasure,minnumbobj,confidencefactor);
    	
    	//aplicar entrenamiento y clasficacion
    	estimador.setMinNumObj(minnumbobj);
    	estimador.setConfidenceFactor((float) confidencefactor);
    	
    	FilteredClassifier fc = new FilteredClassifier();
    	fc.setClassifier(estimador);
    	
    	// train and make predictions
    	fc.buildClassifier(train);
    	int kont=0;
    	ArrayList<Integer> ombre = new ArrayList<Integer>();
    	for (int t = 0; t < test.numInstances(); t++) {
    		try{
	    		Double pred = fc.classifyInstance(test.instance(t));
	    		escribir(fw,test.instance(t),pred);
    		}catch(ArrayIndexOutOfBoundsException e){
    			kont++;
    			ombre.add(t);
    		}
    	}
    	System.out.println(kont);
    	System.out.println(ombre.toString());
    	fw.close();
    	
    	// evaluar el clasificador usado anteriormente
    	Evaluation evaluator2 = new Evaluation(test);
    	estimador.buildClassifier(test);
    	evaluator2.crossValidateModel(estimador, test, 10 ,new Random(1));
    	
    	//aqui se imprime la matriz
    	System.out.println(evaluator2.toMatrixString());
    	System.out.println("Hecho!!!el resultado ha sido guardado en el siguien archivo: TestPredictionJ48.txt ");
    }
	
	private static void escribirEstimador(FileWriter fw, double fmeasure, int minnumobj, double confidence) throws IOException{
		fw.append("Estimator: J48\n");
		fw.append("Fmeasure Max: "+fmeasure+"\n");
		fw.append("MinNumObj: "+minnumobj+"\n");
		fw.append("confidenceFactor: "+confidence+"\n");
	}
	
	private static void escribir(FileWriter fw,Instance instancia, Double pred)throws IOException {
		String actual = instancia.stringValue(instancia.classAttribute());
		String predicted = instancia.classAttribute().value(pred.intValue());
		fw.append("ID: " + instancia.value(0));
		fw.append(", actual: " + actual);
		fw.append(", predicted: " + predicted+"\n");
		//falta imprimir los valores de los atributos en las instancias que han sido mal clasificadas
		if(!actual.equals(predicted)){
			System.out.println("Klase erreala: "+actual+" eta J48 esan duena: "+predicted);

//			for (int k=0;k<instancia.numAttributes();k++) {
//			//	System.out.println(test.instance(t).attribute(k)+":"+test.instance(t).stringValue(k));
//			}
		}
		
    }
	
	public static double getAccuracy(Evaluation evaluator, Instances data){
		Double accuracy = (evaluator.numTruePositives(data.numAttributes()-1)+evaluator.numFalseNegatives(data.numAttributes()-1))/(evaluator.numTruePositives(data.numAttributes()-1)+evaluator.numTrueNegatives(data.numAttributes()-1)+evaluator.numFalsePositives(data.numAttributes()-1)+evaluator.numFalseNegatives(data.numAttributes()-1));
		return accuracy;
	}

}
