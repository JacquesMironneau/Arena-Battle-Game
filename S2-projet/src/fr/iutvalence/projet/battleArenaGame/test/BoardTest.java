package fr.iutvalence.projet.battleArenaGame.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.EndStatus;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.network.Local;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnTeam;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.view.PlayerConsole;

class BoardTest
{

	@Test
	void test()
	{
		fail("Not yet implemented");
	}
	
	@Test
	void testCheckMove()
	{
		PlayerConsole P1 = new PlayerConsole();
		Board b = new Board(new Local(), P1);
		Coordinate coord = new Coordinate(1,1);
		Movement mov =new Movement(Board.getCurrentPawn().getPos(),coord);
		b.checkMove(mov);
		assertTrue("mauvais mouvement X",Board.getCurrentPawn().getPos().getCoordX()==1);
		assertTrue("mauvais mouvement y",Board.getCurrentPawn().getPos().getCoordY()==1);
		coord = new Coordinate(100,100);
		mov =new Movement(Board.getCurrentPawn().getPos(),coord);
		b.checkMove(mov);
		assertFalse("gestion de distance",Board.getCurrentPawn().getPos().getCoordX()==100);
		assertFalse("gestion de distance",Board.getCurrentPawn().getPos().getCoordY()==100);
		coord = new Coordinate(-1,-1);
		mov =new Movement(Board.getCurrentPawn().getPos(),coord);
		b.checkMove(mov);
		assertFalse("pas dans le plateau",Board.getCurrentPawn().getPos().getCoordX()==-1);
		assertFalse("pas dans le plateau",Board.getCurrentPawn().getPos().getCoordY()==-1);
	}
	
	@Test
	void testApplyEffet() //Tested entirely
	{
		
		Board b = new Board(null, null);
		System.out.println(Board.getCurrentPawn());
		//primary tests
		
		assertTrue("effets Non vide", Board.getCurrentPawn().getEffect().isEmpty());
		Board.getCurrentPawn().addEffect(new PawnEffect(SpellEffect.Fire));
		Board.getCurrentPawn().addEffect(new PawnEffect(SpellEffect.Electricity));
		assertTrue("Full life", Board.getCurrentPawn().getHealthPoints()==Pawn.DEFAULT_HEALTH_POINTS);
		assertTrue("Full Actions points", Board.getCurrentPawn().getActionPoints()==Pawn.DEFAULT_ACTION_POINTS);
		b.applyEffect();
		assertTrue("??", Board.getCurrentPawn().getEffect().size()==2);
		assertFalse("Effet vide", Board.getCurrentPawn().getEffect().isEmpty());
		//insert two test
		assertFalse("Full Actions points", Board.getCurrentPawn().getActionPoints()==Pawn.DEFAULT_ACTION_POINTS);
		assertTrue("Actions points not decreased", Board.getCurrentPawn().getActionPoints()==-2);
		b.applyEffect();
		assertTrue("Nb points égaux", Board.getCurrentPawn().getHealthPoints()==0);
		b.applyEffect();
		assertTrue("HP != 0", Board.getCurrentPawn().getHealthPoints()==0);
		b.applyEffect();
		assertTrue("HP négatifs", Board.getCurrentPawn().getHealthPoints()==0);

		
		//Update element test
		Board b4 = new Board(null, null);
		Board.getCurrentPawn().addEffect(new PawnEffect(SpellEffect.Fire));
		b.applyEffect();
		assertFalse("Effect disapears", Board.getCurrentPawn().getEffect().size()==0);
		b.applyEffect();
		assertFalse("Effect disapears", Board.getCurrentPawn().getEffect().size()==0);
		b.applyEffect();

		assertTrue("Effect do dis", Board.getCurrentPawn().getEffect().size()==0);

		//ICE TEST
		Board.getCurrentPawn().addEffect(new PawnEffect(SpellEffect.Ice));
		assertTrue("Base de point de vie mauvaise", Board.getCurrentPawn().getMovePoints()==Pawn.DEFAULT_MOVE_POINTS);
		
		b.applyEffect();
		assertTrue("Base de point de vie mauvaise", Board.getCurrentPawn().getMovePoints()==-2);
		b.applyEffect();
		b.applyEffect();
		assertTrue("Base de point de vie mauvaise", Board.getCurrentPawn().getMovePoints()==-6);

		assertTrue("Effets non vide", Board.getCurrentPawn().getEffect().isEmpty());
		Board.getCurrentPawn().getEffect().clear();
		Board.getCurrentPawn().setActionPoints(Pawn.DEFAULT_ACTION_POINTS);;
		Board.getCurrentPawn().setMovePoints(Pawn.DEFAULT_MOVE_POINTS);
		Board.getCurrentPawn().addEffect(new PawnEffect(SpellEffect.Stone));
		b.applyEffect();

		assertTrue("Echec stone action", Board.getCurrentPawn().getActionPoints()==-1);
		assertTrue("Echec stone move", Board.getCurrentPawn().getMovePoints()==-1);
		b.applyEffect();
		b.applyEffect();
		b.applyEffect();
		assertTrue("Echec stone action", Board.getCurrentPawn().getActionPoints()==-3);
		assertTrue("Echec stone move", Board.getCurrentPawn().getMovePoints()==-3);
		assertTrue("Non vide", Board.getCurrentPawn().getEffect().isEmpty());
		
	}
	
	@Test
	void testCheckSpell()
	{
		PlayerConsole P1 = new PlayerConsole();
		Board b = new Board(new Local(), P1);
		Coordinate coord = new Coordinate(1,1);
SpellPage page1 = new SpellPage("Namepage1");
		
		Shape fist = Shape.Fist;
		Shape ball = Shape.Ball;
		Shape sword = Shape.Sword;
		
		page1.getSpell(0).setShape(sword);
		page1.getSpell(1).setShape(ball);
		page1.getSpell(2).setShape(fist);
		
		SpellEffect anEffect = SpellEffect.Fire;
		page1.getSpell(0).setSpellEffect(anEffect);
		coord = new Coordinate(1,2);
		Board.getCurrentPawn().setSpellPage(page1);
		Movement mov =new Movement(Board.getCurrentPawn().getPos(),coord);
		b.checkSpell(Board.getCurrentPawn().getSpellPage().getSpell(0), mov);
		assertTrue(Board.getCurrentPawn().getSpellPage().getSpell(0).getCurrentCooldown()==Board.getCurrentPawn().getSpellPage().getSpell(0).getDefaultCooldown());

	}
	
	/**
	 * 
	 */
	@Test
	void testNextPawn()
	{

		Board b = new Board(null, null);
		Pawn previousPawn = Board.getCurrentPawn();
		ArrayList<Pawn> list = Board.getTurnOrder();
		Board.nextPawn();
		assertFalse("Suivant de P1 = p1", previousPawn == Board.getCurrentPawn());
		//assertFalse("P1 != P1",  );
		for(Pawn p: list)
		{
			int i = 0;
			Board.nextPawn();
			assertTrue("Allo", Board.getTurnOrder().indexOf(p)==i);
			i++;
		}
		
		//Pawn pLast = Board.getTurnOrder().get(Board.getTurnOrder().size()-1);
	}
	
	@Test
	void testRemoveDead()
	{
		Board b = new Board(null, null);
		//Board.getCurrentPawn().setHealthPoints(10);
		System.out.println(Board.getTurnOrder().size());
		assertTrue("Turn order correct", Board.getTurnOrder().size()==6);
		Board.removeDeads();
		
		
		//System.out.println(Board.getTurnOrder().size());
		assertTrue("Turn order non vide", Board.getTurnOrder().isEmpty());
		
		Board b2 = new Board(null, null);
		Board.getCurrentPawn().setHealthPoints(10);
		assertFalse("Turn order non vide", Board.getTurnOrder().isEmpty());



	}
	
	@Test
	void testGetPawnOnCell()
	{
		Board b1 = new Board(null, null);
		System.out.println(b1.getPawnOnCell(new Coordinate(2,9)));
		assertTrue("Erreur méthode", Board.getCurrentPawn()==b1.getPawnOnCell(new Coordinate(2,0)));
		assertFalse("N'existe pas", b1.getPawnOnCell(new Coordinate(200,200))==null);
	}
	@Test
	void testGetWinTeam()
	{
		Board b1 = new Board(null, null);
		
		assertTrue("Should be running", Board.getWinTeam()==EndStatus.RUNNING);
		
		
		Board.removeDeads();
		assertTrue("Should draw", Board.getWinTeam()==EndStatus.DRAW);
		
		Board b3 = new Board(null, null);
		Board.getCurrentPawn().setHealthPoints(10);
		Board.removeDeads();
		assertTrue("Should victory", Board.getWinTeam()==EndStatus.VICTORY);

		b3 = new Board(null, null);

		Board.nextPawn();
		Board.getCurrentPawn().setHealthPoints(10);
		Board.removeDeads();
		assertTrue("aa", Board.getWinTeam()==EndStatus.DEFEAT);
	}
}
