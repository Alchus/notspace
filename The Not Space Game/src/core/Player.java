package core;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1629337701891364437L;
	public static final Player PLAYER1 = new Player(1);
	public static final Player PLAYER2 = new Player(2);
	public static final Player PLAYER3 = new Player(3);
	public static final Player PLAYER4 = new Player(4);
	public static final Player PASSIVE = new Player(0);
	public static final Player HOSTILE = new Player(-1);
	public static final Player MISSILE = new Player(-4);
	public static final Player ITEMS = new Player(-5);
	public static final Player INTERFACE = new Player(-10);

	static Player getPlayer(int index) {
		if (index == 1)
			return PLAYER1;
		if (index == 2)
			return PLAYER2;
		if (index == 3)
			return PLAYER3;
		if (index == 4)
			return PLAYER4;
		throw (new IndexOutOfBoundsException());

	}

	int team = 0;
	public Unit heroUnit = null;

	Player(int id) {
		if (id > 0)
			team = 1;
		if (id == -1)
			team = -1;
	}

	boolean isAlliedTo(Player p) {
		return (team == p.team);
	}

	public boolean isHostileTo(Player p) {
		if ((p.team == -1) && (team > 0))
			return true;
		if ((team == -1) && (p.team > 0))
			return true;
		return false;
	}
	
	ArrayList<Ability> abilities = new ArrayList<Ability>();
	

}