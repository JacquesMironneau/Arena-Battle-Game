package fr.iutvalence.projet.battleArenaGame.pawn;

import fr.iutvalence.projet.battleArenaGame.spell.Effect;

public class PawnEffect
{
	public final static int MAX_EFFECT_DURATION = 3;

	private Effect myEffect;
	
	private int currentDuration;
	
	public PawnEffect(Effect effect)
	{
		this.myEffect = effect;
		this.currentDuration = PawnEffect.MAX_EFFECT_DURATION;
	}

	public int getCurrentDuration()
	{
		return currentDuration;
	}

	public void setCurrentDuration(int currentDuration)
	{
		this.currentDuration = currentDuration;
	}

	public Effect getMyEffect()
	{
		return myEffect;
	}
	
	public String getEffectName()
	{
		return myEffect.getEffectName();
	}
	
}
