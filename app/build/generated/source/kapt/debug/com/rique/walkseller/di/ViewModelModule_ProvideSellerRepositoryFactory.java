package com.rique.walkseller.di;

import com.rique.walkseller.interfaces.ISellerRepository;
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
public final class ViewModelModule_ProvideSellerRepositoryFactory implements Factory<ISellerRepository> {
  @Override
  public ISellerRepository get() {
    return provideSellerRepository();
  }

  public static ViewModelModule_ProvideSellerRepositoryFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ISellerRepository provideSellerRepository() {
    return Preconditions.checkNotNullFromProvides(ViewModelModule.INSTANCE.provideSellerRepository());
  }

  private static final class InstanceHolder {
    private static final ViewModelModule_ProvideSellerRepositoryFactory INSTANCE = new ViewModelModule_ProvideSellerRepositoryFactory();
  }
}
