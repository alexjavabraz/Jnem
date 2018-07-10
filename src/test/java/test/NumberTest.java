package test;

import static java.lang.System.out;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.valueOf;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

public class NumberTest {

	public static void main(String[] args) {
		long l = 156952313;
//		DecimalFormat format1 = new DecimalFormat("###,######");
//		format1.setGroupingSize(6);
//		out.println(format1.format(l));
//		
//		test(l);

		format(l);
//
		l = 15695231;
		format(l);
//
		l = 5695231;
		format(l);
//
		l = 695231;
		format(l);
		
		l = 95231;
		format(l);
		
		l = 5231;
		format(l);		

		l = 231;
		format(l);
		
		l = 31;
		format(l);

		l = 1;
		format(l);

	}
	
	public static void test(long number) {
		
		DecimalFormat formatter = new DecimalFormat("###.######");
		formatter.setMinimumFractionDigits(6);
		
		out.println("#############################");
		
		BigDecimal bd = new BigDecimal(number);
		out.println(bd);
		bd = bd.divide(BigDecimal.TEN.pow(6));
		out.println(bd);
		out.println(formatter.format(bd));		
		
		out.println("#############################");
		
		bd = new BigDecimal(10);
		out.println(bd);
		bd = bd.divide(BigDecimal.TEN.pow(6));
		out.println(bd);
		out.println(formatter.format(bd));
		
		out.println("#############################");
		
		bd = new BigDecimal(100);
		out.println(bd);
		bd = bd.divide(BigDecimal.TEN.pow(6));
		out.println(bd);
		out.println(formatter.format(bd));
		
		out.println("#############################");
		
		bd = new BigDecimal(1000);
		out.println(bd);
		bd = bd.divide(BigDecimal.TEN.pow(6));
		out.println(bd);
		out.println(formatter.format(bd));
		
		out.println("#############################");			
		 
		
	}

	public static void format(long num) {
		DecimalFormat formatter = new DecimalFormat("###.######");
		formatter.setMinimumFractionDigits(6);
		
		out.println("#############################");
		
		BigDecimal bd = new BigDecimal(num);
		out.println(bd);
		bd = bd.divide(BigDecimal.TEN.pow(6));
		out.println(bd);
		out.println(formatter.format(bd));		
		
		out.println("#############################");
	}
	
	

}
