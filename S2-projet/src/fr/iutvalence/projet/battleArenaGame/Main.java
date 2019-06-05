package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.view.StatusMessages;
import fr.iutvalence.projet.battleArenaGame.view.GameView;
import fr.iutvalence.projet.battleArenaGame.view.PlayerConsole;

public class Main
{
	public static void main (String[] args)
	{
		launch2();
	}
	

	public static void launch()
	{	
		//TODO The IHM which choose between those choices won't be a GameView but an other thing which set parameters for the game
		//Because of that, we directly launch a local game.
		//TODO fix that 
		GameView playerIhm = new PlayerConsole()
		playerIhm.displayMenu();
		switch(playerIhm.askChoiceMenu())
		{		
		case HOST_GAME: 
					
			break;
		case JOIN_GAME: 
				
			break;
				
		case LOCAL_GAME:
				
			//TODO add config ask and remove following default parameters
			int nbPlayer = 3;
			int nbPawn = 1;
			int boardSize = 2;
			new Game(nbPlayer,nbPawn,boardSize).play();
			break;
					
			}
		
	}

//TODO remove that, used to test our game

private static void launch2()
{
	int nbPlayer = 3;
	int nbPawn = 2;
	int boardSize = 3;	
	new Game(nbPlayer,nbPawn,boardSize).play();

}

}


