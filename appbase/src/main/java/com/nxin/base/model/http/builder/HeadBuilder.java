package com.nxin.base.model.http.builder;


import com.nxin.base.model.http.OkHttpUtils;
import com.nxin.base.model.http.request.OtherRequest;
import com.nxin.base.model.http.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
