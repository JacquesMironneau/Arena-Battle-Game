package fr.iutvalence.projet.battleArenaGame.network;

import java.io.IOException;
import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.GameController;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.view.GameView;
import fr.iutvalence.projet.battleArenaGame.view.StatusMessages;

public class GameClientHandler implements GameView
{
	public static final String SEPARATOR = "~~~~";
	
	private GameController gameController;
	
	private ClientConnectionInfo clients;
	
	public GameClientHandler(ClientConnectionInfo clientsInfo)
	{
		this.clients = clientsInfo;

		new Thread(() ->  {
			receive();
		}).start();
		
		
	}
	
	public void setGameController(GameController gc)
	{
		this.gameController = gc;
	}
	
	public void receive()
	{
		while(true) //TODO care for the infinite loop
		{
			String received = null;

				try {
					received = this.clients.getReader().readLine();
				}catch(IOException e)
				{
					e.printStackTrace();
				}
			
			String[] parts = received.split(SEPARATOR);
			if(parts[0].equals("Request"))
				switch(parts[1])
				{
				case "move":
					this.gameController.moveRequest(Integer.parseInt(parts[2]), new Coordinate(Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
					break;
				case "spell":
					this.gameController.spellRequest(Integer.parseInt(parts[2]),Integer.parseInt(parts[3]) , new Coordinate(Integer.parseInt(parts[4]), Integer.parseInt(parts[5])));
					break;
				case "action":
					for(StatusMessages Smsg : StatusMessages.values())
						if(Smsg.name().equals(parts[3]))
							this.gameController.actionRequest(Integer.parseInt(parts[2]), Smsg);
					break;
				case "page":
				//use the one from GameClient	
					break;
				}
		}
	}
	
	public void send(String msg, int index)
	{
		try
		{
			this.clients.getWriter().append(msg);
			this.clients.getWriter().flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void askActionChoice(int currentPlayerIndex)
	{
		this.send("Ask" +GameClient.WORD_SEPARATOR +"actionChoice" + GameClient.WORD_SEPARATOR+currentPlayerIndex, currentPlayerIndex);
	}

	public void askSpell(int currentPlayerIndex)
	{
		this.send("Ask"+GameClient.WORD_SEPARATOR+"spell"+GameClient.WORD_SEPARATOR+currentPlayerIndex, currentPlayerIndex);
	}

	@Override
	public void askPageSelection(int currentPlayerIndex)
	{
		this.send("Ask"+GameClient.WORD_SEPARATOR+"pageSelection"+GameClient.WORD_SEPARATOR+currentPlayerIndex, index);
	}


	@Override
	public void askMove(int currentPlayerIndex)
	{
		this.send("Ask"+GameClient.WORD_SEPARATOR+"move"+currentPlayerIndex,currentPlayerIndex);
	}

	@Override
	public void displaySpellSelection()
	{
		this.send("Display"+GameClient.WORD_SEPARATOR+"spellSelection", 1);
	}

	@Override
	public void displayBoard(Board myBoard, int nbPlayer)
	{
		
	}


	@Override
	public void displayStatus(StatusMessages msg)
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
//		this.send("Display"+GameClient.WORD_SEPARATOR+"pageDetail"+GameClient.WORD_SEPARATOR+pPage.getPageName()+GameClient.GROUP_SEPARATOR+pPage.getSpell(0).getShape().getName()+GameClient.WORD_SEPARATOR+pPage.getSpell(0).getShape().getDamage()+GameClient.WORD_SEPARATOR+pPage.getSpell(0).getShape().getCooldown()+GameClient.WORD_SEPARATOR+pPage.getSpell(0).getShape().getRange()+GameClient.WORD_SEPARATOR+pPage.getSpell(0).getShape().get, 0);
	}

	@Override
	public void diplaySizeError()
	{
		
	}


	@Override
	public void displayMoveSelection()
	{
		
	}


	@Override
	public void displaySpellPage(ArrayList<SpellPage> listPages)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySelectForThisPawn(Pawn thePawn)
	{
		// TODO Auto-generated method stub
		
	}

}
