package com.yonder.addtolist.scenes.createlist

import android.view.LayoutInflater
import androidx.core.text.trimmedLength
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.cursorEnd
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.common.utils.decider.ColorDecider
import com.yonder.addtolist.core.extensions.LENGTH_ZERO
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.databinding.FragmentCreateListBinding
import com.yonder.statelayout.State
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateListFragment : BaseFragment<FragmentCreateListBinding>() {

  val viewModel: CreateListViewModel by viewModels()

  @Inject
  lateinit var colorDecider: ColorDecider

  override fun initBinding(inflater: LayoutInflater) = FragmentCreateListBinding.inflate(inflater)

  private val listNames get() = requireContext().resources.getStringArray(R.array.list_names)

  private val listColors get() = requireContext().resources.getIntArray(R.array.rainbow)


  override fun initObservers() {
    viewModel.state.observe(viewLifecycleOwner) { viewState ->
      when (viewState) {
        is CreateListViewState.ShowBlankListNameError -> {
          showEmptyListError()
        }
        is CreateListViewState.Loading -> {
          binding.stateLayout.setState(State.LOADING)
        }
        is CreateListViewState.ListCreated -> {
          closeFragment()
        }
        else -> Unit
      }
    }
  }

  private fun showEmptyListError() {
    binding.textField.error = getString(R.string.list_name_should_not_be_empty)
    binding.textField.isErrorEnabled = true
  }

  override fun initViews() {
    initHorizontalPicker()
    initCreateBtn()
    initEditText()
  }

  private fun initCreateBtn() = with(binding) {
    btnCreateList.setSafeOnClickListener {
      val listName = tilName.text.toString()
      viewModel.createList(
        listName = listName,
        listColor = colorDecider.convertToHexString(yoColorPicker.getSelectedColorResId())
      )
    }
  }

  private fun initHorizontalPicker() = with(binding) {
    horizontalPicker.initView(listNames) { listName ->
      tilName.setText(listName)
      tilName.cursorEnd()
    }
    yoColorPicker.initView(listColors)
  }

  private fun initEditText() = with(binding.tilName) {
    addTextChangedListener { editable ->
      if (editable?.trimmedLength().orZero() > LENGTH_ZERO) {
        binding.textField.isErrorEnabled = false
      }
    }
  }

}
