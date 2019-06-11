package fr.iutvalence.projet.battleArenaGame;

import fr.iutvalence.projet.battleArenaGame.view.UserView;
import fr.iutvalence.projet.battleArenaGame.view.UserViewWindow;
/**
 * Launcher for the application running with a graphic interface
 */
public class LaunchUserWindow {

	public static void main (String[] args)
	{
		UserView myUserView = new UserViewWindow();
		new User(myUserView).launch();
	}
}
