package com.tictactoe;

public class NameSetter extends GameBuilder {
	
	public NameSetter() {
		super("Enter the %s Player's name");
	}
	
	@Override
	public void printRequestInput() {
		requestInput=requestInput.replace("%s", name1==null?"First":"Second");
		super.printRequestInput();
		requestInput=requestInput.replace("First","%s");
		requestInput=requestInput.replace("Second","%s");
		
	}
	
	@Override
	public boolean inputCommand(String input) {
		if(name1==null) {
			name1=input;
			return false;
		}else {
			name2=input;
			isDone=true;
			return true;
		}
	}
}
