package com.pblibs.pbinterfaces

/**
 * Created by balaji on 11/4/20 7:41 PM
 */


interface PBValidatorCallback {

    fun onValidateSuccess(result: Any)

    fun onValidateError(result: Any)
}