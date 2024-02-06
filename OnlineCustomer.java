package app;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Name: Nichakul Kongnual
 * Student ID: 6588178
 * Secion: 2
 */


public class OnlineCustomer extends Customer {
	
	// TODO 03: Complete class `OnlineCustomer` that inherits Customer class.
	// 		 `OnlineCustomer`:
	//			Subclass "private" instance variables:
	//			- email 
	//			- zipcode 
	//			- SHIPPING <String, double> table storing shipping fee according to the zipcode
	//			Extra public interfaces
	//        	- public OnlineCustomer(String name, String email, String zipcode)
	//			- public String getEmail()
	// 			- public String getZipCode()
	//			- public double getShippingFee() returns 
	//		   Overrided behaviors
	//			- public String log() will also return email and zipcode in parenthesis
	//				For example: "2: Siripen Pongpaichet (siripen.pon@mahidol.edu, 73170)"
	//			- Public boolean equals()

	
	// ============ Instance Variables ============
	// This map table store the key value pair of zipcode and shipping fee
	private static final Map<String, Double> SHIPPING; 
	static {
		SHIPPING = new HashMap<>();
		SHIPPING.put("73170",  50.0);
		SHIPPING.put("10700",  20.0);
		SHIPPING.put("50230", 210.0);
		SHIPPING.put("83120", 250.0);
		SHIPPING.put("20120", 150.0);
	}
		
	private String email = "";
	private String zipcode = "";
	
	
	// ============================================
	
	
	
	// ============== DO NOT MODIFY ===============
	
	public OnlineCustomer(String name, String email, String zipcode) {
		super(name);
		this.email = email;
		this.zipcode = zipcode;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getZipcode() {
		return zipcode;
	}

	// ============================================
	
	
	// =========== YOUR CODE GOES HERE ===========
	public String log() {
	//return email and zipcode in parenthesis "2: Siripen Pongpaichet (siripen.pon@mahidol.edu, 73170)"	
		//return this.customerID + ": " + this.name + " (" + this.email + ", " + zipcode;
		return super.log() + " (" + getEmail() + ", " + getZipcode() + ")";
	}
	
	public boolean equals(Object obj) {
		 if (obj == null || getClass() != obj.getClass()) {
		        return false;
		 }
		  OnlineCustomer other = (OnlineCustomer) obj;
		   return super.equals(obj) && Objects.equals(email, other.email) && Objects.equals(zipcode, other.zipcode);
	}
	
	/**
	 * Lookup for the shipping fee from the SHIPPING table based on the customer's zipcode,
	 * if the zipcode does't exist, returns 99.00 as a default shipping fee.
	 * @return shipping fee 
	 */
	public double getShippingFee() {
		// TODO 04: Implement getShippingFee() method for this class
		if (!SHIPPING.containsKey(zipcode)) {
		    return 99.0;
		} else {
		    return SHIPPING.get(zipcode);
		}
	}
	
	
	
	
}
