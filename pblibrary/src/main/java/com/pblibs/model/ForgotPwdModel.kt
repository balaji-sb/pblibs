package com.pblibs.model

import com.pblibs.utility.PBConstants

/**
 * Created by balaji on 12/4/20 12:57 AM
 */


data class ForgotPwdModel(
    var userEmail: String? = PBConstants.DEFAULT,
    var mobileNumber: String? = PBConstants.DEFAULT
    )