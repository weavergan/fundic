package fundinc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("/js/.*");
		  Matcher matcher = pattern.matcher("/js/easyui/themes/icon.css");
		  System.out.println(matcher.matches());
	}
}
