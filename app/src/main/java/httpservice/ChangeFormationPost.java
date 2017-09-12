package httpservice;

/**
 * Created by ASUS on 2016/12/6.
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
public class ChangeFormationPost
{
    // 请求服务器端的url
    private static String PATH = "http://123.207.237.45/webtt/ChangeFormationAction";
    private static URL url;

    public ChangeFormationPost() {
        // TODO Auto-generated constructor stub
    }

    static {
        try {
            url = new URL(PATH);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static String executeHttpPost(String uSID,String uCN,String uTN,String uE,String uN,String uS,String uQ,String uCPN) {

        try {
            // 发送指令和信息
            Map<String, String> params = new HashMap<String, String>();
            params.put("uSID",uSID);
            params.put("uCN",uCN);
            params.put("uTN",uTN);
            params.put("uE",uE);
            params.put("uN",uN);
            params.put("uS",uS);
            params.put("uQ",uQ);
            params.put("uCPN",uCPN);
            return sendPostMessage(params, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param params
     *            填写的url的参数
     * @param encode
     *            字节编码
     * @return
     */
    public static String sendPostMessage(Map<String, String> params,
                                         String encode) {
        // 作为StringBuffer初始化的字符串
        StringBuffer buffer = new StringBuffer();
        try {
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    // 完成转码操作
                    buffer.append(entry.getKey()).append("=").append(
                            URLEncoder.encode(entry.getValue(), encode))
                            .append("&");
                }
                buffer.deleteCharAt(buffer.length() - 1);
            }
            // System.out.println(buffer.toString());
            // 删除掉最有一个&

           /* System.out.println("-->>"+buffer.toString());*/
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);// 表示从服务器获取数据
            urlConnection.setDoOutput(true);// 表示向服务器写数据
            // 获得上传信息的字节大小以及长度
            byte[] mydata = buffer.toString().getBytes();
            // 表示设置请求体的类型是文本类型
            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length",
                    String.valueOf(mydata.length));
            // 获得输出流,向服务器输出数据
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(mydata,0,mydata.length);
            outputStream.close();
            // 获得服务器响应的结果和状态码
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                return changeInputStream(urlConnection.getInputStream(), encode);
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将一个输入流转换成指定编码的字符串
     *
     * @param inputStream
     * @param encode
     * @return
     */
    private static String changeInputStream(InputStream inputStream,
                                            String encode) {
        // TODO Auto-generated method stub
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, len);
                }
                result = new String(outputStream.toByteArray(), encode);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }
   public static void main(String[] args) {
       ChangeFormationPost po = new ChangeFormationPost() ;
       System.out.println(po.executeHttpPost("151543236","442000199704284595","林伟聪","575948146@qq.com","桦夜","男","好烦","15502080236"));
   }

}
