package lc.platform.admin.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;

/**
 * @author ZhanXue
 * @Title:
 * @Package
 * @Description: httpclient接口
 * @date 2018/3/2210:00
 */
public class SysHttpClient {
    public static String doPostRequest(String url,String jsonData) {
        HttpClient httpClient = null;
        PostMethod postMethod = null;
        int statusCode = 0;
        String response = "";
        try {
            httpClient = new HttpClient();
            System.out.println(url);
            postMethod = new PostMethod(url);
            //传递参数
            StringRequestEntity entity=new StringRequestEntity(jsonData,"application/json","utf-8");
            postMethod.setRequestEntity(entity);

            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(20000);

            httpClient.getHttpConnectionManager().getParams().setSoTimeout(20000);
            statusCode = httpClient.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                // statusCode=200 返回成功
                InputStream inputStream = null;
                InputStreamReader inputStreamReader = null;
                BufferedReader bufferedReader = null;
                String tmp = null;
                // 读取返回报文
                StringBuffer resp = new StringBuffer();
                try {
                    inputStream = postMethod.getResponseBodyAsStream();
                    inputStreamReader = new InputStreamReader(inputStream,
                            "UTF-8");
                    bufferedReader = new BufferedReader(inputStreamReader);
                    while ((tmp = bufferedReader.readLine()) != null) {
                        resp.append(tmp).append("\n");
                    }
                    response = resp.toString();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    // 关闭client链接 关闭流
                    postMethod.releaseConnection();
                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly(inputStreamReader);
                    IOUtils.closeQuietly(bufferedReader);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
