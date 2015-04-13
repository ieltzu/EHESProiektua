package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.rules.OneR;
import weka.core.Instances;
import weka.core.SerializedObject;

public class Idazlea {

	private static Idazlea nireIdazlea=null;
	
	private Idazlea() {
		
	}		
	
	public static Idazlea getIdazlea(){
		if(Idazlea.nireIdazlea==null){
			Idazlea.nireIdazlea = new Idazlea();
		}
		return Idazlea.nireIdazlea;
	}
	
	public void idatziInstantziak(Instances instantziak, String path){
		File output= new File(path);
		try {
			BufferedWriter fw= new BufferedWriter(new FileWriter(output));
			fw.write(instantziak.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fitxategiaEginOneR(String path, Evaluation evaluator, int bMax, int bucketSizeExhaustiboa, String estimazioMota){
		 try {
			
			String matriz = evaluator.toMatrixString();
			double fmeasureMedia = evaluator.weightedFMeasure();
			double precision = evaluator.weightedPrecision();			
			double recall = evaluator.weightedRecall();
			double roc = evaluator.weightedAreaUnderROC();
			double accu =evaluator.pctCorrect();
			File fOneR= new File(path+"/UpperBoundsOneR.txt");
			try {
				FileWriter fw = new FileWriter(fOneR);
				fw.append("F-Measure Batazbestekoa: " + fmeasureMedia+"\n");
				fw.append("BucketSize Maximoa: " + bMax+"\n");
				fw.append("BucketSize metodo ez exhaustiboarekin: " + bucketSizeExhaustiboa+"\n");
				fw.append("Precision Batazbestekoa: " + precision+"\n");
				fw.append("Recall Batazbestekoa: " + recall+"\n");
				fw.append("ROC Area Batazbestekoa: " + roc+"\n");
				fw.append("Correctly Classified Instances: " + accu);
				fw.append("\n" + matriz);
				fw.append("\n******************************************************\n");
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
	}

	public void fitxategiaEginMultilayerPerceptron(String path, Evaluation evaluator, MultilayerPerceptron estimador, String hidenLayerEzExhaustiboa, String estimazioMota){
	
		try {
			double precision= evaluator.weightedPrecision();			
			double recall= evaluator.weightedRecall();
			double roc= evaluator.weightedAreaUnderROC();
			double fmeasureMedia= evaluator.weightedFMeasure();
			String matriz = evaluator.toMatrixString();
			double accu=evaluator.pctCorrect();
			File fP= new File(path +"/UpperBoundsMLP.txt");
			try {
				FileWriter fw= new FileWriter(fP);
				fw.append("F-Measure Batazbestekoa: " + fmeasureMedia+"\n");
				fw.append("Hidden Layer: " + estimador.getHiddenLayers()+"\n");
				fw.append("Hidden Layer metodo ez Exhaustiboarekin: " + hidenLayerEzExhaustiboa+"\n");
				fw.append("Precision Batazbestekoa: " + precision+"\n");
				fw.append("Recall Batazbestekoa: " + recall+"\n");
				fw.append("ROC Area Batazbestekoa: " + roc+"\n");
				fw.append("Correctly Classified Instances: " + accu);
				fw.append("\n" + matriz);
				fw.append("\n******************************************************\n");
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	public void modeloaIdatzi(String  path, Classifier cls){
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(cls);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
