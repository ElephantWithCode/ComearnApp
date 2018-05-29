package com.example.team.comearnapp.util.Retrofit;

import android.content.Context;

import com.example.team.comearnapp.BuildConfig;
import com.example.team.comearnapp.interfaces.HttpRequestInterface;
import com.example.team.comearnapp.util.ToastUtil;
import com.example.team.commonlibrary.base.MyApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 邹特强 on 2018/4/2.
 * @author 邹特强
 * 封装了Retrofit的网络请求工具类
 * 网络请求返回的状态码有三种常见情况：
 */

public class RetroHttpUtil {
    /**
     * 存储服务器返回数据对应的键值
     */
    private final static String RESPONSE_KEY = "response";
    /**
     * 公共url
     */
    private final static String BASE_URL = "http://39.106.63.214:8080/xuebei-mongodb/";
    /**
     * 返回的json数据为空时的数据长度
     */
    private final static int EMPTY_JSON_DATA_LENGTH = 2;
    /**
     * 设置网络连接超时时间
     */
    private final static int CONNECT_TIMEOUT_TIME = 15;
    /**
     * 设置从网络获取数据超时时间
     */
    private final static int READ_TIMEOUT_TIME = 20;
    /**
     * 设置向网络传输数据超时时间
     */
    private final static int WRITE_TIMEOUT_TIME = 20;

    /**
     * 解析将Response体解析为List
     *
     * @param call:利用call传入泛型类型
     */
    private static <T extends Object> List<T> parseResponse(Call<T> call, String jsonDatas) {
        if (jsonDatas.length() == EMPTY_JSON_DATA_LENGTH) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(jsonDatas, new TypeToken<List<T>>() {
        }.getType());
    }

    /**
     * 创建Retrofit实例，返回公用接口
     *
     * @return 返回公用接口
     */
    public static HttpRequestInterface build() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        /**
         * 设置网络超时重连
         */
        okBuilder.connectTimeout(CONNECT_TIMEOUT_TIME, TimeUnit.SECONDS);
        okBuilder.readTimeout(READ_TIMEOUT_TIME, TimeUnit.SECONDS);
        okBuilder.writeTimeout(WRITE_TIMEOUT_TIME, TimeUnit.SECONDS);
        okBuilder.retryOnConnectionFailure(true);
        /**
         * 设置无网络时也能缓存
         */
        Context context = MyApp.getGlobalContext();
        File cacheFile = new File(context.getExternalCacheDir(), "Xuebei.cacheFile");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        okBuilder.cache(cache).addInterceptor(getCacheInterceptor());
        /**
         * 设置log拦截器，便于调试
         */
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            /**
             * 打印所有网络信息
             */
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okBuilder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient okHttpClient = okBuilder.build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(HttpRequestInterface.class);
    }

    /**
     * 获取缓存拦截器的方法
     */
    public static Interceptor getCacheInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                CacheControl.Builder cacheBuilder = new CacheControl.Builder();
                cacheBuilder.maxAge(0, TimeUnit.SECONDS);
                cacheBuilder.maxStale(365, TimeUnit.DAYS);
                CacheControl cacheControl = cacheBuilder.build();
                /**
                 * 设置request拦截操作
                 */
                Request request = chain.request();
                if (!AppNetWorkUtil.isNetWorkAvailable(MyApp.getGlobalContext())) {
                    /**
                     * 网络不好时从缓存获取数据
                     */
                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                    ToastUtil.ToastShortShow("网络未连接", MyApp.getGlobalContext());
                }
                okhttp3.Response response = chain.proceed(request);
                if (AppNetWorkUtil.isNetWorkAvailable(MyApp.getGlobalContext())) {
                    /**
                     * 缓存过期时间
                     */
                    int maxAge = 0;
                    return response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public,max-age=" + maxAge)
                            .build();

                } else {
                    /**
                     * 缓存过期时间
                     */
                    int maxStale = 60 * 60 * 24 * 28;
                    return response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale)
                            .build();
                }
            }
        };
    }

    /**
     * 与服务器交互的同一方法
     * 这里利用泛型控制参数返回的实体类的类型
     * Response.code()：200->请求成功
     *
     * @param call :调用端传入的Call对象
     * @return :返回一个泛型List，存储服务器返回数据
     * TODO:如何从外部获取response
     * TODO:先假设所有请求都是与服务器成功交互的情况,然后再处理response.code()的问题,目前比较疑惑onFailure究竟可以检测哪些请求错误
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> void sendRequest(Call<T> call, Callback<T> callback) {
        call.enqueue(callback);
    }
}
