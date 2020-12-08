package nio;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class JDK11HttpClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] { new HttpsUtil.DefaultTrustManager() }, new SecureRandom());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)// default
                .sslContext(ctx)
                .connectTimeout(Duration.ofSeconds(5))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://cn.bing.com/?ensearch=1&rdr=1&rdrig=CAD65DDB538243BBA6CE458BA1C51102"))
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.122 Safari/537.36")
                .build();

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        System.out.println(responseBody);
    }

    public void testWebSocket() throws InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        WebSocket webSocket = client.newWebSocketBuilder()
                .buildAsync(URI.create("ws://localhost:8080/echo"), new WebSocket.Listener() {

                    @Override
                    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                        // request one more
                        webSocket.request(1);

                        // Print the message when it's available
                        return CompletableFuture.completedFuture(data)
                                .thenAccept(System.out::println);
                    }
                }).join();
        webSocket.sendText("hello ", false);
        webSocket.sendText("world ",true);

        TimeUnit.SECONDS.sleep(10);
        webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "ok").join();
    }

    public void testConcurrentRequests(){
        HttpClient client = HttpClient.newHttpClient();
        List<String> urls = List.of("http://www.baidu.com","http://www.alibaba.com/","http://www.tencent.com");
        List<HttpRequest> requests = urls.stream()
                .map(url -> HttpRequest.newBuilder(URI.create(url)))
                .map(reqBuilder -> reqBuilder.build())
                .collect(Collectors.toList());

        List<CompletableFuture<HttpResponse<String>>> futures = requests.stream()
                .map(request -> client.sendAsync(request, HttpResponse.BodyHandlers.ofString()))
                .collect(Collectors.toList());
        futures.stream()
                .forEach(e -> e.whenComplete((resp,err) -> {
                    if(err != null){
                        err.printStackTrace();
                    }else{
                        System.out.println(resp.body());
                        System.out.println(resp.statusCode());
                    }
                }));
        CompletableFuture.allOf(futures
                .toArray(CompletableFuture<?>[]::new))
                .join();
    }

    public void testAsyncDownload() throws ExecutionException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/file/download"))
                .build();

        CompletableFuture<Path> result = client.sendAsync(request, HttpResponse.BodyHandlers.ofFile(Paths.get("/tmp/body.txt")))
                .thenApply(HttpResponse::body);
        System.out.println(result.get());
    }

    public void testPostForm() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://www.w3school.com.cn/demo/demo_form.asp"))
                .header("Content-Type","application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("name1=value1&name2=value2"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
    }

    public void testCookies() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(5000))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/json/cookie"))
                .header("Cookie","JSESSIONID=4f994730-32d7-4e22-a18b-25667ddeb636; userId=java11")
                .timeout(Duration.ofMillis(5009))
                .build();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

}
