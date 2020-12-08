package nio;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class HttpsUtil {

    static final class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    private static HttpsURLConnection getHttpsURLConnection(String uri, String method) throws IOException {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert ctx != null;
        SSLSocketFactory ssf = ctx.getSocketFactory();

        URL url = new URL(uri);
        HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
        httpsConn.setSSLSocketFactory(ssf);
        httpsConn.setHostnameVerifier((hostname, session) -> true);
        httpsConn.setRequestMethod(method);
        httpsConn.setInstanceFollowRedirects(false);
        httpsConn.setDoInput(true);
        httpsConn.setDoOutput(true);
        return httpsConn;
    }

    private static byte[] getBytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baas = new ByteArrayOutputStream();
        byte[] kb = new byte[1024];
        int len;
        while ((len = is.read(kb)) != -1) {
            baas.write(kb, 0, len);
        }
        byte[] bytes = baas.toByteArray();
        baas.close();
        is.close();
        return bytes;
    }

    private static void setBytesToStream(OutputStream os, byte[] bytes) throws IOException {
        ByteArrayInputStream basis = new ByteArrayInputStream(bytes);
        byte[] kb = new byte[1024];
        int len;
        while ((len = basis.read(kb)) != -1) {
            os.write(kb, 0, len);
        }
        os.flush();
        os.close();
        basis.close();
    }

    public static byte[] doGet(String uri) throws IOException {
        HttpsURLConnection httpsConn = getHttpsURLConnection(uri, "GET");
        return getBytesFromStream(httpsConn.getInputStream());
    }

    public static byte[] doPost(String uri, String data) throws IOException {
        HttpsURLConnection httpsConn = getHttpsURLConnection(uri, "POST");
        setBytesToStream(httpsConn.getOutputStream(), data.getBytes());
        return getBytesFromStream(httpsConn.getInputStream());
    }
}
