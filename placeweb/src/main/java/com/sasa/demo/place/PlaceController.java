/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasa.demo.place;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Sasa
 */

/**
 * @author Sasa
 *
 */
@Controller

public class PlaceController {
    
    @Autowired
    OsobniOdbitak ob;

    @Autowired
    Bruto brutto;
    

	/**
	 * @param args the command line arguments
	 */
	
	@NumberFormat(style = Style.PERCENT)
	private static double VrijeBruto;

	final static double OSNOVNI_ODBITAK = 3800;
	final static double OSNOVICA_ZA_CLANOVE = 2500;

	protected static int VrijePrireza;
	public double employee = VrijeBruto;

	private static int brojDjece;
	private static int brojClanova;

	public static int getVrijePrireza() {
		return VrijePrireza;
	}

	public static void setVrijePrireza(int vrijePrireza) {
		VrijePrireza = vrijePrireza;
	}

	public static int getBrojDjece() {
		return brojDjece;
	}

	public static void setBrojDjece(int brojDjece) {
		PlaceController.brojDjece = brojDjece;
	}

	public static int getBrojClanova() {
		return brojClanova;
	}

	public static void setBrojClanova(int brojClanova) {
		PlaceController.brojClanova = brojClanova;
	}

	public static double getVrijeBruto() {
		return VrijeBruto;
	}
	
	public static void setVrijeBruto(double vrijeBruto) {
		VrijeBruto = vrijeBruto;
	}

	public PlaceController() {

	}

	public PlaceController(double vrijeBruto) {
		VrijeBruto = vrijeBruto;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String index(Model model) {
		
		List<Integer> prirez = new ArrayList<>();
		prirez.add(0);
		prirez.add(1);
		prirez.add(2);
		prirez.add(3);
		prirez.add(4);
		prirez.add(5);
		prirez.add(6);
		prirez.add(7);
		prirez.add(8);
		prirez.add(9);
		prirez.add(10);
		prirez.add(11);
		prirez.add(12);
		prirez.add(13);
		prirez.add(14);
		prirez.add(15);
		prirez.add(16);
		prirez.add(17);
		prirez.add(18);
		
		
		List<Integer> brojDjece = new ArrayList<>();
		brojDjece.add(0);
		brojDjece.add(1);
		brojDjece.add(2);
		brojDjece.add(3);
		brojDjece.add(4);
		brojDjece.add(5);
		brojDjece.add(6);
		
		
		List<Integer> brojClanova = new ArrayList<>();
		brojClanova.add(0);
		brojClanova.add(1);
		brojClanova.add(2);
		brojClanova.add(3);
		brojClanova.add(4);
		brojClanova.add(5);
		
		
		    model.addAttribute("test", prirez);
		    model.addAttribute("djeca",brojDjece);
		    
		    model.addAttribute("clanovi",brojClanova);
		   
		    
		return "form";
	}
	
	
	
	
	

	
	
	
	


	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String handlePost(@RequestParam(value="employee" ,defaultValue="0") double d  , @RequestParam("clanovi") int brojClanova,
			@RequestParam("djeca") int brojDjece, @RequestParam("VrijePrireza") int VrijePrireza) {


		
		PlaceController.setBrojClanova(brojClanova);
		PlaceController.setVrijeBruto(d);
		PlaceController.setBrojDjece(brojDjece);
		PlaceController.setVrijePrireza(VrijePrireza);
		return "redirect:/rezultat";

	}

	@GetMapping("/rezultat")
	public String sayHello(Model theModel) {
		//OsobniOdbitak ob = new OsobniOdbitak();
		double netoPlaca = ob.osnovica(brutto.bruto(VrijeBruto),
				OsobniOdbitak.ukupniOdbitak(getBrojClanova(), getBrojDjece()));

		theModel.addAttribute("clanovi", getBrojClanova());
		theModel.addAttribute("djeca", getBrojDjece());
		theModel.addAttribute("VrijePrireza", getVrijePrireza());

		theModel.addAttribute("getVrijeBruto", getVrijeBruto());
		theModel.addAttribute("employee", VrijeBruto);
		theModel.addAttribute("PocetniBruto", VrijeBruto);

		theModel.addAttribute("mirovinsko1", brutto.mirovinsko1(VrijeBruto));
		theModel.addAttribute("mirovinsko2", brutto.mirovinsko2(VrijeBruto));

		theModel.addAttribute("ukupnaOlaksica", OsobniOdbitak.ukupniOdbitak(getBrojClanova(), getBrojDjece()));

		theModel.addAttribute("osnovicaZaObracunPoreza", Double.valueOf(ob.getOsnovicaZaObracunPoreza()));

		theModel.addAttribute("p24", Double.valueOf(ob.getP24()));
		theModel.addAttribute("p36", Double.valueOf(ob.getP36()));
		theModel.addAttribute("poreziUkupno", Double.valueOf(ob.getP24() + Double.valueOf(ob.getP36())));
		theModel.addAttribute("prirez", Double.valueOf(OsobniOdbitak.getUkupniPrirez()));
		theModel.addAttribute("UkupnoPorezIPrirez", Double.valueOf(OsobniOdbitak.getUkupnoPorezIPrirez()));

		theModel.addAttribute("ukupniTrosakPlace", brutto.ukupniTrosakPlace(VrijeBruto));

		theModel.addAttribute("zdravstveno", brutto.zdravstveno(VrijeBruto));

		theModel.addAttribute("netoPlaca", netoPlaca);

	//	return "helloworld";
		return "viewbar";
	}

}
