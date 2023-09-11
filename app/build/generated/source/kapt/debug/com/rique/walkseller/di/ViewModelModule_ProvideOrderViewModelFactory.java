package com.rique.walkseller.di;

import com.rique.walkseller.ui.viewModel.OrderViewModel;
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
public final class ViewModelModule_ProvideOrderViewModelFactory implements Factory<OrderViewModel> {
  @Override
  public OrderViewModel get() {
    return provideOrderViewModel();
  }

  public static ViewModelModule_ProvideOrderViewModelFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static OrderViewModel provideOrderViewModel() {
    return Preconditions.checkNotNullFromProvides(ViewModelModule.INSTANCE.provideOrderViewModel());
  }

  private static final class InstanceHolder {
    private static final ViewModelModule_ProvideOrderViewModelFactory INSTANCE = new ViewModelModule_ProvideOrderViewModelFactory();
  }
}
