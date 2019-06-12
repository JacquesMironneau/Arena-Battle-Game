package fr.iutvalence.projet.battleArenaGame.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.network.GameClient;
import fr.iutvalence.projet.battleArenaGame.network.GameClientHandler;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.view.GameView;
import fr.iutvalence.projet.battleArenaGame.view.PlayerConsole;
import fr.iutvalence.projet.battleArenaGame.view.StatusMessages;

class TestGameClientHandle {

	private ArrayList<Shape> gameShapes;
	private GameView gameView;

	@Test
	void testReceive() {
		
		ArrayList<GameView> gv = new ArrayList<GameView>();
		gv.add(new PlayerConsole(null));
		PlayerConsole P1 = new PlayerConsole(null);
		Game G = new Game(gv,2,3,15);
		P1.setGameController(G);
		this.gameView = P1;
		gv.get(0).setGameController(G);
		
		
		this.gameShapes = new ArrayList<Shape>();
		//Ball
		HashSet<Coordinate> ballShape = new HashSet<Coordinate>();
		ballShape.addAll(Arrays.asList(new Coordinate(0,0)));
		this.gameShapes.add(new Shape("Ball",10,2,5,3,ballShape));
		//Fist
		HashSet<Coordinate> fistShape = new HashSet<Coordinate>();
		fistShape.addAll(Arrays.asList(new Coordinate(0,0)));
		this.gameShapes.add(new Shape("Fist",15,1,1,2,fistShape));
		//Cross
		HashSet<Coordinate> crossShape = new HashSet<Coordinate>();
		crossShape.addAll(Arrays.asList(new Coordinate(0,0),new Coordinate(-2,0),new Coordinate(-1,0),new Coordinate(1,0),new Coordinate(2,0),new Coordinate(0,-2),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(0,2)));
		this.gameShapes.add(new Shape("Cross",10,3,5,4,crossShape));
		//Square
		HashSet<Coordinate> squareShape = new HashSet<Coordinate>();
		squareShape.addAll(Arrays.asList(new Coordinate(0,0),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(-1,0),new Coordinate(-1,-1),new Coordinate(-1,1),new Coordinate(1,0),new Coordinate(1,-1),new Coordinate(1,1)));
		this.gameShapes.add(new Shape("Square",10,3,4,4,squareShape));
		//Sword
		HashSet<Coordinate> swordShape = new HashSet<Coordinate>();
		swordShape.addAll(Arrays.asList(new Coordinate(-1,-1),new Coordinate(-1,0),new Coordinate(-1,1),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(1,-1),new Coordinate(1,0),new Coordinate(1,1)));
		this.gameShapes.add(new Shape("Sword",8,2,1,3,swordShape));
		//Beam
		HashSet<Coordinate> beamShape = new HashSet<Coordinate>();
		beamShape.addAll(Arrays.asList(new Coordinate(0,1),new Coordinate(0,2),new Coordinate(0,3),new Coordinate(0,4),new Coordinate(0,5)));
		this.gameShapes.add(new Shape("Beam",10,3,1,4,beamShape));
		
		Spell s1 =new Spell();
		s1.setShape(this.gameShapes.get(0));
		Spell s2 =new Spell();
		Spell s3 =new Spell();
		s2.setShape(this.gameShapes.get(1));
		s3.setShape(this.gameShapes.get(2));
		
		SpellPage page1 = new SpellPage("Namepage1",s1,s2,s3);
		Effect anEffect = Effect.Fire;
		page1.getSpell(0).setSpellEffect(anEffect);
		page1.getSpell(1).setSpellEffect(anEffect);
		page1.getSpell(2).setSpellEffect(anEffect);
		G.getBoard().getTurnOrder().get(0).setSpellPage(page1);
		G.getBoard().getTurnOrder().get(1).setSpellPage(page1);
		G.getBoard().getTurnOrder().get(2).setSpellPage(page1);
		G.getBoard().getTurnOrder().get(3).setSpellPage(page1);
		G.getBoard().getTurnOrder().get(4).setSpellPage(page1);
		G.getBoard().getTurnOrder().get(5).setSpellPage(page1);
		G.getBoard().getTurnOrder().get(0).addEffect(new PawnEffect(anEffect));
		G.getBoard().getTurnOrder().get(1).addEffect(new PawnEffect(anEffect));
		G.getBoard().getTurnOrder().get(4).addEffect(new PawnEffect(anEffect));
		Board myBoard=G.getBoard();
		int nbPlayer=2;
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
		String receivedFrame=msg;
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
						System.out.println("un spell");
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
						System.out.println(spellPart[4]);
						p.setSpellPage(s);
						ArrayList<PawnEffect> ef = new ArrayList<PawnEffect>();
						for(int k=0;k<Integer.parseInt(parts2[0]);k++)
						{
							for(Effect eff : Effect.values())
							{
								System.out.println(eff.getEffectName()+" "+parts2[2+k*2]);
								if(eff.getEffectName().equals(parts2[2+k*2]))
								{
									System.out.println("oui");
									ef.add(new PawnEffect(eff));
									ef.get(k).setCurrentDuration(Integer.parseInt(parts2[1+k*2]));
								}
							}
						}
						System.out.println(ef);
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
			case "spellPageDetails":
				String[] spellParts = receivedFrame.split(GameClient.GROUP_SEPARATOR);
				SpellPage p = new SpellPage(parts[2],null,null,null);
				for(int i=1;i<=3;i++)
				{
					String[] parts2 = spellParts[i].split(GameClient.WORD_SEPARATOR);
					HashSet<Coordinate> myCoordinate = new HashSet<Coordinate>();
					for(int k=0;k<Integer.parseInt(parts2[6]);k++)
					{
						myCoordinate.add(new Coordinate(Integer.parseInt(parts2[7+k*2]),Integer.parseInt(parts[8+k*2])));
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
		
		SpellPage pPage=page1;
		msg="Display"+GameClient.WORD_SEPARATOR+"spellPageDetail"+GameClient.WORD_SEPARATOR+pPage.getPageName()+GameClient.WORD_SEPARATOR+GameClient.GROUP_SEPARATOR;
		for(Spell s :pPage.getSpell())
		{
			msg+=s.getShape().getName()+GameClient.WORD_SEPARATOR+s.getShape().getDamage()+GameClient.WORD_SEPARATOR+s.getShape().getCooldown()+GameClient.WORD_SEPARATOR+s.getShape().getRange()+GameClient.WORD_SEPARATOR+s.getShape().getSpellCost()+GameClient.WORD_SEPARATOR+s.getSpellEffect().getEffectName()+GameClient.WORD_SEPARATOR+s.getShape().getEffectedCoordinates().size()+GameClient.WORD_SEPARATOR;
			for(Coordinate coord :s.getShape().getEffectedCoordinates())
			{
				msg+=coord.getCoordX()+GameClient.WORD_SEPARATOR+coord.getCoordY()+GameClient.WORD_SEPARATOR;
			}
		msg+=GameClient.GROUP_SEPARATOR;
		}
		System.out.println(msg);
		receivedFrame = msg;
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
		}
		this.gameView.displaySpellPageDetail(p);
	}
	
	
}
