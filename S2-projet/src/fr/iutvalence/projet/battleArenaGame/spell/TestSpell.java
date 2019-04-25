package fr.iutvalence.projet.battleArenaGame.spell;

import fr.iutvalence.projet.battleArenaGame.shape.Shape;
/**
 * test spell package
 * @author jules chapelle
 *
 */
public class TestSpell {
	
	public static void main(String[] args) {
		
		SpellPage page1 = new SpellPage("Namepage1");
		
		Shape fist = new Shape("fist");
		Shape ball = new Shape("ball");
		Shape sword = new Shape("sword");
		
		page1.getSpell1().setShape(sword);
		page1.getSpell2().setShape(ball);
		page1.getSpell3().setShape(fist);
		
		
		page1.getSpell1().setCurrentCooldown(page1.getSpell1().getDefaultCooldown());
		SpellEffect anEffect = SpellEffect.Fire;
		page1.getSpell1().setSpellEffect(anEffect);
		System.out.println(page1.toString());
	}
}
