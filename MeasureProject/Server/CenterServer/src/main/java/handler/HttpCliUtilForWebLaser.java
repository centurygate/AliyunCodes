package handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptionprocess.Exception2Msg;
import log.SystemLog;
import measurebussiness.model.Measure;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by free on 2016/12/8.
 */
public class HttpCliUtilForWebLaser {
    static CloseableHttpClient httpclient;
    static CloseableHttpResponse httpResponse;
    static HttpGet httpGet;
    static HttpPost httpPost;
    static URI uri;
    static ObjectMapper mapper;
    public static String readResponse(InputStream in) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder inputHTML = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            //SystemLog.log(line);
            inputHTML.append("\n" + line);
        }
        return inputHTML.toString();
    }

    public static boolean loginWebServer(String uriStr, String username, String password) {
        boolean isok = true;
        if (uri == null) {
            uri = URI.create(uriStr);
        }
        if (httpclient == null) {
            httpclient = HttpClients.createDefault();
        }
        if (httpGet == null) {
            httpGet = new HttpGet();
            httpGet.setURI(uri);
        }
        if (httpPost == null) {
            httpPost = new HttpPost();
        }

        try {
            httpResponse = httpclient.execute(httpGet);

            if (HttpStatus.SC_OK != httpResponse.getStatusLine().getStatusCode()) {
                SystemLog.log(httpResponse.getStatusLine());
                return false;
            }
//            HttpEntity entity = httpResponse.getEntity();
//            InputStream in = entity.getContent();
//            String inputHTML = readResponse(in);
//            Parser hiddenparser = new Parser();
//            Parser actionparser = new Parser();
//            hiddenparser.setInputHTML(inputHTML);
//            actionparser.setInputHTML(inputHTML);
//            NodeFilter actionfilter = new CssSelectorNodeFilter("[method=post]");
//            NodeFilter hiddenfilter = new CssSelectorNodeFilter("[type=hidden]");
//            NodeList hiddenlist = hiddenparser.parse(hiddenfilter);
//            NodeList actionlist = actionparser.parse(actionfilter);
//            Node hiddennode = hiddenlist.elementAt(0);
//            Node actionnode = actionlist.elementAt(0);
//            SystemLog.log("++++++++++++++++++++++++++++++++++++++++++++++++++");
//            String hiddenmatchstr = hiddennode.getText();
//            String actionmatchstr = actionnode.getText();
//            String[] hidden_str_array = hiddenmatchstr.split("\\s+");
//            String[] action_str_array = actionmatchstr.split("\\s+");
//            int i = 0;
//            for (i = 0; i < hidden_str_array.length; i++) {
//                SystemLog.log(hidden_str_array[i]);
//            }
//            for (i = 0; i < action_str_array.length; i++) {
//                SystemLog.log(action_str_array[i]);
//            }
//            String[] posturl = action_str_array[1].split("\"");
//            for (i = 0; i < posturl.length; i++) {
//                SystemLog.log(posturl[i]);
//            }
//
//            String[] hiddenparam = hidden_str_array[3].split("\"");
//            //HttpPost post = new HttpPost(baseurl+temp[1]);
//            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//
//            formparams.add(new BasicNameValuePair("username", "user"));
//            formparams.add(new BasicNameValuePair("password", "Abcd1234@@@"));
//            formparams.add(new BasicNameValuePair("_csrf", hiddenparam[1]));
////            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
//            httpPost.setURI(URI.create(uriStr.substring(0, uriStr.lastIndexOf("/")) + posturl[1]));
//            httpPost.setEntity(new UrlEncodedFormEntity(formparams, Consts.UTF_8));
//            httpResponse = httpclient.execute(httpPost);
//            SystemLog.log("##############################################################");
//            if (HttpStatus.SC_MOVED_TEMPORARILY != httpResponse.getStatusLine().getStatusCode()) {
//                SystemLog.log(httpResponse.getStatusLine());
//                return false;
//            }
//
//            Header[] headers = httpResponse.getAllHeaders();
//            for (Header header : headers) {
//                SystemLog.log(header.getName() + ": " + header.getValue());
//            }
        } catch (ClientProtocolException clientProtocolException) {
            clientProtocolException.printStackTrace();
            isok = false;

        } catch (Exception e) {
            SystemLog.log(Exception2Msg.convertException2Msg(e));
            isok = false;
        } finally {
            SystemLog.log("Enter Finally......");
        }
        return isok;
    }

    public static boolean senddata(String serviceurl, Measure measure) throws JsonProcessingException, UnsupportedEncodingException {
        boolean isok = true;
        if (httpclient == null) {
            SystemLog.log("Should be call loginWebServer firstly!");
            return false;
        }
        if (mapper == null)
        {
            mapper = new ObjectMapper();
        }
        String urlstr = serviceurl + URLEncoder.encode( mapper.writeValueAsString(measure),"utf-8");
        SystemLog.log("Send URL:"+urlstr);
        httpGet.setURI(URI.create(urlstr));
        try {
            httpResponse = httpclient.execute(httpGet);
//            if (HttpStatus.SC_OK != httpResponse.getStatusLine().getStatusCode()) {
                SystemLog.log(httpResponse.getStatusLine());
                InputStream in = httpResponse.getEntity().getContent();
                SystemLog.log(readResponse(in));
//                return false;
//            }
        } catch (ClientProtocolException clientProtocolException) {
            clientProtocolException.printStackTrace();
            isok = false;

        } catch (Exception e) {
            SystemLog.log(Exception2Msg.convertException2Msg(e));
            isok = false;
        } finally {
            SystemLog.log("Enter Finally......");
        }
        return isok;
    }
}
