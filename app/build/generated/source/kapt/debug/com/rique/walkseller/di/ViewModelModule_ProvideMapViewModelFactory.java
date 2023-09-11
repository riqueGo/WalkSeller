package com.rique.walkseller.di;

import com.rique.walkseller.interfaces.ISellerRepository;
import com.rique.walkseller.ui.viewModel.MapViewModel;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("dagger.hilt.android.scopes.ViewModelScoped")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ViewModelModule_ProvideMapViewModelFactory implements Factory<MapViewModel> {
  private final Provider<ISellerRepository> sellerRepositoryProvider;

  public ViewModelModule_ProvideMapViewModelFactory(
      Provider<ISellerRepository> sellerRepositoryProvider) {
    this.sellerRepositoryProvider = sellerRepositoryProvider;
  }

  @Override
  public MapViewModel get() {
    return provideMapViewModel(sellerRepositoryProvider.get());
  }

  public static ViewModelModule_ProvideMapViewModelFactory create(
      Provider<ISellerRepository> sellerRepositoryProvider) {
    return new ViewModelModule_ProvideMapViewModelFactory(sellerRepositoryProvider);
  }

  public static MapViewModel provideMapViewModel(ISellerRepository sellerRepository) {
    return Preconditions.checkNotNullFromProvides(ViewModelModule.INSTANCE.provideMapViewModel(sellerRepository));
  }
}
