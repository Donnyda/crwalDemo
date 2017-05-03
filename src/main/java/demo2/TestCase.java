package demo2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class TestCase {
	@Test
    public void testName() throws Exception {
		String url="http://www.biquge5.com/7_7308/2964490.html";
		URL urls=new URL(url);
		HttpURLConnection conn = (HttpURLConnection)urls.openConnection();
		if(conn.getResponseCode()==200){
			InputStreamReader isr = new InputStreamReader(conn.getInputStream(),"gbk");
			BufferedReader reader=new BufferedReader(isr);
			String s=null;
			StringBuffer sb=new StringBuffer();
			while((s=reader.readLine())!=null){
				sb.append(s);
			}
			String urlContent = sb.toString();
			//获取子连接
			String regEx ="<div id=\"content\">(.*?)</div>";
			Pattern pattern = Pattern.compile(regEx);
			Matcher matcher = pattern.matcher(urlContent);
			StringBuffer sb2=new StringBuffer();
			while(matcher.find()){
				String group = matcher.group();
				sb2.append(group);
			}
			String contents=sb2.toString();
			contents = contents.replaceAll("</?\\w+\\s?.+?>", "\n");
			contents = contents.replaceAll("&nbsp;", " ");
			System.out.print(contents);
			reader.close();
			isr.close();
		}
    }
	
	@Test
    public void testCrwal() throws Exception {
	    Crawler c=new Crawler("http://www.sina.com");
	    c.crawl(6);
    }
}
