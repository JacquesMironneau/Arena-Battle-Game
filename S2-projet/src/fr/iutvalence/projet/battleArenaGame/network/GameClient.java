package fr.iutvalence.projet.battleArenaGame.network;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

	public static final String WORD_SEPARATOR ="�";
	public static final String GROUP_SEPARATOR ="&";
	public static final String SENTENCE_SEPARATOR ="#";
	
	private GameView gameView;
	
	private Socket socket;
	
	private BufferedReader reader;
	
	private BufferedWriter writer;
	
	public GameClient(Socket socket, GameView theView)
	{
		this.gameView = theView;
		this.socket = socket;
		try {
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		new Thread(() -> { 
			receive();
		}).start();
	}

	private void send(String msg)
	{
		try {
			writer.append(msg);
			writer.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	/*
	 * Protocol : coding part
	 */
	@Override
	public void moveRequest(int currentPlayerIndex, Coordinate destination)
	{
		String request = "Request"+GameClient.WORD_SEPARATOR+ "move"+GameClient.WORD_SEPARATOR+currentPlayerIndex + GameClient.WORD_SEPARATOR + destination.getCoordX() + GameClient.WORD_SEPARATOR +destination.getCoordY();
		send(request);
	}

	@Override
	public void spellRequest(int currentPlayerIndex, int spellIndex, Coordinate destination)
	{
		String request = "Request"+GameClient.WORD_SEPARATOR+ "spell"+GameClient.WORD_SEPARATOR+currentPlayerIndex+GameClient.WORD_SEPARATOR+spellIndex+GameClient.WORD_SEPARATOR+destination.getCoordX() + GameClient.WORD_SEPARATOR +destination.getCoordY();
		send(request);
	}

	@Override
	public void setPageRequest(int currentPlayerIndex, SpellPage pageToSet)
	{
		String request = "Request" +GameClient.WORD_SEPARATOR+"page"+GameClient.WORD_SEPARATOR+currentPlayerIndex+ GameClient.WORD_SEPARATOR+ pageToSet.getPageName()+ GameClient.WORD_SEPARATOR;
		for(Spell s: pageToSet.getSpell())
		{
			request += s.getShape().getName()+ GameClient.WORD_SEPARATOR + s.getShape().getDamage()+ GameClient.WORD_SEPARATOR+s.getShape().getCooldown()+GameClient.WORD_SEPARATOR+s.getShape().getRange()+ GameClient.WORD_SEPARATOR + s.getShape().getSpellCost()+ GameClient.WORD_SEPARATOR+ s.getShape().getEffectedCoordinates().size()+ GameClient.WORD_SEPARATOR;
			
			for(Coordinate coord: s.getShape().getEffectedCoordinates())
				request += coord.getCoordX()+ GameClient.WORD_SEPARATOR + coord.getCoordY()+ GameClient.WORD_SEPARATOR;
			request += s.getSpellEffect().getEffectName() + GameClient.WORD_SEPARATOR;
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
		
		String request = "Request action "+currentPlayerIndex+GameClient.WORD_SEPARATOR+str;
		send(request);
	}
	
	/*
	 * Decode the receive message (network protocol)
	 */
	public void receive()
	{
		while(true)
		{
			String receivedFrame = null;
			
			try {
				receivedFrame=this.reader.readLine();
			}catch(IOException e)
			{
				e.printStackTrace();
			}

			String[] parts = receivedFrame.split(GameClient.WORD_SEPARATOR);
			switch(parts[0])
			{
			case "Ask":
				switch(parts[1])
				{
				case "actionChoice":
					this.gameView.askActionChoice(Integer.parseInt(parts[2]));
					break;
				case "spell":
					this.gameView.askSpell(Integer.parseInt(parts[2]));
					break;
				case "move":
					this.gameView.askMove(Integer.parseInt(parts[2]));
					break;
				case "spellSelection":
					this.gameView.askPageSelection(Integer.parseInt(parts[2]));
					break;
					
				}
				break;
				
			case "Display":
				switch(parts[1])
				{
				case "board":
					String[] pawnPart = receivedFrame.split(GameClient.SENTENCE_SEPARATOR);
					parts = pawnPart[0].split(GameClient.WORD_SEPARATOR);
					Board b = new Board(Integer.parseInt(parts[2]),Integer.parseInt(parts[4]),Integer.parseInt(parts[3]));
					b.setCurrentPawnIndex(Integer.parseInt(parts[5]));
					ArrayList<Pawn> newTurnOrder = new ArrayList<Pawn>();
					for(int i=1;i<= Integer.parseInt(parts[6]);i++)
					{
						String[] parts2 = pawnPart[i].split(GameClient.WORD_SEPARATOR);
						Pawn p = new Pawn(Integer.parseInt(parts2[0]),new Coordinate(Integer.parseInt(parts2[1]),Integer.parseInt(parts2[2])),parts2[3]);
						p.setHealthPoints(Integer.parseInt(parts2[4]));
						p.setMovePoints(Integer.parseInt(parts2[5]));
						p.setActionPoints(Integer.parseInt(parts2[6]));
						SpellPage s = new SpellPage(parts2[7],null,null,null);
						String[] spellPart = pawnPart[i].split(GameClient.GROUP_SEPARATOR);
						for(int m=1;m<=3;m++)
						{
							parts2 = spellPart[m].split(GameClient.WORD_SEPARATOR);
							HashSet<Coordinate> myCoordinate = new HashSet<Coordinate>();
							for(int k=0;k<Integer.parseInt(parts2[6]);k++)
							{
								myCoordinate.add(new Coordinate(Integer.parseInt(parts2[7+k*2]),Integer.parseInt(parts2[8+k*2])));
							}
							Spell spell = new Spell();
							spell.setShape(new Shape(parts2[0],Integer.parseInt(parts2[1]),Integer.parseInt(parts2[2]),Integer.parseInt(parts2[3]),Integer.parseInt(parts2[4]),myCoordinate));
							spell.setCurrentCooldown(0);
							s.setSpell(m-1,spell);
							for(Effect eff : Effect.values())
							{
								if(eff.getElementName()==parts2[5]);
									s.getSpell(m-1).setSpellEffect(eff);
							}
						}
							parts2 = spellPart[4].split(GameClient.WORD_SEPARATOR);
							p.setSpellPage(s);
							ArrayList<PawnEffect> ef = new ArrayList<PawnEffect>();
							for(int k=0;k<Integer.parseInt(parts2[0]);k++)
							{
								for(Effect eff : Effect.values())
									if(eff.getEffectName()==parts2[2+k*2])
										ef.add(new PawnEffect(eff));
										ef.get(k).setCurrentDuration(Integer.parseInt(parts2[1+k*2]));
							}
						p.getEffect().addAll(ef);
						newTurnOrder.add(p);
					}
					b.setTurnOrder(newTurnOrder);	
					this.gameView.displayBoard(b,Integer.parseInt(parts[2]));
					break;
				case "status":
					for(StatusMessages stat : StatusMessages.values())
						if(stat.name()==parts[2])
							{
								this.gameView.displayStatus(stat);
								break;
							}
					break;
				case "end":
					this.gameView.displayEnd(parts[2]);
					break;
				case "nextTurn":
					this.gameView.displayNextTurn(Integer.parseInt(parts[2]));
					break;
				case "spellPageDetail":
					String[] spellParts = receivedFrame.split(GameClient.GROUP_SEPARATOR);
					SpellPage p = new SpellPage(parts[2],null,null,null);
					for(int i=1;i<=3;i++)
					{
						String[] parts2 = spellParts[i].split(GameClient.WORD_SEPARATOR);
						HashSet<Coordinate> myCoordinate = new HashSet<Coordinate>();
						for(int k=0;k<Integer.parseInt(parts2[6]);k++)
						{
							myCoordinate.add(new Coordinate(Integer.parseInt(parts2[7+k*2]),Integer.parseInt(parts2[8+k*2])));
						}
						Spell spell = new Spell();
						spell.setShape(new Shape(parts2[0],Integer.parseInt(parts2[1]),Integer.parseInt(parts2[2]),Integer.parseInt(parts2[3]),Integer.parseInt(parts2[4]),myCoordinate));
						spell.setCurrentCooldown(0);
						p.setSpell(i-1,spell);
						for(Effect eff : Effect.values())
						{
							if(eff.getElementName()==parts2[5]);
								p.getSpell(i-1).setSpellEffect(eff);
						}
						this.gameView.displaySpellPageDetail(p);
					}
					this.gameView.displaySpellPageDetail(p);
					break;
				case "selectForThisPawn":
					this.gameView.displaySelectForThisPawn(parts[2]);
					break;
				case "spellPages":
					this.gameView.displaySpellPage();
					break;
				case "choiceAction":
					this.gameView.displayChoiceAction();
					break;
				case "MoveDone":
					this.gameView.displayMoveDone();
					break;
				case "moveSlection":
					this.gameView.displayMoveSelection();
				}
				break;
			}
		}
		
	}

	
}
