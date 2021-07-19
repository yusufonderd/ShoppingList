package com.yonder.addtolist.scenes.createlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.cursorEnd
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.common.utils.decider.ColorDecider
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

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViews()
    observeLiveData()
  }

  private fun observeLiveData() {
    viewModel.state.observe(viewLifecycleOwner) { viewState ->
      when (viewState) {
        is CreateListViewState.EmptyListName -> {
          showEmptyListError()
        }
        is CreateListViewState.Loading -> {
          binding.stateLayout.setState(State.LOADING)
        }
        is CreateListViewState.Success -> {
          binding.stateLayout.setState(State.CONTENT)
        }
        is CreateListViewState.Error -> {
          showSnackBar(viewState.errorMessage)
        }
        else -> Unit
      }
    }
  }

  private fun showEmptyListError() {
    showToastMessage(R.string.list_name_should_not_be_empty)
  }

  private fun initViews() = with(binding) {

    horizontalPicker.initView(listNames) { listName ->
      binding.tilName.setText(listName)
      binding.tilName.cursorEnd()
    }

    yoColorPicker.initView(listColors)
    btnCreateList.setSafeOnClickListener {
      val listName = tilName.text.toString()
      viewModel.createList(
        listName = listName,
        listColor = colorDecider.convertToHexString(yoColorPicker.getSelectedColorResId())
      )
    }
  }

}
