package com.example.befresh.view.fragment.explore

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.befresh.R
import com.example.befresh.R.drawable
import com.example.befresh.baseView.BaseFragment
import com.example.befresh.baseView.BaseViewModel
import com.example.befresh.databinding.FragmentExploreBinding
import com.example.befresh.model.request.CategoryData
import com.example.befresh.model.response.GetCategoryResponse
import com.example.befresh.model.response.GetMenuItemResponse
import com.example.befresh.utils.CommonMethod
import com.example.befresh.view.fragment.shop.adapter.ListShowAdapter
import com.example.befresh.viewModel.CategoryViewModel
import com.gocarhub.utils.ktx.loaderManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>(), ListItemAdapter.onClickChek {
    lateinit var adapter: ListItemAdapter

    var list = ArrayList<CategoryData>()
    private val viewModel: CategoryViewModel by viewModels()

    override fun getLayoutResID(): Int {
        return R.layout.fragment_explore
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        viewModel.getCategory()
    }

    private fun initView() {

        adapter = ListItemAdapter(this, list)
        binding.rvExplore.adapter = adapter

    }

    override fun setUpUi(binding: FragmentExploreBinding) {
        setUpObserver()
    }

    private fun setUpObserver() {
        binding.apply {
            viewModel.apply {
                viewStateLiveData.observe(viewLifecycleOwner) {
                    when (it.state) {
                        BaseViewModel.State.LOADING -> {
                            requireContext().loaderManager(false)
                        }
                        BaseViewModel.State.SUCCESS -> {
                            requireContext().loaderManager(false)
                        }
                        BaseViewModel.State.ERROR -> {
                            requireContext().loaderManager(false)
                            it.message?.let { it1 ->
                                CommonMethod.showToast(requireContext(), it1)
                            }
                        }
                    }
                }

                categoryLive.observe(viewLifecycleOwner) {
                    when (it.success) {
                        true -> {
                            val response = it.response as GetCategoryResponse
                            if (response.data != null) {
                                list.clear()
                                list.addAll(response.data)
                                adapter.notifyDataSetChanged()

                            }
                        }
                        else -> {
                            // showToast(requireContext(),)
                        }
                    }
                }

            }
        }
    }

    override fun checkBoxCheck(listModel: CheckModel) {
    }


}