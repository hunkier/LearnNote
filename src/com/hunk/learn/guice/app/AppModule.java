/*
 * Created by IntelliJ IDEA.
 * User: dell
 * Date: 2015/8/19
 * Time: 16:06
 */
package com.hunk.learn.guice.app;

import com.google.common.collect.ImmutableList;
import com.google.inject.*;
import com.google.inject.matcher.Matchers;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.hunk.learn.guice.item.ItemService;
import com.hunk.learn.guice.item.ItemServiceImpl1;
import com.hunk.learn.guice.item.ItemServiceImpl2;
import com.hunk.learn.guice.named.NamedService;
import com.hunk.learn.guice.named.NamedServiceImpl1;
import com.hunk.learn.guice.named.NamedServiceImpl2;
import com.hunk.learn.guice.order.OrderService;
import com.hunk.learn.guice.order.OrderServiceImpl;
import com.hunk.learn.guice.price.PriceService;
import com.hunk.learn.guice.runtime.RuntimeService;
import com.hunk.learn.guice.runtime.RuntimeServiceImp;

import java.util.List;


public class AppModule extends AbstractModule {

    private RuntimeServiceImp runtimeService;

    public AppModule(RuntimeServiceImp runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    protected void configure() {
        final Binder binder = binder();

        if (true){
            binder.bindInterceptor(Matchers.any(), Matchers.any(),ExceptionMethodInterceptor.exception());
        }

        //TODO: bind interface
        binder.bind(OrderService.class).to(OrderServiceImpl.class).in(Scopes.SINGLETON);
        //TODO: bind self class(without interface or base class)
        binder.bind(PriceService.class).in(Scopes.SINGLETON);

        //TODO: Multibinder
        final Multibinder<ItemService> itemServiceMultibinder = Multibinder.newSetBinder(binder,ItemService.class);
        itemServiceMultibinder.addBinding().to(ItemServiceImpl1.class);
        itemServiceMultibinder.addBinding().to(ItemServiceImpl2.class);

        //TODO: bind instance not class
        binder.bind(RuntimeService.class).toInstance(runtimeService);

        //TODO: bind named instance;
        binder.bind(NamedService.class).annotatedWith(Names.named("impl1")).to(NamedServiceImpl1.class);
        binder.bind(NamedService.class).annotatedWith(Names.named("impl2")).to(NamedServiceImpl2.class);


    }

    @Provides
    public List<NamedService> getAllItemServices(@Named("impl1") NamedService namedService1,
                                                 @Named("impl2") NamedService namedService2){
        return  ImmutableList.of(namedService1, namedService2);
    }
}
