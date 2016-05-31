package ALUs;

import java.sql.SQLNonTransientConnectionException;

import javax.naming.NamingEnumeration;
import javax.print.attribute.standard.RequestingUserName;
import javax.swing.tree.AbstractLayoutCache;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

/**
 * 模拟ALU进行整数和浮点数的四则运算
 * @author 151250058_黄凯文
 *
 */

public class ALU {
	//创建全局的ALU对象
	public static ALU aAlu = new ALU(); 
	
	
	//非门,通过
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
	//char非门
	public char charNot(char ch){
		if(ch=='0'){
			return '1';
		}else{
			return '0';
		}
	}
	//与门
	public char And (char s1,char s2){
		if((Integer.parseInt(""+s1)&Integer.parseInt(""+s2))==0){
			return '0';
		}else{
			return '1';
		}
		
	}
	//或门
	public char Or (char s1,char s2){
		if((Integer.parseInt(""+s1)|Integer.parseInt(""+s2))==0){
			return '0';
		}else{
			return '1';
		}
	}
	//异或门,一位进行异或
	public char Xor (char s1,char s2){
		if((Integer.parseInt(""+s1)^Integer.parseInt(""+s2))==0){
			return '0';
		}else{
			return '1';
		}
	}
	
	
	
	/**
	 * 生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	//未测试
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
	 * 生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
 	//特殊情况未表示，一点都没调试过
	public String floatRepresentation (String number, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String ans = "";
		String index = "";
		//符号位
		if(number.charAt(0)=='-'){
			ans = ans+"1";
			number = number.replace("-","");
		}else{
			ans = ans+"0";
		}
		//底数
		String[] spare = new String[2];
		spare = number.split("\\.");
		String overOne = Integer.toBinaryString(Integer.parseInt(spare[0]));
		//小于1
		String lowerOne = null;
		int max = sLength-overOne.length();
		int lower = Integer.parseInt(spare[1]);
		//小数部分
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
		//sNumber 是最终的底数
		String sNumber = overOne+lowerOne;
		for(int i =sNumber.length();i<sLength;i++){
			sNumber=sNumber+"0";
		}
		
		//指数
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
	 * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
	 * 例：ieee754("11.375", 32)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String ieee754 (String number, int length) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 计算二进制补码表示的整数的真值。<br/>
	 * 例：integerTrueValue("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
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
	 * 计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		
		return null;
	}
	
	/**
	 * 按位取反操作。<br/>
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
	 */
	//结束
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
	 * 左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
	 */
	public String leftShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		for(int i=0;i<n;i++){
			operand=operand+"0";
		}
		return operand;
	}
	
	/**
	 * 逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public String logRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		operand="0"+operand;
		return operand;
	}
	
	/**
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
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
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
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
	 * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	//未测试
	public String claAdder (String operand1, String operand2, char c) {
		// TODO YOUR CODE HERE.
		//相乘是And，相加是Or
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
	 * 加一器，实现操作数加1的运算。
	 * 需要采用与门、或门、异或门等模拟，
	 * 不可以直接调用{@link #fullAdder(char, char, char) fullAdder}、
	 * {@link #claAdder(String, String, char) claAdder}、
	 * {@link #adder(String, String, char, int) adder}、
	 * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
	 * 例：oneAdder("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
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
	 * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
	 * 例：adder("0100", "0011", ‘0’, 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param c 最低位进位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		// TODO YOUR CODE HERE.
		String anString="";
		String n1=operand1,n2=operand2;
		//进行补位
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
		
		//从最后4位开始加，不断往前挪，，进位由claAdder得到
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
	 * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		
		return aAlu.adder(operand1, operand2, '0', length);
	}
	
	/**
	 * 整数减法，可调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
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
	 * 整数乘法，使用Booth算法实现，可调用{@link #adder(String, String, char, int) adder}等方法。<br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	//oprand1当y，operand2当x
	public String integerMultiplication (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String n1=operand1+"0",n2=operand2,ans="";
		for(int i=0;i<n1.length();i++){
			
			ans=(2^-1)*(ans+operand2*(n1.charAt(operand1.length()-i)-n1.charAt(operand1.length()-i-1)));
		}
		return null;
	}
	
	/**
	 * 整数的不恢复余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		
		return null;
	}
	
	/**
	 * 带符号整数加法，可以调用{@link #adder(String, String, char, int) adder}等方法，
	 * 但不能直接将操作数转换为补码后使用{@link #integerAddition(String, String, int) integerAddition}、
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}来实现。<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int) integerMultiplication}等方法实现。<br/>
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
}
