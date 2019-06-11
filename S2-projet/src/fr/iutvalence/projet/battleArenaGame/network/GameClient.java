package fr.iutvalence.projet.battleArenaGame.network;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.GameController;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.view.GameView;
import fr.iutvalence.projet.battleArenaGame.view.StatusMessages;

public class GameClient implements GameController
{

	private GameView gameView;
	
	private Socket socket;
	
	private BufferedReader reader;
	
	private BufferedWriter writer;
	
	public GameClient(ClientConnectionInfo client, GameView theView)
	{
		this.gameView = theView;
		this.socket = client.getClientSocket();
		this.reader = client.getReader();
		this.writer = client.getWriter();
		
		new Thread(() -> { 
			receive();
		}).start();
	}

	
	@Override
	public void moveRequest(int currentPlayerIndex, Coordinate destination)
	{
		String request = "Request move " + currentPlayerIndex + " " + destination.getCoordX() + " " +destination.getCoordY();
		send(request);
	}

	@Override
	public void spellRequest(int currentPlayerIndex, int spellIndex, Coordinate destination)
	{
		String request = "Request spell "+currentPlayerIndex+" "+spellIndex+" "+destination.getCoordX() + " " +destination.getCoordY();
		send(request);
	}

	@Override
	public void setPageRequest(int currentPlayerIndex, SpellPage pageToSet)
	{
		String request = "Request page "+currentPlayerIndex+ " "+ pageToSet.getPageName()+ " ";
		for(Spell s: pageToSet.getSpell())
		{
			request += s.getShape().getName()+ " " + s.getShape().getDamage()+ " "+s.getShape().getCooldown()+" "+s.getShape().getRange()+ " " + s.getShape().getSpellCost()+ " "+ s.getShape().getEffectedCoordinates().size()+ " ";
			for(Coordinate c: s.getShape().getEffectedCoordinates())
				request += c.getCoordX()+ " " + c.getCoordY()+ " ";
			request += s.getSpellEffect().getEffectName() + " ";
		}
		send(request);

	}

	@Override
	public void actionRequest(int currentPlayerIndex, StatusMessages choice)
	{
		String str = "";
		switch(choice)
		{
		case MOVE:
			str = "m";
			break;
		case END_TURN:
			str = "e";
			break;
		case LAUNCH_SPELL:
			str = "s";
			break;
		}
		
		String request = "Request action "+currentPlayerIndex+" "+str;
		send(request);
	}
	
	public void send(String msg)
	{
		try
		{
			writer.append(msg);
			writer.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void receive()
	{
		while(true)
		{
			//Receive the string from buffered
			String receivedFrame = null;
			
			try {
				this.reader.readLine();
			}catch(IOException e)
			{
				e.printStackTrace();
			}

			String[] parts = receivedFrame.split(" ");
			
			switch(parts[0])
			{
			case "Ask":
				switch(parts[1])
				{
				case "askChoice":
					break;
//				case
				}
				break;
			case "Display":
				switch(parts[1])
				{
				case "board":
					String[] pawnPart = receivedFrame.split("#");
					Board b = new Board(Integer.parseInt(parts[2]),Integer.parseInt(parts[4]),Integer.parseInt(parts[3]));
					int pawnIndex = Integer.parseInt(parts[5]);
					ArrayList<Pawn> newTurnOrder = new ArrayList<Pawn>();
					for(int i=1;i<= Integer.parseInt(parts[6]);i++)
					{
						Pawn p = new Pawn(Integer.parseInt(parts[0]),new Coordinate(Integer.parseInt(pawnPart[1]),Integer.parseInt(pawnPart[2])),pawnPart[3]);
						p.setHealthPoints(Integer.parseInt(pawnPart[4]));
						p.setMovePoints(Integer.parseInt(pawnPart[5]));
						p.setActionPoints(Integer.parseInt(pawnPart[6]));
						SpellPage s = new SpellPage(pawnPart[7]);
						String[] spellPart = pawnPart[i].split("$");
						for(i=1;i<=3;i++)
						{
							String[] shapePart = spellPart[i].split("&");
							HashSet<Coordinate> myCoordinate = new HashSet<Coordinate>();
							s.getSpell(i-1).setShape(new Shape(shapePart[0],Integer.parseInt(shapePart[1]),Integer.parseInt(shapePart[2]),Integer.parseInt(shapePart[3]),Integer.parseInt(shapePart[4]),myCoordinate));
							for(int k=0;k<Integer.parseInt(shapePart[5]);k++)
							{
								s.getSpell(i-1).getShape().getEffectedCoordinates().add(new Coordinate(Integer.parseInt(shapePart[7+k*2]),Integer.parseInt(shapePart[8+k*2])));	
							}
							for(Effect eff : Effect.values())
							{
								if(eff.getElementName().equals(Integer.parseInt(shapePart[6])))
									s.getSpell(i-1).setSpellEffect(eff);
							}
							p.setSpellPage(s);
							ArrayList<PawnEffect> ef = new ArrayList<PawnEffect>();
							for(int k=0;k<Integer.parseInt(pawnPart[4]);k++)
							{
								for(Effect eff : Effect.values())
									if(eff.getElementName().equals(Integer.parseInt(shapePart[6])))
										ef.add(new PawnEffect(eff));
										ef.get(k).setCurrentDuration(Integer.parseInt(pawnPart[2+k*2]));
							}
						}
						newTurnOrder.add(p);
								
					}
					b.setTurnOrder(newTurnOrder);
					this.gameView.displayBoard(b,Integer.parseInt(parts[2]));
					break;
				case "status":
					break;
				case "end":
					parts = receivedFrame.split(" ");
					this.gameView.displayEnd(parts[2]);
					break;
				case "nextTurn":
					parts = receivedFrame.split(" ");
					this.gameView.displayNextTurn(Integer.parseInt(parts[2]));
					break;
				case "spellPageDetails":
					break;
				case "selectForThisPawn":
					break;
				case "spellPages":
					break;
				case "choiceAction":
					break;
				}
				break;
			}
		}
		
	}
	
}
