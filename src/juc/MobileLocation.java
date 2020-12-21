package juc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.TreeSet;

public class MobileLocation {

    static Set<String> set = new TreeSet();

    public static void main(String[] args) {
        String httpstr = doPost("http://www.yzcopen.com/con/getiphone","iphone="+"15927132947");
        System.out.println(httpstr);
        new Thread(
                new Runnable(){
                    public void run() {
                        //读取文件中手机号段
                        String str = readTxt("C:/qwertyu.txt");
                        String[] strs = str.split(",");
                        //循环号段
                        for (String value : strs) {
                            //号段是7位的 判断
                            if(value.length() == 7){
                                System.out.println(value);
                                String iphone = "";
                                //号段后 补全
                                for(int i = 0; i < 9999; i++){
                                    //每1000个号码打印一次记录
                                    if(i % 1000 == 0){
                                        System.out.println("==="+value+"===> " + i + " <===");
                                    }
                                    //拼接完整 手机号
                                    iphone = value + format(""+i,4);
                                    //获取手机号归属地
                                    String httpstr = doPost("http://www.yzcopen.com/con/getiphone","iphone="+iphone);
                                    //返回json数据，没必要解析。直接判断归属地
                                    if(httpstr != null && !"".equals(httpstr) && httpstr.indexOf("临沂") > 0){
                                        //符合要求手机号
                                        System.out.println(iphone);
                                        //写入文件
                                        write(iphone,iphone.substring(0,3));
                                    }
                                    iphone = "";
                                }
                            }
                        }
                    }
                }
        );//.start();
    }
    /*
     * 补全
     * 接收手机号后四位值
     * 位数
     */
    public static String format(String i,int count){
        String a = i;
        while (a.length() < count){
            a="0"+a;
        }
        return a;
    }

    //网络请求 post
    public static String doPost(String httpUrl, String param) {

        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            os.write(param.getBytes());
            // 通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return result;
    }
    static int counta = 0;
    static int countb = 0;
    static String filePath;
    //写入文件
    public static void write(String data,String str) {
        try{
            //每999个电话号分一个文件
            if(counta % 999 == 0){
                filePath = "C:\\LinYiHone\\LinYiHone"+countb+"_"+str+".vcf";
                File file= new File(filePath);
                if(!file.exists()){
                    file.createNewFile();
                }
                //动态文件名称标识
                countb++;
            }

            //将写入转化为流的形式
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,Boolean.TRUE));
            //一次写一行 OPPO 电话本导入格式
//			BEGIN:VCARD
//			VERSION:2.1
//			N:1;;;;
//			FN:1
//			TEL;CELL:18669300960
//			END:VCARD

            bw.write("BEGIN:VCARD");
            bw.newLine();
            bw.write("VERSION:2.1");
            bw.newLine();
            bw.write("N:"+format(""+counta,11)+";;;;");
            bw.newLine();
            bw.write("FN:"+format(""+counta,11));
            bw.newLine();
            bw.write("TEL;CELL:"+data);
            bw.newLine();
            bw.write("END:VCARD");
            bw.newLine();

//			bw.write(data);
//			bw.newLine();  //换行用

            //关闭流
            bw.close();
            System.out.println("写入成功");

        }catch (Exception e) {
            e.printStackTrace();
        }
        counta++;
    }
    //读取文件
    public static String readTxt(String txtPath) {
        File file = new File(txtPath);
        if(file.isFile() && file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuffer sb = new StringBuffer();
                String text = null;
                while((text = bufferedReader.readLine()) != null){
//                	System.out.println(text);
                    if(!"".equals(text) && text != null){
                        if(text.length() > 3){
                            sb.append(text.replaceAll("	", ","));
                            sb.append(",");
                        }
                    }
                }
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
