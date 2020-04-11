package com.pblibs.pbvalidators.auth

import android.content.Context
import com.pblibrary.proggyblast.R
import com.pblibs.base.PBApplication
import com.pblibs.model.ForgotPwdModel
import com.pblibs.model.LoginModel
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

        fun validateLogin(loginModel: LoginModel): String {
            val mContext: Context = PBApplication.getInstance().context
            val mPbUtils = PBUtils()
            val isEmailAvail = loginModel.userEmail !== PBConstants.DEFAULT
            val isUsernameAvail = loginModel.username !== PBConstants.DEFAULT
            val isMobileNumAvail = loginModel.mobileNumber !== PBConstants.DEFAULT
            var emailUsernameVal = PBConstants.EMPTY
            if (isEmailAvail)
                emailUsernameVal = mPbUtils.validateEmail(loginModel.userEmail)
            else if (isUsernameAvail)
                emailUsernameVal = mPbUtils.validateUserName(loginModel.username, 3)
            else if (isMobileNumAvail)
                emailUsernameVal = mPbUtils.validateMobileNumber(loginModel.mobileNumber)

            if (emailUsernameVal != PBConstants.EMPTY) {
                return emailUsernameVal
            } else if (loginModel.password!!.trim().length == 0) {
                return mContext.getString(R.string.empty_password);
            }
            return emailUsernameVal
        }

        /**
         * validate forgot password
         */

        fun validateForgotPassword(forgotPwdModel: ForgotPwdModel): String {
            val mPbUtils = PBUtils()
            val isEmailAvail = forgotPwdModel.userEmail !== PBConstants.DEFAULT
            val isMobileNumAvail = forgotPwdModel.mobileNumber !== PBConstants.DEFAULT
            var emailUsernameVal = PBConstants.EMPTY
            if (isEmailAvail)
                emailUsernameVal = mPbUtils.validateEmail(forgotPwdModel.userEmail)
            else if (isMobileNumAvail)
                emailUsernameVal = mPbUtils.validateMobileNumber(forgotPwdModel.mobileNumber)
            return emailUsernameVal
        }

    }


}
