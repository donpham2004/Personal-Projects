package com.tictactoe;

public class RoundSetter extends GameBuilder {
	
	public RoundSetter() {
		super("Enter amount of rounds:");
	}
	
	
	@Override
	public boolean inputCommand(String input) {
		try {
			int number = Integer.parseInt(input);
			if(number<=0) {
				System.out.println("Number has to be positive");
				return false;
			}
			rounds = number;
			return true;
		}catch(NumberFormatException e) {
			System.out.println("Enter a number");
		}
		return false;
	}
}
