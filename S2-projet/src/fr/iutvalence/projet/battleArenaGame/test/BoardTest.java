package fr.iutvalence.projet.battleArenaGame.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.EndStatus;
import fr.iutvalence.projet.battleArenaGame.Game;
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
	void testCheckMove()
	{
		PlayerConsole P1 = new PlayerConsole();
		Game G1 = new Game(P1);
		Board b = new Board(new Local(G1), P1);
		Coordinate coord = new Coordinate(1,1);
		b.checkMove(coord);
		assertTrue("mauvais mouvement X",b.getTurnOrder().get(b.getCurrentPawnIndex()).getPos().getCoordX()==1);
		assertTrue("mauvais mouvement y",b.getTurnOrder().get(b.getCurrentPawnIndex()).getPos().getCoordY()==1);
		coord = new Coordinate(100,100);
		b.checkMove(coord);
		assertFalse("gestion de distance",b.getTurnOrder().get(b.getCurrentPawnIndex()).getPos().getCoordX()==100);
		assertFalse("gestion de distance",b.getTurnOrder().get(b.getCurrentPawnIndex()).getPos().getCoordY()==100);
		coord = new Coordinate(-1,-1);
		b.checkMove(coord);
		assertFalse("pas dans le plateau",b.getTurnOrder().get(b.getCurrentPawnIndex()).getPos().getCoordX()==-1);
		assertFalse("pas dans le plateau",b.getTurnOrder().get(b.getCurrentPawnIndex()).getPos().getCoordY()==-1);
	}
	
	@Test
	void testApplyEffet() //Tested entirely
	{
		
		Board b = new Board(null, null);
		System.out.println(b.getTurnOrder().get(b.getCurrentPawnIndex()));
		//primary tests
		
		assertTrue("effets Non vide",b.getTurnOrder().get(b.getCurrentPawnIndex()).getEffect().isEmpty());
		b.getTurnOrder().get(b.getCurrentPawnIndex()).addEffect(new PawnEffect(SpellEffect.Fire));
		b.getTurnOrder().get(b.getCurrentPawnIndex()).addEffect(new PawnEffect(SpellEffect.Electricity));
		assertTrue("Full life",b.getTurnOrder().get(b.getCurrentPawnIndex()).getHealthPoints()==Pawn.DEFAULT_HEALTH_POINTS);
		assertTrue("Full Actions points",b.getTurnOrder().get(b.getCurrentPawnIndex()).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS);
		b.applyEffect();
		assertTrue("??", b.getTurnOrder().get(b.getCurrentPawnIndex()).getEffect().size()==2);
		assertFalse("Effet vide", b.getTurnOrder().get(b.getCurrentPawnIndex()).getEffect().isEmpty());
		//insert two test
		assertFalse("Full Actions points", b.getTurnOrder().get(b.getCurrentPawnIndex()).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS);
		assertTrue("Actions points not decreased", b.getTurnOrder().get(b.getCurrentPawnIndex()).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS-2);
		b.applyEffect();
		assertTrue("Nb points égaux", b.getTurnOrder().get(b.getCurrentPawnIndex()).getHealthPoints()==Pawn.DEFAULT_HEALTH_POINTS-10);
		b.applyEffect();
		assertTrue("HP != 85", b.getTurnOrder().get(b.getCurrentPawnIndex()).getHealthPoints()==Pawn.DEFAULT_HEALTH_POINTS-15);
		
		b.applyEffect();
		assertTrue("HP négatifs", b.getTurnOrder().get(b.getCurrentPawnIndex()).getHealthPoints()==Pawn.DEFAULT_HEALTH_POINTS-15);

		
		//Update element test
		b = new Board(null, null);
		b.getTurnOrder().get(b.getCurrentPawnIndex()).addEffect(new PawnEffect(SpellEffect.Fire));
		b.applyEffect();
		assertFalse("Effect disapears", b.getTurnOrder().get(b.getCurrentPawnIndex()).getEffect().size()==0);
		b.applyEffect();
		assertFalse("Effect disapears", b.getTurnOrder().get(b.getCurrentPawnIndex()).getEffect().size()==0);
		b.applyEffect();

		assertTrue("Effect do dis", b.getTurnOrder().get(b.getCurrentPawnIndex()).getEffect().size()==0);

		//ICE TEST
		b.getTurnOrder().get(b.getCurrentPawnIndex()).addEffect(new PawnEffect(SpellEffect.Ice));
		assertTrue("Base de point de vie mauvaise", b.getTurnOrder().get(b.getCurrentPawnIndex()).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS);
		
		b.applyEffect();
		assertTrue("Base de point de vie mauvaise", b.getTurnOrder().get(b.getCurrentPawnIndex()).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS-2);
		b.applyEffect();
		b.applyEffect();
		b.applyEffect();
		assertTrue("Base de point de vie mauvaise", b.getTurnOrder().get(b.getCurrentPawnIndex()).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS-6);

		assertTrue("Effets non vide", b.getTurnOrder().get(b.getCurrentPawnIndex()).getEffect().isEmpty());
		b.getTurnOrder().get(b.getCurrentPawnIndex()).getEffect().clear();
		b.getTurnOrder().get(b.getCurrentPawnIndex()).setActionPoints(Pawn.DEFAULT_ACTION_POINTS);;
		b.getTurnOrder().get(b.getCurrentPawnIndex()).setMovePoints(Pawn.DEFAULT_MOVE_POINTS);
		b.getTurnOrder().get(b.getCurrentPawnIndex()).addEffect(new PawnEffect(SpellEffect.Stone));
		b.applyEffect();

		assertTrue("Echec stone action", b.getTurnOrder().get(b.getCurrentPawnIndex()).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS-1);
		assertTrue("Echec stone move", b.getTurnOrder().get(b.getCurrentPawnIndex()).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS-1);
		b.applyEffect();
		b.applyEffect();
		b.applyEffect();
		assertTrue("Echec stone action", b.getTurnOrder().get(b.getCurrentPawnIndex()).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS-3);
		assertTrue("Echec stone move", b.getTurnOrder().get(b.getCurrentPawnIndex()).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS-3);
		assertTrue("Non vide", b.getTurnOrder().get(b.getCurrentPawnIndex()).getEffect().isEmpty());
		
	}
	
	@Test
	void testCheckSpell()
	{
		PlayerConsole P1 = new PlayerConsole();
		Game G1 = new Game(P1);
		Board b = new Board(new Local(G1), P1);
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
		b.getTurnOrder().get(b.getCurrentPawnIndex()).setSpellPage(page1);
		b.checkSpell(0, coord);
		assertFalse("getion de port�e",b.getTurnOrder().get(b.getCurrentPawnIndex()).getSpellPage().getSpell(0).getCurrentCooldown()==b.getTurnOrder().get(b.getCurrentPawnIndex()).getSpellPage().getSpell(0).getDefaultCooldown());
		//mov = new Movement(new Coordinate(0,0),new Coordinate(1,0));
		Coordinate c1 = new Coordinate(b.getTurnOrder().get(b.getCurrentPawnIndex()).getPos().getCoordX()+1,b.getTurnOrder().get(b.getCurrentPawnIndex()).getPos().getCoordY());
		b.checkSpell(0, c1);
		assertTrue("gestion range",b.getTurnOrder().get(b.getCurrentPawnIndex()).getSpellPage().getSpell(0).getCurrentCooldown()==b.getTurnOrder().get(b.getCurrentPawnIndex()).getSpellPage().getSpell(0).getDefaultCooldown());
		b.checkSpell(0, c1);
		assertTrue("gestion range",b.getTurnOrder().get(b.getCurrentPawnIndex()).getSpellPage().getSpell(0).getCurrentCooldown()==b.getTurnOrder().get(b.getCurrentPawnIndex()).getSpellPage().getSpell(0).getDefaultCooldown());
		b.getTurnOrder().get(b.getCurrentPawnIndex()).setActionPoints(0);
		b.checkSpell(1, c1);
		assertTrue("gestion points action",b.getTurnOrder().get(b.getCurrentPawnIndex()).getActionPoints()==0);
		assertTrue("gestion action-cooldown",b.getTurnOrder().get(b.getCurrentPawnIndex()).getSpellPage().getSpell(1).getCurrentCooldown()==0);
		
	
	}
	
	/**
	 * 
	 */
	@Test
	void testNextPawn()
	{

		Board b = new Board(null, null);
		Pawn previousPawn = b.getTurnOrder().get(b.getCurrentPawnIndex());
		b.nextPawn();
		assertFalse("Suivant de P1 = p1", previousPawn == b.getTurnOrder().get(b.getCurrentPawnIndex()));
		//assertFalse("P1 != P1",  );
		
		assertTrue("Allo", 1==b.getTurnOrder().indexOf(b.getTurnOrder().get(b.getCurrentPawnIndex())));
		b.nextPawn();
		b.nextPawn();
		b.nextPawn();
		b.nextPawn();
		b.nextPawn();
		
		
		assertTrue("Allo", 0==b.getTurnOrder().indexOf(b.getTurnOrder().get(b.getCurrentPawnIndex())));
		//Pawn pLast = b.getTurnOrder().get(b.getTurnOrder().size()-1);
	}
	
	@Test
	void testRemoveDead()
	{
		Board b = new Board(null, null);
		//(b.getTurnOrder().get(b.getCurrentPawnIndex()).setHealthPoints(10);
		System.out.println(b.getTurnOrder().size());
		assertTrue("Turn order correct", b.getTurnOrder().size()==6);
		b.getTurnOrder().get(0).setHealthPoints(0);
		b.getTurnOrder().get(1).setHealthPoints(0);
		b.getTurnOrder().get(2).setHealthPoints(0);
		b.getTurnOrder().get(3).setHealthPoints(0);
		b.getTurnOrder().get(4).setHealthPoints(0);
		b.getTurnOrder().get(5).setHealthPoints(0);
		b.removeDeads();
		
		
		//System.out.println(b.getTurnOrder().size());
		assertTrue("Turn order non vide", b.getTurnOrder().isEmpty());
		
		b = new Board(null, null);
		b.getTurnOrder().get(b.getCurrentPawnIndex()).setHealthPoints(10);
		assertFalse("Turn order non vide", b.getTurnOrder().isEmpty());



	}
	
	@Test
	void testGetWinTeam()
	{
		Board b = new Board(null, null);
		b.getTurnOrder().get(b.getCurrentPawnIndex()).setHealthPoints(10);
		b.removeDeads();
		b.getTurnOrder().get(0).setHealthPoints(0);
		b.getTurnOrder().get(1).setHealthPoints(0);
		b.getTurnOrder().get(2).setHealthPoints(0);
		b.getTurnOrder().get(3).setHealthPoints(0);
		b.getTurnOrder().get(4).setHealthPoints(0);
		b.getTurnOrder().get(5).setHealthPoints(0);
		b.getTurnOrder().get(b.getCurrentPawnIndex()).setHealthPoints(10);
		b.removeDeads();
		assertTrue("Should victory", b.getWinTeam()==EndStatus.VICTORY);

		b = new Board(null, null);
		
		b.nextPawn();
		b.getTurnOrder().get(0).setHealthPoints(0);
		b.getTurnOrder().get(1).setHealthPoints(0);
		b.getTurnOrder().get(2).setHealthPoints(0);
		b.getTurnOrder().get(3).setHealthPoints(0);
		b.getTurnOrder().get(4).setHealthPoints(0);
		b.getTurnOrder().get(5).setHealthPoints(0);
		b.getTurnOrder().get(b.getCurrentPawnIndex()).setHealthPoints(10);
		
		b.removeDeads();
		assertTrue("victoire adverse", b.getWinTeam()==EndStatus.VICTORY);
		
		b = new Board(null, null);
		
		assertTrue("Should be running", b.getWinTeam()==EndStatus.RUNNING);
		
		b.getTurnOrder().get(0).setHealthPoints(0);
		b.getTurnOrder().get(1).setHealthPoints(0);
		b.getTurnOrder().get(2).setHealthPoints(0);
		b.getTurnOrder().get(3).setHealthPoints(0);
		b.getTurnOrder().get(4).setHealthPoints(0);
		b.getTurnOrder().get(5).setHealthPoints(0);
		b.removeDeads();
		assertTrue("Should draw", b.getWinTeam()==EndStatus.DRAW);
		
		
	}
}
