package com.tictactoe;

public class FirstPlayerSetter extends GameBuilder {

	public FirstPlayerSetter() {
		super("Which player starts: \"first\" / \"second\"");
	}
	
	@Override
	public void printRequestInput() {
		super.printRequestInput();
	}
	
	@Override
	public boolean inputCommand(String input) {
		if(input.equals("first")|input.equals("second")) {
			firstPlayer = input.equals("first")?true:false;
			return true;
		}
		return false;
	}
}