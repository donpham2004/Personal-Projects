package com.tictactoe;

import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToe {
	
	public static void main(String[] args) throws InterruptedException {
		while(true) {
			Scanner scan = new Scanner(System.in);
			for(GameBuilder setUp:GameBuilder.getSetUps()) {
				do {
					setUp.printRequestInput();
				}while(!setUp.inputCommand(scan.next()));
			}
			ArrayList<AbstractPlayer> players =GameBuilder.getPlayers();
			for(int i=0;i<AbstractPlayer.rounds;i++) {
				AbstractPlayer.startRound();
				while(AbstractPlayer.inGame) {
					players.get(0).play();
					//Thread.sleep(1000);
					players.get(1).play();
					//Thread.sleep(1000);
				}
			}
		}
	}
}
