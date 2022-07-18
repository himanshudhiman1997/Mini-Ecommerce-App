package com.example.miniecommerceapp.shared.wishlist.data.repository.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.example.miniecommerceapp.shared.wishlist.data.repository.WishlistRepository

class WishlistSharedPrefRepo(context: Context) : WishlistRepository {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        SHARED_PREFS_FILE_WISHLIST, Context.MODE_PRIVATE
    )

    override suspend fun isFavorite(productId: String): Boolean {
        with(sharedPreferences) {
            val favorites = getStringSet(KEY_FAVORITES_SET, emptySet())
            return favorites!!.contains(productId)
        }
    }

    override suspend fun addToWishlist(productId: String) {
        with(sharedPreferences) {
            val favorites = getStringSet(KEY_FAVORITES_SET, emptySet())
            mutableSetOf<String>().run {
                addAll(favorites!!.toList())
                add(productId)

                sharedPreferences.edit().putStringSet(KEY_FAVORITES_SET, this).apply()
            }
        }
    }

    override suspend fun removeFromWishlist(productId: String) {
        with(sharedPreferences) {
            val favorites = getStringSet(KEY_FAVORITES_SET, emptySet())
            mutableSetOf<String>().run {
                addAll(favorites!!.toList())
                remove(productId)

                sharedPreferences.edit().putStringSet(KEY_FAVORITES_SET, this).apply()
            }
        }
    }

    companion object {

        const val SHARED_PREFS_FILE_WISHLIST = "com.example.miniecommerceapp.shared.wishlist"
        const val KEY_FAVORITES_SET = "KEY_FAVORITES_SET"
    }

}