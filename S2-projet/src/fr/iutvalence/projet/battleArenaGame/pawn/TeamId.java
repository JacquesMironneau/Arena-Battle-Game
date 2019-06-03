package fr.iutvalence.projet.battleArenaGame.pawn;

/**
 * This represents a Team and allow us to identify every players and every pawns
 * @author pashmi
 *
 */
public class TeamId
{
	/**
	 * The number of the team
	 */
	private int id;
	
	/**
	 * The number of existing team
	 */
	private static int occurences = 0;
	/**
	 * A name associated to a team
	 */
	private String name;
	
	/**
	 * Create a team Id with a defined id and a name linked to it
	 */
	public TeamId()
	{
		this.id = TeamId.occurences;
		this.name = "Joueur_"+this.id;
		
		occurences++;

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
