package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import weka.core.Instances;

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
}
