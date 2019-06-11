package fr.iutvalence.projet.battleArenaGame.view;
/**
 * This stands for every error the player can face
 * used to display in the view (HMI)
 */
public enum StatusMessages
{
	//For a movement
	MOVE_OUT_OF_RANGE("You don't have enough point to move"),
	MOVE_OUT_OF_BOARD("Your target location is outside the board..."),
	CASE_OCCUPATED("This location is unavailable"),
	MOVE_DONE("Destination reached"),
	
	//To cast a spell
	NOT_ENOUGH_ACTION_POINT("You need more AP in order to cast this spell !"),
	SPELL_IN_COOLDOWN("This spell is still in cooldown !"),
	SPELL_TARGET_OUT_OF_RANGE("Your aiming too far !"),
	SPELL_SENT("Spell successfully sent"),
	
	//To set pawn page
	PAGE_SET("The spell page has been set"),
	
	
	//To choose an action
	MOVE(""),
	LAUNCH_SPELL(""),
	END_TURN(""),
	
	//Other
	SYSTEM_ERROR("Error system"),
	WRONG_INDEX("You chose a wrong index");
	
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
