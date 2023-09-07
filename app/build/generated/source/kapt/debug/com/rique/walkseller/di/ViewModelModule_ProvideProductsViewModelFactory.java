package com.rique.walkseller.di;

import com.rique.walkseller.interfaces.IProductRepository;
import com.rique.walkseller.ui.viewModel.ProductsViewModel;
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
public final class ViewModelModule_ProvideProductsViewModelFactory implements Factory<ProductsViewModel> {
  private final Provider<IProductRepository> productRepositoryProvider;

  public ViewModelModule_ProvideProductsViewModelFactory(
      Provider<IProductRepository> productRepositoryProvider) {
    this.productRepositoryProvider = productRepositoryProvider;
  }

  @Override
  public ProductsViewModel get() {
    return provideProductsViewModel(productRepositoryProvider.get());
  }

  public static ViewModelModule_ProvideProductsViewModelFactory create(
      Provider<IProductRepository> productRepositoryProvider) {
    return new ViewModelModule_ProvideProductsViewModelFactory(productRepositoryProvider);
  }

  public static ProductsViewModel provideProductsViewModel(IProductRepository productRepository) {
    return Preconditions.checkNotNullFromProvides(ViewModelModule.INSTANCE.provideProductsViewModel(productRepository));
  }
}
