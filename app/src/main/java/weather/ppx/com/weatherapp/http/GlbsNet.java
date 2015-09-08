package weather.ppx.com.weatherapp.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import weather.ppx.com.weatherapp.Util.LogUtil;
import weather.ppx.com.weatherapp.Util.SSLSocketFactoryEx;
import weather.ppx.com.weatherapp.Util.URLUtil;

/**
 * 网络连接管理类：判断网络状态、管理网络连接、发起GET和POST请求。
 * 
 * @author heyongbo@infohold.com.cn
 */

public final class GlbsNet {
	
	public static final String HTTP_ERROR_MESSAGE = "抱歉，您的网络无法连通，请您检查网络设置后重试。";
	public static final String HTTP_ERROR_TIMEOUT = "抱歉，您的网络无法连通，请您检查网络设置后重试。";

	private static MyHttpClient sHttpClient;
	private static ConnectivityManager sConnManager;
	private static OnNetDisconnListener sOnNetDisconnListener;

	private static long CONNECTION_TIMEOUT = 40;// 设置超时时间，秒单位

	private GlbsNet() {
	}

	private static final ResponseHandler<byte[]> sByteResponseHandler = new ResponseHandler<byte[]>() {
		public byte[] handleResponse(final HttpResponse response)
				throws ClientProtocolException, IOException {
			final StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(),
						statusLine.getReasonPhrase());
			}
			final HttpEntity e = response.getEntity();
			if (e == null)
				return null;
			try {
				return EntityUtils.toByteArray(e);
			} finally {
				e.consumeContent();
			}
		}
	};
	private static final ResponseHandler<String> sStringResponseHandler = new ResponseHandler<String>() {
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			StatusLine statusLine = response.getStatusLine();
			LogUtil.i("NET", "Code: " + statusLine.getStatusCode());
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(),
						statusLine.getReasonPhrase());
			}
			final HttpEntity e = response.getEntity();
			if (e == null)
				return null;
			try {
				return EntityUtils.toString(e, HTTP.UTF_8);
			} finally {
				e.consumeContent();
			}
		}
	};

	/**
	 * 初始化网络连接。
	 * 
	 * @param context
	 *            应用上下文
	 * @param OnNetDisconnListener
	 *            网络断开监听
	 */
	public static boolean init(Context context, OnNetDisconnListener l) {
		if (null == sHttpClient) {
			if (null == l) {
				throw new IllegalArgumentException("网络中断监听不可以为空！");
			}
			sOnNetDisconnListener = l;

			sHttpClient = MyHttpClient.newInstance();
			sConnManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
//			sApnResolver = context.getContentResolver();
		}

		final NetworkInfo netInfo = sConnManager.getActiveNetworkInfo();
		if (netInfo == null) {
			LogUtil.e("NET", "网络无活动连接：NetworkInfo=" + netInfo);
			l.onNetDisconnected();
			return false;
		}

		final NetworkInfo.State state = netInfo.getState();
		if (state != NetworkInfo.State.CONNECTED) {
			LogUtil.e("NET", "网络暂时中断：" + state.toString());
			l.onNetDisconnected();
			return false;
		}
		return true;
	}

	/**
	 * 释放网络连接。
	 * 
	 * @param context
	 *            应用上下文
	 */
	public static void release(Context context) {
		if (sHttpClient != null) {
			sHttpClient.close();
			sHttpClient = null;
		}
		sConnManager = null;
		sOnNetDisconnListener = null;
	}

	/**
	 * 向指定URI，发起一个GET请求，并返回服务器二进制数据或者字符串。
	 * 
	 * @param uri
	 *            请求地址
	 * @return 返回服务器响应的二进制数据或者字符串。网络异常时，返回错误信息。
	 */
	public static String doGet(String uri) {
		sHttpClient = MyHttpClient.newInstance();
		HttpGet httpGet = new HttpGet();
		try {
			httpGet.setURI(URI.create(uri));
			if(isWapNetwork()){
				setWapProxy();
			}
			return sHttpClient.execute(httpGet, sStringResponseHandler);
		} catch (HttpResponseException e) {
			LogUtil.e("NET", "服务器异常", e);
		} catch (HttpHostConnectException e) {
			LogUtil.e("NET", "服务器异常", e);
		} catch (ClientProtocolException e) {
			LogUtil.e("NET", "请求地址有误或图片不存在：" + uri, e);
		} catch (SocketTimeoutException e) {
			LogUtil.e("NET", "网络超时", e);
		} catch (ConnectTimeoutException e) {
			LogUtil.e("NET", "网络超时", e);
		} catch (InterruptedIOException e) {
			LogUtil.e("NET", "" + e.getMessage());
		} catch (IllegalArgumentException e) {
			LogUtil.e("NET", "", e);
		} catch (IOException e) {
			LogUtil.e("NET", "=====> IO异常！", e);
		} catch (Throwable e) {
			LogUtil.e("NET", "网络請求发生未知错误", e);
		} finally {
			httpGet.abort();
		}

		return HTTP_ERROR_MESSAGE;
	}

	/**
	 * 向指定URI，发起一个POST请求，并返回服务器响应的json串。
	 * 
	 * @param uri
	 *            请求地址
	 * @param paramMap
	 *            请求参数集
	 * @return 返回服务器响应的json串。网络异常时，返回{@code null}。
	 */
	public static String doPost(String uri,HashMap<String, String> params, Context con) {
		sHttpClient = MyHttpClient.newInstance();
		HttpPost httpPost = new HttpPost();
		try {
			httpPost.setURI(URI.create(uri));
			if(isWapNetwork()){
				if(!isUsedWifi(con))
					setWapProxy();
			}
			if (params != null) {
				final int size = params.size();
				if (size > 0) {
					String paramString = URLUtil.map2string(params);
					ByteArrayEntity entity = new ByteArrayEntity(
							paramString.getBytes());
					entity.setContentType("application/x-www-form-urlencoded");
					httpPost.setEntity(entity);
					httpPost.setHeader("Accept-Encoding", "gzip, deflate, sdch");
				}
			}
			return getJsonStringFromGZIP(sHttpClient.execute(httpPost));
//			return sHttpClient.execute(httpPost, sStringResponseHandler);
		} catch (HttpResponseException e) {
			return HTTP_ERROR_MESSAGE;
//			LogUtil.e("NET", "服务器异常", e);
		} catch (ClientProtocolException e) {
			LogUtil.e("NET", "网络请求地址有误：" + uri, e);
		} catch (HttpHostConnectException e) {
			return HTTP_ERROR_MESSAGE;
//			LogUtil.e("NET", "服务器异常", e);
		} catch (SocketTimeoutException e) {
			LogUtil.e("NET", "" + e.getMessage());
		} catch (ConnectTimeoutException e) {
			LogUtil.e("NET", "" + e.getMessage());
		} catch (InterruptedIOException e) {
			LogUtil.e("NET", "" + e.getMessage());
		} catch (IllegalArgumentException e) {
			LogUtil.e("NET", "", e);
		} catch (IOException e) {
			LogUtil.e("NET", "=====> IO异常！", e);
		} catch (Throwable e) {
			LogUtil.e("NET", "网络請求发生未知错误", e);
		} finally {
			httpPost.abort();
		}

		return HTTP_ERROR_TIMEOUT;
	}
	
	private static String getJsonStringFromGZIP(HttpResponse response) {  
        String jsonString = null;  
        try {  
            InputStream is = response.getEntity().getContent();  
            BufferedInputStream bis = new BufferedInputStream(is);  
            bis.mark(2);  
            // 取前两个字节  
            byte[] header = new byte[2];  
            int result = bis.read(header);  
            // reset输入流到开始位置  
            bis.reset();  
            // 判断是否是GZIP格式  
            int headerData = getShort(header);  
            // Gzip 流 的前两个字节是 0x1f8b  
            if (result != -1 && headerData == 0x1f8b) { LogUtil.d("HttpTask", " use GZIPInputStream  ");  
                is = new GZIPInputStream(bis);  
            } else {  
                LogUtil.d("HttpTask", " not use GZIPInputStream");  
                is = bis;  
            }  
            InputStreamReader reader = new InputStreamReader(is, "utf-8");  
            char[] data = new char[100];  
            int readSize;  
            StringBuffer sb = new StringBuffer();  
            while ((readSize = reader.read(data)) > 0) {  
                sb.append(data, 0, readSize);  
            }  
            jsonString = sb.toString();  
            bis.close();  
            reader.close();  
        } catch (Exception e) {  
            LogUtil.e("HttpTask", e.toString(),e);  
        }  
   
        LogUtil.d("HttpTask", "getJsonStringFromGZIP net output : " + jsonString );  
        return jsonString;  
    }  
   
    private static int getShort(byte[] data) {  
        return (int)((data[0]<<8) | data[1]&0xFF);  
    }
	
	/**
	 * 向指定URI，发起一个POST请求，并返回服务器响应的json串。
	 * 
	 * @param uri
	 *            请求地址
	 * @param paramMap
	 *            请求参数集
	 * @return 返回服务器响应的json串。网络异常时，返回{@code null}。
	 */
	public static String doPostNew(String uri,HashMap<String, String> params) {
		InputStreamReader isr = null;
		BufferedReader br = null;
		HttpURLConnection urlConn = null;
		String result="";
		try {
			urlConn = getURLConnection(uri);
			urlConn.setRequestMethod("POST");
			urlConn.setUseCaches(false);
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			//urlConn.setInstanceFollowRedirects(true);
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConn.connect();
			//DataOutputStream流
			DataOutputStream out=new DataOutputStream(urlConn.getOutputStream());
			//要上传的参数
			String content=URLUtil.map2string(params);
			out.writeBytes(content);
			//刷新、关闭
			out.flush();
			out.close();
			
			//得到读取的内容（流）
			isr=new InputStreamReader(urlConn.getInputStream());
			//为输出创建BufferedReader
			br=new BufferedReader(isr);
			String inputLine=null;
			while ((inputLine=br.readLine())!=null) {
				result+=inputLine;
			}
			LogUtil.d("TestDemo", result);
		} catch (Exception e) {
			LogUtil.e("NetError", e.getMessage());
		} finally {
			try {
				if (isr != null)
					isr.close();
				if (br != null)
					br.close();
				if(urlConn!=null)
					urlConn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	private static HttpURLConnection getURLConnection(String url) throws Exception {
		String proxyHost = Proxy.getDefaultHost();
		if (proxyHost != null) {
			java.net.Proxy p = new java.net.Proxy(java.net.Proxy.Type.HTTP,
					new InetSocketAddress(Proxy.getDefaultHost(),
							Proxy.getDefaultPort()));

			return (HttpURLConnection) new URL(url).openConnection(p);

		} else {
			return (HttpURLConnection) new URL(url).openConnection();
		}
	}

	private static void checkInit() {
		if (sHttpClient == null) {
			throw new IllegalStateException("请执行GlbsNet.init()初始化GlbsNet！");
		}
		// sHttpClient = MyHttpClient.newInstance();
	}

//	private static void handleNoNetCase() {
//		new Handler(Looper.getMainLooper(), new Handler.Callback() {
//			public boolean handleMessage(Message msg) {
//				if (null != sOnNetDisconnListener) {
//					sOnNetDisconnListener.onNetDisconnected();
//				}
//				return true;
//			}
//		}).sendEmptyMessage(0);
//	}

//	private static void toast(final String info) {
//		GlbsToast.toastFromNonUiThread(info);
//	}

//	public static boolean isUsedWifi() {
//		WifiManager wifiManager = (WifiManager) GlbsActivity.GLOBAL_CONTEXT
//				.getSystemService(Context.WIFI_SERVICE);
//		boolean isUsedWifi = false;// wifiManager.isWifiEnabled();
//		ConnectivityManager connectivityManager = (ConnectivityManager) GlbsActivity.GLOBAL_CONTEXT
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
//		if (activeNetInfo != null
//				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//			isUsedWifi = true;
//		}
//		if (isUsedWifi && wifiManager.isWifiEnabled()) {
//			return true;
//		} else {
//			return false;
//		}
//	}

//	private static HttpHost getTargetHost() throws NoNetException {
//		final NetworkInfo net = sConnManager.getActiveNetworkInfo();
//
//		if (net == null || !net.isConnected())
//			throw new NoNetException();
//
//		if (ConnectivityManager.TYPE_WIFI == net.getType())
//			return null;
//
//		Cursor c = null;
//		try {
//			c = sApnResolver.query(APN_URI, APN_FIELDS, null, null, null);
//			if ((c != null) && (c.moveToNext())) {
//				final String proxy = c.getString(0);
//				if ((proxy != null) && (proxy.trim().length() > 0)) {
//					return new HttpHost(proxy, 80);
//				}
//			}
//		} catch (Exception e) {
//			// do nothing.
//		} finally {
//			if ((c != null) && (!c.isClosed())) {
//				c.close();
//			}
//		}
//
//		return null;
//	}

	private static boolean isWapNetwork() {
		final String proxyHost = Proxy.getDefaultHost();
		return !TextUtils.isEmpty(proxyHost);
	}
	
	public static boolean isUsedWifi(Context con) {
        WifiManager wifiManager = (WifiManager) con.getSystemService(Context.WIFI_SERVICE);
        boolean isUsedWifi = false;// wifiManager.isWifiEnabled();
        ConnectivityManager connectivityManager = (ConnectivityManager) con.getSystemService(
            Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            isUsedWifi = true;
        }
        if (isUsedWifi && wifiManager.isWifiEnabled()) {
            return true;
        } else {
            return false;
        }
    }

	private static void setWapProxy() {
		final HttpHost host = (HttpHost) sHttpClient.getParams().getParameter(
				ConnRouteParams.DEFAULT_PROXY);
		if (host == null) {
			final String host1 = Proxy.getDefaultHost();
			int port = Proxy.getDefaultPort();
			HttpHost httpHost = new HttpHost(host1, port);
			sHttpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY,
					httpHost);
		}
	}

//	@SuppressWarnings("serial")
//	private static final class NoNetException extends Exception {
//	}

	/**
	 * 网络中断监听。
	 */
	public interface OnNetDisconnListener {
		/**
		 * 当网络中断时，执行此方法。
		 */
		void onNetDisconnected();
	}

	/**
	 * Subclass of the Apache {@link DefaultHttpClient} that is configured with
	 * reasonable default settings and registered schemes for Android, and also
	 * lets the user add {@link HttpRequestInterceptor} classes. Don't create
	 * this directly, use the {@link #newInstance} factory method.
	 * 
	 * <p>
	 * This client processes cookies but does not retain them by default. To
	 * retain cookies, simply add a cookie store to the HttpContext:
	 * </p>
	 * 
	 * <pre>
	 * context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	 * </pre>
	 */
	static final class MyHttpClient extends DefaultHttpClient {

		// Gzip of data shorter than this probably won't be worthwhile
		private static long DEFAULT_SYNC_MIN_GZIP_BYTES = 256;

		/**
		 * Create a new HttpClient with reasonable defaults (which you can
		 * update).
		 * 
		 * @param userAgent
		 *            to report in your HTTP requests.
		 * @param sessionCache
		 *            persistent session cache
		 * @return AndroidHttpClient for you to use for all your requests.
		 */
		public static MyHttpClient newInstance() {
			final BasicHttpParams params = new BasicHttpParams();
			params.setParameter(AllClientPNames.PROTOCOL_VERSION,
					HttpVersion.HTTP_1_1);
			params.setParameter(AllClientPNames.HTTP_CONTENT_CHARSET,
					HTTP.DEFAULT_CONTENT_CHARSET);
			// AllClientPNames.HTTP_CONTENT_CHARSET,
			// HTTP.DEFAULT_CONTENT_CHARSET);
			// 那次返回的中文为乱码，原因在这里
			params.setParameter(AllClientPNames.USER_AGENT, "Android");
			params.setBooleanParameter(AllClientPNames.USE_EXPECT_CONTINUE,
					false);
			params.setBooleanParameter(AllClientPNames.STALE_CONNECTION_CHECK,
					false);
			params.setIntParameter(AllClientPNames.CONNECTION_TIMEOUT,
					30 * 1000);
			params.setIntParameter(AllClientPNames.SO_TIMEOUT, 40 * 1000);
			params.setIntParameter(AllClientPNames.SOCKET_BUFFER_SIZE, 8192);
			params.setBooleanParameter(AllClientPNames.HANDLE_REDIRECTS, true);
			params.setIntParameter(AllClientPNames.MAX_REDIRECTS, 3);
			params.setIntParameter(AllClientPNames.MAX_TOTAL_CONNECTIONS, 20);
			/*
			 * TODO params.setParameter(
			 * AllClientPNames.MAX_CONNECTIONS_PER_ROUTE, new
			 * ConnPerRouteBean(5));
			 */
			final SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			// 支持https
			// schemeRegistry.register(new Scheme("https",
			// SSLSocketFactory.getSocketFactory(),
			// 443));
			try {
				KeyStore trustStore = KeyStore.getInstance(KeyStore
						.getDefaultType());
				trustStore.load(null, null);
				SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
				sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				schemeRegistry.register(new Scheme("https", sf, 443));
			} catch (Exception e) {

			}
			final ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
					params, schemeRegistry);
			// final MyThreadSafeClientConnManager manager = new
			// MyThreadSafeClientConnManager(params, schemeRegistry);
			return new MyHttpClient(manager, params);
		}

		public static MyHttpClient newInstance(int timeout) {
			final BasicHttpParams params = new BasicHttpParams();
			params.setParameter(AllClientPNames.PROTOCOL_VERSION,
					HttpVersion.HTTP_1_1);
			params.setParameter(AllClientPNames.HTTP_CONTENT_CHARSET,
					HTTP.DEFAULT_CONTENT_CHARSET);
			// AllClientPNames.HTTP_CONTENT_CHARSET,
			// HTTP.DEFAULT_CONTENT_CHARSET);
			// 那次返回的中文为乱码，原因在这里
			params.setParameter(AllClientPNames.USER_AGENT, "Android");
			params.setBooleanParameter(AllClientPNames.USE_EXPECT_CONTINUE,
					false);
			params.setBooleanParameter(AllClientPNames.STALE_CONNECTION_CHECK,
					false);
			params.setIntParameter(AllClientPNames.CONNECTION_TIMEOUT,
					timeout * 1000);
			params.setIntParameter(AllClientPNames.SO_TIMEOUT, timeout * 1000);
			params.setIntParameter(AllClientPNames.SOCKET_BUFFER_SIZE, 8192);
			params.setBooleanParameter(AllClientPNames.HANDLE_REDIRECTS, true);
			params.setIntParameter(AllClientPNames.MAX_REDIRECTS, 3);
			params.setIntParameter(AllClientPNames.MAX_TOTAL_CONNECTIONS, 20);
			/*
			 * TODO params.setParameter(
			 * AllClientPNames.MAX_CONNECTIONS_PER_ROUTE, new
			 * ConnPerRouteBean(5));
			 */
			final SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			// 支持https
			// schemeRegistry.register(new Scheme("https",
			// SSLSocketFactory.getSocketFactory(),
			// 443));
			try {
				KeyStore trustStore = KeyStore.getInstance(KeyStore
						.getDefaultType());
				trustStore.load(null, null);
				SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
				sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				schemeRegistry.register(new Scheme("https", sf, 443));
			} catch (Exception e) {

			}
			final ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
					params, schemeRegistry);
			// final MyThreadSafeClientConnManager manager = new
			// MyThreadSafeClientConnManager(params, schemeRegistry);
			return new MyHttpClient(manager, params);
		}

		private MyHttpClient(ThreadSafeClientConnManager connM,
				HttpParams params) {
			super(connM, params);
		}

		/**
		 * Release resources associated with this client. You must call this, or
		 * significant resources (sockets and memory) may be leaked.
		 */
		public void close() {
			getConnectionManager().shutdown();
		}

		protected BasicHttpProcessor createHttpProcessor() {
			BasicHttpProcessor processor = super.createHttpProcessor();
			processor.addRequestInterceptor(new HttpRequestInterceptor() {
				public void process(HttpRequest res, HttpContext cxt)
						throws HttpException, IOException {
					res.addHeader("Connection", "Keep-Alive");
				}
			});
			return processor;
		}

		/**
		 * Modifies a request to indicate to the server that we would like a
		 * gzipped response. (Uses the "Accept-Encoding" HTTP header.)
		 * 
		 * @param request
		 *            the request to modify
		 * @see #getUngzippedContent
		 */
		public static void modifyRequestToAcceptGzipResponse(HttpRequest request) {
			request.addHeader("Accept-Encoding", "gzip");
		}

		/**
		 * Gets the input stream from a response entity. If the entity is
		 * gzipped then this will get a stream over the uncompressed data.
		 * 
		 * @param entity
		 *            the entity whose content should be read
		 * @return the input stream to read from
		 * @throws IOException
		 */
		public static InputStream getUngzippedContent(HttpEntity entity)
				throws IOException {
			InputStream responseStream = entity.getContent();
			if (responseStream == null)
				return responseStream;
			Header header = entity.getContentEncoding();
			if (header == null)
				return responseStream;
			String contentEncoding = header.getValue();
			if (contentEncoding == null)
				return responseStream;
			if (contentEncoding.contains("gzip"))
				responseStream = new GZIPInputStream(responseStream);
			return responseStream;
		}

		/**
		 * Compress data to send to server. Creates a Http Entity holding the
		 * gzipped data. The data will not be compressed if it is too short.
		 * 
		 * @param data
		 *            The bytes to compress
		 * @return Entity holding the data
		 */
		public static AbstractHttpEntity getCompressedEntity(byte data[])
				throws IOException {
			AbstractHttpEntity entity;
			if (data.length < DEFAULT_SYNC_MIN_GZIP_BYTES) {
				entity = new ByteArrayEntity(data);
			} else {
				ByteArrayOutputStream arr = new ByteArrayOutputStream();
				OutputStream zipper = new GZIPOutputStream(arr);
				zipper.write(data);
				zipper.close();
				entity = new ByteArrayEntity(arr.toByteArray());
				entity.setContentEncoding("gzip");
			}
			return entity;
		}
	}

}