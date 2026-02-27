package com.numberconversion;

public class Main {
	public static void main(String args[]) {
		NumberClass number = new NumberClass("73",BaseNumber.OCTAL);
		number.printNumber();
		number.toHexaDecimal();
		number.printNumber();
	}
}
