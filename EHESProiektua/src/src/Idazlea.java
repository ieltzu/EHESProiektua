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
			output.createNewFile();
			BufferedWriter fw= new BufferedWriter(new FileWriter(output));
			fw.write(instantziak.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fitxategiaEginOneR(String path, Evaluation evaluator, int bMax, int bucketSizeExhaustiboa, String estimazioMota,Boolean berria){
		 try {
			
			String matriz = evaluator.toMatrixString();
			double fmeasureMedia = evaluator.weightedFMeasure();
			double precision = evaluator.weightedPrecision();			
			double recall = evaluator.weightedRecall();
			double roc = evaluator.weightedAreaUnderROC();
			double accu =evaluator.pctCorrect();
			try {
				FileWriter fw = new FileWriter(path,berria);
				fw.write("\n******************************************************\n");
				fw.write("\n****"+estimazioMota+"****\n");
				fw.write("\n******************************************************\n");
				fw.write("F-Measure Batazbestekoa: " + fmeasureMedia+"\n");
				fw.write("BucketSize Maximoa: " + bMax+"\n");
				fw.write("BucketSize metodo ez exhaustiboarekin: " + bucketSizeExhaustiboa+"\n");
				fw.write("Precision Batazbestekoa: " + precision+"\n");
				fw.write("Recall Batazbestekoa: " + recall+"\n");
				fw.write("ROC Area Batazbestekoa: " + roc+"\n");
				fw.write("Correctly Classified Instances: " + accu);
				fw.write("\n" + matriz);
				fw.write("\n******************************************************\n");
				fw.close();
			} catch (IOException e) {
				File f = new File(path);
				f.mkdirs();
				fitxategiaEginOneR(path, evaluator, bMax, bucketSizeExhaustiboa, estimazioMota, berria);
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
	}

	public void fitxategiaEginMultilayerPerceptron(String path, Evaluation evaluator, MultilayerPerceptron estimador, String hidenLayerEzExhaustiboa, String estimazioMota,boolean berria){
	
		try {
			double precision= evaluator.weightedPrecision();			
			double recall= evaluator.weightedRecall();
			double roc= evaluator.weightedAreaUnderROC();
			double fmeasureMedia= evaluator.weightedFMeasure();
			String matriz = evaluator.toMatrixString();
			double accu=evaluator.pctCorrect();
			try {
				FileWriter fw= new FileWriter(path, berria);
				fw.write("\n******************************************************\n");
				fw.write("\n****"+estimazioMota+"****\n");
				fw.write("\n******************************************************\n");
				fw.write("F-Measure Batazbestekoa: " + fmeasureMedia+"\n");
				fw.write("Hidden Layer: " + estimador.getHiddenLayers()+"\n");
				fw.write("Learning Rate: " + estimador.getLearningRate()+"\n");
				fw.write("Momentum: " + estimador.getMomentum()+"\n");
				fw.write("Training Times: " + estimador.getTrainingTime()+"\n");
				fw.write("Decay: " + estimador.getDecay()+"\n");
				fw.write("Hidden Layer metodo ez Exhaustiboarekin: " + hidenLayerEzExhaustiboa+"\n");
				fw.write("Precision Batazbestekoa: " + precision+"\n");
				fw.write("Recall Batazbestekoa: " + recall+"\n");
				fw.write("ROC Area Batazbestekoa: " + roc+"\n");
				fw.write("Correctly Classified Instances: " + accu);
				fw.write("\n" + matriz);
				fw.close();
				 
		} catch (IOException e) {
			File f = new File(path);
			f.mkdirs();
			fitxategiaEginMultilayerPerceptron(path, evaluator, estimador, hidenLayerEzExhaustiboa, estimazioMota, berria);
		}
		} catch (Exception e1) {
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
			File f = new File(path);
			f.mkdirs();
			modeloaIdatzi(path, cls);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
