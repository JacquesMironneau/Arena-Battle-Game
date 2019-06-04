package fr.iutvalence.projet.battleArenaGame.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellIndexException;
import fr.iutvalence.projet.battleArenaGame.network.Local;
import fr.iutvalence.projet.battleArenaGame.pawn.TeamId;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.view.PlayerConsole;

class testGame {

	@Test
	void testCreateSpellPage() {
		PlayerConsole P1 = new PlayerConsole();
		Game G1 =new Game(P1);
		G1.createSpellPage();
		assertTrue("page n'existe pas",Game.getSpellPages().size()==1);
		assertFalse("sort is null",Game.getSpellPages().get(0).getSpell(0)==null);
		assertFalse("sort is null",Game.getSpellPages().get(0).getSpell(1)==null);
		assertFalse("sort is null",Game.getSpellPages().get(0).getSpell(2)==null);
		//il fait saisir les bons elements et shapes
		assertTrue("mauvais element",Game.getSpellPages().get(0).getSpell(0).getSpellEffect().getElementName()=="Fire");
		assertTrue("mauvais shape",Game.getSpellPages().get(0).getSpell(0).getShape().equals(Shape.Ball));
		assertTrue("mauvais cooldown",Game.getSpellPages().get(0).getSpell(0).getCurrentCooldown()==0);
		
		//test pour special
		G1.createSpellPage();
		assertTrue("mauvais special",Game.getSpellPages().get(1).getSpell(0).getShape()==Shape.Cross);
		assertTrue("mauvais special",Game.getSpellPages().get(1).getSpell(1).getShape()==Shape.Square);
		assertTrue("mauvais special",Game.getSpellPages().get(1).getSpell(2).getShape()==Shape.Beam);
	}

	@Test
	void testPawnSelection() {
		PlayerConsole P1 = new PlayerConsole();
		Game G1 =new Game(P1);
		G1.setWinnerID(new TeamId(4));
		Board b = new Board(new Local(G1), P1);
		G1.setBoard(b);
		SpellPage p1 = new SpellPage("page1");
		Spell s1 = new Spell();
		Spell s2 = new Spell();
		Spell s3 = new Spell();
		s1.setShape(Shape.Ball);
		s2.setShape(Shape.Fist);
		s3.setShape(Shape.Cross);
		s1.setSpellEffect(SpellEffect.Fire);
		s2.setSpellEffect(SpellEffect.Ice);
		s3.setSpellEffect(SpellEffect.Fire);
		
		try {
			p1.setSpell(0,s1);
		} catch (SpellIndexException e) {
			
			e.printStackTrace();
		}
		try {	
			p1.setSpell(1,s2);
		} catch (SpellIndexException e) {
			
			e.printStackTrace();
		}
		try {
			p1.setSpell(2,s3);
		} catch (SpellIndexException e) {
			
			e.printStackTrace();
		}
		Game.getSpellPages().add(p1);
		G1.launch();
		assertFalse("même ref",b.getTurnOrder().get(0).getSpellPage()==b.getTurnOrder().get(1).getSpellPage());
		assertTrue("mauvais element",b.getTurnOrder().get(0).getSpellPage().getSpell(0).getSpellEffect().getElementName()=="Fire");
		assertTrue("mauvais Shape",b.getTurnOrder().get(0).getSpellPage().getSpell(1).getShape().equals(Shape.Fist));
		assertTrue("mauvais cooldown",b.getTurnOrder().get(0).getSpellPage().getSpell(2).getCurrentCooldown()==0);
	}

	@Test
	public void testEndTurn() {
		PlayerConsole P1 = new PlayerConsole();
		Game G1 =new Game(P1);
		Board b = new Board(new Local(G1), P1);
		G1.setBoard(b);	
		
	}
	
}
