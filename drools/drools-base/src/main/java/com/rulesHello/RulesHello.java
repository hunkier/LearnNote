package com.rulesHello;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * drools
 *
 * @author hunkier
 * @version V1.0
 * @program: drools
 * @create: 2022-06-02 17:23
 * @since V1.0
 **/
public class RulesHello {
    public static void main(String[] args) {
        final KieServices kss = KieServices.get();
        final KieContainer kc = kss.getKieClasspathContainer();
        final KieSession ks = kc.newKieSession("testhellworld");
        int count = ks.fireAllRules();
        System.out.println("总共执行了"+count+"条规则");
        ks.dispose();
    }
}
