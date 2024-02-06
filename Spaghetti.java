/**
 * Name: Nichakul Kongnual
 * Student ID: 6588178
 * Secion: 2
 */
package app;

public class Spaghetti extends Item{
	
	private String brand;

	public Spaghetti(String name, double price, int quantity) {
		super(name, price, quantity);
	}
	
	public Spaghetti(String name, double price, int quantity,String brand) {
		super(name, price, quantity);
		this.brand = brand;
	}

	public String getType() {
		return brand;
	}

	public void setType(String type) {
		this.brand = type;
	}


}
