package org.fund.stat.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.htmlparser.Parser;

public class GetDataUtils {

    public static String getSourceCode(String strURL, String encoding) throws Exception {
        URL url = new URL(strURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        InputStreamReader input = new InputStreamReader(httpConn.getInputStream(), encoding);
        BufferedReader bufReader = new BufferedReader(input);
        String line = "";
        StringBuilder contentBuf = new StringBuilder();
        while ((line = bufReader.readLine()) != null) {
            contentBuf.append(line + "\n");
            // System.out.println(line);
        }
        return contentBuf.toString();
    }

    public static Parser getParser(String strURL, String encoding) {
        Parser parser = null;
        try {
            parser = new Parser(strURL);
            parser.setEncoding(encoding);
            return parser;
        } catch (Exception e) {}
        return null;
    }

    // 得到估值
    // public static String getValuation(Parser parser) {
    // if(parser == null) return "";
    // NodeFilter filter = new AndFilter(new TagNameFilter("div"),
    // new HasAttributeFilter("id", Constant.VaLUATIONNODEID));
    // NodeList nlist = null;
    // try {
    // nlist = parser.extractAllNodesThatMatch(filter);
    // } catch (ParserException e) {
    // }
    // Node node = nlist.elementAt(0).getFirstChild();
    // String valuation = node.toPlainTextString();
    // return valuation;
    // }
    // 得到净值
    // private static String getWorth(Parser parser) {
    // if(parser == null) return "";
    // NodeFilter filter = new AndFilter(new TagNameFilter("div"),
    // new HasAttributeFilter("id", Constant.VaLUATIONNODEID));
    // NodeList nlist = null;
    // try {
    // nlist = parser.extractAllNodesThatMatch(filter);
    // } catch (ParserException e) {
    // }
    // Node node = nlist.elementAt(0).getFirstChild();
    // String valuation = node.toPlainTextString();
    // return valuation;
    // }

    // public static String[] getValuationAndWorth(String code) {
    // String[] values = new String[2];
    // String url = Constant.EASTMONEYPREFIX + code + Constant.EASTMONEYSUFFIX;
    // Parser parser = getParser(url);
    // values[0] = getValuation(parser);
    // // values[1] = getValuation(parser);
    //
    // return values;
    // }

    public static void main(String[] args) throws Exception {
        String code = "161022";
        // System.out.println(getValuationAndWorth(code));
    }
}
