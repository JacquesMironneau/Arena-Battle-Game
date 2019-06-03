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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeamId other = (TeamId) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

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
