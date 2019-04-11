package com.nxin.base.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fcl on 18.5.9
 * desc:
 */

public class BaseParcelableModel implements Parcelable {

    public BaseParcelableModel(){

    }

    protected BaseParcelableModel(Parcel in) {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseParcelableModel> CREATOR = new Creator<BaseParcelableModel>() {
        @Override
        public BaseParcelableModel createFromParcel(Parcel in) {
            return new BaseParcelableModel(in);
        }

        @Override
        public BaseParcelableModel[] newArray(int size) {
            return new BaseParcelableModel[size];
        }
    };
}
