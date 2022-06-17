package com.ingelmogarcia.appnapptilus

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.ingelmogarcia.appnapptilus.model.OompaLoompaModel

class OompaLoompaCover @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    private var image:ImageView
    private var title:TextView


    init {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.cover_oompa_loompa, this, true)

        image = view.findViewById(R.id.image)
        title = view.findViewById(R.id.title)
    }

    fun setOompaLoompa(oompaLoompaModel: OompaLoompaModel){
        Glide
            .with(context)
            .load(oompaLoompaModel.urlImage)
            .into(image)
        this.title.text = oompaLoompaModel.title
    }
}