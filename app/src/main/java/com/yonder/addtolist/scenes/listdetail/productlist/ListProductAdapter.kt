package com.yonder.addtolist.scenes.listdetail.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseListAdapter
import com.yonder.addtolist.domain.decider.CurrencyDecider
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

class ListProductAdapter(
    private val currencyDecider: CurrencyDecider,
    private var listProductCallbacks: ListProductCallbacks
) :
    BaseListAdapter<UserListProductUiModel>(
        itemsSame = { old, new -> old.id == new.id },
        contentsSame = { old, new -> old == new }
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_product, parent, false)
        return ListProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ListProductViewHolder -> {
                holder.bind(
                    product = getItem(position),
                    position = position,
                    productCallbacks = listProductCallbacks,
                    currencyDecider = currencyDecider
                )
            }
        }
    }

}
