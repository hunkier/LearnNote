package cn.hunkier.decorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteComponent1 extends Decorator{
    public ConcreteComponent1(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();
    }

    private void doAnotherThing() {
        log.info("功能B");
    }
}
