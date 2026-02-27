package com.numberconversion;

public enum BaseNumber {
	BASE_TEN('0','1','2','3','4','5','6','7','8','9'),
	BINARY('0','1'),
	HEXADECIMAL('0','1','2','3','4','5','6','7','8','9'
			,'A','B','C','D','E','F'), OCTAL('0','1','2','3','4','5','6','7');
	private char digits[];
	private BaseNumber(char... digits) {
		this.digits=digits.clone();
	}
	
	public char getIndexOf(int index) {
		return digits[index];
	}
	
	public int getIndexOf(char element) {
		for(int i=0;i<digits.length;i++) {
			if(digits[i]==element) {
				return i;
			}
		}
		return -1;
	}
	
	public int getDigitsLength() {
		return digits.length;
	}
}
