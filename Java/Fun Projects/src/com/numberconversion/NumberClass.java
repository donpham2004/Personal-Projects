package com.numberconversion;


public class NumberClass {
	private BaseNumber baseNumber;
	private char[] numbers;
	
	public NumberClass(String number,BaseNumber baseNumber) {
		setBaseNumber(baseNumber);
		setNumber(number);
		
	}
	
	public NumberClass(BaseNumber baseNumber, String number) {
		this(number,baseNumber);
	}
	
	public NumberClass(BaseNumber baseNumber) {
		this("0",baseNumber);
	}
	
	public NumberClass(String number) {
		this(number,null);
	}
	
	public NumberClass() {
		this("0",BaseNumber.BASE_TEN);
	}
	
	public void setNumber(String number) {
		if(number.isEmpty())number="0";
		char[] tempNumbers = number.toCharArray();
		for(char tempNumber:tempNumbers) {
			if(baseNumber.getIndexOf(tempNumber)<0) {
				return;
			}
		}
 		this.numbers=tempNumbers.clone();
	}
	
	public char[] getNumber() {
		return numbers;
	}
	
	public BaseNumber getBaseNumber() {
		return baseNumber;
	}

	public void setBaseNumber(BaseNumber baseNumber) {
		this.baseNumber = baseNumber;
	}

	public void printNumber() {
		
		for(char number:numbers) {
			System.out.print(number);
		}
		System.out.println();
	}
	
	public void toBaseTen() {
		if(baseNumber!=BaseNumber.BASE_TEN) {
			long sum=0;
			for(int i=0;i<numbers.length;i++) {
				sum+=baseNumber.getIndexOf(numbers[numbers.length-1-i])
						*Math.pow(baseNumber.getDigitsLength(), i);
			}
			setBaseNumber(BaseNumber.BASE_TEN);
			setNumber(Long.toString(sum));
		}
	}
	
	public void toBase(BaseNumber baseNumber) {
		if(this.baseNumber!=baseNumber) {
			toBaseTen();
			StringBuilder tempString = new StringBuilder();
			for(long integerNumbers=Long.parseLong(new String(numbers));integerNumbers!=0;integerNumbers/=baseNumber.getDigitsLength()) {
				tempString.append(baseNumber.getIndexOf((int) (integerNumbers%baseNumber.getDigitsLength())));
			}
			tempString.reverse();
			setBaseNumber(baseNumber);
			setNumber(tempString.toString());
		}
	}
	
	public void toBinary() {
		toBase(BaseNumber.BINARY);
	}
	
	public void toHexaDecimal() {
		toBase(BaseNumber.HEXADECIMAL);
	}
	
	public void converToOctal() {
		toBase(BaseNumber.OCTAL);
	}
}
