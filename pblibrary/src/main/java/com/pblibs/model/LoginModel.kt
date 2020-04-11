package com.pblibs.model

import com.pblibs.utility.PBConstants

/**
 * Created by balaji on 11/4/20 7:13 PM
 */


data class LoginModel(
    var userEmail: String? = PBConstants.DEFAULT,
    var password: String? = PBConstants.DEFAULT,
    var mobileNumber: String? = PBConstants.DEFAULT,
    var username: String? = PBConstants.DEFAULT
)