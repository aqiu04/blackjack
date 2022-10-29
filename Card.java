package CardsUI;

public class Card {
	
	String suit;
	String number;
	
	public Card(String s, String n){
		suit = s;
		number = n;
	}

	
	
	public String toString() {
		return number + suit;
	}
	
	public int getValue() {
		switch(number) {
		case "A": 	return 1;
		case "2": 	return 2;
		case "3": 	return 3;
		case "4": 	return 4;
		case "5": 	return 5;
		case "6": 	return 6;
		case "7": 	return 7;
		case "8": 	return 8;
		case "9": 	return 9;
		case "10": 	return 10;
		case "J": 	return 10;
		case "Q": 	return 10;
		case "K": 	return 10;
		}
		
		//should not happen assuming everything initializes properly
		return -1;
	}
	
}
