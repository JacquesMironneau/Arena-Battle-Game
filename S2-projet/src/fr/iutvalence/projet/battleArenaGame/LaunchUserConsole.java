package fr.iutvalence.projet.battleArenaGame;

import fr.iutvalence.projet.battleArenaGame.view.UserView;
import fr.iutvalence.projet.battleArenaGame.view.UserViewConsole;
/**
 * Launcher for the application running with a console interface
 */
public class LaunchUserConsole {
	
	public static void main (String[] args)
	{
		UserView myUserView = new UserViewConsole();
		new User(myUserView).launch();
	}
}
