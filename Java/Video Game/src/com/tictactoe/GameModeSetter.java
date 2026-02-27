package com.tictactoe;

public class GameModeSetter extends GameBuilder{
	
	
	public GameModeSetter() {
		super("Enter gamemode: \"single\" / \"multi\" / \"auto\"");
	}
	
	@Override
	public boolean inputCommand(String input) {
		if(input.equals("single")|input.equals("multi")|input.equals("auto")) {
			gameMode=input;
			return true;
		}
		System.out.println("It has to be single or multi or auto");
		return false;
	}
	
}
