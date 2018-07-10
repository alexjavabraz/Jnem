package nem.academy;

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

}
