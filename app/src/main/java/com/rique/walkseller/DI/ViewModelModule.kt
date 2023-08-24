package com.rique.walkseller.DI

import android.content.Context
import com.rique.walkseller.interfaces.ISellerRepository
import com.rique.walkseller.repository.MockSellerRepository
import com.rique.walkseller.ui.viewModel.MapViewModel
import com.rique.walkseller.ui.viewModel.SellerMarkersViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideMapViewModel(sellerRepository: ISellerRepository, sellerMarkersViewModel: SellerMarkersViewModel): MapViewModel {
        return MapViewModel(sellerRepository, sellerMarkersViewModel)
    }

    @Provides
    @ViewModelScoped
    fun provideSellerRepository(@ApplicationContext context: Context): ISellerRepository {
        return MockSellerRepository(context)
    }

    @Provides
    @ViewModelScoped
    fun provideSellerMarkersViewModel(): SellerMarkersViewModel {
        return SellerMarkersViewModel()
    }
}