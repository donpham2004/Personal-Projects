package com.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GameBuilder {
	protected String requestInput;
	protected static String gameMode,name1,name2;
	protected static boolean firstPlayer;
	protected static int rounds;
	protected static boolean isDone=false;
	protected static ArrayList<GameBuilder> setUps = new 
			 ArrayList<GameBuilder>(Arrays.asList(
	new GameModeSetter(),
	new RoundSetter(),
	new FirstPlayerSetter(),
	new NameSetter()));
	
	public abstract boolean inputCommand(String input);
	
	public GameBuilder(String requestInput) {
		setRequestInput(requestInput);
	}
	
	public void printRequestInput() {
		System.out.println(requestInput);
	}
	
	public static ArrayList<GameBuilder> getSetUps() {
		gameMode=name1=name2=null;
		return setUps;
	}
	
	public static ArrayList<AbstractPlayer> getPlayers() {
		if(isDone) {
			AbstractPlayer.rounds=rounds;
			AbstractPlayer.inGame=true;
			AbstractPlayer.counter=1;
			if(gameMode.equals("multi")) {
				return new ArrayList<AbstractPlayer>(Arrays.asList(
						new RealPlayer(PlayerPieces.getPlayerPiece(firstPlayer).getPiece(),firstPlayer,name1),
						new RealPlayer(PlayerPieces.getPlayerPiece(!firstPlayer).getPiece(),!firstPlayer,name2)));
			}else if(gameMode.equals("single")){
				return new ArrayList<AbstractPlayer>(Arrays.asList(
						new RealPlayer(PlayerPieces.getPlayerPiece(firstPlayer).getPiece(),firstPlayer,name1),
						new ComputerPlayer(PlayerPieces.getPlayerPiece(!firstPlayer).getPiece(),!firstPlayer,name2)));
			}else if(gameMode.equals("auto")){
				return new ArrayList<AbstractPlayer>(Arrays.asList(
						new ComputerPlayer(PlayerPieces.getPlayerPiece(firstPlayer).getPiece(),firstPlayer,name1),
						new ComputerPlayer(PlayerPieces.getPlayerPiece(!firstPlayer).getPiece(),!firstPlayer,name2)));
			}
		}
		throw new IllegalArgumentException();
	}
	
	protected void setRequestInput(String requestInput) {
		this.requestInput=requestInput;
	}
}