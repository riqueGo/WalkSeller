package com.rique.walkseller.DI

import android.content.Context
import com.rique.walkseller.interfaces.ISellerRepository
import com.rique.walkseller.repository.MockSellerRepository
import com.rique.walkseller.viewModel.MapViewModel
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
    fun provideMapViewModel(sellerRepository: ISellerRepository): MapViewModel {
        return MapViewModel(sellerRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSellerRepository(@ApplicationContext context: Context): ISellerRepository {
        return MockSellerRepository(context)
    }
}