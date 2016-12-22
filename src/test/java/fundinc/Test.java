package fundinc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) {
//		Pattern pattern = Pattern.compile("/js/.*");
//		  Matcher matcher = pattern.matcher("/js/easyui/themes/icon.css");
//		  System.out.println(matcher.matches());

//		String[] strs = Test.tokenizeToStringArray("org.fund.user.dao,org.fund.stat.dao", ",; \t\n", true, true);
//		for (String str: strs
//			 ) {
//			System.out.println(str);
//		}

		List<Integer> codes = new ArrayList<Integer>();
		codes.add(1);
		codes.add(3);
		codes.add(5);
		codes.add(4);
		codes.add(12);
		System.out.println(codes);
	}

	public static String[] tokenizeToStringArray(
			String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

		if (str == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}

	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return collection.toArray(new String[collection.size()]);
	}
}
