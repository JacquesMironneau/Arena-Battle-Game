package fr.iutvalence.projet.battleArenaGame;

import fr.iutvalence.projet.battleArenaGame.view.PlayerConsole;

public class Main
{
	public static void main (String[] args)
	{
		new Game(new PlayerConsole()).launch();
	}

}
