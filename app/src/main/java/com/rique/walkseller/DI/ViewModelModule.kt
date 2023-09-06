package com.rique.walkseller.DI

import com.google.firebase.database.FirebaseDatabase
import com.rique.walkseller.interfaces.IProductRepository
import com.rique.walkseller.interfaces.ISellerRepository
import com.rique.walkseller.repository.ProductRepository
import com.rique.walkseller.repository.SellerRepository
import com.rique.walkseller.ui.viewModel.MapViewModel
import com.rique.walkseller.ui.viewModel.OrderViewModel
import com.rique.walkseller.ui.viewModel.ProductsViewModel
import com.rique.walkseller.ui.viewModel.SellerBottomSheetViewModel
import com.rique.walkseller.ui.viewModel.SellerMarkersViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideMapViewModel(sellerRepository: ISellerRepository): MapViewModel {
        return MapViewModel(sellerRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideProductsViewModel(productRepository: IProductRepository): ProductsViewModel {
        return ProductsViewModel(productRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSellerMarkersViewModel(): SellerMarkersViewModel {
        return SellerMarkersViewModel()
    }

    @Provides
    @ViewModelScoped
    fun provideSellerBottomSheetViewModel(): SellerBottomSheetViewModel {
        return SellerBottomSheetViewModel()
    }

    @Provides
    @ViewModelScoped
    fun provideOrderViewModel(): OrderViewModel {
        return OrderViewModel()
    }

    @Provides
    @ViewModelScoped
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @ViewModelScoped
    fun provideSellerRepository(): ISellerRepository {
        return SellerRepository()
    }

    @Provides
    @ViewModelScoped
    fun provideProductRepository(): IProductRepository {
        return ProductRepository()
    }
}