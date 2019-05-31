package fr.iutvalence.projet.battleArenaGame.view;
/**
 * This stands for every error the player can face
 * used to display in the view (HMI)
 * @author pashmi
 *
 */
public enum ErrorMessages
{
	MOVE_OUT_OF_RANGE("You don't have enough point to move"),
	MOVE_OUT_OF_BOARD("Your target location is outside the board..."),
	NOT_ENOUGH_MOVE_POINT("You need more MP in order to move to this location !"),
	CASE_OCCUPATED("This location is unavailable"),
	
	NOT_ENOUGH_ACTION_POINT("You need more AP in order to cast this spell !"),
	SPELL_IN_COOLDOWN("This spell is still in cooldown !"),
	SPELL_TARGET_OUT_OF_RANGE("Your aiming too far !"),
	
	PAWN_NO_TEAM("This pawn is not remote or local..."),
	SYSTEM_ERROR("Error system");
	
	private String errMessage;
	
	ErrorMessages(String s)
	{
		this.errMessage = s;
	}
	
	public String getErrorMessage()
	{
		return this.errMessage;
	}
	
}
