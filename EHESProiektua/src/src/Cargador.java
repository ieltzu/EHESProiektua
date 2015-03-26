package src;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;


public class Cargador {

	public Cargador() {
        
    }
        //Datuak komando lerrotik kargatu dira.
        //Klase honek metodo bakarra izango du eta metodo honen bidez datuak kargatu eta
        //prestatuko dira.
	
    public Instances cargarArchivo(String args){              
	    // 1.2. Open the file
	    FileReader fi = null;
	    try {
			fi=new FileReader(args);
	    }catch (FileNotFoundException e) {
			System.out.println("ERROR: Fitxategiaren patha berrikusi:"+args);
		}         
	    // 1.3. Load the instances
		Instances data=null;
		try {
			data = new Instances(fi);
		} catch (IOException e) {
			System.out.println("ERROR: Fitxategiaren edukia berrikusi: "+args);
		}
	  	// 1.4. Close the file
		try {
			fi.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		// 1.6. Specify which attribute will be used as the class: the last one, in this case 
		data.setClassIndex(data.numAttributes()-1);      
	    return data;
   }
        
}

