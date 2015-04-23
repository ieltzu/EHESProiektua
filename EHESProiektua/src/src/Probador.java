package src;

public class Probador {
	
	public static void main(String[] args) {
		//Lehenengo datuak prosezatuko ditugu.
		 String[] params = new String [5];
		 params[0] = "ficheros/dev.p3.obfuscated.arff"; 
		 params[2] = "ficheros/devaurre.arff";
		 params[1] = "ficheros/train.p3.resampled.obfuscated.arff";
		 params[3] = "ficheros/trainaurre.arff";
		 params[4] = "100";
		 //AurreProzesadorea.main(params);
		 
		 //Modeloa deituko dugu
		 String[] paramsM = new String [2];
		 paramsM[0] = "ficheros/trainaurre.arff";
		 paramsM[1] = "ficheros/devaurre.arff";
		 Modeloa.main(paramsM);
		 //Lehenengo Baseline
		 String[] paramsS = new String [3];
		 paramsS[0] = "modeloak/BaselineModel.model";
		//paramsS[1] = "ficheros/test.copia.arff";
		 paramsS[1] = "ficheros/test.p3.obfuscated.noclass.arff";
		 paramsS[2] = "ficheros/TestPredictionsBaseline.arff";
		 //Sailkatzailea.main(paramsS);
		//Orain multilayer perceptron
		 paramsS[0] = "modeloak/MultilayerPerceptronModel.model";
		 //paramsS[1] = "ficheros/test.copia.arff";
		 paramsS[1] = "ficheros/test.p3.obfuscated.noclass.arff";
		 paramsS[2] = "ficheros/TestPredictionsMultilayerPerceptron.arff";
		 //Sailkatzailea.main(paramsS);
		 
		 
		 
		 
	}

}
