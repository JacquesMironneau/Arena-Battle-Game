package fr.iutvalence.projet.battleArenaGame.network;

import java.io.IOException;
import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.GameController;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.view.GameView;
import fr.iutvalence.projet.battleArenaGame.view.StatusMessages;

public class GameClientHandler implements GameView
{
	private GameController gameController;
	
	private ClientConnectionInfo[] clients;
	
	public GameClientHandler(GameController gc, ClientConnectionInfo[] clientsInfo)
	{
		this.gameController = gc;
		this.clients = clientsInfo;

		new Thread(() ->  {
			receive();
		}).start();
		
		
	}
	
	public void receive()
	{
		while(true) //TODO care for the infinite loop
		{
			for(ClientConnectionInfo client: this.clients)
			{
				try {
					client.getReader().readLine();
				}catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void send(String msg, int index)
	{
		try
		{
			this.clients[index].getWriter().append(msg);
			this.clients[index].getWriter().flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void askActionChoice(int currentPlayerIndex)
	{

	}

	@Override
	public void askSpell(int currentPlayerIndex)
	{
		
	}

	@Override
	public void askPageSelection(int currentPlayerIndex)
	{
		
	}

	@Override
	public void askChoiceMenu(int currentPlayerIndex)
	{
		
	}

	@Override
	public String askPageName()
	{
		return null;
	}

	@Override
	public Effect askSpellElement()
	{
		return null;
	}

	@Override
	public void askMove(int currentPlayerIndex)
	{
		
	}

	@Override
	public void displaySpellSelection()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayBoard(Board myBoard, int nbPlayer)
	{
		
	}

	@Override
	public void displayMenu()
	{
		
	}

	@Override
	public void displayStatus(StatusMessages msg)
	{
		
	}

	@Override
	public void displaySpellPage(ArrayList<SpellPage> listPages)
	{
		
	}

	@Override
	public void displayChoiceAction()
	{
		
	}

	@Override
	public void displayEnd(String winTeam)
	{
		
	}

	@Override
	public void displayElementChoice()
	{
		
	}

	@Override
	public void displayShapeChoice()
	{
		
	}

	@Override
	public void displayNextTurn(int numPlayer)
	{
		
	}

	@Override
	public void displayMoveDone()
	{
		
	}

	@Override
	public void displaySpellPageDetail(SpellPage pPage)
	{
		
	}

	@Override
	public void diplaySizeError()
	{
		
	}

	@Override
	public void displaySelectForThisPawn(Pawn thePawn)
	{
		
	}

	@Override
	public void displayMoveSelection()
	{
		
	}

}
