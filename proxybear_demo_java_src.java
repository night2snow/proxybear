import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class testforjava {
        
    public testforjava() {
    }
    
    public static void main(String[] args) {
    	execHttpTest();
    }
    
    public static void execHttpTest() {
    	String host = "http://127.0.0.1:28776/";
    	String SN = "";
    	//SN = "kcNpUcnnduQ7eTdRTTowSF4o9PlxgR4U";

		Map<String, String> params = new HashMap<String, String>();

		//API:test    
        String result = LoadPage(getProxyUrl(host, SN, "test", params));
        System.out.println("API: test result = " + result); 
        	
		//API:getLevel
        result = LoadPage(getProxyUrl(host, SN, "getlevel", params));
        System.out.println("API: getLevel result = " + result); 

		//API:getActiveProxy
        result = LoadPage(getProxyUrl(host, SN, "getactiveproxy", params));
        System.out.println("API: getActiveProxy result = " + result); 

		//API:getTestedProxy
        result = LoadPage(getProxyUrl(host, SN, "getTestedProxy", params));
        System.out.println("API: getTestedProxy result = " + result); 

		//API:copyActiveProxy
        result = LoadPage(getProxyUrl(host, SN, "copyActiveProxy", params));
        System.out.println("API: copyActiveProxy result = " + result); 

		//API:CopyTestedProxy
        result = LoadPage(getProxyUrl(host, SN, "CopyTestedProxy", params));
        System.out.println("API: CopyTestedProxy result = " + result); 

		//API:copyAllProxy
        result = LoadPage(getProxyUrl(host, SN, "copyCrawlProxy", params));
        System.out.println("API: copyCrawlProxy result = " + result); 

		//API:removeActiveProxy
		params.clear();
		params.put("count", "1");
        result = LoadPage(getProxyUrl(host, SN, "removeActiveProxy", params));
        System.out.println("API: removeActiveProxy result = " + result); 

		//API:removeTestedProxy
        result = LoadPage(getProxyUrl(host, SN, "removeTestedProxy", params));
        System.out.println("API: removeTestedProxy result = " + result); 

		//API:removeBlockProxy
        result = LoadPage(getProxyUrl(host, SN, "removeBlockProxy", params));
        System.out.println("API: removeBlockProxy result = " + result); 

		//API:removeAllProxy
        result = LoadPage(getProxyUrl(host, SN, "removeCrawlProxy", params));
        System.out.println("API: removeCrawlProxy result = " + result); 

		//API:countActive
        result = LoadPage(getProxyUrl(host, SN, "countActive", params));
        System.out.println("API: countActive result = " + result); 

		//API:countTested
        result = LoadPage(getProxyUrl(host, SN, "countTested", params));
        System.out.println("API: countTested result = " + result); 

		//API:countBlock
        result = LoadPage(getProxyUrl(host, SN, "countBlock", params));
        System.out.println("API: countBlock result = " + result); 

		//API:countall
        result = LoadPage(getProxyUrl(host, SN, "countCrawl", params));
        System.out.println("API: countCrawl result = " + result); 

		//API:returnActiveProxy
		params.clear();
		params.put("proxy", proxy2LongString("202.101.202.101:8080"));
        result = LoadPage(getProxyUrl(host, SN, "returnActiveProxy", params));
        System.out.println("API: returnActiveProxy result = " + result); 

		//API:returnBlockProxy
		params.clear();
		params.put("proxy", proxy2LongString("202.101.202.102:8080"));
        result = LoadPage(getProxyUrl(host, SN, "returnBlockProxy", params));
        System.out.println("API: returnBlockProxy result = " + result); 

		//API:returnTestedProxy
		params.clear();
		params.put("proxy", proxy2LongString("202.101.202.103:8080"));
        result = LoadPage(getProxyUrl(host, SN, "returnTestedProxy", params));
        System.out.println("API: returnTestedProxy result = " + result); 
    }
    
    public static void execSocketTest() {
    	Socket socket = null;
    	
    	try {
            socket = new Socket();
            socket.connect("127.0.0.1", 28777);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getProxyUrl(String host, String sn, String method, Map<String, String> parms){
    	String parameter = "";
    	
    	for (String key : parms.keySet()) {
    		parameter += (parameter.startsWith("?")) ? "&" : "?";
    		parameter += key + "=" + parms.get(key);
    	}
    	
    	if ((sn != null) && (sn != "")) {
	    	parameter += (parameter == "") ? "?" : "&";
	    	parameter += "sn=" + sn;
    	}
    	
    	return host + method + parameter;
    }
    
    public static String proxy2LongString(String proxy) {  
    	Long num = 0L;  
    	if (proxy == null) {
        	return "";  
    	}  
      
    	try {  
        	proxy = proxy.replaceAll("[^0-9\\.\\:]", "");
        	String[] ipx = proxy.split("\\:");
        	String[] ips = ipx[0].split("\\.");  
        	if (ips.length == 4) {  
            	num = Long.parseLong(ips[0], 10) * 256L * 256L * 256L * 256L * 256L + Long.parseLong(ips[1], 10) * 256L * 256L * 256L * 256L + Long.parseLong(ips[2], 10) * 256L * 256L * 256L + Long.parseLong(ips[3], 10) * 256L * 256L + Long.parseLong(ipx[1], 10);
            	num = num >>> 0;  
        	}  
    	} catch(NullPointerException ex) {  
        	System.out.println(proxy);  
    	}  
      
    	return Long.toString(num);
	}  
    
	public static String LoadPage(String url) {
        String result="";
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        String params = "";
        
        try {
			java.net.URL connURL = new java.net.URL(url);  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();  
            httpConn.connect();
            
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;  
            while ((line = in.readLine()) != null) {
            	result += line;  
            }  
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }
        return result ;
    }  
    	
    private byte[] getRequestData(short method, int count, String ip, short port, String sn) {
    	byte[] flag = "mplz".getBytes();
    	byte[] result = new byte[88];
    	
    	System.arraycopy(flag, 0, result, 0, 4);
    	System.arraycopy(flag, 0, result, 84, 4);
    	
    	result[4] = (byte)(method >> 8);
    	result[5] = (byte)(method >> 0);
    	
    	for (int i = 0; i < 4; i++) {
    		result[6 + i] = (byte)(count >> (4 - 1 - i) * 8);
    	}
    	
       	String[] ips = ipx[0].split("\\.");
       	for (int i = 0; i < 4; i++) {
       		result[10 + i] = (byte )Short.parseShort(ips[i]);
       	}
       	
       	result[18] = (byte)(port >> 8);
       	result[19] = (byte)(port >> 0);
       	
       	if (sn != "") {
       		byte[] bsn = sn.getBytes();
       		System.arraycopy(bsn, 0, result, 20, sn.length());
       	}
       	
       	return result;
    }
    
    private void pressData(byte[] data){
    	Socket sock = null;
    	DataOutputStream outos = null;
    	DataInputStream inos = null;
    	byte[] buff = new byte[1024];
    	
    	try{
	    	sock = new Socket();
    		sock.connect(new SocketAddress("", 28777), 10 * 1000);
    		outos = new DataOutputStream(sock.getOutputStream());
    		outos.write(data, 0, 88);
	    	outos.flush();
    		InputStream inos = new InputStream(sock.getInputStream());
    		buff = new byte[1024];
    		while ((buff = inos.read(buff)) == -1) break;
    	} catch (Exception e) {
    	} final {
    		if (outos != null) outos.close();
    		if (sock != null) sock.close();
    	}
    	
    	if ((buff[0] != 0x7F) || (buff[1] != 0x7E)) {
    		System.out.println("Invalid result.");
    		return;
    	}
    	
    	if ((buff[2] != 1)) {
    		System.out.println("Request failded.");
    		return ;
    	}
    }
}
