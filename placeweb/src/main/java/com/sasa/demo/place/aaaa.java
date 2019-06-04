
package com.sasa.demo.place;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import org.springframework.stereotype.Component;

/**
 *
 * @author Sasa
 */

public class aaaa  {
   
    public void postaviVrijednosti(double brutto, double brojDjece, int brClanova, double prirez){
   this.brClanova=brClanova;
   this.brDjece=brDjece;
    
    }
    public int brClanova;
    public int brDjece;

    public int getBrClanova() {
        return brClanova;
    }

    public int getBrDjece() {
        return brDjece;
    }
    
    
    
    

	protected static double ukupniOdbitak;
	protected static double noviBruto;

	protected  double p24;
	protected  double p36;
	protected double osnovicaZaObracunPoreza;
	protected  double dohodakNakonMirovinskog;
	protected  double placaNeto;
	protected  double ukupniPrirez;

	protected static double UkupnoPorezIPrirez;

	final static double OSNOVNI_ODBITAK = 3800;
	final static double OSNOVICA_ZA_CLANOVE = 2500;



	// množenje koeficijenta sa osnovnim odbitkom
	public  double ukupniOdbitak( ) {
		System.out.println("uk odbitak " + ukupniOdbitak);
		return OSNOVNI_ODBITAK + OSNOVICA_ZA_CLANOVE * koeficijentOdbitaka(brClanova, brDjece);

	}

	public double osnovica(double noviBruto, double ukupniOdbitak) {

		// dohodak = bruto umanjen za mirovinsko (80% od bruta)
		double dohodak = noviBruto * 0.8;
		this.dohodakNakonMirovinskog = dohodak;

		System.out.println("dohodak nakon mirovinskog " + dohodak);
		double osnovicaZaObracunPoreza = dohodak - ukupniOdbitak;
		System.out.println("osnovica za obracun je " + osnovicaZaObracunPoreza);

		if (ukupniOdbitak > dohodak) {
			System.out.println("=====>>vrati samo dohodak " + dohodak);
			System.out.println("====>>>Neto plaća iznosi " + dohodak);

			return dohodak;
		} else {
			// ako je dohodak veci od osbitka racunaj porez
			racunanjePoreza(osnovicaZaObracunPoreza);
			// prirez(VrijePrireza);
			prirez(PlaceController.getVrijePrireza());
			return placaNeto;

		}

	}

	public double racunanjePoreza(double osnovicaZaObracunPoreza) {

		System.out.println("-----------------osnovica 1 prije odbitka iznosi " + osnovicaZaObracunPoreza);
		this.osnovicaZaObracunPoreza = osnovicaZaObracunPoreza;

		if (osnovicaZaObracunPoreza < 30000) {
			double porez24 = osnovicaZaObracunPoreza * 0.24;
			System.out.println("porez do 24% " + porez24);
			// System.out.println("porez prije prireza " +porez24);
			this.p24 = porez24;
			System.out.println("this .p24 iznosi " + p24);
			return porez24;

		} else {
			System.out.println("porez do 24% maximalno je " + 7200);
			this.p24 = 7200;
			double porez36 = (osnovicaZaObracunPoreza - 30000) * 0.36;
			System.out.println("porez do 36% je " + porez36);
			System.out.println("porez prije prireza " + (7200 + porez36));
			this.p36 = (porez36);
			System.out.println("this.p36  je " + p36);
			return (porez36);

		}

	}

	public double prirez(double stopaPrireza) {

		if (stopaPrireza >= 0 && stopaPrireza <= 18) {
			// pozivanje ukupnog poreza iz gornje metode
			double osnovPrirez = (p24 + p36);

			System.out.println("==================double p24 ==== " + p24);
			System.out.println("==================double p36 ==== " + p36);

			System.out.println("==================double osnovPrirez ==== " + osnovPrirez);

			// računanje prireza
			double prirez = (stopaPrireza / 100) * (p24 + p36);
			this.ukupniPrirez = prirez;

			System.out.println(" ==========prirez iznosi " + prirez);
			// ukupni zbroj poreza i prireza
			double ukupno = osnovPrirez + prirez;
			this.UkupnoPorezIPrirez = (ukupno);
			System.out.println("ukupno porez i prirez iznose " + ukupno);
			// neto plaća
			double neto = this.dohodakNakonMirovinskog - ukupno;
			this.placaNeto = neto;
			System.out.println("neto placa iznosi " + neto);
			return ukupno;
		}
		;
		return 0;

	}

	private static double koeficijentOdbitaka(int brClanova, int brDjece) {

		
		 
		double odbitakZaDjecu ;
		double odbitakZaClanove ;
		double koeficijentOdbitaka;

		// računanje za djecu
		switch (brDjece) {
		case 1:
			odbitakZaDjecu = 0.7;
			break;
		case 2:
			odbitakZaDjecu = 1.7;
			break;
		case 3:
			odbitakZaDjecu = 3.1;
			break;
		case 4:
			odbitakZaDjecu = 5;
			break;
		case 5:
			odbitakZaDjecu = 7.5;
			break;
		case 6:
			odbitakZaDjecu = 10.7;
			break;
		default:
			odbitakZaDjecu = 0;
		
			
		}

		// računanje za uzdržavane članove
		switch (brClanova) {
		case 1:
			brClanova = 1;
			odbitakZaClanove = 0.7;
			break;
		case 2:
			brClanova = 2;
			odbitakZaClanove = 0.7 * 2;
			break;
		case 3:
			brClanova = 3;
			odbitakZaClanove = 0.7 * 3;
			break;
		case 4:
			brClanova = 4;
			odbitakZaClanove = 0.7 * 4;
			break;
		case 5:
			brClanova = 5;
			odbitakZaClanove = 0.7 * 5;
			break;
		default:
			odbitakZaClanove = 0;

		}

		// zbroj obadvije vrijednosti
		koeficijentOdbitaka = odbitakZaClanove + odbitakZaDjecu;
		System.out.println("koef odbitaka " + koeficijentOdbitaka);

		return koeficijentOdbitaka;
	}

	public  double getUkupniPrirez() {
		return ukupniPrirez;
	}

	public  void setUkupniPrirez(double ukupniPrirez) {
		OsobniOdbitak.ukupniPrirez = ukupniPrirez;
	}

	public double getP24() {
		return p24;
	}

	public void setP24(double p24) {
		this.p24 = p24;
	}

	public double getP36() {
		return p36;
	}

	public void setP36(double p36) {
		this.p36 = p36;
	}

	public double getOsnovicaZaObracunPoreza() {
		return osnovicaZaObracunPoreza;
	}

	public void setOsnovicaZaObracunPoreza(double osnovicaZaObracunPoreza) {
		this.osnovicaZaObracunPoreza = osnovicaZaObracunPoreza;
	}

	public double getDohodakNakonMirovinskog() {
		return dohodakNakonMirovinskog;
	}

	public void setDohodakNakonMirovinskog(double dohodakNakonMirovinskog) {
		this.dohodakNakonMirovinskog = dohodakNakonMirovinskog;
	}

	public static double getUkupnoPorezIPrirez() {
		return UkupnoPorezIPrirez;
	}

	public static void setUkupnoPorezIPrirez(double ukupnoPorezIPrirez) {
		UkupnoPorezIPrirez = ukupnoPorezIPrirez;
	}



}
