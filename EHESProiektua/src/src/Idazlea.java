package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
	
	public void fitxategiaEginOneR(OneR estimador, Evaluation evaluator, int bMax, Instances devaurre, double fmeasureMediaMax, int bucketSizeEzExhaustiboa){
		 // TODO JORGE
	}
	public void fitxategiaEginMultilayerPerceptron(MultilayerPerceptron estimador, Evaluation evaluator, Instances data, String hiddenlayersMax, String hidenLayerEzExhaustiboa){
		 // TODO JORGE
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
