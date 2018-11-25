package cn.hunkier.decorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteComponent implements Component{
    @Override
    public void doSomething() {
      log.info("功能A");
    }
}
