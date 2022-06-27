package com.ingelmogarcia.appnapptilus.ui.view.activity

import android.app.ProgressDialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.ingelmogarcia.appnapptilus.R
import com.ingelmogarcia.appnapptilus.data.model.OompaLoompaDetailModel
import com.ingelmogarcia.appnapptilus.databinding.ActivityOompaLoompaDetailBinding
import com.ingelmogarcia.appnapptilus.ui.adapter.MyViewPagerAdapter
import com.ingelmogarcia.appnapptilus.ui.viewmodel.OompaLoompaDetailViewModel

class OompaLoompaDetailActivity : AppCompatActivity() {

    companion object{
        var oompaLoompaDetail: OompaLoompaDetailModel? = null
    }

    private val KEY_OOMPALOOMPAID = "oompaLoompaId"
    private lateinit var binding: ActivityOompaLoompaDetailBinding
    private val viewModel : OompaLoompaDetailViewModel by viewModels()
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOompaLoompaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            setSupportActionBar(detailToolbar)
            detailToolbar.setNavigationOnClickListener { onBackClicked() }
        }

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val params = intent.extras
        val oompaLoompaId = params?.getInt(KEY_OOMPALOOMPAID,-1)

        if (oompaLoompaId != null && oompaLoompaId != -1){
            viewModel.downloadOompaLoompaDetail(oompaLoompaId)
        }

        viewModel.isLoading.observe(this, Observer(this::handleProgressDialog))
        viewModel.oompaLoompaDetailModel.observe(this, Observer (this::setData))
    }

    fun handleProgressDialog(boolean: Boolean){
        if(boolean){
            enabledProgressDialog()
        }else{
            disabledProgressDialog()
        }
    }

    fun setData(oompaLoompaDetail: OompaLoompaDetailModel) {
        OompaLoompaDetailActivity.oompaLoompaDetail = oompaLoompaDetail

        val imageGender = if (oompaLoompaDetail.gender.equals("F")) {
            R.drawable.img_male
        } else {
            R.drawable.img_female
        }

        with(binding) {
            collapsingToolbar.title =
                oompaLoompaDetail.first_name + " " + oompaLoompaDetail.last_name
            Glide
                .with(image.context)
                .load(oompaLoompaDetail.image)
                .into(image)
            Glide
                .with(genderImage.context)
                .load(imageGender)
                .into(genderImage)
        }

        setUpViewPager()
    }

    fun setUpViewPager(){
        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())
        binding.viewPager.adapter = MyViewPagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewPager){ tab, index ->
            tab.text = when(index){
                0 -> { "Info" }
                1 -> { "Song" }
                2 -> { "Random String" }
                3 -> { "Quota" }
                else -> { throw Resources.NotFoundException(getString(R.string.notFoundException)) }
            }
        }.attach()
    }

    private fun enabledProgressDialog(){
        progressDialog = ProgressDialog(this)
        progressDialog.show()
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun disabledProgressDialog(){
        progressDialog.dismiss()
        progressDialog.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun onBackClicked() {
        finish()
    }
}




private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f

class ZoomOutPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 1 -> { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horzMargin = pageWidth * (1 - scaleFactor) / 2
                    translationX = if (position < 0) {
                        horzMargin - vertMargin / 2
                    } else {
                        horzMargin + vertMargin / 2
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    // Fade the page relative to its size.
                    alpha = (MIN_ALPHA +
                            (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
        }
    }
}


