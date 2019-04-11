package com.nxin.base.web.jsimpl;

import android.content.Intent;

/**
 * Created by fcl on 19.3.9
 * desc:
 */

public interface JSSDKInterface {

    boolean onNewIntent(Intent intent);

    boolean onResume();

    boolean onPause();

    boolean onDestroy();

    boolean onActivityResult(int requestCode, int resultCode, Intent data);

    boolean onBackPressed();
}
