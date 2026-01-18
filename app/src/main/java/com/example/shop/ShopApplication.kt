package com.example.shop

import android.app.Application
import android.content.Context
import com.example.shop.di.DatabaseModule
import com.example.shop.di.RepositoryModule
import com.example.shop.di.RetrofitModule
import com.example.shop.di.UseCaseModule
import com.example.shop.di.UtilsModule
import com.example.shop.di.ViewModelModule
import com.example.shop.presentation.activity.ShopActivity
import com.example.shop.presentation.addItemScreen.fragments.AddItemFragment
import com.example.shop.presentation.loadItemScreen.fragments.LoadItemsFragment
import com.example.shop.presentation.updateItemScreen.fragments.UpdateItemFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


class ShopApplication: Application() {
    val component: MyAppComponent = DaggerMyAppComponent.factory().create(this)
}
@Component(modules = [DatabaseModule::class, RepositoryModule::class, RetrofitModule::class, UseCaseModule::class, UtilsModule::class, ViewModelModule::class])
@Singleton
interface MyAppComponent{
    fun inject(shopActivity: ShopActivity)
    fun inject(addItemFragment: AddItemFragment)
    fun inject(loadItemsFragment: LoadItemsFragment)
    fun inject(updateItemFragment: UpdateItemFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MyAppComponent
    }
}