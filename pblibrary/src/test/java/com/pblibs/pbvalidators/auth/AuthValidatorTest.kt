package com.pblibs.pbvalidators.auth

import android.content.Context
import com.pblibrary.proggyblast.R
import com.pblibs.base.PBApplication
import com.pblibs.model.ForgotPwdModel
import com.pblibs.model.LoginModel
import com.pblibs.utility.PBConstants
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by balaji on 11/4/20 9:01 PM
 */

@RunWith(MockitoJUnitRunner::class)
class AuthValidatorTest {

    @Mock
    private lateinit var mContext: Context

    @Before
    fun setUp() {
        PBApplication.getInstance().setContext(mContext)
    }

    @Test
    fun checkUsername() {
        `when`(mContext.getString(R.string.empty_username)).thenReturn("empty username")
        `when`(mContext.getString(R.string.invalid_username)).thenReturn("invalid username")
        val loginModel = LoginModel()
        loginModel.username = ""
        loginModel.password = "test"
        var name = AuthValidator.validateLogin(loginModel)
        assertTrue(name == mContext.getString(R.string.empty_username))
        loginModel.username = "12"
        loginModel.password = "test"
        name = AuthValidator.validateLogin(loginModel)
        assertTrue(name == mContext.getString(R.string.invalid_username))
    }

    @Test
    fun checkPassword() {
        `when`(mContext.getString(R.string.empty_password)).thenReturn("empty password")
        val loginModel = LoginModel()
        loginModel.userEmail = "sbb@gmail.com"
        loginModel.password = ""
        var name = AuthValidator.validateLogin(loginModel)
        assertEquals(name, mContext.getString(R.string.empty_password))
    }

    @Test
    fun checkLogin() {
        val loginModel = LoginModel()
        loginModel.userEmail = "sbb@gmail.com"
        loginModel.password = "test"
        val name = AuthValidator.validateLogin(loginModel)
        assertTrue(name == PBConstants.EMPTY)
    }

    @Test
    fun checkEmail() {
        `when`(mContext.getString(R.string.empty_email)).thenReturn("empty email")
        `when`(mContext.getString(R.string.invalid_email)).thenReturn("invalid email")
        val loginModel = LoginModel()
        loginModel.userEmail = "sbb@gmail"
        loginModel.password = "test"
        var name = AuthValidator.validateLogin(loginModel)
        assertEquals(name, mContext.getString(R.string.invalid_email))
        loginModel.userEmail = ""
        loginModel.password = "test"
        name = AuthValidator.validateLogin(loginModel)
        assertEquals(name, mContext.getString(R.string.empty_email))
    }


    @Test
    fun checkForgotPwdWithEmail() {
        `when`(mContext.getString(R.string.empty_email)).thenReturn("empty email")
        `when`(mContext.getString(R.string.invalid_email)).thenReturn("invalid email")
        val forgotPwdModel = ForgotPwdModel()
        forgotPwdModel.userEmail = ""
        var name = AuthValidator.validateForgotPassword(forgotPwdModel)
        assertEquals(name, mContext.getString(R.string.empty_email))
        forgotPwdModel.userEmail = "sbb"
        name = AuthValidator.validateForgotPassword(forgotPwdModel)
        assertEquals(name, mContext.getString(R.string.invalid_email))
        forgotPwdModel.userEmail = "sbb@gmail.com"
        name = AuthValidator.validateForgotPassword(forgotPwdModel)
        assertEquals(name, PBConstants.EMPTY)
    }

    @Test
    fun checkForgotPwdWithMobileNum() {
        `when`(mContext.getString(R.string.empty_mobile_num)).thenReturn("empty mobile num")
        `when`(mContext.getString(R.string.invalid_mobile_num)).thenReturn("invalid mobile num")
        val forgotPwdModel = ForgotPwdModel()
        forgotPwdModel.mobileNumber = ""
        var name = AuthValidator.validateForgotPassword(forgotPwdModel)
        assertEquals(name, mContext.getString(R.string.empty_mobile_num))
        forgotPwdModel.mobileNumber = "12"
        name = AuthValidator.validateForgotPassword(forgotPwdModel)
        assertEquals(name, mContext.getString(R.string.invalid_mobile_num))
        forgotPwdModel.mobileNumber = "+918122336740"
        name = AuthValidator.validateForgotPassword(forgotPwdModel)
        assertEquals(name, PBConstants.EMPTY)
    }
}