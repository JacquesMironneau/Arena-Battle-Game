package fr.iutvalence.projet.battleArenaGame.exceptions;

import fr.iutvalence.projet.battleArenaGame.spell.Spell;

/**
 * This exception is raised if the player select a spell that does not exist
 * @author pashmi
 *
 */
public class SpellNotFoundException extends Exception
{

	public SpellNotFoundException(Spell pSpell)
	{
		super(new String("This spell " + pSpell + "is not owned by this Pawn."));
	}
}
