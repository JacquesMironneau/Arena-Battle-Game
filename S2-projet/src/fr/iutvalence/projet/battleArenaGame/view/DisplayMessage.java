package fr.iutvalence.projet.battleArenaGame.view;

import fr.iutvalence.projet.battleArenaGame.spell.Effect;

/**
 * Simple messages that can be displayed to the player
 *
 */
public enum DisplayMessage
{
	MENU("----------------Menu-----------------\n1) Creer page de sort\n2) Créer une partie\n3) Rejoindre une partie\n4) cr�� partie local"),
	LOCAL_CONFIG("Entrer le nombre de joueur, puis le nombre de pion par joueur, puis la taille du plateau puis le nombre d'interface console voulue"),
	SERVER_CONFIG("Entrer le nombre de joueur, puis le nombre de pion par joueur, puis la taille du plateau");
	
	
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


