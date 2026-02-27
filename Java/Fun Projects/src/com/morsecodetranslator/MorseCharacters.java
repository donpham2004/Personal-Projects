package com.morsecodetranslator;

import java.util.ArrayList;
import java.util.Arrays;


enum DotDash {
	DOT(0,"."),DASH(1,"-");
	private int dotDashValue;
	private String dotDashString;
	
	private DotDash(int dotDashValue,String dotDashString) {
		this.dotDashValue=dotDashValue;
		this.dotDashString=dotDashString;
	}
	
	public static DotDash getDotDash(int dotDashValue) {
		for(DotDash dotDash:DotDash.values()) {
			if(dotDash.dotDashValue==dotDashValue) return dotDash;
		}
		return null;
	}
	
	public static DotDash toDotDashs(char input) {
		for(DotDash dotDash:DotDash.values()) {
			if(dotDash.dotDashString.equals(Character.toString(input))) return dotDash;
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		return dotDashString;
	}
}


public enum MorseCharacters {
	NULL('\n'),
	
	//LETTERS
	A('A',0,1)		,B('B',1,0,0,0)		,C('C',1,0,1,0)		,D('D',1,0,0),
	E('E',0)		,F('F',0,0,1,0)		,G('G',1,1,0)		,H('H',0,0,0,0),
	I('I',0,0)		,J('J',0,1,1,1)		,K('K',1,0,1)		,L('L',0,1,0,0),
	M('M',1,1)		,N('N',1,0)			,O('O',1,1,1)		,P('P',0,1,1,0),
	Q('Q',1,1,0,1)	,R('R',0,1,0)		,S('S',0,0,0)		,T('T',1),
	U('U',0,0,1)	,V('V',0,0,0,1)		,W('W',0,1,1)		,X('X',1,0,0,1),
	Y('Y',1,0,1,1)	,Z('Z',1,1,0,0), 
	
	//NUMBERS
	ONE('1',0,1,1,1,1),		TWO('2',0,0,1,1,1),		THREE('3',0,0,0,1,1),
	FOUR('4',0,0,0,0,1),	FIVE('5',0,0,0,0,0),	SIX('6',1,0,0,0,0),
	SEVEN('7',1,1,0,0,0),	EIGHT('8',1,1,1,0,0),	NINE('9',1,1,1,1,0),
	ZERO('0',1,1,1,1,1),
	
	//PUNCTUATIONS
	PERIOD('.',0,1,0,1,0,1),		COMMA(',',1,1,0,0,1,1),				QUESTION('?',0,0,1,1,0,0),
	APOSTROPHE('\'',0,1,1,1,1,0),	SLASH('/',1,0,0,1,0),				EXCLAMATION('!',1,0,1,0,1,1),
	OPEN_PARANTHESIS('(',1,0,1,1,0),CLOSE_PARANTHESIS('(',1,0,1,1,0,1),	AMPERSAND('&',0,1,0,0,0),
	COLON(':',1,1,1,0,0,0),			SEMICOLON(';',1,0,1,0,1,0),			EQUALS('=',1,0,0,0,1),
	PLUS('+',0,1,0,1,0),			HYPHEN('-',1,0,0,0,0,1),			UNDERSCORE('_',0,0,1,1,0,1),
	QUOTATION('"',0,1,0,0,1,0),		DOLLAR('$',0,0,0,1,0,0,1),			AT('@',0,1,1,0,1,0);
	private DotDash[] dotDashs;
	private char charValue;
	private MorseCharacters(char charValue,int...dotDashs) {
		this.dotDashs=new DotDash[dotDashs.length];
		this.charValue=charValue;
		addDotDash(dotDashs);
	}
	
	public void addDotDash(int[] dotDashs) {
		for(int i=0;i<dotDashs.length;i++) {
			this.dotDashs[i]=DotDash.getDotDash(dotDashs[i]);
		}
	}
	
	public static MorseCharacters getMorseCharacters(DotDash[] dotDashs) {
		for(MorseCharacters character:MorseCharacters.values()) {
			if(Arrays.asList(character.dotDashs).equals(Arrays.asList(dotDashs))) return character;
		}
		return NULL;
	}
	
	public static MorseCharacters getMorseCharacters(char charValue) {
		for(MorseCharacters character:MorseCharacters.values()) {
			if(character.toString().equalsIgnoreCase(Character.toString(charValue))) return character;
		}
		if((int)charValue==8217) return APOSTROPHE;
		return NULL;
	}
	
	public DotDash[] getDotDashs() {
		return dotDashs;
	}
	
	@Override
	public String toString() {
		
		return Character.toString(charValue);
	}
}
