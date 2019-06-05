package fr.iutvalence.projet.battleArenaGame;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

public interface GameController
{
	public void moveRequest(int currentPlayerIndex,Coordinate destination);
	public void spellRequest(int currentPlayerIndex,int spellIndex,Coordinate destination);
	//TODO replace (int)pageToSet by a SpellPage. The problem is that we need to access to the player's page in the GameView actually.
	//The solution may be to send spellPages with the configurable part of the application which will be worked later.
	public void setPageRequest(int currentPlayerIndex,int pageToSet);

}
