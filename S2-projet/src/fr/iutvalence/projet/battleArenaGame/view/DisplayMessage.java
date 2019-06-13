package fr.iutvalence.projet.battleArenaGame.view;

/**
 * Simple messages that can be displayed to the player to give information
 */
public enum DisplayMessage
{
	//Instructions
	MENU("----------------Menu-----------------\n1) Creer page de sort\n2) Créer une partie\n3) Rejoindre une partie\n4) créer partie local"),
	LOCAL_CONFIG("Entrer le nombre de joueur, puis le nombre de pion par joueur, puis la taille du plateau puis le nombre d'interface console voulue"),
	SERVER_CONFIG("Entrer le nombre de joueur, puis le nombre de pion par joueur, puis la taille du plateau"),
	PAGE_CREATION("Entrer 3 fois l'index de l'element voulu puis l'index de la forme voulue, terminer par le nom de la page"),
	
	//Infos
	PAGE_CREATED("La page a été créee"),
	
	
	
	//Errors
	WRONG_PLAYER_NUMBER("Le nombre de joueur entré est incorrect, retour au menu"),
	WRONG_PAWN_NUMBER("Le nombre de pions entré est incorrect, retour au menu"),
	WRONG_BOARD_SIZE("La taille du plateau entré est trop petite, changée a sa plus petit taille possible");
	
	
	private String msg;
	
	DisplayMessage(String pMsg)
	{
		this.msg = pMsg;
	}
	
	public String getMsg()
	{
		return this.msg;
	}
	
}


