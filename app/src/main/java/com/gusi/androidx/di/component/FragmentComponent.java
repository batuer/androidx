package com.gusi.androidx.di.component;


import com.gusi.androidx.di.module.FragmentModule;
import com.gusi.androidx.di.scope.FragmentScope;
import com.gusi.androidx.module.areastate.AreaStateFragment;

import dagger.Component;

/**
 * @author ylw   2018/6/21 18:12
 * @Des
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(AreaStateFragment fragment);
}
