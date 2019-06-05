package fr.iutvalence.projet.battleArenaGame.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellIndexException;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.network.Local;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.view.GameView;
import fr.iutvalence.projet.battleArenaGame.view.PlayerConsole;

class BoardTest
{

	
	@Test
	void testCheckMove()
	{
		PlayerConsole P1 = new PlayerConsole();
		ArrayList<GameView> views = new ArrayList<GameView>();
		Game G = new Game(views,2,3,15);
		int oldX= G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getPos().getCoordX();
		int oldY = G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getPos().getCoordY();
		Coordinate coord = new Coordinate(oldX+1,oldY);
		G.getBoard().checkMove(coord);
		assertTrue("mauvais mouvement X",G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getPos().getCoordX()==oldX+1);
		assertTrue("mauvais mouvement y",G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getPos().getCoordY()==oldY);
		coord = new Coordinate(100,100);
		G.getBoard().checkMove(coord);
		assertFalse("gestion de distance",G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getPos().getCoordX()==100);
		assertFalse("gestion de distance",G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getPos().getCoordY()==100);
		coord = new Coordinate(-1,-1);
		G.getBoard().checkMove(coord);
		assertFalse("pas dans le plateau",G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getPos().getCoordX()==-1);
		assertFalse("pas dans le plateau",G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getPos().getCoordY()==-1);
	}
	
	@Test
	void testApplyEffet() //Tested entirely
	{
		ArrayList<GameView> views = new ArrayList<GameView>();
		Game G = new Game(views,2,3,15);
		System.out.println(G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()));
		//primary tests
		
		assertTrue("effets Non vide",G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getEffect().isEmpty());
		G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).addEffect(new PawnEffect(SpellEffect.Fire));
		G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).addEffect(new PawnEffect(SpellEffect.Electricity));
		assertTrue("Full life",G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getHealthPoints()==Pawn.DEFAULT_HEALTH_POINTS);
		assertTrue("Full Actions points",G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS);
		G.getBoard().applyEffect();
		assertTrue("??", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getEffect().size()==2);
		assertFalse("Effet vide", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getEffect().isEmpty());
		//insert two test
		assertFalse("Full Actions points", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS);
		assertTrue("Actions points not decreased", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS-2);
		G.getBoard().applyEffect();
		assertTrue("Nb points Ã©gaux", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getHealthPoints()==Pawn.DEFAULT_HEALTH_POINTS-10);
		G.getBoard().applyEffect();
		assertTrue("HP != 85", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getHealthPoints()==Pawn.DEFAULT_HEALTH_POINTS-15);
		
		G.getBoard().applyEffect();
		assertTrue("HP nÃ©gatifs", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getHealthPoints()==Pawn.DEFAULT_HEALTH_POINTS-15);

		
		//Update element test
		 views = new ArrayList<GameView>();
		G = new Game(views,2,3,15);
		G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).addEffect(new PawnEffect(SpellEffect.Fire));
		G.getBoard().applyEffect();
		assertFalse("Effect disapears", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getEffect().size()==0);
		G.getBoard().applyEffect();
		assertFalse("Effect disapears", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getEffect().size()==0);
		G.getBoard().applyEffect();

		assertTrue("Effect do dis", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getEffect().size()==0);

		//ICE TEST
		G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).addEffect(new PawnEffect(SpellEffect.Ice));
		assertTrue("Base de point de vie mauvaise", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS);
		
		G.getBoard().applyEffect();
		assertTrue("Base de point de vie mauvaise", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS-2);
		G.getBoard().applyEffect();
		G.getBoard().applyEffect();
		G.getBoard().applyEffect();
		assertTrue("Base de point de vie mauvaise", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS-6);

		assertTrue("Effets non vide", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getEffect().isEmpty());
		G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getEffect().clear();
		G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).setActionPoints(Pawn.DEFAULT_ACTION_POINTS);;
		G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).setMovePoints(Pawn.DEFAULT_MOVE_POINTS);
		G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).addEffect(new PawnEffect(SpellEffect.Stone));
		G.getBoard().applyEffect();

		assertTrue("Echec stone action", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS-1);
		assertTrue("Echec stone move", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS-1);
		G.getBoard().applyEffect();
		G.getBoard().applyEffect();
		G.getBoard().applyEffect();
		assertTrue("Echec stone action", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getActionPoints()==Pawn.DEFAULT_ACTION_POINTS-3);
		assertTrue("Echec stone move", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getMovePoints()==Pawn.DEFAULT_MOVE_POINTS-3);
		assertTrue("Non vide", G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getEffect().isEmpty());
		
	}
	
	@Test
	void testCheckSpell()
	{
		PlayerConsole P1 = new PlayerConsole();
		ArrayList<GameView> views = new ArrayList<GameView>();
		Game G = new Game(views,2,3,15);
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
		G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).setSpellPage(page1);
		G.getBoard().checkSpell(coord);
		assertFalse("getion de portée",G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getSpellPage().getSpell(1).getCurrentCooldown()==G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getSpellPage().getSpell(1).getDefaultCooldown());
		
		Coordinate c1 = new Coordinate(G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getPos().getCoordX()+1,G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getPos().getCoordY());
		G.getBoard().checkSpell(c1);
		assertTrue("gestion range",G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getSpellPage().getSpell(0).getCurrentCooldown()==G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getSpellPage().getSpell(0).getDefaultCooldown());
		
		G.getBoard().checkSpell(c1);
		assertTrue("gestion range",G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getSpellPage().getSpell(0).getCurrentCooldown()==G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getSpellPage().getSpell(0).getDefaultCooldown());
		G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).setActionPoints(0);
		G.getBoard().checkSpell(c1);
		assertTrue("gestion points action",G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getActionPoints()==0);
		assertTrue("gestion action-cooldown",G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).getSpellPage().getSpell(1).getCurrentCooldown()==0);
		
	
	}
	
	/**
	 * 
	 */
	@Test
	void testNextPawn()
	{
		ArrayList<GameView> views = new ArrayList<GameView>();
		Game G = new Game(views,2,3,15);
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
	
	@Test
	void testRemoveDead()
	{
		ArrayList<GameView> views = new ArrayList<GameView>();
		Game G = new Game(views,2,3,15);
		//(G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).setHealthPoints(10);
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
		
		views = new ArrayList<GameView>();
		G = new Game(views,2,3,15);
		G.getBoard().getTurnOrder().get(G.getBoard().getCurrentPawnIndex()).setHealthPoints(10);
		assertFalse("Turn order non vide", G.getBoard().getTurnOrder().isEmpty());



	}
	
	@Test
	void testAreAllPageSet() throws SpellIndexException {
		ArrayList<GameView> views = new ArrayList<GameView>();
		Game G = new Game(views,2,3,15);
		assertFalse("pas de page de sort inisialisé",G.getBoard().areAllPageSet());
		SpellPage p1 = new SpellPage("page1");
		Spell s1 = new Spell();
		Spell s2 = new Spell();
		Spell s3 = new Spell();
		s1.setShape(Shape.Ball);
		s2.setShape(Shape.Fist);
		s3.setShape(Shape.Square);
		s1.setSpellEffect(SpellEffect.Fire);
		s2.setSpellEffect(SpellEffect.Ice);
		s3.setSpellEffect(SpellEffect.Fire);
		
		p1.setSpell(0,s1);
		p1.setSpell(1,s2);
		p1.setSpell(2,s3);
		
		G.getBoard().getTurnOrder().get(0).setSpellPage(p1);
		G.getBoard().getTurnOrder().get(1).setSpellPage(p1);
		G.getBoard().getTurnOrder().get(2).setSpellPage(p1);
		assertFalse("pas toutes les pages de sort inisialisé",G.getBoard().areAllPageSet());
		G.getBoard().getTurnOrder().get(3).setSpellPage(p1);
		G.getBoard().getTurnOrder().get(4).setSpellPage(p1);
		G.getBoard().getTurnOrder().get(5).setSpellPage(p1);
		assertTrue("toutes les pages de sort inisialisé",G.getBoard().areAllPageSet());
	}
	
	@Test
	void testBoard() {
		ArrayList<GameView> views = new ArrayList<GameView>();
		Game G = new Game(views,2,3,15);
		assertTrue("page de sort non vide",G.getBoard().getTurnOrder().get(0).getSpellPage()==null);
		assertTrue("nombre de pion",G.getBoard().getTurnOrder().size()==6);
		assertTrue("valeur nbPawn",G.getBoard().getNbPawn()==3);
	}
	
}
