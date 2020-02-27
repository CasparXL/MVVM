package com.lxb.mvvmproject.network.websocket;


import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.lxb.mvvmproject.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * 基于服务端的WebSocket技术,客户端勿用
 */
public final class WebSocketManager {
    private final static int MAX_NUM = 5;       // 最大重连数
    private final static int MILLIS = 3000;     // 重连间隔时间，毫秒
    private volatile static WebSocketManager manager;

    private OkHttpClient client;//请求辅助类
    private Request request;//请求
    private IReceiveMessage receiveMessage;//连接状态接口
    private WebSocket mWebSocket;//WebSocket主要类

    private boolean isConnect = false;//是否连接
    private int connectNum = 0;//当前连接次数
    private String sendMessage = "";//断开连接时发送的命令
    private boolean isStart = false;//是否正在连接

    private WebSocketManager() {
//        GsonUtils.fromJson(text, TestBean.class);
    }

    public static WebSocketManager getInstance() {
        if (manager == null) {
            synchronized (WebSocketManager.class) {
                if (manager == null) {
                    manager = new WebSocketManager();
                }
            }
        }
        return manager;
    }

    public void init(String url, IReceiveMessage message) {
        try {
            @SuppressLint("TrustAllX509TrustManager") final X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            client = new OkHttpClient.Builder()
                    .writeTimeout(5, TimeUnit.SECONDS)//写入超时
                    .readTimeout(5, TimeUnit.SECONDS)//读取超时
                    .connectTimeout(10, TimeUnit.SECONDS)//连接超时规定时间
                    .retryOnConnectionFailure(true)//重连操作，但是感觉没啥用。
                    .sslSocketFactory(sslSocketFactory, trustManager)//SSL协议证书，同意所有协议网络请求，否则可能不支持http请求
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .build();
            request = new Request.Builder().url(url)
                    .addHeader("Sec-WebSocket-Key", "1234")//WebSocket必须要的请求头
                    .build();
            receiveMessage = message;
            connect();
        } catch (Exception e) {
            LogUtil.e(e.getLocalizedMessage() + "失败了");
            e.printStackTrace();
        }
    }

    /**
     * 连接
     */
    private void connect() {
        if (isConnect()) {
            //连接中就没必要重连了
            return;
        }
        if (!isStart) {
            LogUtil.e("Web Socket开始连接");
            isStart = true;
            client.newWebSocket(request, createListener());
        } else {
            LogUtil.e("已经有一个正在重连");
        }
    }

    /**
     * 重连
     */
    private void reconnect() {
        if (connectNum <= MAX_NUM) {
            try {
                Thread.sleep(MILLIS);
                isStart = false;//这里重置一下状态，避免多次点击数据不对
                connect();
                connectNum++;
            } catch (InterruptedException e) {
                LogUtil.e(e.getMessage());
                e.printStackTrace();
            }
        } else {
            isStart = false;
            connectNum = 0;
            LogUtil.e("通过 " + MAX_NUM + "次重连后失败,请检查url或网络");
            if (receiveMessage != null) {
                receiveMessage.onConnectFailed("max");
            }
        }
    }

    /**
     * 是否连接
     */
    public boolean isConnect() {
        return mWebSocket != null && isConnect;
    }

    /**
     * 发送消息
     *
     * @param text 字符串
     * @return boolean
     */
    public boolean sendMessage(String text) {
        if (!isConnect()) return false;
        LogUtil.i("发送命令：");
        sendMessage = text;
        return mWebSocket.send(text);
    }

    /**
     * 发送消息
     *
     * @param byteString 字符集
     * @return boolean
     */
    public boolean sendMessage(ByteString byteString) {
        if (!isConnect()) return false;
        return mWebSocket.send(byteString);
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (isConnect()) {
            LogUtil.e("客户端主动关闭连接");
            mWebSocket.cancel();
            mWebSocket.close(1001, "客户端主动关闭连接");
        }
    }

    private WebSocketListener createListener() {
        return new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                LogUtil.e("打开WebSocket:" + response.toString());
                mWebSocket = webSocket;
                isConnect = response.code() == 101;//101证明连接成功
                if (!isConnect) {
                    reconnect();
                } else {
                    connectNum = 0;
                    isStart = false;//这里重置一下状态，避免切换网络重新连接其它失败
                    LogUtil.e("连接成功");
                    if (receiveMessage != null) {
                        receiveMessage.onConnectSuccess();
                        if (!TextUtils.isEmpty(sendMessage)) {
                            sendMessage(sendMessage);
                        }
                    }
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                if (!TextUtils.isEmpty(text) && text.contains("{") && text.contains("}")) {
                    try {
                        JSONObject object = new JSONObject(text);
                        String type = object.optString("type");
                    } catch (JSONException e) {
                        LogUtil.e("解析数据异常,错误原因:" + e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                } else {
                    LogUtil.i("服务器返回非json数据:" + text);
                }

            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                LogUtil.e("长连接返回的数据:" + bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                LogUtil.e("onClosing：远程不再传入消息，连接断开");
                mWebSocket = null;
                isConnect = false;
                if (receiveMessage != null) {
                    receiveMessage.onClose();
                }
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                LogUtil.e("onClosed：连接已成功释放,两端都不再发送消息");
                mWebSocket = null;
                isConnect = false;
                if (receiveMessage != null) {
                    receiveMessage.onClose();
                }
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                if (response != null) {
                    LogUtil.e("连接失败：" + response.message());
                }
                LogUtil.e("Socket连接建立失败,错误原因:" + t.getMessage());
                isConnect = false;
                mWebSocket = null;//出现了异常，就关闭掉本次Socket，根据情况可能会重连
                if (!"Socket closed".equals(t.getMessage())) {//Socket手动关闭后极大概率走这个回调，所以在手动关闭时就不进行重连，否则可能会出现莫名的bug
                    reconnect();
                }
                if (receiveMessage != null) {
                    receiveMessage.onConnectFailed(t.getMessage());
                }
            }
        };
    }
}