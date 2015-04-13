package src;

public class Probador {
	
	public static void main(String[] args) {
		//Lehenengo datuak prosezatuko ditugu.
		 String[] params = new String [2];
		 params[0] = "/EHESProiektua/ficheros/dev.p3.obfuscated.arff"; 
		 params[1] = "/EHESProiektua/ficheros/dev";
		 AurreProzesadorea.main(params);
		 params[0] = "/EHESProiektua/ficheros/train.p3.resampled.obfuscated.arff";
		 params[1] = "/EHESProiektua/ficheros/train";
		 AurreProzesadorea.main(params);
		 
		 //Modeloa deituko dugu
		 String[] paramsM = new String [2];
		 params[0] = "/EHESProiektua/ficheros/train";
		 params[1] = "/EHESProiektua/ficheros/dev";
		 try {
			Modeloa.main(paramsM);
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Something happened");
		}
		 //Lehenengo Baseline
		 String[] paramsS = new String [3];
		 params[0] = "modeloak/BaselineModel.jar";
		 params[1] = "/EHESProiektua/ficheros/test.p3.obfuscated.noclass.arff";
		 params[2] = "result/TestPredictionsBaseline.txt";
		 Sailkatzailea.main(paramsS);
		//Orain multilayer perceptron
		 params[0] = "modeloak/MultilayerPerceptionModel.jar";
		 params[1] = "/EHESProiektua/ficheros/test.p3.obfuscated.noclass.arff";
		 params[2] = "result/TestPredictionsMultilayerPerceptron.txt";
		 Sailkatzailea.main(paramsS);
		 
		 
		 
		 
	}

}
