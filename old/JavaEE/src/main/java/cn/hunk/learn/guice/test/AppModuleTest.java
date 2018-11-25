package cn.hunk.learn.guice.test;

import cn.hunk.learn.guice.named.NamedService;
import cn.hunk.learn.guice.order.OrderService;
import cn.hunk.learn.guice.order.OrderServiceImpl;
import cn.hunk.learn.guice.price.PriceService;
import cn.hunk.learn.guice.runtime.RuntimeService;
import cn.hunk.learn.guice.runtime.RuntimeServiceImp;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import cn.hunk.learn.guice.app.AppModule;
import cn.hunk.learn.guice.item.ItemService;
import cn.hunk.learn.guice.item.ItemServiceImpl1;
import cn.hunk.learn.guice.item.ItemServiceImpl2;
import cn.hunk.learn.guice.named.NamedService;
import cn.hunk.learn.guice.order.OrderService;
import cn.hunk.learn.guice.order.OrderServiceImpl;
import cn.hunk.learn.guice.price.PriceService;
import cn.hunk.learn.guice.runtime.RuntimeService;
import cn.hunk.learn.guice.runtime.RuntimeServiceImp;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Created by hunk on 2015/8/19.
 */
public class AppModuleTest {
    private Injector injector;
    @Before
    public void setUp() throws  Exception{
        injector = Guice.createInjector(new AppModule(new RuntimeServiceImp()));
    }
    @Test
    public void shold_get_order_service_from_guice_module() throws  Exception{
        //given
        //when
        final OrderService instance = injector.getInstance(OrderService.class);
        //then
        Assert.assertThat(instance, Is.is(Matchers.instanceOf(OrderServiceImpl.class)));
        final List<ItemService> itemServices = Lists.newArrayList(((OrderServiceImpl) instance).getItemServices());
        Assert.assertThat(itemServices.get(0),Is.is(Matchers.instanceOf(ItemServiceImpl1.class)));
        Assert.assertThat(itemServices.get(1),Is.is(Matchers.instanceOf(ItemServiceImpl2.class)));
        Assert.assertThat(((OrderServiceImpl) instance).getPriceService(),Is.is(Matchers.instanceOf(PriceService.class)));
    }


    @Test
    public void should_get_all_item_service()throws Exception{
        //given

        //when
        final List<ItemService> instance = Lists.newArrayList(injector.getInstance(new Key<Set<ItemService>>() {
        }));
        //then
        Assert.assertThat(instance.size(),Is.is(2));
        Assert.assertThat(instance.get(0),Is.is(Matchers.instanceOf(ItemServiceImpl1.class)));
        Assert.assertThat(instance.get(1),Is.is(Matchers.instanceOf(ItemServiceImpl2.class)));
    }

    @Test
    public void should_register_service_runtion()throws  Exception{
        //given
        //when
        final RuntimeService instance = injector.getInstance(RuntimeService.class);
        //then
        Assert.assertThat(instance,Is.is(Matchers.instanceOf(RuntimeServiceImp.class)));
    }

    @Test
    public void should_be_singleton_for_one_without_interface_bean()throws  Exception{
        //given
        //when
        final PriceService first = injector.getInstance(PriceService.class);
        final PriceService second = injector.getInstance(PriceService.class);
        Assert.assertThat(first, Is.is(Matchers.sameInstance(second)));
    }

    @Test
    public void should_get_named_service_with_Provides_bean()throws  Exception{
        //given
        //when
        final  List<NamedService> namedServices = injector.getInstance(new Key<List<NamedService>>(){});
        //then
        Assert.assertThat(namedServices.size(),Is.is(2));
    }
}
