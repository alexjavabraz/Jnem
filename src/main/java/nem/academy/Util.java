package nem.academy;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Util {
	
	public static final boolean isBlankOrNull(Object o) {
		if(null == o) {
			return true;
		}
		
		if("".equals(o.toString())) {
			return true;
		}
		
		return false;
	}
	
	public static final String sanityzer(String s) {
		if(!isBlankOrNull(s)) {
			s = s.replace("-", "");
			s = s.trim();
		}
		
		return s;
	}
	
	
	/**
	 * 
	 * @param num
	 * @return
	 */
	public static String format(long num) {
		DecimalFormat formatter = new DecimalFormat("###.######");
		formatter.setMinimumFractionDigits(6);
		BigDecimal bd = new BigDecimal(num);
		bd = bd.divide(BigDecimal.TEN.pow(6));
		String retorno = (formatter.format(bd));
		return retorno.replace(",", ".");		
	}	

}
