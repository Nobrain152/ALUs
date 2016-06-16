package ALUs;

public class main {
	public static void main(String[] args) {
		ALU aAlu=new ALU();
		//System.out.println(aAlu.floatTrueValue("00111111000000000", 8, 8));
		//System.out.println(aAlu.floatTrueValue("00111111001100000", 8, 8));
		//System.out.println(aAlu.floatTrueValue(aAlu.floatAddition("00111111000000000", "00111111001100000", 8, 8, 8).substring(1), 8, 8));
		//System.out.println(aAlu.floatAddition("00111111000000000", "00111111001100000", 8, 8, 8));
		System.out.print(aAlu.floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 4) );
		
		//System.out.println(aAlu.floatTrueValue("00111111010000000", 8, 8));
		//System.out.println(aAlu.floatTrueValue("00111111001100000", 8, 8));
		//System.out.println(aAlu.integerTrueValue("111111110"));
		//System.out.println(aAlu.integerMultiplication("0011", "1110",4));
		//System.out.println(aAlu.floatMultiplication("00111111010000000", "00111111001100000", 8, 8));
		//System.out.println(aAlu.adder("0111", "1101", '0', 8));
		//System.out.println(Integer.toBinaryString(4));
		//System.out.println(aAlu.claAdder("0011", "1001", '0'));
		//System.out.println(aAlu.integerTrueValue("111111111110"));
		//System.out.println(aAlu.leftShift("00111", 2));
		//System.out.println(aAlu.ariRightShift("00101", 2));
		//System.out.println(aAlu.integerMultiplication("1010", "1001",4));
		//System.out.println(aAlu.floatRepresentation("1.8125", 4, 8));
		//System.out.println(aAlu.makeFull("0010", 8));
		//System.out.println(aAlu.adder("0011", "1111", '0', 4));
		//System.out.println(aAlu.signedAddition("0111", "1001", 4));
		//System.out.println(aAlu.floatTrueValue("00111110000000000", 8, 8));
		//System.out.println(aAlu.integerDivision("1001", "0011", 4));
		//System.out.println(aAlu.signedAddition("01111101", "01111110", 8));
		//System.out.println(aAlu.floatMultiplication("00111110111000000", "00111111000000000", 8, 8));
		//System.out.println(aAlu.adder("01111101", "01111110", '0', 8));
		//System.out.println(aAlu.integerSubtraction("01111110", "01111111",12));
	}
}
