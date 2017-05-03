package JasonTest.JasonTest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.util.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Hello world!
 *
 */
public class App 
{
	 public static String readResponse(InputStream in) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder inputHTML = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            //System.out.println(line);
            inputHTML.append("\n"+line);
        }
        return inputHTML.toString();
	}
	 
	 
    public static void main( String[] args ) throws Exception
    {
//        System.out.println( "Hello World!" );
    	class User
    	{
    		String username;
    		String password;
			public User(String username, String password) {
				super();
				this.username = username;
				this.password = password;
			}
			public String getUsername() {
				return username;
			}
			public void setUsername(String username) {
				this.username = username;
			}
			public String getPassword() {
				return password;
			}
			public void setPassword(String password) {
				this.password = password;
			}
    	}
    	
		
		 CloseableHttpClient httpclient = HttpClients.createDefault();
	        String host = "http://localhost:1234";
	        
	        //工程1的应用
	        //String baseurl = "http://localhost:1234/WebLaserProject";
	        
	        //工程2的应用
	        String baseurl = "http://localhost:1234/DrainProject";
	        
	        HttpGet httpGet = new HttpGet(baseurl);
	        try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
	            System.out.println(response1.getStatusLine());
	            HttpEntity entity1 = response1.getEntity();
	            // do something useful with the response body
	            // and ensure it is fully consumed
	            
	            InputStream in = entity1.getContent();
	            String inputHTML = readResponse(in);
	            Parser hiddenparser = new Parser ();
	            Parser actionparser = new Parser ();
	            hiddenparser.setInputHTML(inputHTML);
	            actionparser.setInputHTML(inputHTML);
	            NodeFilter actionfilter = new CssSelectorNodeFilter("[method=post]");
	            NodeFilter hiddenfilter = new CssSelectorNodeFilter("[type=hidden]");
	            NodeList hiddenlist = hiddenparser.parse(hiddenfilter);
	            NodeList actionlist = actionparser.parse(actionfilter);
	            Node hiddennode = hiddenlist.elementAt(0);
	            Node actionnode = actionlist.elementAt(0);
	            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
	            String hiddenmatchstr = hiddennode.getText();
	            String actionmatchstr = actionnode.getText();
	            String[] hidden_str_array = hiddenmatchstr.split("\\s+");
	            String[] action_str_array = actionmatchstr.split("\\s+");
	            int i =0;
	            for(i = 0;i <hidden_str_array.length;i++)
	            {
	                System.out.println(hidden_str_array[i]);
	            }
	             for(i = 0;i <action_str_array.length;i++)
	            {
	                System.out.println(action_str_array[i]);
	            }
	            String[] posturl = action_str_array[1].split("\"");
	             for(i = 0;i <posturl.length;i++)
	            {
	                System.out.println(posturl[i]);
	            }
	             
	            String[] hiddenparam = hidden_str_array[3].split("\"");
	            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	           
	            formparams.add(new BasicNameValuePair("username", "user"));
	            formparams.add(new BasicNameValuePair("password", "Abcd1234@@@"));
	            formparams.add(new BasicNameValuePair("_csrf", hiddenparam[1]));
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
	            HttpPost httppost = new HttpPost(host+posturl[1]);
	            
	            httppost.setEntity(entity);
	            CloseableHttpResponse response2 = httpclient.execute(httppost);
	            


	            System.out.println("##############################################################");
	            System.out.println(response2.getStatusLine());
	            Header[] headers = response2.getAllHeaders();
	            for (Header header : headers) {
	                System.out.println(header.getName() + ": " + header.getValue());
	            }
	            
	            
	            CloseableHttpResponse response3 = httpclient.execute(httpGet);
	            HttpEntity entity3 = response3.getEntity();
	            InputStream in3 = entity3.getContent();
	            String inputHTML3 = readResponse(in3);
	            System.out.println(inputHTML3);
	        
	    		
	            	     
	            int cnt =0;
	    		while(true)
	    		{
	    			
	    			//工程1的应用
/*	    			Measure measure = new Measure();
	    			
	    			Date now = new Date();
	    			String addr = "abdc";
	    			measure.setRecordtime(now);
	    			measure.setDevaddr(addr);
	    			
	    			DecimalFormat df=new DecimalFormat(".##");
	    			double val = new Double(df.format(Math.random()));
	    			measure.setDistance(val);
*/
	    			
	    			
	    			//工程2的应用========================================
	    			List<Measure> measureList = new ArrayList<Measure>();  
	    			
	    			
	    			//每隔5X2秒产生一次警报的极大值
	    			int alarmIdx = -1;
	    			if(cnt%10 == 0){
	    				Random random=new Random();
	    				alarmIdx = random.nextInt(15); 
	    			}
	    			cnt++;
	    			
	    			for(int j=0;j<15;j++)
	    			{
	    				Measure measure = new Measure();
	    				
	    				Date now = new Date();
		    			String addr = "abdc";
		    			measure.setRecordtime(now);
		    			measure.setDevaddr(addr);
		    			
		    			DecimalFormat df=new DecimalFormat(".##");
		    			double val_measure = new Double(df.format(Math.random()));
		    			measure.setValue(val_measure);
		    			
		    			measureList.add(measure);
	    			}
	    			
	    			//添加随机的报警值
	    			if(alarmIdx != -1){
	    				double curvalue= measureList.get(alarmIdx).getValue();
	    			    measureList.get(alarmIdx).setValue(curvalue + 100.0);	
	    			}
	    			
		            ObjectMapper mapper = new ObjectMapper();
		            String string_measure = mapper.writeValueAsString(measureList);
	    			
	    			
		            
		    		//========================================================
	    			
		    		//工程1的应用
		    		//String putdataurl = ""+host+"/WebLaserProject/service/rest/putdata?value="+URLEncoder.encode(mapper.writeValueAsString(measure/*val*/),"utf-8");
		    		
		    		//工程2的应用
		    		String putdataurl = ""+host+"/DrainProject/service/rest/putdata?valueMeasure="+URLEncoder.encode(string_measure,"utf-8");


		    		HttpGet httpget4 = new HttpGet(putdataurl);
		    			    			
		    		CloseableHttpResponse response4 = httpclient.execute(httpget4);
		    		System.out.println("##############################################################");
		    		System.out.println(new Long(Calendar.getInstance().getTime().getTime()));
		            System.out.println(response4.getStatusLine());
		            InputStream in4 = response4.getEntity().getContent();
		            inputHTML = readResponse(in4);
		            System.out.println(inputHTML);
		            Thread.sleep(2000);
	    		}
	        }
    }
}
