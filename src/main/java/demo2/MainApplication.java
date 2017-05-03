package demo2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

	
public class MainApplication {
	public static void main(String[] args) {
		try {
			
	        URL url=new URL("http://www.biquge5.com/7_7308/2964490.html");
	        HttpURLConnection conn =(HttpURLConnection) url.openConnection();
	        String host = url.getHost();
	        //System.out.println("host:"+host);
	        InputStream is = conn.getInputStream();
	       /* int i=0;
	        int flag=0;
	        while((i=fis.read())!=-1){
	        	System.out.print(Integer.toHexString(i)+" ");
	        	flag++;
	        	if(flag==16){
	        		System.out.println();
	        		flag=0;
	        	}
	        }*/
	        //System.out.println(conn.getContent());
	        Map<String, List<String>> fields = conn.getHeaderFields();
	        //System.out.println(JSONObject.toJSONString(fields));
	        File file=new File("e:/"+host+".html");
	        OutputStream fos=new FileOutputStream(file);
	        byte[] bytes=new byte[1024];
	        //读取到字节数组
	        int length=0;
	        while((length=is.read(bytes))!=-1){
	        	fos.write(bytes, 0, length);
	        	String content = new String(bytes,0,length);
	        	System.out.print(content);
	        }
	        
	        fos.close();
        } catch (Exception e) {
	        e.printStackTrace();
        }
    }
}
