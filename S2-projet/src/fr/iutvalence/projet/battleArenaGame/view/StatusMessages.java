package fr.iutvalence.projet.battleArenaGame.view;
/**
 * This stands for every error the player can face
 * used to display in the view (HMI)
 * @author pashmi
 *
 */
public enum StatusMessages
{
	MOVE_OUT_OF_RANGE("You don't have enough point to move"),
	MOVE_OUT_OF_BOARD("Your target location is outside the board..."),
	CASE_OCCUPATED("This location is unavailable"),
	MOVE_DONE("Destination reached"),
	
	WRONG_INDEX("You chose a wrong index"),
	NOT_ENOUGH_ACTION_POINT("You need more AP in order to cast this spell !"),
	SPELL_IN_COOLDOWN("This spell is still in cooldown !"),
	SPELL_TARGET_OUT_OF_RANGE("Your aiming too far !"),
	SPELL_SENT("Spell successfully sent"),
	
	PAGE_SET("The spell page has been set"),
	
	SYSTEM_ERROR("Error system");
	
	private String statusMessage;
	
	StatusMessages(String s)
	{
		this.statusMessage = s;
	}
	
	public String getStatusMessage()
	{
		return this.statusMessage;
	}
	
}
