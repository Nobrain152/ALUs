package ALUs;

import java.sql.SQLNonTransientConnectionException;

import javax.naming.NamingEnumeration;
import javax.print.attribute.standard.RequestingUserName;
import javax.swing.tree.AbstractLayoutCache;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

/**
 * ģ��ALU���������͸���������������
 * @author 151250058_�ƿ���
 *
 */

public class ALU {
	//����ȫ�ֵ�ALU����
	public static ALU aAlu = new ALU(); 
	
	
	//����,ͨ��
	public String Not (String s){
		String ans = "";
		for(int i=0;i<s.length();i++){
			if(s.charAt(i)=='0'){
				ans=ans+"1";
			}else{
				ans=ans+"0";
			}
		}
		return ans;
	}
	//char����
	public char charNot(char ch){
		if(ch=='0'){
			return '1';
		}else{
			return '0';
		}
	}
	//����
	public char And (char s1,char s2){
		if((Integer.parseInt(""+s1)&Integer.parseInt(""+s2))==0){
			return '0';
		}else{
			return '1';
		}
		
	}
	//����
	public char Or (char s1,char s2){
		if((Integer.parseInt(""+s1)|Integer.parseInt(""+s2))==0){
			return '0';
		}else{
			return '1';
		}
	}
	//�����,һλ�������
	public char Xor (char s1,char s2){
		if((Integer.parseInt(""+s1)^Integer.parseInt(""+s2))==0){
			return '0';
		}else{
			return '1';
		}
	}
	
	
	
	/**
	 * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
	 * ����integerRepresentation("9", 8)
	 * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʋ����ʾ�ĳ���
	 * @return number�Ķ����Ʋ����ʾ������Ϊlength
	 */
	//δ����
 	public String integerRepresentation (String number, int length) {
		// TODO YOUR CODE HERE.
		String ans = "";
		String negativeAns = "";
		if(number.charAt(0)=='-'){
			String negative = number.substring(1);
			String text = Integer.toBinaryString(Integer.parseInt(negative));
			
			for(int i=0;i<(length-text.length());i++){
				ans = ans + "0";
			}
			 ans = ans + Integer.toBinaryString(Integer.parseInt(negative));
			
			 for(int i=length-1;i>=0;i--){
				 if(ans.charAt(i)=='1'){
					 for(int m =0;m<i;m++){
						 negativeAns=negativeAns+charNot(ans.charAt(m));
					 }
					 negativeAns=negativeAns+ans.substring(i);
					 break;
				 }
					
			 }
			 ans = negativeAns;
		}else{
			String text = Integer.toBinaryString(Integer.parseInt(number));
			for(int i=0;i<(length-text.length());i++){
				ans = ans + "0";
			}
			 ans = ans + Integer.toBinaryString(Integer.parseInt(number));
		}
		return ans;
	}
	
	/**
	 * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
	 * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
	 * �������Ϊ��0���롣<br/>
	 * ����floatRepresentation("11.375", 8, 11)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
 	//�������δ��ʾ��һ�㶼û���Թ�
	public String floatRepresentation (String number, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String ans = "";
		String index = "";
		//����λ
		if(number.charAt(0)=='-'){
			ans = ans+"1";
			number = number.replace("-","");
		}else{
			ans = ans+"0";
		}
		//����
		String[] spare = new String[2];
		spare = number.split("\\.");
		String overOne = Integer.toBinaryString(Integer.parseInt(spare[0]));
		//С��1
		String lowerOne = null;
		int max = sLength-overOne.length();
		int lower = Integer.parseInt(spare[1]);
		//С������
		for(int i =0;i<max;i++){
			if(lower==0){
				break;
			}
			lower=lower*2;
			if(lower>=Math.pow(10, (double)(spare[1].length()+1))){
				lowerOne=lowerOne+"1";
				lower=(int) (lower-Math.pow(10, (double)(spare[1].length()+1)));
			}else{
				lowerOne=lowerOne+"0";
			}
		}
		//sNumber �����յĵ���
		String sNumber = overOne+lowerOne;
		for(int i =sNumber.length();i<sLength;i++){
			sNumber=sNumber+"0";
		}
		
		//ָ��
		int trueIndex = 0;
		if(spare[0].equals("0")){
			for(int i=0;i<lowerOne.length();i++){
				if(lowerOne.charAt(i)=='0'){
					trueIndex=trueIndex-1;
				}else{
					break;
				}
				}
		}else{
			trueIndex=overOne.length()-1;
			for(int i =0;i<overOne.length();i++){
				if(overOne.charAt(i)=='0'){
					trueIndex=trueIndex-1;
				}else{
					break;
				}
			}
		}
		trueIndex = (int) (trueIndex+Math.pow(2, (double)(eLength-1))-1);
		index= aAlu.integerRepresentation(""+trueIndex, eLength);
		ans  = ans+index+sNumber;
		return ans;
	}
	
	/**
	 * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
	 * ����ieee754("11.375", 32)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
	 * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String ieee754 (String number, int length) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * ��������Ʋ����ʾ����������ֵ��<br/>
	 * ����integerTrueValue("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 */
	public String integerTrueValue (String operand) {
		// TODO YOUR CODE HERE.
		double ans=0;
		String anString="";
		String op=operand;
		if(operand.charAt(0)=='0'){
			for(double i=0;i<operand.length();i++){
				ans=ans+Math.pow(2, i)*Integer.parseInt(""+operand.charAt((int)(operand.length()-i-1)));
			}
		}else{
			op=aAlu.integerSubtraction(operand, "1", operand.length());
			op=aAlu.Not(op);
			for(double i=0;i<operand.length();i++){
				ans=ans+Math.pow(2, i)*Integer.parseInt(""+op.charAt((int)(op.length()-i-1)));
			}
			anString=anString+"-";
		}
		anString=anString+(int)ans;
		return anString;
	}
	
	/**
	 * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
	 * ����floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		
		return null;
	}
	
	/**
	 * ��λȡ��������<br/>
	 * ����negation("00001001")
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @return operand��λȡ���Ľ��
	 */
	//����
	public String negation (String operand) {
		// TODO YOUR CODE HERE.
		char[] Number = new char[operand.length()];
		Number = operand.toCharArray();
		for(int i = 0;i<operand.length();i++){
			if(Number[i]=='0'){
				Number[i]='1';
			}else{
				Number[i]='0';
			}
		}
		String ans = new String(Number); 
		return ans;
	}
	
	/**
	 * ���Ʋ�����<br/>
	 * ����leftShift("00001001", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand����nλ�Ľ��
	 */
	public String leftShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		for(int i=0;i<n;i++){
			operand=operand+"0";
		}
		return operand;
	}
	
	/**
	 * �߼����Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand�߼�����nλ�Ľ��
	 */
	public String logRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		operand="0"+operand;
		return operand;
	}
	
	/**
	 * �������Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand��������nλ�Ľ��
	 */
	public String ariRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		if(operand.charAt(0)=='0'){
			operand="0"+operand;
		}else{
			operand="1"+operand;
		}
		return operand;
	}
	
	/**
	 * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
	 * ����fullAdder('1', '1', '0')
	 * @param x ��������ĳһλ��ȡ0��1
	 * @param y ������ĳһλ��ȡ0��1
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
	 */
	public String fullAdder (char x, char y, char c) {
		// TODO YOUR CODE HERE.
		String ans = "";
		if(Or(Or(And(x, y), And(y, c)), And(x, c))=='0'){
			ans = ans+"0";
		}else{
			ans = ans+"1";
		}
		if(Xor(Xor(x, y), c)=='0'){
			ans = ans +"0";
		}else{
			ans = ans + "1";
		}
		
		return ans;
	}
	
	/**
	 * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
	 * ����claAdder("1001", "0001", '1')
	 * @param operand1 4λ�����Ʊ�ʾ�ı�����
	 * @param operand2 4λ�����Ʊ�ʾ�ļ���
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ�����
	 */
	//δ����
	public String claAdder (String operand1, String operand2, char c) {
		// TODO YOUR CODE HERE.
		//�����And�������Or
		String ans="";
		char x1y1=And(operand1.charAt(3), operand2.charAt(3));
		char x2y2=And(operand1.charAt(2), operand2.charAt(2));
		char x3y3=And(operand1.charAt(1), operand2.charAt(1));
		char x4y4=And(operand1.charAt(0), operand2.charAt(0));
		char x1Andy1=Or(operand1.charAt(3), operand2.charAt(3));
		char x2Andy2=Or(operand1.charAt(2), operand2.charAt(2));
		char x3Andy3=Or(operand1.charAt(1), operand2.charAt(1));
		char x4Andy4=Or(operand1.charAt(0), operand2.charAt(0));
		
		char c1=Or(x1y1, And(x1Andy1, c));
		char c2=Or(Or(x2y2, And(x2Andy2, x1y1)), And(And(x2Andy2, x1Andy1), c));
		char c3=Or(Or(And(x1y1, And(x3Andy3, x2Andy2)), Or(x3y3, And(x2Andy2, x1y1))), And(And(And(x3Andy3, x2Andy2), x1Andy1), c));
		char c4=Or(And(And(And(x4Andy4, x3Andy3), x2Andy2), And(x1Andy1, c)), Or(Or(Or(x4y4, And(x3y3, x4Andy4)), And(And(x4Andy4, x3Andy3), x2y2)), And(And(x4Andy4, x3Andy3), And(x2Andy2, x1y1))));
		char[] cs = {c1,c2,c3,c4};
		ans=ans+c4;
		for(int i=0;i<3;i++){
			ans=ans+fullAdder(operand1.charAt(i), operand2.charAt(i), cs[2-i]).charAt(1);
		}
		ans=ans+fullAdder(operand1.charAt(3),operand2.charAt(3), c).charAt(1);
		
		return ans;
	}
	
	/**
	 * ��һ����ʵ�ֲ�������1�����㡣
	 * ��Ҫ�������š����š�����ŵ�ģ�⣬
	 * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
	 * {@link #claAdder(String, String, char) claAdder}��
	 * {@link #adder(String, String, char, int) adder}��
	 * {@link #integerAddition(String, String, int) integerAddition}������<br/>
	 * ����oneAdder("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
	 */
	public String oneAdder (String operand) {
		// TODO YOUR CODE HERE.
		String anString="";
		if(operand.charAt(operand.length()-1)=='0'){
			anString="0"+operand.substring(0, operand.length()-1)+"1";
		}else{
			
			for(int i=0;i<operand.length();i++){
				if(operand.charAt(operand.length()-1-i)=='0'){
					anString="0"+operand.substring(0,operand.length()-1-i)+Not(operand.substring(operand.length()-1-i));
					break;
				}
				if(i==(operand.length()-1)){
					anString="1"+Not(operand);
				}
			}
		}
		return anString;
	}
	
	/**
	 * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
	 * ����adder("0100", "0011", ��0��, 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param c ���λ��λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		// TODO YOUR CODE HERE.
		String anString="";
		String n1=operand1,n2=operand2;
		//���в�λ
		if(operand1.charAt(0)=='0'){
			for(int i=0;i<(length-n1.length());i++){
				operand1="0"+operand1;
			}
		}else{
			for(int i=0;i<(length-n1.length());i++){
				operand1="1"+operand1;
			}
		}
		if(operand2.charAt(0)=='0'){
			for(int i=0;i<(length-n2.length());i++){
				operand2="0"+operand2;
			}
		}else{
			for(int i=0;i<(length-n2.length());i++){
				operand2="1"+operand2;
			}
		}
		
		//�����4λ��ʼ�ӣ�������ǰŲ������λ��claAdder�õ�
		char cs=c;
		for(int i=4;i<=length;i=i+4){
			if(i==4){
				anString=aAlu.claAdder(operand1.substring(length-i,length-i+4), operand2.substring(length-i,length-i+4), c).substring(1)+anString;
				
			}else{
				anString=aAlu.claAdder(operand1.substring(length-i, length-i+4), operand2.substring(length-i, length-i+4), cs).substring(1)+anString;
			}
			cs=aAlu.claAdder(operand1.substring(length-i,length-i+4), operand2.substring(length-i,length-i+4), cs).charAt(0);
		}
		if(cs=='1'){
			anString="1"+anString;
		}else{
			anString="0"+anString;
		}
		return anString;
	}
	
	/**
	 * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerAddition("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		
		return aAlu.adder(operand1, operand2, '0', length);
	}
	
	/**
	 * �����������ɵ���{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerSubtraction("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		
		String ans="";
		String n2=Not(operand2);
		n2=aAlu.oneAdder(n2).substring(1);
		
		ans=aAlu.adder(operand1,n2,'0',length).substring(1);
		
		char sn=aAlu.adder(operand1,n2,'0',length).charAt(1);
		if(Or(And(And(operand1.charAt(0), n2.charAt(0)), charNot(sn)), And(And(charNot(operand1.charAt(0)), charNot(n2.charAt(0))), sn))=='1'){
			ans="1"+ans;
		}else{
			ans="0"+ans;
		}
		return ans;
		
	}
	
	/**
	 * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
	 * ����integerMultiplication("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
	 */
	//oprand1��y��operand2��x
	public String integerMultiplication (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String n1=operand1+"0",n2=operand2,ans="";
		for(int i=0;i<n1.length();i++){
			
			ans=(2^-1)*(ans+operand2*(n1.charAt(operand1.length()-i)-n1.charAt(operand1.length()-i-1)));
		}
		return null;
	}
	
	/**
	 * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
	 * ����integerDivision("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		
		return null;
	}
	
	/**
	 * �����������ӷ������Ե���{@link #adder(String, String, char, int) adder}�ȷ�����
	 * ������ֱ�ӽ�������ת��Ϊ�����ʹ��{@link #integerAddition(String, String, int) integerAddition}��
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}��ʵ�֡�<br/>
	 * ����signedAddition("1100", "1011", 8)
	 * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
	 * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
	 * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
	 * ����floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
	 * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
	 * ����floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
	 * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
}
