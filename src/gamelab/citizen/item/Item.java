package gamelab.citizen.item;

/** @author Kevin Krol
 * @since Jun 12, 2014 */
public class Item {
	public static final int TREE = 0;
	public static final int FOOD = 1;
	
	private int id;
	
	public Item(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
