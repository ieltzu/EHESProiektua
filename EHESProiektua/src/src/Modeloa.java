package src;

import weka.core.Instances;

public class Modeloa {

	
	public static void main(String[] args) {
		//trainaurre eta devaurre entrenamendu multzoak jasoko dira. Lehenik beti jarriko da train multzoa eta gero dev multzoa
		
		Instances trainaurre = Irakurtzailea.getIrakurtzailea().instantziakIrakurri(args[0]);
		Instances devaurre = Irakurtzailea.getIrakurtzailea().instantziakIrakurri(args[1]);
		
		// Baseline (Naive Bayes)
		
		
		
		Idazlea.getIdazlea().fitxategiakEgin(datuak);
		Idazlea.getIdazlea().modeloaIdatzi(mod, cls);
		
		// Multilayer Perceptron
		
		Idazlea.getIdazlea().fitxategiakEgin(datuak);
		Idazlea.getIdazlea().modeloaIdatzi(mod, cls);
	}
	
	
}
