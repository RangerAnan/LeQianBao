package com.nxin.base.model.http.log;

import android.text.TextUtils;

import com.nxin.base.utils.EncryptUtils;
import com.nxin.base.utils.Logger;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by zhy on 16/3/1.
 */
public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "OkHttpUtils";
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            //===>response log
            Logger.e("=========================response'log start=====");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
//            Log.e(tag, "url : " + clone.request().url());
//            Log.e(tag, "code : " + clone.code());
//            Log.e(tag, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message())) {
//                Log.e(tag, "message : " + clone.message());
            }
            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
//                        Log.e(tag, "responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();

                            if (EncryptUtils.isBase64Data(resp)) {
                                resp = EncryptUtils.DeCodeBase64String(resp);
                            }
                            Logger.i("responseBody's content : " + resp);

                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else {
                            Logger.e("responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }

            Logger.e("============================response'log end=====");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            Logger.e("===========================request'log start=====");
//            Log.e(tag, "method : " + request.method());
            Logger.e("url : " + url);
            if (headers != null && headers.size() > 0) {
                Logger.e("headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
//                    Logger.e(tag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Logger.e("requestBody's content : " + bodyToString(request));
                    } else {
                        Logger.e("requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            Logger.e("==============================request'log end=====");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return EncryptUtils.DeCodeBase64String(buffer.readUtf8());
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
