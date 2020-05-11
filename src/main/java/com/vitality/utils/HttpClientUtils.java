package com.vitality.utils;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;

/**
 *
 * HTTPClient工具类
 * 获取token
 * 访问post请求
 * 带query参数、带Header认证、带json参数
 * @author 江越天
 * @date 2020-05-11 12:11
 * @param
 * @return
 */
public class HttpClientUtils {
    private static final String CHARSET = "utf-8";
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    /**
     * Header参数
     */
    private static final String Authorization = "Authorization";

    private static SSLConnectionSocketFactory sslConnectionSocketFactory;
    private static RequestConfig config;

    /**
     * 单例实现token
     */
    private String accessToken = null;

    private static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public HttpClientUtils() {
        initTrustHosts();
        initConfig();
    }

    private void initTrustHosts() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        }catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void initConfig() {
        config = RequestConfig.custom().setConnectTimeout(10000000).setSocketTimeout(10000000).build();
    }

    /**
     * 获取token
     * @return
     */
    public boolean instanceTargetToken() {
        if (accessToken == null) {
            try {
                //Map<String, String> taskConfigMap = configReaderUtil.loadConfigsByFile("task.properties");
                Map<String, String> targetParamsMap = getTargetParams();

                String writeToken = clientPost("https://sso.vitalitytex.com/auth/realms/vitality/protocol/openid-connect/token", targetParamsMap);
                JSONObject json = JSONObject.parseObject(writeToken);

                if (!json.containsKey("access_token")) {
                    log.info(writeToken);
                    return false;
                }
                setAccessToken(json.getString("access_token"));
                log.info(accessToken);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return true;
    }

    /**
     * 登录接口参数
     * @return
     */
    public Map<String, String> getTargetParams() {
        Map<String, String> targetParamsMap = new HashMap<String, String>();
        try {

            targetParamsMap.put("grant_type", "client_credentials");
            targetParamsMap.put("client_id", "wms");
            targetParamsMap.put("client_secret", "08bf9c95-579c-4975-8900-4111e437890d");
            return targetParamsMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetParamsMap;

    }

    public String clientPost(String url, Map<String, String> params) {
        String result = null;
        try {
            Thread.sleep(300);
            HttpPost httppost = new HttpPost(url);
            //建立HttpPost对象
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            //建立一个NameValuePair数组，用于存储欲传送的参数
            Iterator<String> keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.get(key);
                param.add(new BasicNameValuePair(key, value));
            }
            StringEntity stringEntity = new StringEntity("", CHARSET);
            stringEntity.setContentType(CONTENT_TYPE_TEXT_JSON);
            //添加参数
            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded"));
            httppost.setEntity(new UrlEncodedFormEntity(param, HTTP.UTF_8));
            //设置编码
            CloseableHttpResponse response = createClient().execute(httppost);
            //发送Post,并返回一个HttpResponse对象
            if (response.getStatusLine().getStatusCode() == 200) {//如果状态码为200,就是正常返回
                result = EntityUtils.toString(response.getEntity());
            }
            response.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }



    private CloseableHttpClient createClient() {
        return HttpClients.custom().setDefaultRequestConfig(config)
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                .build();
    }

    /**
     * POST---有参(普通参数 + 对象参数)
     * query参数放入url中处理即可
     * @date
     */
    public String doPost(String url,String token,Map<String,Object> params) {

        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        // 创建json参数
        JSONObject jsonObject = new JSONObject();
        for (String key:params.keySet()){
            jsonObject.put(key,params.get(key));
        }

        // 模拟表单
        //UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
        // 将user对象转换为json字符串，并放入entity中
        StringEntity entity = new StringEntity(jsonObject.toJSONString(), ContentType.APPLICATION_JSON);

        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        httpPost.setHeader(Authorization, "Bearer " + token);
        // 响应模型
        CloseableHttpResponse response = null;
        String result = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            //System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                result = EntityUtils.toString(responseEntity);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
