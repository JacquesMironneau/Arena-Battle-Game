package fr.iutvalence.projet.battleArenaGame.exceptions;

import fr.iutvalence.projet.battleArenaGame.spell.Spell;

/**
 * This exception is raised if the player select a spell that does not exist
 * @author pashmi
 *
 */
public class SpellNotFoundException extends Exception
{

	/**
	 *serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object 
	 */
	private static final long serialVersionUID = 5516668276449081673L;

	public SpellNotFoundException(Spell pSpell)
	{
		super(new String("This spell " + pSpell + "is not owned by this Pawn."));
	}
}
