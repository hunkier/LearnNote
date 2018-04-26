package com.hunk.learn.guice.order;

import com.google.inject.Inject;
import com.hunk.learn.guice.item.ItemService;
import com.hunk.learn.guice.price.PriceService;

import java.util.Set;

/**
 * Created by hunk on 2015/8/19.
 */
public class OrderServiceImpl implements  OrderService{

    private Set<ItemService> itemServices;
    private PriceService priceService;

    public OrderServiceImpl() {
    }

    public Set<ItemService> getItemServices() {
        return itemServices;
    }

    public PriceService getPriceService() {
        return priceService;
    }

    @Inject
    public OrderServiceImpl(Set<ItemService> itemServices, PriceService priceService) {
        this.itemServices = itemServices;
        this.priceService = priceService;
    }

    @Override
    public void add(Order order) {
        for(ItemService item : itemServices){
            item.get(0);
        }
        priceService.getPrice();
    }

    @Override
    public void remove(Order order) {

    }

    @Override
    public Order get(int id) {
        for(ItemService item : itemServices){
            item.get(0);
        }
        return new Order(id);
    }
}
