package demo2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class TestCase {
	
	private static String prePage="";
	private static String nextPage="";
	private static String baseUrl="http://www.23us.com";
	
	@Test
    public void testName() throws Exception {
		String currentPage="/html/54/54128/21868193.html";
		String url=baseUrl+currentPage;
		getNextConent(url);
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
	        String next = scanner.next();
	        if("exit".equals(next)){
	        	break;
	        }else if("next".equals(next)){
	        	getNextConent(nextPage);
	        }else if("pre".equals(next)){
	        	getNextConent(prePage);
	        }
        }
    }
	
	public void getNextConent(String url) throws Exception{
		URL urls=new URL(url);
		HttpURLConnection conn = (HttpURLConnection)urls.openConnection();
		
		if(conn.getResponseCode()==200){
			InputStreamReader isr = new InputStreamReader(conn.getInputStream(),"gbk");
			BufferedReader reader=new BufferedReader(isr);
			String s=null;
			StringBuffer sb=new StringBuffer();
			while((s=reader.readLine())!=null){
				sb.append(s);
				sb.append("\n");
			}
			String urlContent = sb.toString();
//			String regEx ="<dd id=\"contents\">(.*?)</dd>";
			String regEx ="id=\"content[s]?\"[^>]*>(.*)";
			Pattern pattern = Pattern.compile(regEx);
			Matcher matcher = pattern.matcher(urlContent);
			StringBuffer sb2=new StringBuffer();
			while(matcher.find()){
				String group = matcher.group(1);
				sb2.append(group);
			}
			String contents=sb2.toString();
			//System.out.println(contents);
			contents = contents.replaceAll("</?\\w+\\s?.+?>", "\n");
			contents = contents.replaceAll("&nbsp;", " ");
			System.out.println("....................................................");
			System.out.print(contents);
			System.out.println("....................................................");
			System.out.print("输入next回车看下一章|输入pre回车看上一章：");
			//获取上页和下一页
			String regnextPage="<a href=\"([^\"]+?)\"\\s*[^>]*>.{1}一.{1}";
			Pattern patternPage=Pattern.compile(regnextPage);
			matcher=patternPage.matcher(urlContent);
			List<String> strs=new ArrayList<String>();
			while(matcher.find()){
				strs.add(matcher.group(1));
			}
			prePage=baseUrl+strs.get(0);
			nextPage=baseUrl+strs.get(1);
			reader.close();
			isr.close();
		}
	}
	
	/*@Test
    public void testCrwal() throws Exception {
	    Crawler c=new Crawler("http://www.sina.com");
	    c.crawl(6);
    }*/
}
