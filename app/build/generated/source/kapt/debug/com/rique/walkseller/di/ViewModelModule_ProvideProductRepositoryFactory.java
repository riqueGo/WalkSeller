package com.rique.walkseller.di;

import com.rique.walkseller.interfaces.IProductRepository;
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
public final class ViewModelModule_ProvideProductRepositoryFactory implements Factory<IProductRepository> {
  @Override
  public IProductRepository get() {
    return provideProductRepository();
  }

  public static ViewModelModule_ProvideProductRepositoryFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static IProductRepository provideProductRepository() {
    return Preconditions.checkNotNullFromProvides(ViewModelModule.INSTANCE.provideProductRepository());
  }

  private static final class InstanceHolder {
    private static final ViewModelModule_ProvideProductRepositoryFactory INSTANCE = new ViewModelModule_ProvideProductRepositoryFactory();
  }
}
