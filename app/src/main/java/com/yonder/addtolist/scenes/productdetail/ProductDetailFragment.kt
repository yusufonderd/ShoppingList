package com.yonder.addtolist.scenes.productdetail

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButtonToggleGroup
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {


  private val args: ProductDetailFragmentArgs by navArgs()

  val viewModel: ProductDetailViewModel by viewModels()

  override fun initBinding(inflater: LayoutInflater) =
    FragmentProductDetailBinding.inflate(inflater)

  override fun initObservers() {

  }

  override fun initViews() {
    binding.etProductName.setText(args.userListProduct.name)
    binding.etPrice.setText(args.userListProduct.wrappedPrice())
    binding.etQuantity.setText(args.userListProduct.wrappedQuantityWith(requireContext()))
    setUnit(binding.toggleButton,args.userListProduct.unit)

  }

  fun setUnit(group: MaterialButtonToggleGroup, unit: String?) {
    when (unit) {
      PIECE -> {
        group.check(R.id.button1)
      }
      PACKAGE -> {
        group.check(R.id.button2)
      }
      KG -> {
        group.check(R.id.button3)
      }
      LT -> {
        group.check(R.id.button4)
      }
    }

  }

  companion object {
    const val VIBRATE_MILLISECOND: Long = 50
    const val KG = "kg"
    const val LT = "lt"
    const val PACKAGE = "package"
    const val PIECE = "piece"
  }


}