package com.tyhgg.core.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * http协议接口请求调用工具类
 * 功能：实现get请求，post请求参数发生与响应结果处理
 * @类名称: HttpClientUtil
 * @类描述: 
 * @创建人：zyt5668
 * @修改人：zyt5668
 * @修改时间：2019年4月28日 下午3:38:53
 * @修改备注：
 */
public class HttpClientUtil {
	

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
	
	/**
	 * http公共参数配置
	 * */
	private static RequestConfig requestConfig = RequestConfig.custom()
			.setSocketTimeout(15000)
			.setConnectTimeout(15000)
			.setConnectionRequestTimeout(15000).build();
	
	/**
	 * 处理get请求
	 * @param url 请求地址
	 * @return 返回响应结果
	 * */
	public static String httpGet(String url){
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			
			HttpEntity httpEntity = httpResponse.getEntity();
			LOGGER.info("http请求url:"+url+" ,处理状态status:"+httpResponse.getStatusLine().getStatusCode());
			return EntityUtils.toString(httpEntity);
		} catch (Exception e) {
			
			LOGGER.error("http请求异常 url="+url, e);
		} finally {
			
		}
		return null;
	}
	
	/**
	 * 处理post请求
	 * @param url 请求地址
	 * @param params 参数
	 * @return 返回响应结果
	 * */
	public static String httpPost(String url, List<? extends NameValuePair> params){
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(url);
		HttpEntity entity;
		try {
			httpPost.setConfig(requestConfig);
			entity = new UrlEncodedFormEntity(params,"UTF-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);

			LOGGER.info("http请求url:"+url+" ,处理状态status:"+httpResponse.getStatusLine().getStatusCode());
			HttpEntity entityResult = httpResponse.getEntity();
			return EntityUtils.toString(entityResult);
			
		} catch (Exception e) {
			LOGGER.error("http请求异常 url="+url, e);
		}
		return null;
	}
	/**
	 * 处理post请求
	 * @param name 文件标识
	 * @param url 请求地址
	 * @param MultipartFile 文件流
	 * @return 返回响应结果
	 * */
	public static String httpMultipart(String name,String url,MultipartFile multipartFile){
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
//		String fileName = multipartFile.getOriginalFilename();
		HttpPost httpPost = new HttpPost(url);
		ContentType contentType = ContentType.create("multipart/form-data",Charset.forName("UTF-8"));
		MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532).seContentType(contentType).setCharset(java.nio.charset.Charset.forName("UTF-8"));
		try {
			builder.addBinaryBody(name, multipartFile.getInputStream(), ContentType.DEFAULT_BINARY, multipartFile.getOriginalFilename());
			HttpEntity entity = builder.build();
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity responseEntity= response.getEntity();
			if(responseEntity!=null){
				result = EntityUtils.toString(responseEntity,Charset.forName("UTF-8"));
			}
		} catch (IOException e) {
			LOGGER.error("", e);
		}finally{
			if(httpClient!=null){
				try {
					httpClient.close();
				} catch (IOException e) {
					LOGGER.error("关闭文件流异常", e);
				}
			}
		}
		
		return result;
	}
	/**
	 * 处理post请求
	 * @param name 文件标识
	 * @param url 请求地址
	 * @param MultipartFile 文件流
	 * @return 返回响应结果
	 * */
	public static String httpMultipart(String name, String url, MultipartFile multipartFile,
			Map<String,String> params){
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		ContentType contentType = ContentType.create("multipart/form-data",Charset.forName("UTF-8"));
		MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532)
				.seContentType(contentType).setCharset(java.nio.charset.Charset.forName("UTF-8"));
		try {
			builder.addBinaryBody(name, multipartFile.getInputStream(), ContentType.DEFAULT_BINARY, 
					multipartFile.getOriginalFilename());
			if(params!=null){
				Set<String> keySet = params.keySet();
				Iterator<String> iterator = keySet.iterator();
				if(iterator.hasNext()){
					String key = (String)iterator.next();
					builder.addTextBody(key, params.get(key));
				}
			}
			HttpEntity entity = builder.build();
			httpPost.setEntity(entity);
			httpPost.addHeader("clientid", params.get("clientid"));
			httpPost.addHeader("uuid", params.get("uuid"));
			
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity responseEntity= response.getEntity();
			if(responseEntity!=null){
				result = EntityUtils.toString(responseEntity,Charset.forName("UTF-8"));
			}
		} catch (IOException e) {
			LOGGER.error("", e);
		}finally{
			if(httpClient!=null){
				try {
					httpClient.close();
				} catch (IOException e) {
					LOGGER.error("关闭文件流异常", e);
				}
			}
		}
		
		return result;
	}
	/**
	 * 处理post请求
	 * @param url 请求地址
	 * @param params 参数
	 * @return 返回响应结果
	 * */
	public static String httpPost(String url, Map<String, String> params){
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> NameValuePair = new ArrayList<NameValuePair>();
		HttpEntity entity;
		try {
			httpPost.setConfig(requestConfig);
			if(params!=null){
				Iterator<String> iterator = params.keySet().iterator();
				while(iterator.hasNext()){
					String key = (String)iterator.next();
					String val = (String)params.get(key);
					NameValuePair.add(new BasicNameValuePair(key,val));
				}
			}
			entity = new UrlEncodedFormEntity(NameValuePair,"UTF-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);

			LOGGER.info("http请求url:"+url+" ,处理状态status:"+httpResponse.getStatusLine().getStatusCode());
			HttpEntity entityResult = httpResponse.getEntity();
			return EntityUtils.toString(entityResult);
			
		} catch (Exception e) {
			LOGGER.error("http请求异常 url="+url, e);
		}
		return null;
	}
	
	/**
	 * 处理post请求
	 * @param url 请求地址
	 * @param jsonString 参数
	 * @return 返回响应结果
	 * */
	public static String httpPost(String url, String jsonString){
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);

			LOGGER.info("http请求url:"+url+" ,处理状态status:"+httpResponse.getStatusLine().getStatusCode());
			HttpEntity entityResult = httpResponse.getEntity();
			return EntityUtils.toString(entityResult);
			
		} catch (Exception e) {
			LOGGER.error("http请求异常 url="+url, e);
		}
		
		return null;
	}
	
	
	public static String httpDelete(String url){
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpDelete hd = new HttpDelete(url);
		StringBuilder sb = new StringBuilder();
		try {
			HttpResponse response = httpClient.execute(hd);
			HttpEntity httpEntity = response.getEntity();
			if(httpEntity!=null){
				httpEntity = new BufferedHttpEntity(httpEntity);
				InputStream is = httpEntity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
				String str;
				while((str=br.readLine())!=null){
					sb.append(str);
				}
				is.close();
			}
		} catch (IOException e) {
			LOGGER.error("", e);
		}
		return sb.toString();
	}
	
//	public static void main(String[] args) {
//		String url = "http://22.188.127.187/cgi-bin/gettoken?corpid=wl67f9b477b9&corpsecret=6LrmpycrOFweNR-kbgC6J9S5dvfMeWDDFnRdZGzM2QA";
//		
//		System.out.println(httpGet(url));
//		// {"errcode":0,"errmsg":"ok","access_token":"DuMCKYkWBmaG4pMpjLanw6vhNeLUIS-_CX-ttwvmj4kkThHP2rKcahfvFFVXcqAPvmJ__7Rl-rnXbUUdqpisEhX7yq6na7FPoc3hzhdn21_TEZochPoLL3s2T4ETE1K-LevmI8we7NdX7Cak3-zVwn3yzxZwIJTIsFmGSB2WdY-5Rf3Fpg3V6qCOgu_k6_yMTPVo2umesPci0sNQ2DferQ","expires_in":7200}
//		String accessToken = "DuMCKYkWBmaG4pMpjLanw6vhNeLUIS-_CX-ttwvmj4kkThHP2rKcahfvFFVXcqAPvmJ__7Rl-rnXbUUdqpisEhX7yq6na7FPoc3hzhdn21_TEZochPoLL3s2T4ETE1K-LevmI8we7NdX7Cak3-zVwn3yzxZwIJTIsFmGSB2WdY-5Rf3Fpg3V6qCOgu_k6_yMTPVo2umesPci0sNQ2DferQ";
//		url = "http://22.188.127.187/cgi-bin/user/get?access_token=" + accessToken + "&userid=7614448";
//		
//		System.out.println(httpGet(url));
//		// {"errcode":0,"errmsg":"ok","userid":"7614448","name":"邢远","department":[8],"position":"","mobile":"","gender":"1","email":"","avatar":"","status":1,"isleader":0,"extattr":{"attrs":[]},"english_name":"","telephone":"","enable":1,"hide_mobile":0,"order":[0]}
//		
//	}
}
