package com.leqian.bao.model.network.appinfo;

import com.leqian.bao.model.network.base.BaseModelResp;

/**
 * Created by fcl on 19.5.11
 * desc:
 */
public class CheckAppVersionResp extends BaseModelResp {

    public boolean hasUpdate = true;

    public String updateDesc = "V1.1.0更新提示:\n\n1.优化界面\n2.增加新功能";

    public String apkUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557569416771&di=1e2e4a8dd5e717de88ab53db60d4ee43&imgtype=0&src=http%3A%2F%2Fku.90sjimg.com%2Felement_origin_min_pic%2F01%2F59%2F45%2F2757485d2425ae9.jpg";
    public String newVersion = "1.1.0";
}
