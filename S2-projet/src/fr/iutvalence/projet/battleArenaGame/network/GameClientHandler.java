package fr.iutvalence.projet.battleArenaGame.network;

import java.io.IOException;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.GameController;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.view.GameView;
import fr.iutvalence.projet.battleArenaGame.view.StatusMessages;

public class GameClientHandler implements GameView
{
	
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
			
			String[] parts = received.split(GameClient.WORD_SEPARATOR);
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
	
	public void send(String msg)
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
		this.send("Ask" +GameClient.WORD_SEPARATOR +"actionChoice" + GameClient.WORD_SEPARATOR+currentPlayerIndex);
	}

	public void askSpell(int currentPlayerIndex)
	{
		this.send("Ask"+GameClient.WORD_SEPARATOR+"spell"+GameClient.WORD_SEPARATOR+currentPlayerIndex);
	}

	@Override
	public void askPageSelection(int currentPlayerIndex)
	{
		this.send("Ask"+GameClient.WORD_SEPARATOR+"pageSelection"+GameClient.WORD_SEPARATOR+currentPlayerIndex);
	}


	@Override
	public void askMove(int currentPlayerIndex)
	{
		this.send("Ask"+GameClient.WORD_SEPARATOR+"move"+currentPlayerIndex);
	}

	@Override
	public void displaySpellSelection()
	{
		this.send("Display"+GameClient.WORD_SEPARATOR+"spellSelection");
	}

	@Override
	public void displayBoard(Board myBoard, int nbPlayer)
	{
		String msg="Display"+GameClient.WORD_SEPARATOR+"board"+GameClient.WORD_SEPARATOR+nbPlayer+GameClient.WORD_SEPARATOR+myBoard.boardSize+GameClient.WORD_SEPARATOR+myBoard.getNbPawn()+GameClient.WORD_SEPARATOR+myBoard.getCurrentPawnIndex()+GameClient.WORD_SEPARATOR+myBoard.getTurnOrder().size()+GameClient.WORD_SEPARATOR+GameClient.SENTENCE_SEPARATOR;		
		for(Pawn p :myBoard.getTurnOrder())
		{
			msg+=p.getTeamId()+GameClient.WORD_SEPARATOR+p.getPos().getCoordX()+GameClient.WORD_SEPARATOR+p.getPos().getCoordY()+GameClient.WORD_SEPARATOR+p.getName()+GameClient.WORD_SEPARATOR+p.getHealthPoints()+GameClient.WORD_SEPARATOR+p.getMovePoints()+GameClient.WORD_SEPARATOR+p.getActionPoints()+GameClient.WORD_SEPARATOR+p.getSpellPage().getPageName()+GameClient.WORD_SEPARATOR+GameClient.GROUP_SEPARATOR;
			for(Spell s :p.getSpellPage().getSpell())
			{
				msg+=s.getShape().getName()+GameClient.WORD_SEPARATOR+s.getShape().getDamage()+GameClient.WORD_SEPARATOR+s.getShape().getCooldown()+GameClient.WORD_SEPARATOR+s.getShape().getRange()+GameClient.WORD_SEPARATOR+s.getShape().getSpellCost()+GameClient.WORD_SEPARATOR+s.getSpellEffect().getEffectName()+GameClient.WORD_SEPARATOR+s.getShape().getEffectedCoordinates().size()+GameClient.WORD_SEPARATOR;
				for(Coordinate coord :s.getShape().getEffectedCoordinates())
				{
					msg+=coord.getCoordX()+GameClient.WORD_SEPARATOR+coord.getCoordY()+GameClient.WORD_SEPARATOR;
				}
				msg+=GameClient.GROUP_SEPARATOR;
			}
			msg+=p.getEffect().size()+GameClient.WORD_SEPARATOR;
			for(PawnEffect effp :p.getEffect())
			{
				msg+=effp.getCurrentDuration()+GameClient.WORD_SEPARATOR+effp.getEffectName()+GameClient.WORD_SEPARATOR;
			}
			msg+=GameClient.SENTENCE_SEPARATOR;
		}
		System.out.println(msg);
		this.send(msg);
	}


	@Override
	public void displayStatus(StatusMessages msg)
	{
		this.send("Display"+GameClient.WORD_SEPARATOR+"status"+GameClient.WORD_SEPARATOR+msg.name());
	}


	@Override
	public void displayChoiceAction()
	{
		this.send("Display"+GameClient.WORD_SEPARATOR+"choiceAction");
	}

	@Override
	public void displayEnd(String winTeam)
	{
		this.send("Display"+GameClient.WORD_SEPARATOR+"end"+GameClient.WORD_SEPARATOR+winTeam);
	}


	@Override
	public void displayNextTurn(int numPlayer)
	{
		this.send("Display"+GameClient.WORD_SEPARATOR+"nextTurn"+GameClient.WORD_SEPARATOR+numPlayer);
	}

	@Override
	public void displayMoveDone()
	{
		this.send("Display"+GameClient.WORD_SEPARATOR+"moveDone");
	}

	@Override
	public void displaySpellPageDetail(SpellPage pPage)
	{
		String msg="Display"+GameClient.WORD_SEPARATOR+"spellPageDetail"+GameClient.WORD_SEPARATOR+pPage.getPageName()+GameClient.WORD_SEPARATOR+GameClient.GROUP_SEPARATOR;
		for(Spell s :pPage.getSpell())
		{
			msg+=s.getShape().getName()+GameClient.WORD_SEPARATOR+s.getShape().getDamage()+GameClient.WORD_SEPARATOR+s.getShape().getCooldown()+GameClient.WORD_SEPARATOR+s.getShape().getRange()+GameClient.WORD_SEPARATOR+s.getShape().getSpellCost()+GameClient.WORD_SEPARATOR+s.getSpellEffect().getEffectName()+GameClient.WORD_SEPARATOR+s.getShape().getEffectedCoordinates().size()+GameClient.WORD_SEPARATOR;
			for(Coordinate coord :s.getShape().getEffectedCoordinates())
			{
				msg+=coord.getCoordX()+GameClient.WORD_SEPARATOR+coord.getCoordY()+GameClient.WORD_SEPARATOR;
			}
		msg+=GameClient.GROUP_SEPARATOR;
		}
		send(msg);
	}

	@Override
	public void displayMoveSelection()
	{
		this.send("Display"+GameClient.WORD_SEPARATOR+"moveSelection");
	}

	@Override
	public void displaySpellPage() {
		this.send("Display"+GameClient.WORD_SEPARATOR+"spellPage");
	}

	@Override
	public void displaySelectForThisPawn(String thePawn) {
		this.send("Display"+GameClient.WORD_SEPARATOR+"selectForThisPawn"+GameClient.WORD_SEPARATOR+thePawn);
	}

	@Override
	public void diplaySizeError() {
		this.send("Display"+GameClient.WORD_SEPARATOR+"sizeErrors");
		
	}


}
