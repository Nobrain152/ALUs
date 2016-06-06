package ALUs;

public class main {
	public static void main(String[] args) {
		ALU aAlu=new ALU();

		//System.out.println(aAlu.adder("0111", "1101", '0', 8));
		//System.out.println(Integer.toBinaryString(4));
		//System.out.println(aAlu.claAdder("0011", "1001", '0'));
		//System.out.println(aAlu.integerTrueValue("1001"));
		//System.out.println(aAlu.leftShift("00111", 2));
		//System.out.println(aAlu.ariRightShift("00101", 2));
		//System.out.println(aAlu.integerMultiplication("1010", "1001",4));
		//System.out.println(aAlu.floatRepresentation("1.8125", 4, 8));
		//System.out.println(aAlu.makeFull("0010", 8));
		//System.out.println(aAlu.adder("1011", "1000", '0', 8));
		System.out.println(aAlu.signedAddition("0010", "0101", 4));
		
	}
}
