package fr.iutvalence.projet.battleArenaGame.test;
import fr.iutvalence.projet.battleArenaGame.pawn.*;
import fr.iutvalence.projet.battleArenaGame.spell.*;
import fr.iutvalence.projet.battleArenaGame.shape.*;
import fr.iutvalence.projet.battleArenaGame.move.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class PawnTest
{	
	
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
		SpellEffect anEffect = SpellEffect.Fire;
		page1.getSpell(0).setSpellEffect(anEffect);
		Coordinate coord = new Coordinate(1,2);
		Pawn pawn1 = new Pawn(new TeamId(0),coord,"lol");
		assertEquals(true,pawn1.getTeamId().getId() == 0);
	}
	

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
		
		SpellEffect anEffect = SpellEffect.Fire;
		page1.getSpell(0).setSpellEffect(anEffect);
		
		Coordinate coord = new Coordinate(1,2);
		Pawn pawn1 = new Pawn(new TeamId(0),coord,"lol");
		
		PawnEffect eff = new PawnEffect(anEffect);
		ArrayList<PawnEffect> theEffects = new ArrayList<PawnEffect>();
		theEffects.add(eff);
		
		pawn1.addEffect(eff);
		
		assertEquals(true,pawn1.getEffect().get(0) == eff);
		
	}

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
		
		SpellEffect anEffect = SpellEffect.Fire;
		
		page1.getSpell(0).setSpellEffect(anEffect);
		Coordinate coord = new Coordinate(1,2);
		
		Pawn pawn1 = new Pawn(new TeamId(0),coord,"lol");
		
		PawnEffect eff = new PawnEffect(anEffect);
		
		assertEquals(true,pawn1.getEffect().isEmpty());
		
		pawn1.addEffect(eff);
		assertEquals(true,pawn1.getEffect().get(0) == eff);
		
		
		
	}

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
		
		SpellEffect anEffect = SpellEffect.Fire;
		
		page1.getSpell(0).setSpellEffect(anEffect);
		Coordinate coord = new Coordinate(1,2);
		Pawn pawn1 = new Pawn(new TeamId(0),coord,"lol");
		PawnEffect eff = new PawnEffect(anEffect);
		pawn1.addEffect(eff);
		pawn1.updateEffect();
		assertEquals(true,pawn1.getEffect().get(0).getCurrentDuration()==2);
		pawn1.updateEffect();
		pawn1.updateEffect();
		assertEquals(true,pawn1.getEffect().isEmpty());
		
	}
	
}

