package fr.iutvalence.projet.battleArenaGame.test;
import fr.iutvalence.projet.battleArenaGame.pawn.*;
import fr.iutvalence.projet.battleArenaGame.spell.*;
import fr.iutvalence.projet.battleArenaGame.shape.*;
import fr.iutvalence.projet.battleArenaGame.move.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
/**
 * test class for pawn
 */
class PawnTest
{	
	/**
	 * test for constructor of Pawn
	 */
	@Test
	public final void testPawn()
	{
		SpellPage page1 = new SpellPage("Namepage1");
		
		Shape fist = Shape.Fist;
		Shape ball = Shape.Ball;
		Shape sword = Shape.Sword;
		
		page1.getSpell(0).setShape(sword);
		page1.getSpell(1).setShape(ball);
		page1.getSpell(2).setShape(fist);
		
		
		page1.getSpell(0).setCurrentCooldown(page1.getSpell(0).getDefaultCooldown());
		Effect anEffect = Effect.Fire;
		page1.getSpell(0).setSpellEffect(anEffect);
		Coordinate coord = new Coordinate(1,2);
		Pawn pawn1 = new Pawn(0,coord,"lol");
		assertTrue("wrong id",pawn1.getTeamId() == 0);
	}
	
/**
 * test and addEffect
 * check if effect are Correctly add to a pawn
 */
	@Test
	public final void testAddEffect()
	{
		SpellPage page1 = new SpellPage("Namepage1");
		
		Shape fist = Shape.Fist;
		Shape ball = Shape.Ball;
		Shape sword = Shape.Sword;
		
		page1.getSpell(0).setShape(sword);
		page1.getSpell(1).setShape(ball);
		page1.getSpell(2).setShape(fist);
		
		page1.getSpell(0).setCurrentCooldown(page1.getSpell(0).getDefaultCooldown());
		
		Effect anEffect = Effect.Fire;
		page1.getSpell(0).setSpellEffect(anEffect);
		
		Coordinate coord = new Coordinate(1,2);
		Pawn pawn1 = new Pawn(0,coord,"lol");
		
		PawnEffect eff = new PawnEffect(anEffect);
		
		pawn1.addEffect(eff);
		assertTrue("wrong effect",pawn1.getEffect().get(0) == eff);
		
		
	}
/**
 * check if getEffect return the right effect
 */
	@Test
	public final void testGetEffect()
	{
		SpellPage page1 = new SpellPage("Namepage1");
		
		Shape fist = Shape.Fist;
		Shape ball = Shape.Ball;
		Shape sword = Shape.Sword;
		
		page1.getSpell(0).setShape(sword);
		page1.getSpell(1).setShape(ball);
		page1.getSpell(2).setShape(fist);
		
		page1.getSpell(0).setCurrentCooldown(page1.getSpell(0).getDefaultCooldown());
		
		Effect anEffect = Effect.Fire;
		
		page1.getSpell(0).setSpellEffect(anEffect);
		Coordinate coord = new Coordinate(1,2);
		
		Pawn pawn1 = new Pawn(0,coord,"mypawn");
		
		PawnEffect eff = new PawnEffect(anEffect);
		
		assertTrue("init",pawn1.getEffect().isEmpty());
		
		pawn1.addEffect(eff);
		assertTrue("wrong effect",pawn1.getEffect().get(0) == eff);
		}

	/**
	 * check if update effect reduce the lifetime of effect and remove them if they reach 0
	 */
	@Test
	public final void testUpdateEffect()
	{
		SpellPage page1 = new SpellPage("Namepage1");
		
		Shape fist = Shape.Fist;
		Shape ball = Shape.Ball;
		Shape sword = Shape.Sword;
		
		page1.getSpell(0).setShape(sword);
		page1.getSpell(1).setShape(ball);
		page1.getSpell(2).setShape(fist);
		
		page1.getSpell(0).setCurrentCooldown(page1.getSpell(0).getDefaultCooldown());
		
		Effect anEffect = Effect.Fire;
		
		page1.getSpell(0).setSpellEffect(anEffect);
		Coordinate coord = new Coordinate(1,2);
		Pawn pawn1 = new Pawn(0,coord,"lol");
		PawnEffect eff = new PawnEffect(anEffect);
		pawn1.addEffect(eff);
		pawn1.updateEffect();
		assertTrue("lifetime",pawn1.getEffect().get(0).getCurrentDuration()==2);
		pawn1.updateEffect();
		pawn1.updateEffect();
		assertTrue("is empty",pawn1.getEffect().isEmpty());
		
	}
	
}

