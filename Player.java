package CardsUI;

import java.util.*;


public class Player {
	
	ArrayList<Card> cards = new ArrayList<>();
	
	String name;
	boolean isHuman;
	int total = 0;
	boolean hasAce = false;
	
	public Player(String n, boolean b) {
		name = n; isHuman = b;
	}
	
	
	public String toString() {
		
		String result = name + "\n";
		
		for(Card c: cards) {
			result += c + "  ";
		}
		
		result += "\nTotal: " + total;
		
		if(total <= 11 && hasAce) {
			result += "/" + (total + 10);
		}
		
		if(total >= 22) {
			result += "\nBUST";
		}
		else if(total == 21 || (total == 11 && hasAce)) {
			result += "\nBLACKJACK!!";
		}
		
		return result;
		
	}
	
	public void draw(Card c)
	{
		cards.add(c);
		total += c.getValue();
		if(c.number.equals("A")) {
			hasAce = true;
		}
	}
	
	public int getTotal() {
		return total;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean hasBlackjack() {
		if(total == 21 || (total == 11 && hasAce))
			return true;
		return false;
	}
	
	public int getHandValue() {
		if(hasAce && total <= 11)
			return total + 10;
		return total;
	}
	
	public void reset() {
		cards.clear();
		total = 0;
		hasAce = false;
	}
}
