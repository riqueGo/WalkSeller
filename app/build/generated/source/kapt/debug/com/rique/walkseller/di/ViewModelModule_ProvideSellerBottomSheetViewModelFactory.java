package com.rique.walkseller.di;

import com.rique.walkseller.ui.viewModel.SellerBottomSheetViewModel;
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
public final class ViewModelModule_ProvideSellerBottomSheetViewModelFactory implements Factory<SellerBottomSheetViewModel> {
  @Override
  public SellerBottomSheetViewModel get() {
    return provideSellerBottomSheetViewModel();
  }

  public static ViewModelModule_ProvideSellerBottomSheetViewModelFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SellerBottomSheetViewModel provideSellerBottomSheetViewModel() {
    return Preconditions.checkNotNullFromProvides(ViewModelModule.INSTANCE.provideSellerBottomSheetViewModel());
  }

  private static final class InstanceHolder {
    private static final ViewModelModule_ProvideSellerBottomSheetViewModelFactory INSTANCE = new ViewModelModule_ProvideSellerBottomSheetViewModelFactory();
  }
}
