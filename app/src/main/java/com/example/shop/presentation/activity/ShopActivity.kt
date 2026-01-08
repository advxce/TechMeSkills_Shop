package com.example.shop.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shop.R
import com.example.shop.databinding.ActivityShopBinding
import com.example.shop.presentation.loadItemScreen.fragments.LoadItemsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopActivity : AppCompatActivity() {

    private var binding: ActivityShopBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragContainerView, LoadItemsFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
