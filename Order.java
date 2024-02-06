package app;
/**
 * Name: Nichakul Kongnual
 * Student ID: 6588178
 */

import java.util.ArrayList;


public class Order implements Loggable{

	// ============ Instance Variables ============
	public static int runningID = 0;

	private int orderID;
	private Customer customer;
	private ArrayList<Item> items;
	private Payment payment;
	// ============================================

    // =============== Constructors ===============

	/**
	 * Constructor to initialize orderID according to the running ID
	 * The first order's ID is 1. The second order's ID is 2, and so on.
	 * @param c : Customer
	 */
	public Order(Customer c) {
		// TODO 17: Implement a constructor method of Order
		runningID++;
		this.orderID = runningID;
		this.customer = c;
		items = new ArrayList<>();
		this.payment = null;		
	}

	// ============================================

	//  ============= DO NOT MODIFY  =============
	
	public int getOrderID() {
		return orderID;
	}

	public Customer getCustomer() {
		return customer;
	}

	public int numItems() {
		return this.items.size();
	}
	
	public Payment getPayment() {
		return this.payment;
	}
	
	public boolean checkPaymentStatus() {
		if(payment != null) {
			return this.payment.isValid();
		} else {
			return false;
		}
	}

	@Override
	public boolean equals(Object object) {
		Order o = (Order) object;
		return this.log().equalsIgnoreCase(o.log());
	}
	
	// ============================================

	/**
	 * Return total price of all items in the `items` array list.
	 * If the customer is an OnlineCustomer, include his shipping fee into the total too.
	 * Note that if there is no order, nothing to ship, the total price will always be zero.
	 * @return
	 */
	public double getTotalPrice() {
		// TODO 18: Implement getTotalPrice() method
		double totalp = 0.00;
		if(items.size()==0){
			return 0;
		}else {
			for(Item it: items) {
				totalp += it.getTotal();}
				if(customer instanceof OnlineCustomer) {
					totalp += ((OnlineCustomer) customer).getShippingFee();
				} return totalp;
				}
	}
	
	
	/**
	 * Return an item in the `items` array list if the `name` exists
	 * otherwise return null
	 *
	 * @param name
	 * @return Item | null
	 */
	public Item findItem(String name) {
		// TODO 19: Implemenent findItem(String name) method
			for(Item it : items){
		     if(it.getName().equals(name)){
		          return it;
		     }
		   } return null;
	}


	/**
	 * Add `newItem` to the `items` array list.
	 * If the name exists, add only the quantity to the existing item, ignoring the price.
	 * Otherwise add `newItem` as an object.
	 *
	 * For example:
	 * items:
	 *  0: Sinovac 1 doses
	 *
	 * Add Sinovac 1 doses
	 * 		-> items:
	 * 			0: Sinovac 2 doses
	 * Add AstraZeneca 1 doses
	 * 		-> items:
	 * 			0: Sinovac 2 doses
	 * 			1: AstraZeneca 1 doses
	 *
	 * @param newItem
	 */
	public void addItem(Item newItem) {
		// TODO 20: Implement addItem(Item newItem) method
		Item it = findItem(newItem.getName());
	    //check
		if (it != null) {
	        it.setQuantity(it.getQuantity() + newItem.getQuantity());
	    } else {
	        //add the newitem
	        items.add(newItem);
	    }
	}
	

	/**
	 * Add an item using `barcode`. See `addItem(Item newItem)`	for the documentation
	 * The `barcode` of items is defined in `ItemFactory`
	 *
	 * @param barcode
	 * @param quantity
	 */
	public void addItem(int barcode, int quantity) {
		// TODO 21: Implement addItem(int barcode, int quantity) method
		// Hint:
		// - Use ItemFactory to create an item
		// - Use the above method addItem(Item newItem) to add the item
		Item it= ItemFactory.createItem(barcode, quantity);
			addItem(it);
	}

	/**
	 * Reduce `reducingItem` to the `items` array list.
	 * If the name exists, reduce the quantity to the existing item.
	 * Otherwise do nothing.
	 *
	 * After the reduction, if the quantity is less than or equal to 0,
	 * remove the item from the list.
	 *
	 *
	 * For example:
	 * items:
	 *  0: Sinovac 2 doses
	 *
	 * Reduce Sinovac 1 doses
	 * 		-> items:
	 * 			0: Sinovac 1 doses
	 * Reduce Sinovac 1 doses
	 * 		-> items: (empty)
	 *
	 * @param reducingItem
	 */
	public void reduceItem(Item reducingItem) {
		// TODO 22: Implement reduceItem(Item reducingItem) method
		for(Item it : items){
			if(it.getName().equals(reducingItem.getName())) {
				it.setQuantity(it.getQuantity()-reducingItem.getQuantity());
					if(it.getQuantity()<= 0){
						items.remove(it);
					} return;
			}
		}
    }

	/**
	 * Reduce item using `barcode`. See `reduceItem(Item reducingItem)` for the documentation.
	 * The `barcode` of items is defined in `ItemFactory`
	 *
	 * @param barcode
	 * @param deductQuantity
	 */
	public void reduceItem(int barcode, int deductQuantity) {
		// TODO 23: Implement reduceItem(int barcode, int deductQuantity) method
		Item reduce = ItemFactory.createItem(barcode, deductQuantity); //create item
		reduceItem(reduce);
	}

	
	/**
	 * Set a payment method for this order
	 * @param args
	 */
	
	public void setPayment(String[] args) {
		// TODO 24: Implement setPayment(String type, Object[] params) method 
		// Hint:
		// - Use PaymentFactory to create an payment object
		// - Use getTotalPrice() to get an total order amount
		
		//assertTrue(order.checkPaymentStatus(), "Valid credit card payment");
	    payment = PaymentFactory.createPayment(getTotalPrice(), args);
	}
	
	
	
	/**
	 * Return a string representation of an order
	 * 
	 * Example:
	 * Customer: 1: Siripen Pongpaichet
	 * - Sinovac\t3000.00\t2 (doses)\t6000.00
	 * - AstraZeneca\t300.00\t1 (doses)\t300.00
	 * Total: 6300.00
	 * [VALID] CASH::6300::7000::700
	 * 
	 * If there is not payment yet, return [PENDING] in the last line
	 * 
	 * Example:
	 * Customer: 1: Siripen Pongpaichet
	 * - Sinovac\t3000.00\t2 (doses)\t6000.00
	 * - AstraZeneca\t300.00\t1 (doses)\t300.00
	 * Total: 6300.00
	 * [PENDING]
	 * 
	 * 
	 * @return String
	 */
	public String log() {
		// TODO 25: Implement log() method for Order class.
		// (siripen.pon@mahidol.ac.th, 73170) //getZipcode() //getEmail() 
//String print = "Customer: " + customer.getCustomerID() + ": " + customer.getName() + "\t" + "(" + OnlineCustomer.getEmail() + ", " + OnlineCustomergetZipcode() + ")"+"\n";
		String print = "Customer: " + customer.log() + "\n";
	    	for(Item it: items){
	    	print += "- " +it.getName() + "\t" + df.format(it.getPrice()) + "\t" + it.getQuantity() + " (units)\t" + df.format(it.getTotal()) + "\n";
	    }
	    	print += String.format("Total: %.2f\n", getTotalPrice());
	    if(payment != null){
	      print += payment.log() ;
	    }else {
	      print += "[PENDING]";
	    }
	    	return print;
		}

	// ============================================
}
