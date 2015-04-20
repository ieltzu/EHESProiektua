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

	
	
	public static void main(String[] args){
		
		Evaluation evaluator = null;
		
		//trainaurre eta devaurre entrenamendu multzoak jasoko dira. Lehenik beti jarriko da train multzoa eta gero dev multzoa
		Instances trainaurre = Irakurtzailea.getIrakurtzailea().instantziakIrakurri(args[0]);
		Instances devaurre = Irakurtzailea.getIrakurtzailea().instantziakIrakurri(args[1]);
		
		//klase minoritaria kalkulatu
		//int min = minorityclassindex(trainaurre);
		
		// train eta dev gehitu
	    Instances trainetadev=new Instances(trainaurre);
	    trainetadev.addAll(devaurre);
		
		//train eta dev-ren aldaerak eraiki.
		int trainSize = (int) Math.round(trainetadev.numInstances() * 0.7);
    	int testSize = trainetadev.numInstances() - trainSize;
	    Instances trainetadev70 = new Instances(trainetadev, 0, trainSize);
    	Instances trainetadev30 = new Instances(trainetadev, trainSize, testSize);
		
		// Baseline (One-R
/*
		OneR estimador= new OneR();
		
		int bMax=0;
		double fmeasureMedia=0;
		double fmeasureMediaMax=0;
		Boolean capabilities=false;
		
		for(int b = 0; b < trainaurre.numInstances(); b++){ //minBucketSize-ko balore desberdinekin probatzen da
			estimador.setMinBucketSize(b);	
			for (int i = 0; i < 2; i++) {
				estimador.setDoNotCheckCapabilities((i<1));
				try {
					estimador.buildClassifier(trainaurre);
					//ebaluatzailea hasieratu
					evaluator = new Evaluation(trainaurre);
			
					evaluator.evaluateModel(estimador, devaurre);
					//klase minoritariaren f-measurearekin konparatuz.
					fmeasureMedia = evaluator.weightedFMeasure();
					if(fmeasureMedia>fmeasureMediaMax){
						bMax=b;
						capabilities = (i<1);
						fmeasureMediaMax=fmeasureMedia;
					}
				}catch(Exception e){
					e.printStackTrace(); System.exit(1);
					
					
				}
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
	    estimador.setDoNotCheckCapabilities(capabilities);
	    
	    // Ez zintzoa
	    try {
	    	evaluator = new Evaluation(trainetadev);
			estimador.buildClassifier(trainetadev);
			evaluator.evaluateModel(estimador, trainetadev);
	    } catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
	    System.out.println("No fair base");
	    Idazlea.getIdazlea().fitxategiaEginOneR("ficheros/EvaluationBaseline.txt", evaluator, bMax, bucketSizeEzExhaustiboa, "Ez zintzoa",false);
	    
	    // Hold out 70 30
	    try{
	    	evaluator = new Evaluation(trainetadev70);
	    	estimador.buildClassifier(trainetadev70);
	    	evaluator.evaluateModel(estimador, trainetadev30);
	    } catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
	    Idazlea.getIdazlea().fitxategiaEginOneR("ficheros/EvaluationBaseline.txt", evaluator, bMax, bucketSizeEzExhaustiboa, "Hold Out 70 30",true);
	    System.out.println("70 30 base");
	    // hold out train dev
	    try{
	    	evaluator = new Evaluation(trainaurre);
	    	estimador.buildClassifier(trainaurre);
	    	evaluator.evaluateModel(estimador, devaurre);
	    } catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
	    System.out.println("Train eta dev base");
	    
	    Idazlea.getIdazlea().fitxategiaEginOneR("ficheros/EvaluationBaseline.txt", evaluator, bMax, bucketSizeEzExhaustiboa, "Hold Out train dev",true);
 
	    // 10 Fold cross validation
	    try{
	    	evaluator = new Evaluation(trainetadev);
	    	estimador.buildClassifier(trainetadev);
	    	evaluator.crossValidateModel(estimador, trainetadev, 10, new Random(1));
	    } catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
	    
	    Idazlea.getIdazlea().fitxategiaEginOneR("ficheros/EvaluationBaseline.txt", evaluator, bMax, bucketSizeEzExhaustiboa, "10 Fold cross",true);
	    System.out.println("10 fold x validation base");
	    //modeloa egin
	    Idazlea.getIdazlea().modeloaIdatzi("modeloak/BaselineModel.model", estimador);
	    
	    System.out.println("Baseline Model modeloa idatzita");
		*/
		// Multilayer Perceptron
		MultilayerPerceptron estimadorMulti = new MultilayerPerceptron();
		
		double errorratemax=1.0;
		double fmeasureMediaMulti=0;
		double fmeasureMediaMaxMulti=0;
		double rateMax = 0.0;
		double momentumMax = 0.2;
		int trainingtimemax = 0;
		boolean decaymax = false;
		
		//HIDDEN LAYERS AUKERAK A, I , O , T DIRENEZ, array batean gorde
		ArrayList<String> hiddenlayers= new ArrayList<String>();
		hiddenlayers.add("a");
		hiddenlayers.add("i");
		hiddenlayers.add("o");
		hiddenlayers.add("t");
        String hiddenlayersMax= "";
        estimadorMulti.setAutoBuild(true); // hidden layers
        
        //Aurreprozesatzailean egiten direlako.
        estimadorMulti.setNominalToBinaryFilter(false);
        estimadorMulti.setNormalizeAttributes(false);
        estimadorMulti.setNormalizeNumericClass(false);
        
        //estimadorMulti.setGUI(true);
		for (int i=0;i<hiddenlayers.size();i++){
			//hidden layer egokiena aukeratzeko loopa, f-measure altuenaren bila.
			estimadorMulti.setHiddenLayers(hiddenlayers.get(i));
			for (double rate = 0.2; rate<=1; rate+=0.2){
				estimadorMulti.setLearningRate(rate);
				for(double momentum = 0.2; momentum<=1; momentum+=0.2){
					estimadorMulti.setMomentum(momentum);
					for (int trainingtime = 5; trainingtime < 50; trainingtime+=5){
						estimadorMulti.setTrainingTime(trainingtime);
						for (int decay = 0; decay < 2; decay++) {
							estimadorMulti.setDecay(decay<1);
							System.out.println("##############\nhiddenlayers:"+i+"\nrate:"+rate+"\nmomentum:"+momentum+"\ntrainigtime:"+trainingtime+"\nDecay:"+decay);
							try{
								evaluator = new Evaluation(trainaurre);
								estimadorMulti.buildClassifier(trainetadev);
								evaluator.evaluateModel(estimadorMulti, devaurre);
								// klase minoritariaren fmeasurearekin konparatu
								
								fmeasureMediaMulti = evaluator.fMeasure(minorityclassindex(trainetadev));
								/*evaluator.errorRate();
								if(evaluator.errorRate()<errorratemax){
									errorratemax = evaluator.errorRate();
									hiddenlayersMax =  hiddenlayers.get(i);
									rateMax = rate;
									momentumMax=momentum;
									trainingtimemax= trainingtime;
									decaymax=(decay<1);
									
								}/*/
								if(fmeasureMediaMulti>fmeasureMediaMaxMulti){
									fmeasureMediaMaxMulti = fmeasureMediaMulti;
									hiddenlayersMax =  hiddenlayers.get(i);
									rateMax = rate;
									momentumMax=momentum;
									trainingtimemax= trainingtime;
									decaymax=(decay<1);
									
								}
							} catch (Exception e) {
								e.printStackTrace(); System.exit(1);
							}
						}
					}
				}
			}
		}
		CVParameterSelection bilaketaEzExhaustiboaMulti = new CVParameterSelection();
		bilaketaEzExhaustiboaMulti.setClassifier(estimadorMulti);
		try{
			bilaketaEzExhaustiboaMulti.buildClassifier(trainaurre);
		} catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
		MultilayerPerceptron sailkMulti = (MultilayerPerceptron)bilaketaEzExhaustiboaMulti.getClassifier();
	    String hiddenLayerEzexhaustiboa= sailkMulti.getHiddenLayers();
	    System.out.println("MultiLayer Perceptron prozesuaren emaitzak imprimatzen:");
		
	    //Inferentzia
	    
	    estimadorMulti.setHiddenLayers(hiddenlayersMax);
		estimadorMulti.setLearningRate(rateMax);
		estimadorMulti.setMomentum(momentumMax);
		estimadorMulti.setTrainingTime(trainingtimemax);
		estimadorMulti.setDecay(decaymax);
	    
	    // Ez zintzoa
	    try{
	    	evaluator = new Evaluation(trainetadev);
	    	estimadorMulti.buildClassifier(trainetadev);
	    	evaluator.evaluateModel(estimadorMulti, trainetadev);
	    } catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
	    Idazlea.getIdazlea().fitxategiaEginMultilayerPerceptron("ficheros/EvaluationMultilayerPerceptron.txt", evaluator, estimadorMulti, hiddenLayerEzexhaustiboa, "Ez zintzoa",false);
	    System.out.println("No Fair Mp");
	    // Hold out 70 30
	    try{
	    	evaluator = new Evaluation(trainetadev70);
	    	estimadorMulti.buildClassifier(trainetadev70);
	    	evaluator.evaluateModel(estimadorMulti, trainetadev30);
	    } catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
	    
	    Idazlea.getIdazlea().fitxategiaEginMultilayerPerceptron("ficheros/EvaluationMultilayerPerceptron.txt", evaluator, estimadorMulti, hiddenLayerEzexhaustiboa, "Hold Out 70 30",true);	    
	    System.out.println("70 30 MP");
	    // hold out train dev
	    try{
	    	evaluator = new Evaluation(trainaurre);
	    	estimadorMulti.buildClassifier(trainaurre);
	    	evaluator.evaluateModel(estimadorMulti, devaurre);
	    } catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
	    Idazlea.getIdazlea().fitxategiaEginMultilayerPerceptron("ficheros/EvaluationMultilayerPerceptron.txt", evaluator, estimadorMulti, hiddenLayerEzexhaustiboa, "Hold Out 70 30",true);	    
	    System.out.println("HoldOut MP");
	    // 10 Fold cross validation
	    try{
	    	evaluator = new Evaluation(trainetadev);
	    	estimadorMulti.buildClassifier(trainetadev);
	    	evaluator.crossValidateModel(estimadorMulti, trainetadev, 10, new Random(1));
	    } catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
	    System.out.println("10 fold X validation MP");
	    
	    Idazlea.getIdazlea().fitxategiaEginMultilayerPerceptron("ficheros/EvaluationMultilayerPerceptron.txt", evaluator, estimadorMulti, hiddenLayerEzexhaustiboa, "10 Fold cross",true);
		
	    // Modeloa egin
	    Idazlea.getIdazlea().modeloaIdatzi("modeloak/MultilayerPerceptronModel.model", estimadorMulti);
	    System.out.println("MultilayerPerceptronModel modeloa idatzita");
	    
	    
		
	}
	
	private static int minorityclassindex(Instances i){
		int kont [] = new int [i.numClasses()];
		for (int j : kont) {
			kont[j] = 0;
		}
		for (Instance instance : i) {
			kont[1+(instance.classAttribute().indexOfValue(""+instance.classValue()))] +=1;
		}
		int min = kont[0];
		int ind = 0;
		for (int j = 1; j < kont.length; j++)  {
			if(kont[j] < min){
				min = kont[j];
				ind = j;
			}
		}
		return ind;
	}
	
}
