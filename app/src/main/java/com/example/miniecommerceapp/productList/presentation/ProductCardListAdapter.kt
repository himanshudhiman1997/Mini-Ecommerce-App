package com.example.miniecommerceapp.productList.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.miniecommerceapp.R
import com.example.miniecommerceapp.databinding.ProductCardBinding

class ProductCardListAdapter(val onItemClicked: (ProductCardViewState) -> Unit) :
    RecyclerView.Adapter<ProductCardListAdapter.ViewHolder>() {
    private var data: List<ProductCardViewState> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(productList: List<ProductCardViewState>) {
        this.data = productList
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(productCardViewState: ProductCardViewState) {
            val bind = ProductCardBinding.bind(itemView)

            bind.apply {
                viewProductName.text = productCardViewState.title
                viewProductDescription.text = productCardViewState.description
                productPrice.text = productCardViewState.price

                viewWishlistIcon.setImageDrawable(
                    if (productCardViewState.isFavorite) {
                        ResourcesCompat.getDrawable(
                            viewWishlistIcon.resources,
                            R.drawable.ic_baseline_favorite,
                            null
                        )
                    } else {
                        ResourcesCompat.getDrawable(
                            viewWishlistIcon.resources,
                            R.drawable.ic_baseline_favorite_disabled,
                            null
                        )
                    }
                )

                Glide.with(itemView.context).load(productCardViewState.imageUrl)
                    .into(productImage)
            }


            itemView.setOnClickListener {
                onItemClicked(productCardViewState)
            }
        }
    }

}