package com.morsecodetranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
	private static final String WHITE_SPACE="\s";
	private LinkedList<String> strings = new LinkedList<String>();
	
	public String toMorse(String input) {
		StringBuilder line = new StringBuilder();
		String[] strings = input.split("\\"+WHITE_SPACE+"+");
		for(String string:strings) {
			for(char character:string.toCharArray()) {
				for(DotDash dotDash:MorseCharacters.getMorseCharacters(character).getDotDashs()) {
					line.append(dotDash.toString());
				}
				line.append(WHITE_SPACE);
			}
			line.append(WHITE_SPACE);
		}
		return line.toString().trim();
	}
	
	public String toEnglish(String input) {
		StringBuilder line = new StringBuilder();
		String[] strings = input.split("\\"+WHITE_SPACE+"+"+"\\"+WHITE_SPACE+"+");
		for(String string:strings) {
			for(String characters:string.split(WHITE_SPACE)) {
				DotDash[] dotDashs = new DotDash[characters.length()];
				for(int i=0;i<dotDashs.length;i++) {
					dotDashs[i]=DotDash.toDotDashs(characters.toCharArray()[i]);
				}
				line.append(MorseCharacters.getMorseCharacters(dotDashs));
			}
			line.append(WHITE_SPACE);
		}
		return line.toString().trim();
	}
	
	
	
	
	public static void main(String[] args) throws IOException {
		Main main = new Main();
		BufferedReader br = new BufferedReader(new FileReader("/Users/donpham/Desktop/main.text"));
		for(String str = br.readLine();str!=null;str=br.readLine()) {
			System.out.println(str);
			main.strings.add(main.toEnglish(str));
		}
		System.out.println();
		for(String string:main.strings) {
			System.out.println(string);
		}
	}
}
