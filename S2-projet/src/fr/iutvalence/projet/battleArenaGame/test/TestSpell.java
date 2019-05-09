package fr.iutvalence.projet.battleArenaGame.test;

import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
/**
 * Test Class for a Spell
 * @author jules chapelle
 *
 */
public class TestSpell {
	
	public static void main(String[] args) {
		
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
		System.out.println(page1.toString());
	}
}
