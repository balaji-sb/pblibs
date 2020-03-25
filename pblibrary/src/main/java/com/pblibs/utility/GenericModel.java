package com.pblibs.utility;

/**
 * Created  by Proggy Blast on 7/7/19.
 */

public class GenericModel {

    private static GenericModel mInstance;

    public static GenericModel getInstance() {
        if (mInstance == null) {
            synchronized (GenericModel.class) {
                if (mInstance == null) {
                    mInstance = new GenericModel();
                }
            }
        }
        return mInstance;
    }

    private int mFrameID;

    public int getFrameID() {
        return mFrameID;
    }

    public void setFrameID(int frameID) {
        this.mFrameID = frameID;
    }
}
