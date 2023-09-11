package com.rique.walkseller.di;

import com.rique.walkseller.ui.viewModel.SellerMarkersViewModel;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class ViewModelModule_ProvideSellerMarkersViewModelFactory implements Factory<SellerMarkersViewModel> {
  @Override
  public SellerMarkersViewModel get() {
    return provideSellerMarkersViewModel();
  }

  public static ViewModelModule_ProvideSellerMarkersViewModelFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SellerMarkersViewModel provideSellerMarkersViewModel() {
    return Preconditions.checkNotNullFromProvides(ViewModelModule.INSTANCE.provideSellerMarkersViewModel());
  }

  private static final class InstanceHolder {
    private static final ViewModelModule_ProvideSellerMarkersViewModelFactory INSTANCE = new ViewModelModule_ProvideSellerMarkersViewModelFactory();
  }
}
