package fr.iutvalence.projet.battleArenaGame.test;

import fr.iutvalence.projet.battleArenaGame.view.Player;
import fr.iutvalence.projet.battleArenaGame.pawn.*;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;

public class TestPlayer {
	public static void main(String[] args)
	{
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
		
		Coordinate coord1 = new Coordinate(1,1);
		Game game1 = new Game();
		PawnTeam team1 = PawnTeam.PAWN_LOCAL;
		Pawn monPawn = new Pawn(team1,Game.BASE_POS_1PAWN1, page1);
		game1.getPlayer().setPawn(monPawn);
		game1.getPlayer().addSpellPage(page1);
		System.out.println(game1.getPlayer().askMove(coord1));
		
		
		
		
	}
}
