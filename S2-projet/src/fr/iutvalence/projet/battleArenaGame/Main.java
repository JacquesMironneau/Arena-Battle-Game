package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.view.ErrorMessages;
import fr.iutvalence.projet.battleArenaGame.view.GameView;
import fr.iutvalence.projet.battleArenaGame.view.PlayerConsole;

public class Main
{
	public static void main (String[] args)
	{
		GameView playerIhm = new PlayerConsole();
		launch(playerIhm);
	}
	

	public static void launch(GameView playerIhm)
	{		
		playerIhm.displayMenu();
			switch(playerIhm.askChoiceMenu())
			{		
				case HOST_GAME: // server
					
					break;
				case JOIN_GAME: //Client
					/*this.communication = new Client(Game.PORT,Game.HOST_ADDRESS, myNetwork);
					this.communication.init();
					//Receive board from host
					while(this.board == null)
					{
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}	
					pawnSelection();
					this.play();*/
					break;
				
				case LOCAL_GAME:
				
					//TODO add config ask and remove following default parameters
					int nbPlayer = 3;
					int nbPawn = 1;
					int boardSize = 2;
					ArrayList<GameView> gameViews = new ArrayList<GameView>();
					for(int i=0;i<nbPlayer;i++)
						gameViews.add(new PlayerConsole());
					Game myGame =new Game(gameViews,nbPlayer,nbPawn,boardSize);
					myGame.pawnSelection();
					myGame.play();
					break;
					
				default:
					playerIhm.displayError(ErrorMessages.SYSTEM_ERROR);
				}
		
			}
		}


