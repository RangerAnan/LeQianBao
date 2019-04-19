package com.leqian.bao.model.resource;

import com.google.gson.annotations.SerializedName;
import com.leqian.bao.model.BaseModelResp;

/**
 * Created by fcl on 19.4.19
 * desc:
 */
public class LinkResourceResp extends BaseModelResp {

    public String title;


    @SerializedName("0")
    private String _$0;
    @SerializedName("1")
    private String _$1;
    @SerializedName("2")
    private String _$2;


    public String get_$0() {
        return _$0;
    }

    public void set_$0(String _$0) {
        this._$0 = _$0;
    }

    public String get_$1() {
        return _$1;
    }

    public void set_$1(String _$1) {
        this._$1 = _$1;
    }

    public String get_$2() {
        return _$2;
    }

    public void set_$2(String _$2) {
        this._$2 = _$2;
    }


}
