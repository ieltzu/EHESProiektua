package src;

import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.rules.OneR;
import weka.core.Instance;
import weka.core.Instances;

public class Modeloa {

	private static Evaluation evaluator;
	
	public static void main(String[] args){
		
		//trainaurre eta devaurre entrenamendu multzoak jasoko dira. Lehenik beti jarriko da train multzoa eta gero dev multzoa
		Instances trainaurre = Irakurtzailea.getIrakurtzailea().instantziakIrakurri(args[0]);
		Instances devaurre = Irakurtzailea.getIrakurtzailea().instantziakIrakurri(args[1]);
		
		// train eta dev gehitu
	    Instances trainetadev=new Instances(trainaurre);
	    trainetadev.addAll(devaurre);
		
		//train eta dev-ren aldaerak eraiki.
		int trainSize = (int) Math.round(trainetadev.numInstances() * 0.7);
    	int testSize = trainetadev.numInstances() - trainSize;
	    Instances trainetadev70 = new Instances(trainetadev, 0, trainSize);
    	Instances trainetadev30 = new Instances(trainetadev, trainSize, testSize);
		
		// Baseline (One - R)
		OneR estimador= new OneR();
		
		int bMax=0;
		double fmeasureMedia=0;
		double fmeasureMediaMax=0;
		
		for(int b = 0; b < trainaurre.numInstances(); b++){ //minBucketSize-ko balore desberdinekin probatzen da
			
			estimador.setMinBucketSize(b);
			try {
				estimador.buildClassifier(trainaurre);
			
				//ebaluatzailea hasieratu
				evaluator = new Evaluation(trainaurre);
		
				evaluator.evaluateModel(estimador, devaurre);
				//klase minoritariaren f-measurearekin konparatuz.
				fmeasureMedia= evaluator.weightedFMeasure();								
				if(fmeasureMedia>fmeasureMediaMax){
					bMax=b;
					fmeasureMediaMax=fmeasureMedia;
				}
			}catch(Exception e){
				e.printStackTrace(); System.exit(1);
				
				
			}
		}
		
		// Bilaketa ez exhaustiboa egin
		
		CVParameterSelection bilaketaEzExhaustiboa = new CVParameterSelection();
		bilaketaEzExhaustiboa.setClassifier(estimador);
		try {
			bilaketaEzExhaustiboa.setNumFolds(5);
			bilaketaEzExhaustiboa.addCVParameter("K 1 "+trainaurre.numInstances()+" " +trainaurre.numInstances());
			bilaketaEzExhaustiboa.buildClassifier(trainaurre);
		} catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
		OneR sailk = (OneR)bilaketaEzExhaustiboa.getClassifier();
	    int bucketSizeEzExhaustiboa= sailk.getMinBucketSize();
	   
	    
	    
	    
	    // Inferentzia
	    
	    estimador.setMinBucketSize(bMax);
	    
	    // Ez zintzoa
	    try {
			estimador.buildClassifier(trainetadev);
			evaluator.evaluateModel(estimador, trainetadev);
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); System.exit(1);
		}
	    
	    Idazlea.getIdazlea().fitxategiaEginOneR("ficheros/EvaluationBaseline.txt", evaluator, bMax, bucketSizeEzExhaustiboa, "Ez zintzoa");
	    
	    // Hold out 70 30
	    try{
	    	estimador.buildClassifier(trainetadev70);
	    	evaluator.evaluateModel(estimador, trainetadev30);
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); System.exit(1);
		}
	    Idazlea.getIdazlea().fitxategiaEginOneR("ficheros/EvaluationBaseline.txt", evaluator, bMax, bucketSizeEzExhaustiboa, "Hold Out 70 30");
	    
	    // hold out train dev
	    try{
	    	estimador.buildClassifier(trainaurre);
	    	evaluator.evaluateModel(estimador, devaurre);
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); System.exit(1);
		}
	    
	    Idazlea.getIdazlea().fitxategiaEginOneR("ficheros/EvaluationBaseline.txt", evaluator, bMax, bucketSizeEzExhaustiboa, "Hold Out train dev");
 
	    // 10 Fold cross validation
	    try{
	    	estimador.buildClassifier(trainetadev);
	    	evaluator.crossValidateModel(estimador, trainetadev, 10, new Random(1));
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); System.exit(1);
		}
	    
	    Idazlea.getIdazlea().fitxategiaEginOneR("ficheros/EvaluationBaseline.txt", evaluator, bMax, bucketSizeEzExhaustiboa, "10 Fold cross");
		
	    //modeloa egin
	    Idazlea.getIdazlea().modeloaIdatzi("modeloak/BaselineModel.jar", estimador);
		
		// Multilayer Perceptron
		MultilayerPerceptron estimadorMulti = new MultilayerPerceptron();
		
		double fmeasureMediaMulti=0;
		double fmeasureMediaMaxMulti=0;
		
		//HIDDEN LAYERS AUKERAK A, I , O , T DIRENEZ, array batean gorde
		ArrayList<String> hiddenlayers= new ArrayList<String>();
		hiddenlayers.add("t");
		hiddenlayers.add("a");
		hiddenlayers.add("i");
		hiddenlayers.add("o");
        String hiddenlayersMax= "";
        
		for (int i=0;i<hiddenlayers.size();i++){
			//hidden layer egokiena aukeratzeko loopa, f-measure altuenaren bila.
			estimadorMulti.setHiddenLayers(hiddenlayers.get(i));
			try{
				estimadorMulti.buildClassifier(trainaurre);                     
				evaluator = new Evaluation(trainaurre);          
				evaluator.evaluateModel(estimador, devaurre);
	        
				// klase minoritariaren fmeasurearekin konparatu
				fmeasureMediaMulti = evaluator.weightedFMeasure();
				if(fmeasureMediaMulti>fmeasureMediaMaxMulti){
					fmeasureMediaMaxMulti = fmeasureMediaMulti;
					hiddenlayersMax =  hiddenlayers.get(i);
				}
			} catch (Exception e) {
				e.printStackTrace(); System.exit(1);
			}
		}
		CVParameterSelection bilaketaEzExhaustiboaMulti = new CVParameterSelection();
		bilaketaEzExhaustiboaMulti.setClassifier(estimador);
		try{
			bilaketaEzExhaustiboaMulti.buildClassifier(trainaurre);
		} catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
		MultilayerPerceptron sailkMulti = (MultilayerPerceptron)bilaketaEzExhaustiboa.getClassifier();
	    String hiddenLayerEzexhaustiboa= sailkMulti.getHiddenLayers();
	    System.out.println("MultiLayer Perceptron prozesuaren emaitzak imprimatzen:");
		
	    //Inferentzia
	    
	    estimadorMulti.setHiddenLayers(hiddenlayersMax);
	    
	    // Ez zintzoa
	    try{
	    	estimadorMulti.buildClassifier(trainetadev);
	    	evaluator.evaluateModel(estimadorMulti, trainetadev);
	    } catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
	    Idazlea.getIdazlea().fitxategiaEginMultilayerPerceptron("ficheros/EvaluationMultilayerPerception.txt", evaluator, estimadorMulti, hiddenLayerEzexhaustiboa, "Ez zintzoa");
	    
	    // Hold out 70 30
	    try{
	    	estimadorMulti.buildClassifier(trainetadev70);
	    	evaluator.evaluateModel(estimadorMulti, trainetadev30);
	    } catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
	    
	    Idazlea.getIdazlea().fitxategiaEginMultilayerPerceptron("ficheros/EvaluationMultilayerPerception.txt", evaluator, estimadorMulti, hiddenLayerEzexhaustiboa, "Hold Out 70 30");	    
	    
	    // hold out train dev
	    try{
	    	estimadorMulti.buildClassifier(trainaurre);
	    	evaluator.evaluateModel(estimadorMulti, devaurre);
	    } catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
	    Idazlea.getIdazlea().fitxategiaEginMultilayerPerceptron("ficheros/EvaluationMultilayerPerception.txt", evaluator, estimadorMulti, hiddenLayerEzexhaustiboa, "Hold Out 70 30");	    
	    
	    // 10 Fold cross validation
	    try{
	    	estimadorMulti.buildClassifier(trainetadev);
	    	evaluator.crossValidateModel(estimadorMulti, trainetadev, 10, new Random(1));
	    } catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
	    
	    Idazlea.getIdazlea().fitxategiaEginOneR("ficheros/EvaluationMultilayerPerception.txt", evaluator, bMax, bucketSizeEzExhaustiboa, "10 Fold cross");
		
	    // Modeloa egin
	    Idazlea.getIdazlea().modeloaIdatzi("modeloak/MultilayerPerceptionModel.jar", estimadorMulti);
		
	}
	
	
}
