package test;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.junit.Test;

import static nem.academy.Util.format;

public class NumberTest {

	@Test
	public void testValues() {
		long l = 156952313;

		String retorno = format(l);
		assertEquals("156.952313", retorno);
//
		l = 15695231;
		retorno = format(l);
		assertEquals("15.695231", retorno);

//
		l = 5695231;
		retorno = format(l);
		assertEquals("5.695231", retorno);
//
		l = 695231;
		retorno = format(l);
		assertEquals("0.695231", retorno);
		
		l = 95231;
		retorno = format(l);
		assertEquals("0.095231", retorno);
		
		l = 5231;
		retorno = format(l);
		assertEquals("0.005231", retorno);

		l = 231;
		retorno = format(l);
		assertEquals("0.000231", retorno);
		
		l = 31;
		retorno = format(l);
		assertEquals("0.000031", retorno);

		l = 1;
		retorno = format(l);
		assertEquals("0.000001", retorno);

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

	@Test
	public void convertToNem() {
		
	}
	
	

}
