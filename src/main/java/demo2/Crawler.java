package demo2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 爬虫程序
 * @author laidong
 *
 */
public class Crawler {
	
	private String root;//种子节点
	private Set<String> visitedUrls=new HashSet<String>();//已访问列表
	private LinkedList<String> toDoUrls=new LinkedList<String>();//待访问列表
	
	public Crawler(String root) {
		this.root = root;
	}
	
	public void intoQueue(String url){
		toDoUrls.addLast(url);
	}
	
	public String outQueue(){
		return toDoUrls.pollFirst();
	}
	
	public boolean checkVisited(String url){
		return visitedUrls.contains(url);
	}
	
	public void visited(String url){
		visitedUrls.add(url);
	}
	
	/*宽度优先算法处理当前节点内容*/
	public void crawl(int flag){
		intoQueue(root);
		while(toDoUrls.size()>0){
			if(visitedUrls.size()==flag){
				recordVisitedToFile();
				System.out.println("队列中的数量："+toDoUrls.size());
				break;
			}
			String url = outQueue();
			System.out.println("正在访问-----------"+url);
			if(checkVisited(url)){
				System.out.println("已访问过");
				continue;
			}
			String urlContent = getURLContent(url);
			if(urlContent!=null){
				//获取子连接
				String regEx ="(?!.+js)http://[^'\"]+";
				Pattern pattern = Pattern.compile(regEx);
				Matcher matcher = pattern.matcher(urlContent);
				while(matcher.find()){
					String group = matcher.group();//获取到的超链接
					intoQueue(group);//子节点入列
					//System.out.println(group);
				}
			}
			visited(url);
		}
		
	}
	
	public String getURLContent(String url){
		try {
	        URL urls=new URL(url);
	        HttpURLConnection conn = (HttpURLConnection)urls.openConnection();
	        if(conn.getResponseCode()==200){
	        	InputStream is = conn.getInputStream();
	        	byte[] bytes=new byte[1024];
		        //读取到字节数组
		        int length=0;
		        StringBuffer sb=new StringBuffer();
		        while((length=is.read(bytes))!=-1){
		        	String content = new String(bytes,0,length);
		        	sb.append(content);
		        }
		        return sb.toString();
	        }
	        System.out.println("访问失败");
	        
        } catch (Exception e) {
	        e.printStackTrace();
        }
		return null;
	}
	
	public void recordVisitedToFile(){
		FileOutputStream fos=null;
		String path="e:/visited.txt";
		File file=new File(path);
		try {
	        fos=new FileOutputStream(file);
	        Iterator<String> iterator = visitedUrls.iterator();
	        while(iterator.hasNext()){
	        	String url = iterator.next()+"\n";
	        	fos.write(url.getBytes());
	        }
	        fos.flush();
        } catch (Exception e) {
	        e.printStackTrace();
        }finally{
        	if(fos!=null){
        		try {
	                fos.close();
                } catch (IOException e) {
	                e.printStackTrace();
                }
        	}
        }
	}
}
