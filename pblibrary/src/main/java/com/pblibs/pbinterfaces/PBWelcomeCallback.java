package com.pblibs.pbinterfaces;

/**
 * Created  by Proggy Blast on 16/8/19.
 */

public interface PBWelcomeCallback {

    void onSkipButtonClick();

    void onNextButtonClick();

    void onDoneButtonClick(String screenName);
}
