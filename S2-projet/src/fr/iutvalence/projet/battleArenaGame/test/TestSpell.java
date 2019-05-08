package fr.iutvalence.projet.battleArenaGame.test;

import fr.iutvalence.projet.battleArenaGame.shape.OldShape;
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
		
		OldShape fist = new OldShape("fist");
		OldShape ball = new OldShape("ball");
		OldShape sword = new OldShape("sword");
		
		page1.getSpell1().setShape(sword);
		page1.getSpell2().setShape(ball);
		page1.getSpell3().setShape(fist);
		
		
		page1.getSpell1().setCurrentCooldown(page1.getSpell1().getDefaultCooldown());
		SpellEffect anEffect = SpellEffect.Fire;
		page1.getSpell1().setSpellEffect(anEffect);
		System.out.println(page1.toString());
	}
}
