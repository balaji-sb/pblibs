package com.pblibs.ftue

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.pblibrary.proggyblast.R
import com.pblibs.base.PBApplication
import com.pblibs.base.PBBaseActivity
import kotlinx.android.synthetic.main.activity_splash.*


/**
 * Created by Proggy Blast on 20/11/19
 */


open abstract class PBSplashActivity : PBBaseActivity() {

    private lateinit var handler: Handler
    private lateinit var redirectClassName: String
    private var isCheckPermission = true
    private var permissions = arrayOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        PBApplication.getInstance().context = this@PBSplashActivity
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
        initValues()
    }

    /**
     * Initialize values
     */

    private fun initValues() {
        handler = Handler()
    }

    /**
     * to set logo and name of the splash page
     */

    protected fun setSplashContent(name: String, image: Int?) {
        try {
            val bgImage = image ?: R.drawable.fallback_logo
            logoImg.setImageResource(bgImage)
            logoTitle.setText(name)
        } catch (ex: Exception) {
            ex.printStackTrace()
            logoImg.setImageResource(R.drawable.fallback_logo)
        }
    }

    /**
     * set logo title visiblity
     */

    protected fun setTitleVisible(isVisible: Boolean) {
        if (isVisible) {
            logoTitle.visibility = View.VISIBLE
        } else {
            logoTitle.visibility = View.GONE
        }
    }

    /**
     * set splash drawable background
     */

    protected fun setSplashBgDrawable(resource: Int) {
        try {
            splash_root_constraint.setBackgroundResource(resource)
        } catch (ex: Exception) {
            ex.printStackTrace();
        }
    }

    /**
     * set splash color background
     */

    protected fun setSplashBgColor(color: Int) {
        try {
            splash_root_constraint.setBackgroundColor(ContextCompat.getColor(mContext, color));
        } catch (ex: Exception) {
            ex.printStackTrace();
        }
    }

    /**
     * to set the redirect activity from splash page
     */

    protected fun setRedirectActivity(
        className: String,
        splashDuration: Long,
        checkPermission: Boolean,
        lpermissions: Array<String>
    ) {
        isCheckPermission = checkPermission
        if (isCheckPermission && !lpermissions.isNullOrEmpty()) {
            permissions = lpermissions
        }
        redirectClassName = className
        handler.postDelayed(runnable, splashDuration)
    }

    val runnable = Runnable {
        if (isCheckPermission) {
            if (checkPermission(permissions)) launchRedirectPage()
            else askPermission(permissions);
        } else {
            launchRedirectPage()
        }
    }

    /**
     * To redirect specific activity
     */

    private fun launchRedirectPage() {
        navigateActivity(redirectClassName, true,null)
    }


    /**
     * Removing runnable callbacks for handler
     */

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    /**
     * Handle runtime permission is granted or not
     */

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        var isGrant = false
        for (i in 0 until grantResults.size) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                isGrant = true
            } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                val isNextDontAsk = shouldShowRequestPermissionRationale(permissions[i])
                if (!isNextDontAsk) {
                    launchRedirectPage()
                    return
                }
            }
        }

        if (isGrant) {
            launchRedirectPage()
        } else askPermission(permissions)
    }

    abstract fun getSplashInterval(): Long

}