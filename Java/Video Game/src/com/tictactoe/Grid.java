package com.tictactoe;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Grid implements Cloneable{
	private String grid[];
	private int emptyLength=9;
	
	public Grid() {
		grid = new String[9];
		for(int i=0;i<9;i++)grid[i]=" ";
		
	}
	
	public void removePiece(int position) {
		grid[position]=" ";
	}
	
	public boolean putPiece(int position,String piece) {
		if(position>=grid.length|position<0) {
			System.out.println("Out of Bounds");
		}else if(!(grid[position].equals(" "))) {
			System.out.println("Already taken");
		}else {
			grid[position]=piece;
			emptyLength--;
			return true;
		}
		return false;
	}
	
	public Grid getClone() {
		try {
			return (Grid) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int calcPoint(String piece) {
		int point=0;
		for(int i=0;i<7;i=i+3) {
			if(grid[i].equals(grid[i+1])&&grid[i+1].equals(grid[i+2])) {
				if(grid[i].equals(piece)) point= 1000;
				else if(!grid[i].equals(" ")) point= -1000;
			}
		}
		for(int i=0;i<3;i++) {
			if(grid[i].equals(grid[i+3])&&grid[i+3].equals(grid[i+6])) {
				if(grid[i].equals(piece)) point= 1000;
				else if(!grid[i].equals(" ")) point= -1000;
			}
		}
		for(int i=2;i<5;i=i+2) {
			if(grid[4].equals(grid[4-i])&&grid[4-i].equals(grid[4+i])) {
				if(grid[i].equals(piece)) point= 1000;
				else if(!grid[i].equals(" ")) point= -1000;
			}
		}
		return point;
	}
	
	public int getEmptyLength() {
		return emptyLength;
	}

	public int[] getEmptySpace() {
		
		int[] emptySpaces= new int[9];
		int k=0;
		for(int i=0;i<9;i++) {
			if(grid[i].equals(" ")) emptySpaces[k++]=i;
		}
		return Arrays.copyOfRange(emptySpaces, 0, k);
	}
	
	public void printGrid() {
		for(int i=0;i<3;i++) {
			System.out.printf("| %s | %s | %s |\t\t",grid[i*3],grid[i*3+1],grid[i*3+2]);
			System.out.printf("| %d | %d | %d |\n",i*3,i*3+1,i*3+2);
		}
	}  
}
