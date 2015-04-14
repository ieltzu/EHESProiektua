package src;

import java.io.ObjectInputStream.GetField;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class Sailkatzailea {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub  MIKEL
		String modeloPath = args[0];
		String testPath = args[1];
		String resultPath = (args.length >= 3) ? args[2] : "./emaitza.arff";
		sailkatu(modeloPath, testPath, resultPath);
	}
	
	public static void sailkatu(String modeloPath, String testPath, String resultPath){
		resultPath = (resultPath != null) ? resultPath : "./emaitza.arff";
		Classifier classifier = Irakurtzailea.getIrakurtzailea().modeloaKargatu(modeloPath);
		Instances test = Irakurtzailea.getIrakurtzailea().instantziakIrakurri(testPath);
		
		Instance ins;
		int noClassify = 0;
		for (int i = 0; i < test.numInstances(); i++) {
			ins = test.get(i);
			try {
				double em = classifier.classifyInstance(ins);
				ins.setClassValue(em);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(i+". instantzia ezin izan da klasifikatu.");
				noClassify++;
			}
		}
		System.out.println("\nNumber of instances: "+test.numInstances()+"\nNumber classified instances: "+(test.numInstances()-noClassify)+"\nNumber NO classified instances: "+noClassify);
		
		Idazlea.getIdazlea().idatziInstantziak(test, resultPath);
	}

}
