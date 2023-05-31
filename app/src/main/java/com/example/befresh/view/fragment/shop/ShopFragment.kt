package com.example.befresh.view.fragment.shop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.befresh.MainActivity
import com.example.befresh.R
import com.example.befresh.databinding.FragmentShopBinding
import com.example.befresh.baseView.BaseFragment
import com.example.befresh.baseView.BaseViewModel
import com.example.befresh.model.response.GetMenuItemResponse
import com.example.befresh.utils.CommonMethod
import com.example.befresh.utils.CommonMethod.showToast
import com.example.befresh.view.fragment.shop.adapter.ListOfferNameAdapter
import com.example.befresh.view.fragment.shop.adapter.OnClickOfferItem
import com.example.befresh.viewModel.ProductHomeViewModel
import com.gocarhub.utils.ktx.loaderManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopFragment : BaseFragment<FragmentShopBinding>(), OnClickOfferItem {

    lateinit var offerAdapter: ListOfferNameAdapter


    private val viewModel: ProductHomeViewModel by viewModels()

    override fun getLayoutResID(): Int {
        return R.layout.fragment_shop
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).isBottomBar(true)


      //  viewModel.getMenuItemViewModel()

        viewModel.getMenuItemViewModel()

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
                                showToast(requireContext(), it1)
                            }
                        }
                    }
                }

                getMenuItemLive.observe(viewLifecycleOwner) {
                    when (it.success) {
                        true -> {
                            val response = it.response as GetMenuItemResponse
                            if (response.data != null) {
                                offerAdapter.setData(response.data.data as ArrayList<GetMenuItemResponse.DataX>)
                                offerAdapter.notifyDataSetChanged()
                                binding.shimmerFrameLayout.stopShimmer()
                                binding.shimmerFrameLayout.visibility = View.GONE
                                binding.rvOffer.visibility = View.VISIBLE
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

    fun getData() {
        if (CommonMethod.checkIfHasNetwork()) {
            binding.rvOffer.smoothScrollToPosition(0)
            binding.rvOffer.visibility = View.GONE
            binding.shimmerFrameLayout.startShimmer()
            binding.shimmerFrameLayout.visibility = View.VISIBLE
            //Filter List
//            binding.tvNoData.visibility = View.GONE
//            viewModel.newsList.value = null
//            offerAdapter.clearData()
//            mScrollListener?.resetState()
//            viewModel.storiesParams.apply {
//                limit = 20
//                offset = 0
//            }
            viewModel.getMenuItemViewModel()
        } else {
            showToast(requireContext(), "internet no")
        }
    }


    private fun allClicksListeners() {
        offerAdapter = ListOfferNameAdapter(this, arrayListOf())
        binding.rvOffer.adapter = offerAdapter

    }

    override fun onOfferClickItem() {
        findNavController().navigate(R.id.addCartFragment)
    }

    private fun sliderImageSet() {
        val imageList = ArrayList<SlideModel>()
        imageList.add(
            SlideModel(
                "https://bit.ly/2YoJ77H",
                "",
                ScaleTypes.CENTER_CROP
            )
        )
        imageList.add(
            SlideModel(
                "https://bit.ly/2BteuF2",
                "",
                ScaleTypes.CENTER_CROP
            )
        )
        imageList.add(SlideModel("https://bit.ly/3fLJf72", "", ScaleTypes.CENTER_CROP))

        binding.imageSlider.setImageList(imageList)

        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {

                findNavController().navigate(R.id.addCartFragment)

                // You can listen here
//                binding.imageSlider.startSliding(3000) // with new period
//                binding.imageSlider.startSliding()
//                binding.imageSlider.stopSliding()
            }
        })

    }

    override fun setUpUi(binding: FragmentShopBinding) {
        allClicksListeners()
        sliderImageSet()
        setUpObserver()
      //  getData()
    }


}