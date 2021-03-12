toast('开始执行脚本。。。');

auto.waitFor();//辅助权限授予
auto.setMode("normal");



function addToPlayList(paylist){
    var last = 'last';

    while(true){
        var now = 'now';
        var listView = className("android.support.v7.widget.RecyclerView").id('results').findOne();
        // log(listView);
        var title = '';
        var titles = [];
        listView.children().forEach(child => { 

            var target = child.findOne(id("title"));
            // if(target && target.text() && target.text().indexOf('Go核心编程')!=-1 ){
            if(target && target.text() ){
                title = target.text();
                log(title);
                
                // log(parseInt(title));
                // log(parseInt(JSON.stringify(title)),parseInt(title),title);
                // var tt = text(t).findOnce();
                // target.parent().parent().click();
                // toEdit();
                // item = child;
                // num++;
                if(
                    now.indexOf(title)==-1
                     && last.indexOf(title)==-1
                    //  && title.indexOf('尚硅谷')!=-1
                    //  && title.indexOf('尚硅谷')==-1
                    //  && title.indexOf('尚硅谷_SpringCloud')!=-1
                     ){
                    titles.push(title);
        
                }
            }

            
        
        });
        titles.sort();
        log(titles);
        titles.forEach(content =>{
            className("android.support.v7.widget.RecyclerView").id('results').waitFor();
            var obj = text(content).findOnce();
            // var obj = text(content).findOne();
            var i = 0;
            while(obj==null && i <30){
                sleep(50);
                obj = text(content).findOnce();
                i++ ;
            }
            // console.log(obj);
            if(obj){
                // text(content).waitFor();
                add(paylist, content);
                now += content;
            }
            
            // log(content);
            // text(content).findOne().parent().parent().click();
            // toEdit();
        });
        // id("content_recycler").waitFor();
        // id("content_recycler").findOne().scrollBackward();
        listView = className("android.support.v7.widget.RecyclerView").id('results').findOne();
        listView.scrollBackward();
        sleep(3000);
        last = now;
    }
}

function add(paylist, content){


    text(content).waitFor();
    // log(text(content).findOne().parent());
    // log(text(content).findOne().parent().parent());
    text(content).findOne().parent().parent().findOne(id("contextual_menu_anchor")).click();
    text('保存到播放列表').waitFor();
    sleep(500);
    text('保存到播放列表').findOne().parent().click();
    text('将视频保存至…').waitFor();
    id("list").waitFor();
    text(paylist).waitFor();
    var item = text(paylist).findOne().parent();
    var checkbox = item.findOne(id("checkbox"));
    if(checkbox.checked()==false){
    // if(checkbox.checked()==true){
        // log(checkbox.checked());
        item.click();
    }
    sleep(300);

    id('add_to_playlist_bottom_sheet_close_button').findOne().click();
    log(content);
    sleep(100);
}
// text = 003-尚硅谷-Netty核心技术及源码剖析-应用场景和学习资料
// var paylist= '互联网大厂高频重点Java面试题';
// var paylist= 'Netty核心技术及源码剖析';
// var paylist= 'H5面试题大全第一季';
// var paylist= 'SpringCloud';
// var paylist= 'JVM从入门到精通';
// var paylist= 'SpringData';
// var paylist= 'SVN';
// var paylist= 'Docker';
// var paylist= 'Dubbo';
// var paylist= 'Java设计模式';
// var paylist= 'IDEA教程';
// var paylist= 'MySQL高级';
// var paylist= 'Redis';
// var paylist= 'nginx';
// var paylist= 'Shiro';
// var paylist= 'SpringBoot';
// var paylist= 'SpringBoot整合篇';
// var paylist= 'Spring 4';
// var paylist= 'Git&GitHub';
// var paylist= 'Hibernate';
// var paylist= 'maven';
// var paylist= 'JPA';
// var paylist= 'Jenkkins';
// var paylist= 'MyBatis';
// var paylist= 'MyBatisPlus';
// var paylist= 'RBAC权限实战';
// var paylist= 'SpringData';
// var paylist= 'SpringMVC';
// var paylist= 'Spring Annotation 注解驱动开发';
// var paylist= 'Struts2';
// var paylist= 'Spring4 + Struts2.3 + Hibernate4 整合案例';
// var paylist= 'Spring + SpringMVC + MyBatis 高级整合';
// var paylist= 'SpringMVC + Spring + SpringData\JPA 整合案例';
// var paylist= 'JDBC核心技术(2019新版)';
// var paylist= 'MySQL核心技术';
// var paylist= 'Java数据结构和算法';
// var paylist = 'SpringCloud2020';
// var paylist = 'Java14新特性';
// var paylist = 'Java8新特性';
// var paylist = 'java.util.concurrent';
// var paylist = 'JUC';
// var paylist = 'Java9新特性';
// var paylist = 'Java11新特性';
// var paylist = '深入解读Java12&13新特性';
// var paylist = '谷粒商城';
addToPlayList(paylist);

// var item = text(paylist).findOne().parent();
// var checkbox = item.findOne(id("checkbox"));
// if(checkbox.checked()==false){
//     log(checkbox.checked());
//     item.click();
// }
// log(item);
// log(checkbox);