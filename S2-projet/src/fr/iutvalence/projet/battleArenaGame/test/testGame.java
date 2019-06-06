package fr.iutvalence.projet.battleArenaGame.test;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import fr.iutvalence.projet.battleArenaGame.EndStatus;
import fr.iutvalence.projet.battleArenaGame.Game;

/**
 * test class for game 
 * 
 */

class testGame {

	/**
	 * test endGame():
	 * check if that send the right endStatus according to the pawns alive in the turnOrder. 	 
	 */
	@Test
	public void testEndGame() {
		Game G1 =new Game(2,3,15);
		G1.getBoard().getTurnOrder().get(0).setHealthPoints(0);;
		G1.getBoard().getTurnOrder().get(1).setHealthPoints(0);
		G1.getBoard().getTurnOrder().get(2).setHealthPoints(0);
		G1.getBoard().getTurnOrder().get(3).setHealthPoints(0);
		G1.getBoard().getTurnOrder().get(4).setHealthPoints(0);
		G1.getBoard().getTurnOrder().get(5).setHealthPoints(0);
		G1.getBoard().removeDeads();
		assertTrue("draw",G1.endGame() == EndStatus.DRAW);
	
		G1 =new Game(2,3,15);
		G1.getBoard().getTurnOrder().get(0).setHealthPoints(0);;
		G1.getBoard().getTurnOrder().get(1).setHealthPoints(0);
		G1.getBoard().getTurnOrder().get(2).setHealthPoints(0);
		G1.getBoard().getTurnOrder().get(3).setHealthPoints(0);
		G1.getBoard().getTurnOrder().get(4).setHealthPoints(0);
		G1.getBoard().getTurnOrder().get(5).setHealthPoints(10);
		G1.getBoard().removeDeads();
		assertTrue("draw",G1.endGame() == EndStatus.VICTORY);
		
		G1 =new Game(2,3,15);
		G1.getBoard().getTurnOrder().get(0).setHealthPoints(0);;
		G1.getBoard().getTurnOrder().get(1).setHealthPoints(0);
		G1.getBoard().getTurnOrder().get(2).setHealthPoints(0);
		G1.getBoard().getTurnOrder().get(3).setHealthPoints(0);
		G1.getBoard().getTurnOrder().get(4).setHealthPoints(0);
		G1.getBoard().getTurnOrder().get(5).setHealthPoints(10);
		G1.getBoard().removeDeads();
		assertTrue("draw",G1.endGame() == EndStatus.VICTORY);
		
		G1 =new Game(2,3,15);
		G1.getBoard().getTurnOrder().get(0).setHealthPoints(10);;
		G1.getBoard().getTurnOrder().get(1).setHealthPoints(10);
		G1.getBoard().getTurnOrder().get(2).setHealthPoints(10);
		G1.getBoard().getTurnOrder().get(3).setHealthPoints(10);
		G1.getBoard().getTurnOrder().get(4).setHealthPoints(0);
		G1.getBoard().getTurnOrder().get(5).setHealthPoints(10);
		G1.getBoard().removeDeads();
		assertTrue("draw",G1.endGame() == EndStatus.RUNNING);
	}
	

}