package fr.iutvalence.projet.battleArenaGame.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.view.StatusMessages;
/**
 * class for test Board class
 *
 */
class BoardTest
{

	/**
	 * test of checkMove:
	 * 
	 * checkMove():
	 * check range or out of board
	 */
	@Test
	void testCheckMove()
	{
		Game G = new Game(2,1,15);
		G.getBoard().getTurnOrder().get(1).setPos(new Coordinate(4,4));
		G.getBoard().getTurnOrder().get(0).setPos(new Coordinate(1,1));
		int oldX= 1;
		int oldY = 1; 
		G.getBoard().getTurnOrder().get(0).getPos().getCoordY();
		Coordinate coord = new Coordinate(oldX+1,oldY);
		assertTrue("wrong message done",G.getBoard().checkMove(coord)==StatusMessages.MOVE_DONE);
		assertTrue("wrong mouvment X",G.getBoard().getTurnOrder().get(0).getPos().getCoordX()==oldX+1);
		assertTrue("wrong mouvement y",G.getBoard().getTurnOrder().get(0).getPos().getCoordY()==oldY);
		coord = new Coordinate(14,14);
		assertTrue("wrong messag out of range",G.getBoard().checkMove(coord)==StatusMessages.MOVE_OUT_OF_RANGE);
		assertFalse("gestion de distance",G.getBoard().getTurnOrder().get(0).getPos().getCoordX()==14);
		assertFalse("gestion de distance",G.getBoard().getTurnOrder().get(0).getPos().getCoordY()==14);
		coord = new Coordinate(-1,-1);
		assertTrue("wrong messag out of Board",G.getBoard().checkMove(coord)==StatusMessages.MOVE_OUT_OF_BOARD);
		assertFalse("pas dans le plateau",G.getBoard().getTurnOrder().get(0).getPos().getCoordX()==-1);
		assertFalse("pas dans le plateau",G.getBoard().getTurnOrder().get(0).getPos().getCoordY()==-1);
		
	
	}
	
	/**
	 * applyEffect():
	 * apply the right effect reduce the lifetime of the effects,remove correctly points or hp
	 * 
	 */
	@Test
	void testApplyEffet() //Tested entirely
	{
		Game G = new Game(2,3,15);
		System.out.println(G.getBoard().getTurnOrder().get(0));
		//primary tests
		
		assertTrue("effets Non vide",G.getBoard().getTurnOrder().get(0).getEffect().isEmpty());
		G.getBoard().getTurnOrder().get(0).addEffect(new PawnEffect(Effect.Fire));
		G.getBoard().getTurnOrder().get(0).addEffect(new PawnEffect(Effect.Electricity));
		assertTrue("Full life",G.getBoard().getTurnOrder().get(0).getHealthPoints()==Pawn.DEFAULT_HEALTH_POINTS);
		assertTrue("Full Actions points",G.getBoard().getTurnOrder().get(0).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS);
		G.getBoard().applyEffect();
		assertTrue("??", G.getBoard().getTurnOrder().get(0).getEffect().size()==2);
		assertFalse("Effet vide", G.getBoard().getTurnOrder().get(0).getEffect().isEmpty());
		//insert two test
		assertFalse("Full Actions points", G.getBoard().getTurnOrder().get(0).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS);
		assertTrue("Actions points not decreased", G.getBoard().getTurnOrder().get(0).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS-2);
		G.getBoard().applyEffect();
		assertTrue("Nb points égaux", G.getBoard().getTurnOrder().get(0).getHealthPoints()==Pawn.DEFAULT_HEALTH_POINTS-10);
		G.getBoard().applyEffect();
		assertTrue("HP != 85", G.getBoard().getTurnOrder().get(0).getHealthPoints()==Pawn.DEFAULT_HEALTH_POINTS-15);
		
		G.getBoard().applyEffect();
		assertTrue("HP négatifs", G.getBoard().getTurnOrder().get(0).getHealthPoints()==Pawn.DEFAULT_HEALTH_POINTS-15);

		
		//Update element test
		G = new Game(2,3,15);
		G.getBoard().getTurnOrder().get(0).addEffect(new PawnEffect(Effect.Fire));
		G.getBoard().applyEffect();
		assertFalse("Effect disapears", G.getBoard().getTurnOrder().get(0).getEffect().size()==0);
		G.getBoard().applyEffect();
		assertFalse("Effect disapears", G.getBoard().getTurnOrder().get(0).getEffect().size()==0);
		G.getBoard().applyEffect();

		assertTrue("Effect do dis", G.getBoard().getTurnOrder().get(0).getEffect().size()==0);

		//ICE TEST
		G.getBoard().getTurnOrder().get(0).addEffect(new PawnEffect(Effect.Ice));
		assertTrue("Base de point de vie mauvaise", G.getBoard().getTurnOrder().get(0).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS);
		
		G.getBoard().applyEffect();
		assertTrue("Base de point de vie mauvaise", G.getBoard().getTurnOrder().get(0).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS-2);
		G.getBoard().applyEffect();
		G.getBoard().applyEffect();
		G.getBoard().applyEffect();
		assertTrue("Base de point de vie mauvaise", G.getBoard().getTurnOrder().get(0).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS-6);

		assertTrue("Effets non vide", G.getBoard().getTurnOrder().get(0).getEffect().isEmpty());
		G.getBoard().getTurnOrder().get(0).getEffect().clear();
		G.getBoard().getTurnOrder().get(0).setActionPoints(Pawn.DEFAULT_ACTION_POINTS);;
		G.getBoard().getTurnOrder().get(0).setMovePoints(Pawn.DEFAULT_MOVE_POINTS);
		G.getBoard().getTurnOrder().get(0).addEffect(new PawnEffect(Effect.Stone));
		G.getBoard().applyEffect();

		assertTrue("Echec stone action", G.getBoard().getTurnOrder().get(0).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS-1);
		assertTrue("Echec stone move", G.getBoard().getTurnOrder().get(0).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS-1);
		G.getBoard().applyEffect();
		G.getBoard().applyEffect();
		G.getBoard().applyEffect();
		assertTrue("Echec stone action", G.getBoard().getTurnOrder().get(0).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS-3);
		assertTrue("Echec stone move", G.getBoard().getTurnOrder().get(0).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS-3);
		assertTrue("Non vide", G.getBoard().getTurnOrder().get(0).getEffect().isEmpty());
		
	}
	
	/**
	 * check spell():
	 * check amount of actionPoint,check and set cooldown,check range
	 */
	@Test
	void testCheckSpell()
	{
		Game G = new Game(2,3,15);
		Coordinate coord = new Coordinate(1,1);
		SpellPage page1 = new SpellPage("Namepage1");
		
		Shape fist = Shape.Fist;
		Shape ball = Shape.Ball;
		Shape sword = Shape.Sword;
		
		page1.getSpell(0).setShape(sword);
		page1.getSpell(1).setShape(ball);
		page1.getSpell(2).setShape(fist);
		
		Effect anEffect = Effect.Fire;
		page1.getSpell(0).setSpellEffect(anEffect);
		coord = new Coordinate(1,2);
		G.getBoard().getTurnOrder().get(0).setSpellPage(page1);
		assertTrue("wrong message range",G.getBoard().checkSpell(0,coord)==StatusMessages.SPELL_TARGET_OUT_OF_RANGE);
		assertFalse("out of range",G.getBoard().getTurnOrder().get(0).getSpellPage().getSpell(1).getCurrentCooldown()==G.getBoard().getTurnOrder().get(0).getSpellPage().getSpell(1).getDefaultCooldown());
		
		Coordinate c1 = new Coordinate(G.getBoard().getTurnOrder().get(0).getPos().getCoordX()+1,G.getBoard().getTurnOrder().get(0).getPos().getCoordY());
		G.getBoard().checkSpell(0,c1);
		assertTrue("gestion range",G.getBoard().getTurnOrder().get(0).getSpellPage().getSpell(0).getCurrentCooldown()==G.getBoard().getTurnOrder().get(0).getSpellPage().getSpell(0).getDefaultCooldown());
		
		G.getBoard().checkSpell(0,c1);
		assertTrue("gestion range",G.getBoard().getTurnOrder().get(0).getSpellPage().getSpell(0).getCurrentCooldown()==G.getBoard().getTurnOrder().get(0).getSpellPage().getSpell(0).getDefaultCooldown());
		
		G.getBoard().getTurnOrder().get(0).setActionPoints(0);
		G.getBoard().getTurnOrder().get(0).getSpellPage().getSpell(0).setCurrentCooldown(0);;
		assertTrue("wrong message done",G.getBoard().checkSpell(0,coord)==StatusMessages.NOT_ENOUGH_ACTION_POINT);
		assertTrue("gestion points action",G.getBoard().getTurnOrder().get(0).getActionPoints()==0);
		assertTrue("gestion action-cooldown",G.getBoard().getTurnOrder().get(0).getSpellPage().getSpell(0).getCurrentCooldown()==0);
		
	
	}
	
	/**
	 * nextPawn():
	 * the currentpawn attributes correctly pass to the next one following the order of pawn in turnOrder
	 * pass to 0 if the current pawn was the last one in the turn order
	 * 
	 */
	@Test
	void testNextPawn()
	{
		Game G = new Game(2,3,15);
		Pawn previousPawn = G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex());
		G.getBoard().nextPawn();
		assertFalse("Suivant de P1 = p1", previousPawn == G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()));
		//assertFalse("P1 != P1",  );
		
		assertTrue("Allo", 1==G.getBoard().getTurnOrder().indexOf(G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex())));
		G.getBoard().nextPawn();
		G.getBoard().nextPawn();
		G.getBoard().nextPawn();
		G.getBoard().nextPawn();
		G.getBoard().nextPawn();
		
		
		assertTrue("Allo", 0==G.getBoard().getTurnOrder().indexOf(G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex())));
		//Pawn pLast = G.getBoard().getTurnOrder().get(G.getBoard().getTurnOrder().size()-1);
	}
	
	/**
	 * removeDead():
	 * remove who have 0 health point or less
	 */
	@Test
	void testRemoveDead()
	{
		Game G = new Game(2,3,15);
		//(G.getBoard().getTurnOrder().get(0).setHealthPoints(10);
		System.out.println(G.getBoard().getTurnOrder().size());
		assertTrue("Turn order correct", G.getBoard().getTurnOrder().size()==6);
		G.getBoard().getTurnOrder().get(0).setHealthPoints(0);
		G.getBoard().getTurnOrder().get(1).setHealthPoints(0);
		G.getBoard().getTurnOrder().get(2).setHealthPoints(0);
		G.getBoard().getTurnOrder().get(3).setHealthPoints(0);
		G.getBoard().getTurnOrder().get(4).setHealthPoints(0);
		G.getBoard().getTurnOrder().get(5).setHealthPoints(0);
		G.getBoard().removeDeads();
		
		
		//System.out.println(G.getBoard().getTurnOrder().size());
		assertTrue("Turn order non vide", G.getBoard().getTurnOrder().isEmpty());
		
		G = new Game(2,3,15);
		G.getBoard().getTurnOrder().get(0).setHealthPoints(10);
		assertFalse("Turn order non vide", G.getBoard().getTurnOrder().isEmpty());



	}
	
	/**
	 * board:check board constructor.check if the turnOrder have the right amount of pawn.
	 */
	@Test
	void testBoard() {
		Game G = new Game(2,3,15);
		
		assertTrue("page de sort non vide",G.getBoard().getTurnOrder().get(0).getSpellPage()==null);
		assertTrue("nombre de pion",G.getBoard().getTurnOrder().size()==6);
		assertTrue("valeur nbPawn",G.getBoard().getNbPawn()==3);
	}
	
}
