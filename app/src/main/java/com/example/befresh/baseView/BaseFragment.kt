package com.example.befresh.baseView

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.befresh.MainActivity
import com.gocarhub.utils.ktx.loaderManager


abstract class BaseFragment<T : ViewBinding> : Fragment() {
   lateinit var binding: T
    var mainActivity: MainActivity? = null
    public abstract fun setUpUi(binding: T)

    private fun getContainerActivity(): MainActivity? {
        return mainActivity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            this@BaseFragment.mainActivity = context
        }
    }

    @LayoutRes
    abstract fun getLayoutResID(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::binding.isInitialized) {
            binding = DataBindingUtil.inflate(inflater, getLayoutResID(), container, false)
            setUpUi(binding)
        }
//        getContainerActivity()?.let {
//            it.configToolbar(configToolbar())
//            it.isBottomBar(showBottomBar())
//            it.isToolbarVisible(showToolbar())
//        }
        return binding.root
    }

    fun showProgress(context: Context) {
        /*dialog.setContentView(R.layout.custom_progress)
        dialog.setCancelable(false)
        dialog.show()*/
        requireActivity().loaderManager(true)
    }

    fun dismissProgress() {
        /*if (dialog.isShowing)
            dialog.dismiss()*/
        requireActivity().loaderManager(false)
    }

    fun showApiError(msg : String){
        Toast.makeText(context, "Error is $msg", Toast.LENGTH_LONG).show()
    }
//    fun showToast(error: String?) {
//        if (!TextUtils.isEmpty(error))
//            error?.let {
//                val typeface = ResourcesCompat.getFont(requireContext(), R.font.montserrat_medium)!!
//                Alerter.create(requireActivity())
//                    .setText(it)
//                    .setTextTypeface(typeface)
//                    .hideIcon()
//                    .setBackgroundColorRes(R.color.purple_500)
//                    .setOnShowListener {
//
//                    }
//                    .show()
//            }
//    }
}