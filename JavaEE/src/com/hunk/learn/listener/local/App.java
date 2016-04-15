package com.hunk.learn.listener.local;

import org.junit.Test;

import java.util.Locale;

/**
 * Created by hunk on 2015/8/14.
 */
public class App {
    /**
     * 本地化对象：Local
     * 封装语言、国家信息对象，由java。util提供
     * @throws Exception
     */
    @Test
    public void testLocal() throws  Exception{
        // 模拟中国语言环境
        // Locale locale = Locale.CHINA;
         // Locale locale = Locale.CHINESE;
        Locale locale = Locale.getDefault();
        System.out.println(locale.getCountry());
        System.out.println(locale.getDisplayCountry());
        System.out.println(locale.getLanguage());

        // 模拟美国国家
        Locale l_us = Locale.US;
        System.out.println(l_us.getCountry());
        System.out.println(l_us.getDisplayCountry());
    }
}
