package com.hunk.learn.guice.order;


/**
 * Created by hunk on 2015/8/19.
 */
public interface OrderService {

    public void add(Order order);

    public void remove(Order order);

    public Order get(int id);

}
