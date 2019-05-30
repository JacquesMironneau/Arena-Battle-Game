package fr.iutvalence.projet.battleArenaGame.test;

import java.util.Scanner;

import fr.iutvalence.projet.battleArenaGame.view.CheapScanner;

public class TestCheapScanner {
	public static void main(String[] args) {
		CheapScanner cs = new CheapScanner();
		System.out.println("Veuillez saisir un entier : \n");
		System.out.println(cs.getInt());
		
		System.out.println("Veuillez saisir une chaine de caracteres : \n");
		System.out.println(cs.getStr());
		
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Veuillez saisir un entier");
//		System.out.println(sc.nextInt());
	}
}
