package src;

import java.util.ArrayList;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.rules.OneR;
import weka.core.Instances;

public class Modeloa {

	private static Evaluation evaluator;
	
	public static void main(String[] args) throws Exception {
		//trainaurre eta devaurre entrenamendu multzoak jasoko dira. Lehenik beti jarriko da train multzoa eta gero dev multzoa
		
		Instances trainaurre = Irakurtzailea.getIrakurtzailea().instantziakIrakurri(args[0]);
		Instances devaurre = Irakurtzailea.getIrakurtzailea().instantziakIrakurri(args[1]);
		
		// Baseline (One - R)
		OneR estimador= new OneR();
		
		int bMax=0;
		double fmeasureMedia=0;
		double fmeasureMediaMax=0;
		
		for(int b = 0; b < trainaurre.numInstances(); b++){ //minBucketSize-ko balore desberdinekin probatzen da
			
			estimador.setMinBucketSize(b);
			estimador.buildClassifier(trainaurre);
			//ebaluatzailea hasieratu
			Evaluation evaluator = new Evaluation(trainaurre);
		
			evaluator.evaluateModel(estimador, devaurre);
			//klase minoritariaren f-measurearekin konparatuz.
			fmeasureMedia= evaluator.weightedFMeasure();								
			if(fmeasureMedia>fmeasureMediaMax){
					bMax=b;
					fmeasureMediaMax=fmeasureMedia;
			}
		}
		
		// Bilaketa ez exhaustiboa egin
		
		CVParameterSelection bilaketaEzExhaustiboa = new CVParameterSelection();
		bilaketaEzExhaustiboa.setClassifier(estimador);
		bilaketaEzExhaustiboa.setNumFolds(5);
		bilaketaEzExhaustiboa.addCVParameter("K 1 "+trainaurre.numInstances()+" " +trainaurre.numInstances());
		bilaketaEzExhaustiboa.buildClassifier(trainaurre);
		OneR sailk = (OneR)bilaketaEzExhaustiboa.getClassifier();
	    int bucketSizeEzExhaustiboa= sailk.getMinBucketSize();
	    
		Idazlea.getIdazlea().fitxategiaEginOneR(estimador, evaluator, bMax, devaurre, fmeasureMediaMax, bucketSizeEzExhaustiboa);
		
		//Idazlea.getIdazlea().modeloaIdatzi(mod, cls);
		
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
			estimadorMulti.buildClassifier(trainaurre);                      
			
			Evaluation evaluatorMulti = new Evaluation(trainaurre);          
	        evaluatorMulti.evaluateModel(estimador, devaurre);
	        
	        // klase minoritariaren fmeasurearekin konparatu
            fmeasureMediaMulti = evaluatorMulti.weightedFMeasure();
            if(fmeasureMediaMulti>fmeasureMediaMaxMulti){
            	fmeasureMediaMaxMulti = fmeasureMediaMulti;
                hiddenlayersMax =  hiddenlayers.get(i);
            }
		}
		CVParameterSelection bilaketaEzExhaustiboaMulti = new CVParameterSelection();
		bilaketaEzExhaustiboaMulti.setClassifier(estimador);
		bilaketaEzExhaustiboaMulti.buildClassifier(trainaurre);
		MultilayerPerceptron sailkMulti = (MultilayerPerceptron)bilaketaEzExhaustiboa.getClassifier();
	    String hiddenLayerEzexhaustiboa= sailkMulti.getHiddenLayers();
	    System.out.println("MultiLayer Perceptron prozesuaren emaitzak imprimatzen:");
		
		Idazlea.getIdazlea().fitxategiaEginMultilayerPerceptron(estimadorMulti, evaluator, devaurre, hiddenlayersMax, hiddenLayerEzexhaustiboa);
		//Idazlea.getIdazlea().modeloaIdatzi(mod, cls);
	}
	
	
}
