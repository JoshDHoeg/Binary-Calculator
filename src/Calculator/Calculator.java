package Calculator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class DivideByZeroException extends Exception {
	   public DivideByZeroException(){
	      super("Dude! You can't divide by Zero!!");
	   }
	}

public class Calculator {
	
public static void main(String[] args) {
	
	System.out.println("Welcome to the BinaryCalculator");
	Scanner in = new Scanner(System.in); 
	while(true){
		String arg1; 
		if(in.hasNext()){
			arg1 = in.next(); 
			if(arg1.equalsIgnoreCase("QUIT")){
				break;
			}
		} else {
			break;
		} 
		String operator = "?", arg2 = "0"; 
		if(in.hasNext()){ 
			operator = in.next(); 
		} 
		if(in.hasNext()){
			arg2 = in.next();
		} 
		if(arg1.length() != arg2.length()){
			System.err.println("ERROR: '" + arg1 + "' and '" + arg2 + "' are not the same length."); 
			continue; 
		}

		
		//I created two arrays of chars and now am going to loop through the input and assign values
		Boolean [] a = new Boolean[arg1.length()];
		Boolean[] b = new Boolean[arg2.length()];
		
		for (int i = arg1.length()-1; i >= 0; i--){
			if(arg1.charAt(i) == '1'){
				a[i] = true;
			}else {
				a[i] = false;
			}
			
			if(arg2.charAt(i) == '1'){
				b[i] = true;
			}else{
				b[i] = false;
			}
		}
		
		// set up a series of if statements that correspond to the operator	
		// if the operator corresponds with a specific sign do the function associated with that
		if(operator.equals("+")){
			Add(a,b);
		} else if(operator.equals("-")){
			Subtract(a,b);
		}else if(operator.equals("*")){
			if( (a[0] && b[0])|| (!a[0] && !b[0])){
				System.out.println(toDecimal(a) + " * " + toDecimal(b) + " = " + toDecimal(Multiply(a,b)));
			}else if(a[0]){
				System.out.println(toDecimal(a) + " * " + toDecimal(b) + " = -" + toDecimal(Multiply(TwosComp(a),b)));
			}else{
				System.out.println(toDecimal(a) + " * " + toDecimal(b) + " = -" + toDecimal(Multiply(a, TwosComp(b))));
			}
		}else if(operator.equals("/")){
			try {
				System.out.print(toDecimal(a) + " / " + toDecimal(b) + " = ");
				Divide(a, b);
			} catch (DivideByZeroException e) {
				System.out.println(" ERROR");
			}
		}
		
		}
	in.close();
	}

static String toDecimal(Boolean a[]){
	String ret = "";
	int index = 0;
	long r = 0;
	
	if(a[0]){
		a = TwosComp(a);
		ret = ret + "-";
	}
	
	for ( int i = a.length - 1; i >= 0; i--){
		if(a[i] == true){
			r = r + (int)(Math.pow(2, index));
		}
		index++;
	}
	
	ret = ret + r;
	return ret;
}

static Boolean[] BinaryAddition (Boolean a[], Boolean b[]){
	int length = a.length;
	Boolean ret[] = new Boolean[length];
	Boolean carry = false;
	
	
	for (int i = length-1; i >= 0; i --){
		if(a[i] && b[i]){
			if(carry){
				ret[i] = true;
			}else {
				ret[i] = false;
			}
			carry = true;
		}else if(a[i] || b[i]){
			if(carry){
				ret[i] = false;
				carry = true;
			}else {
				ret[i] = true;
				carry = false;
			}
		}else if(!a[i] && !b[i]){
			if(carry){
				ret[i] = true;
			}else {
				ret[i] = false;
			}
			carry = false;
		}
	}
	

	return ret;
}

static Boolean[] BinarySubtraction(Boolean a[], Boolean b[]){
	return BinaryAddition(a, TwosComp(b));
}

static Boolean[] TwosComp(Boolean a[]){
	Boolean [] one = new Boolean[a.length];
	Boolean [] ret = new Boolean[a.length];
	for ( int k = 0; k < one.length - 1; k++){
		one[k] = false;
	}
	one[one.length - 1] = true;
	
	for ( int i = 0; i < a.length; i++){
		if(a[i]){
			ret[i] = false;
		}else if(!a[i]){
			ret[i] = true;
		}
	}
	ret = BinaryAddition(ret, one);

	return ret;
}

static Boolean[] LSR(Boolean a[], int n){
	Boolean ret [] = new Boolean[a.length];
	
	for(int i = 0; i < a.length-1; i++){
		ret[i+1] = a[i];
	}
	
	ret[0] = false;
	if(n == 0){
		return a;
	}else if(n == 1){
		return ret;
	}else{
		return LSR(ret, n-1);
	}

	
}

static Boolean[] LSL(Boolean a[], int n){
	Boolean ret [] = new Boolean[a.length];
	
	for(int i = a.length-1; i > 0; i--){
		ret[i-1] = a[i];
	}
	
	ret[a.length-1] = false;
	
	if(n == 0){
		return a;
	}else if(n == 1){
		return ret;
	}else{
		return LSL(ret, n-1);
	}

	
}

static void Add(Boolean a[], Boolean b[]){
	Boolean [] ret = BinaryAddition(a,b);
	
	System.out.println(toDecimal(a) + " + " + toDecimal(b) + " = " + toDecimal(ret));
	
	//BinaryPrint(a, b, ret);	
}

static void Subtract(Boolean a[], Boolean b[]){
	Boolean [] ret = BinaryAddition(a, TwosComp(b));
	
	System.out.println(toDecimal(a) + " - " + toDecimal(b) + " = " + toDecimal(ret));
	
	//BinaryPrint(a, b, ret);	
}

static Boolean[] Multiply(Boolean a[], Boolean b[]){
	Boolean [] ret = new Boolean[a.length * 2];
	Boolean [] copy = new Boolean[a.length * 2];
	for(int j = 0; j < ret.length; j++){
		ret[j] = false;
		copy[j] = false;
	}
	
	for(int k = 0; k < a.length; k++){
		copy[copy.length-(a.length-k)] = a[k];
	}

	for( int i = 0; i < b.length; i++){
		if(b[b.length-1-i]){
			ret = BinaryAddition(ret, LSL(copy, i));
		}
	}
	
	return ret;
}

static void Divide(Boolean Dividend[], Boolean Divisor[]) throws DivideByZeroException{
	Boolean count = false;
	for(int i = 0; i < Divisor.length; i++){
		if(Divisor[i]){
			count = true;
		}
	}
	if (!count) {
		throw new DivideByZeroException();
	}
	
	ArrayList<Boolean> DivisorList = new ArrayList<Boolean>(Arrays.asList(Divisor));
	ArrayList<Boolean> DividendList = new ArrayList<Boolean>(Arrays.asList(Dividend));
	
	ArrayList<Boolean> remainder = new ArrayList<Boolean>(DivisorList);
	ArrayList<Boolean> divisor = new ArrayList<Boolean>(DividendList);
	Boolean[] quotient = new Boolean[Dividend.length*2];
	
	for (int i = 0; i < DivisorList.size(); i++) {
		remainder.add(false);
		divisor.add(false);

	}
	
	for(int j = 1; j < divisor.size(); j++){
		quotient[j] = false;
	}
	
	Boolean [] DivisorArray = divisor.toArray(new Boolean[divisor.size()]);
	Boolean [] RemainderArray = remainder.toArray(new Boolean[remainder.size()]);
	
	
	for (int i = 0; i < quotient.length+1; i++) {
		//BinaryPrint(RemainderArray, DivisorArray, quotient);
		RemainderArray = BinaryAddition(RemainderArray, TwosComp(DivisorArray));
		//BinaryPrint(RemainderArray, DivisorArray, quotient);
		if (RemainderArray[0]) {
			RemainderArray = BinaryAddition(RemainderArray, DivisorArray);
			quotient = LSL(quotient, 1);
			quotient[quotient.length-1]= false;
		} else {
			quotient = LSL(quotient, 1);
			quotient[quotient.length-1] =  true;
		}
		DivisorArray = LSL(DivisorArray, 1);
	}

	
	System.out.println(toDecimal(quotient) + "R" + toDecimal(RemainderArray));
	
}

static void BinaryPrint (Boolean a[], Boolean b[], Boolean ret[]){
	System.out.println();
	for(int i = 0; i < a.length; i++){
		if(a[i]){
			System.out.print("1");
		}else{
			System.out.print("0");
		}
	}
	System.out.println();
	for(int i = 0; i < b.length; i++){
		if(b[i]){
			System.out.print("1");
		}else{
			System.out.print("0");
		}
	}
	System.out.println();
	for(int i = 0; i < b.length; i++){
		System.out.print("-");
	}
	System.out.println();
	for(int i = 0; i < ret.length; i++){
		if(ret[i]){
			System.out.print("1");
		}else{
			System.out.print("0");
		}
	}
	System.out.println();
	
}
}

