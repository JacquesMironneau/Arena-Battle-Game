package fr.iutvalence.projet.battleArenaGame.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.view.PlayerConsole;

class testGame {

	@Test
	void testLaunch() {
		fail("Not yet implemented");
	}

	@Test
	void testPlay() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateSpellPage() {
	PlayerConsole P1 = new PlayerConsole();
	Game G1 =new Game(P1);
	G1.createSpellPage();
	assertTrue("page n'existe pas",Game.getSpellPages().size()==1);
	assertFalse("sort is null",Game.getSpellPages().get(0).getSpell(0)==null);
	assertFalse("sort is null",Game.getSpellPages().get(0).getSpell(1)==null);
	assertFalse("sort is null",Game.getSpellPages().get(0).getSpell(2)==null);
	
	}

	@Test
	void testPageSelection() {
		fail("Not yet implemented");
	}

}
