package com.pblibs.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pblibs.utility.PBSessionManager;

abstract public class PBBaseFragment extends Fragment {

    protected Context mContext;
    protected Activity mActivity;
    protected PBSessionManager mPbSessionManager;
    protected View mView;
    protected String mUserId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mContext = PBApplication.getInstance().getContext();
        mPbSessionManager = PBSessionManager.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getContentView(), container, false);
        return mView;
    }

    public abstract int getContentView();
}
