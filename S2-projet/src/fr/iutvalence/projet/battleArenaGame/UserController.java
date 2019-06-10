package fr.iutvalence.projet.battleArenaGame;

public interface UserController {

	/**
	 * method to create a new SpellPage
	 */
	public void CreateSpellPage();
	
	public void requestChoiseMenu();

	public void requestConfig();
	
	public void choiceMenuRequest(int i);

	public void localConfigRequest(int nbPlayer,int nbPawn,int BoardSize,int nbPlayerCons);
	
	public void serverConfigRequest(int bnPlayer,int nbPawn, int BoardSize);

	public void createDefaultShapes();
}

