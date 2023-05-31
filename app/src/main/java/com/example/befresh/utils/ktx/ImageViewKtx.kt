package com.gocarhub.utils.ktx

import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.io.File

interface GlideCallback {
    fun ready()
    fun fail()
}

fun ImageView.setImageFromUrl(
    path: String?,
    error: Int,
    placeholder: Int,
    progress: View?,

    glideCallback: GlideCallback?
) {
    path?.let {
        progress?.isVisible = true
        Glide.with(context).load(path).error(error).placeholder(placeholder)
            . override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    progress?.isVisible = false
                    glideCallback?.fail()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    progress?.isVisible = false
                    glideCallback?.ready()
                    return false
                }
            }).into(this)
    } ?: this.apply {
        glideCallback?.fail()
       Log.e("ERROR","Image not found")
    }

}

fun ImageView.setBitmapFromUri(uri: Uri?) {
    uri?.let {
        Glide.with(this.context).asBitmap().load(uri)
            .into(this)
    } ?: this.apply {
        showSnackBar("Image not found try again")
    }

}

fun ImageView.setBitmapFromDrawable(image: Int?) {

    image?.let {
        Glide.with(this.context).load(image)
            .into(this)
    } ?: this.apply {
        showSnackBar("Image not found try again")
    }
}

fun ImageView.setBitmapFromFile(image: File) {

    image?.let {
        Glide.with(this.context).load(image)
            .into(this)
    } ?: this.apply {
        showSnackBar("Image not found try again")
    }
}

