package CardsUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.*;


public class Main implements ActionListener{
	
	
	static ArrayList<Player> players = new ArrayList<>();
	static ArrayList<Card> deck = new ArrayList<>();
	static int playerIndex = -1;
	static boolean turnStarted = true;
	static ArrayList<JTextArea> textAreas = new ArrayList<>();
	static JTextField eventText = new JTextField("");
	static int leader = 0;
	static ArrayList<Player> winners = new ArrayList<>();

	
	public static void main(String[] args) {
		
		initializeCards(deck);
		
		Player p1 = new Player("Tester", true);
		
		Player p2 = new Player("CPU2", false);
		Player p3 = new Player("CPU3", false);
		Player p4 = new Player("CPU4", false);

		
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);	
		
		for(Player p: players) {
			draw(p, deck, 2);
		}
		
		GridLayout gl = new GridLayout(3, 1);
		JFrame mainFrame = new JFrame();
		mainFrame.setLayout(gl);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel top = new JPanel(new GridLayout(2, 1));
		JTextField title = new JTextField("BlackJack!!");
		title.setHorizontalAlignment(JTextField.CENTER);
		title.setEditable(false);
		top.add(title);
		
		eventText.setHorizontalAlignment(JTextField.CENTER);
		eventText.setEditable(false);
		top.add(eventText);
		
		
		JPanel mid = new JPanel(new GridLayout(1, 4));
		
		JTextArea p1Text = new JTextArea();
		JTextArea p2Text = new JTextArea();
		JTextArea p3Text = new JTextArea();
		JTextArea p4Text = new JTextArea();
		
		textAreas.add(p1Text);
		textAreas.add(p2Text);
		textAreas.add(p3Text);
		textAreas.add(p4Text);
		
		p1Text.setEditable(false);
		p2Text.setEditable(false);
		p3Text.setEditable(false);
		p4Text.setEditable(false);
		
		mid.add(p1Text);
		mid.add(p2Text);
		mid.add(p3Text);
		mid.add(p4Text);
		
		JPanel bottom = new JPanel(new FlowLayout());
		
		JButton hitButton = new JButton("Hit");
		JButton stayButton = new JButton("Stay");
		JButton endButton = new JButton("New Game");
		JButton aiButton = new JButton("AI Move");
		hitButton.setEnabled(false);
		stayButton.setEnabled(false);
		aiButton.setEnabled(false);
			
		bottom.add(hitButton);
		bottom.add(stayButton);
		bottom.add(endButton);
		bottom.add(aiButton);
		
		hitButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	if (hit(players.get(playerIndex))) {
		    		hitButton.setEnabled(false);
		    		stayButton.setEnabled(false);
		    		endButton.setEnabled(true);
		    		aiButton.setEnabled(false);
		    	}
		    }
		});
		
		stayButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        hitButton.setEnabled(false);
		        stayButton.setEnabled(false);
		        endButton.setEnabled(true);
		        aiButton.setEnabled(false);
		        eventText.setText(players.get(playerIndex).getName() + " stays with " + players.get(playerIndex).getHandValue() + "!");
		    }
		});
		
		endButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        if(turnStarted == false) {
		        	if(players.get(playerIndex).getHandValue() == 21) {
		        		eventText.setText(players.get(playerIndex).getName() + " scored a Blackjack!!");
		        	}
		        	else {
		        		if(players.get(playerIndex).isHuman) {
		        			hitButton.setEnabled(true);
			        		stayButton.setEnabled(true);
		        		}
		        		else
		        		{
		        			aiButton.setEnabled(true);
		        		}
		        		
			        	endButton.setEnabled(false);	
		        	}
		        	endButton.setText("End Turn");
		        	turnStarted = true;
		        }
		        else {
		        	endButton.setText("Start Turn");
		        	
		        	turnStarted = false;
		        	
		        	if(playerIndex != -1) {
		        		int handValue = players.get(playerIndex).getHandValue();
		        		if(handValue <= 21) {
		        			if(handValue > leader) {
		        				leader = handValue;
		        				winners.clear();
		        				winners.add(players.get(playerIndex));
		        			}
		        			else if(handValue == leader) {
		        				winners.add(players.get(playerIndex));
		        			}
		        		}
			        	
		        	}
		        	
		        	
		        	
		        	playerIndex++;
		        	if(playerIndex == players.size()) {
		        		String winnersText = "Winner(s): ";
		        		if(winners.isEmpty()) {
		        			winnersText += "None";
		        		}
		        		else {
		        		while(winners.size() > 1) {
		        				winnersText += winners.remove(0).getName() + ", ";
		        			}
		        			winnersText += winners.remove(0).getName();
		        		}
		        		
		        		eventText.setText(winnersText);
		        		
		        		endButton.setText("New Game");
		        		reset();
		        	}
		        	else {
		        		eventText.setText(players.get(playerIndex).getName() + "'s Turn!");
		        		
		        		for(int i = 0; i < players.size(); i++) {
		        		textAreas.get(i).setText(players.get(i).toString());
		        		}
		        	}
		        }
		    }
		});
		
		aiButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	if(hitAI(players.get(playerIndex).getHandValue())) {
		    		hitButton.setEnabled(true);
		    		hitButton.doClick();
		    		hitButton.setEnabled(false);
		    	}
		    	else {
		    		stayButton.setEnabled(true);
		    		stayButton.doClick();
		    		stayButton.setEnabled(false);
		    	}
		    }
		});
		
		mainFrame.setSize(500, 300);

		mainFrame.add(top);		
		mainFrame.add(mid);
		mainFrame.add(bottom);
		
		mainFrame.setVisible(true);
		
		//
		//
		//
		
		
		
				
	}
	
	public static void reset() {
		
		deck.clear();
		
		initializeCards(deck);
		
		for(Player p: players) {
			p.reset();
			draw(p, deck, 2);
		}
		
		leader = 0;
		winners.clear();
		playerIndex = -1;
		turnStarted = true;
	}
	
	public static boolean hit(Player p) {
		Card c = draw(p, deck, 1);
        textAreas.get(playerIndex).setText(p.toString());
        String displayText = p.getName() + " hits and draws " + c + "! ";
        if(p.getHandValue() == 21) {
        	displayText += "Blackjack!!";
        }
        else if(p.getHandValue() >= 21) {
        	displayText += p.getName() + " busts!";
        }
        eventText.setText(displayText);
        
        return p.getHandValue() >= 21;
	}
	
	
	public static Card draw(Player p, ArrayList<Card> d, int count) {
		Card c = null;
		for(int i = 0; i < count; i++) {
			c = d.remove(0);
			p.draw(c);
		}
		return c;
	}
	
	//behavior of computer players
	public static boolean hitAI(int handValue) {
		
		if(handValue < leader)
			return true;
		
		switch (handValue) {
		case 20: return Math.random() < 0.02;
		case 19: return Math.random() < 0.12;
		case 18: return Math.random() < 0.45;
		case 17: return Math.random() < 0.63;
		case 16: return Math.random() < 0.78;
		case 15: return Math.random() < 0.85;
		case 14: return Math.random() < 0.90;
		case 13: return Math.random() < 0.95;
		case 12: return Math.random() < 0.98;
		default: return true;
		}
		
	}
	
	public static void initializeCards(ArrayList<Card> d) {
		
		File file = new File("C:\\Users\\allen\\eclipse-workspace\\CardsUI\\src\\CardsUI\\cards.txt");
	    try{ 
	    	Scanner sc = new Scanner(file);
	    	String currentSuit = "";
	    	boolean isSuit = false;
	    	while (sc.hasNextLine()) {
	    		String line = sc.nextLine();
	    		
	    		if(line.equals("~"))
	    			isSuit = true;
	    		else if(isSuit == true) {
	    			currentSuit = line;
	    			isSuit = false;
	    		}
	    		else {
	    			d.add((int)(Math.random() * d.size()), new Card(currentSuit, line));
	    		}
	    	}
	    	
	    	sc.close();
	    
	    }
	    catch (IOException e) {
	    	System.out.println("Error reading file.");
	    }
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}


