package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import weka.classifiers.Classifier;
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
	
	public void fitxategiakEgin(ArrayList<String> datuak){
		 
	}
	public void modeloaIdatzi(ObjectOutputStream mod, Classifier cls){
		
	}
}
