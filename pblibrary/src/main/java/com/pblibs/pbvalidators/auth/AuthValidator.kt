package com.pblibs.pbvalidators.auth

import android.content.Context
import com.pblibrary.proggyblast.R
import com.pblibs.base.PBApplication
import com.pblibs.model.ForgotPwdRequest
import com.pblibs.model.LoginRequest
import com.pblibs.utility.PBConstants
import com.pblibs.utility.PBUtils

/**
 * Created by balaji on 11/4/20 8:37 PM
 */


class AuthValidator {

    companion object {

        /**
         * validate login
         */

        fun validateLogin(loginRequest: LoginRequest): String {
            val mContext: Context = PBApplication.getInstance().context
            val mPbUtils = PBUtils()
            val isEmailAvail = loginRequest.userEmail !== PBConstants.DEFAULT
            val isUsernameAvail = loginRequest.username !== PBConstants.DEFAULT
            val isMobileNumAvail = loginRequest.mobileNumber !== PBConstants.DEFAULT
            var emailUsernameVal = PBConstants.EMPTY
            if (isEmailAvail)
                emailUsernameVal = mPbUtils.validateEmail(loginRequest.userEmail)
            else if (isUsernameAvail)
                emailUsernameVal = mPbUtils.validateUserName(loginRequest.username, 3)
            else if (isMobileNumAvail)
                emailUsernameVal = mPbUtils.validateMobileNumber(loginRequest.mobileNumber)

            if (emailUsernameVal != PBConstants.EMPTY) {
                return emailUsernameVal
            } else if (loginRequest.password!!.trim().length == 0) {
                return mContext.getString(R.string.empty_password);
            }
            return emailUsernameVal
        }

        /**
         * validate forgot password
         */

        fun validateForgotPassword(forgotPwdRequest: ForgotPwdRequest): String {
            val mPbUtils = PBUtils()
            val isEmailAvail = forgotPwdRequest.userEmail !== PBConstants.DEFAULT
            val isMobileNumAvail = forgotPwdRequest.mobileNumber !== PBConstants.DEFAULT
            var emailUsernameVal = PBConstants.EMPTY
            if (isEmailAvail)
                emailUsernameVal = mPbUtils.validateEmail(forgotPwdRequest.userEmail)
            else if (isMobileNumAvail)
                emailUsernameVal = mPbUtils.validateMobileNumber(forgotPwdRequest.mobileNumber)
            return emailUsernameVal
        }

    }


}
