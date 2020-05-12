package com.pblibs.uiwidgets

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.pblibrary.proggyblast.R
import com.pblibs.model.GalleryModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_gallery_viewpager.view.*
import kotlinx.android.synthetic.main.layout_viewpager.view.*

/**
 * Created by balaji on 12/5/20 4:54 PM
 */

abstract open class GalleryViewPager(context: Context) : ViewPager(context), ViewPager.OnPageChangeListener {

    private var mGalleryList: List<GalleryModel>

    init {
        mGalleryList = getLayoutList()
        viewPager.addOnPageChangeListener(this)
        val adapter = GalleryViewPagerAdapter(mGalleryList)
        viewPager.adapter = adapter
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageSelected(position: Int) {
    }

    override fun onPageScrolled(position: Int, offset: Float, offsetPixels: Int) {
        super.onPageScrolled(position, offset, offsetPixels)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(getSelectedItem())
    }

    class GalleryViewPagerAdapter(mGalleryList: List<GalleryModel>) : PagerAdapter() {

        var mLayoutList = mGalleryList

        override fun getCount(): Int {
            return mLayoutList.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(container.context)
                .inflate(R.layout.activity_gallery_viewpager, container, false)
            try {
                val image = mLayoutList.get(position).imagePath
                if (!image.isEmpty()) {
                    Picasso.get().load(image).into(view.bannerImg)
                } else {
                    view!!.bannerImg.setImageResource(R.drawable.fallback_logo)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            container.addView(view)
            return view
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }

    }

    abstract fun getSelectedItem(): Int

    abstract fun getLayoutList(): List<GalleryModel>


}