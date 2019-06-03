package fr.iutvalence.projet.battleArenaGame.pawn;
import java.io.Serializable;

/**
 * This represents a Team and allow us to identify every players and every pawns
 * @author pashmi
 *
 */
public class TeamId implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The number of the team
	 */
	private int id;
	
	/**
	 * A name associated to a team
	 */
	private String name;
	
	/**
	 * Create a team Id with a defined id and a name linked to it
	 */
	public TeamId(int pId)
	{
		this.id = pId;
		this.name = "Joueur_"+this.id;

	}
	
	public int getId()
	{
		return this.id;
	}

	public String toString()
	{
		return "TeamId [name=" + name + "]";
	}

}
