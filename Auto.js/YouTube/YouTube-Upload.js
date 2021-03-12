toast('开始执行脚本。。。');
// var u2 =  app.parseUri('file:///storage/emulated/0/$MuMu共享文件夹/Go语言核心编程/');
// var uri = app.getUriForFile('/storage/emulated/0/$MuMu共享文件夹/Go语言核心编程/091_尚硅谷_Go核心编程_for循环语法和执行流程.avi');

// console.show();
// log('uri: ', uri );
// log('u2: ', u2 );


// var dir = "/mnt/sdcard/$MuMu共享文件夹/upload/";
var dir = "/mnt/sdcard/$MuMu共享文件夹/Go语言核心编程/";
// dir = '/mnt/sdcard/$MuMu共享文件夹/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/尚硅谷区块链技术之GoWeb/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/尚硅谷-以太坊理论/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/硅谷拍卖系统/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/经典Java面试题/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/互联网大厂高频重点面试题/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/Netty核心技术及源码剖析/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/尚硅谷H5学科_面试题大全_第一季/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/明哥的沟通技巧八堂课/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/明哥的职场礼仪七堂课/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/求职指导视频/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/数据库中间件Mycat教程/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/SpringCloud/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/jvm/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/SVN/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/Dubbo/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/Java设计模式/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/IDEA教程/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/MySQL高级/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/Redis/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/nginx/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/Shiro/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/SpringBoot/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/SpringBoot4/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/Git&GitHub/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/Hibernate/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/maven/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/JPA/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/Jenkkins/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/MyBatis/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/MyBatisPlus/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/MyBatisPlus2/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/RBAC权限实战/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/SpringData/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/SpringMVC/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/Spring注解驱动开发/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/Struts2/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/SSH整合案例/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/SSM高级整合/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/SSSP/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/JDBC核心技术(2019新版)/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/MySQL/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/Java数据结构和算法/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/SpringCloud2020/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/Java14新特性/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/java8新特性/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/juc/2/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/nio/2/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/Java9新特性/2/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/Java11新特性/2/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/深入解读Java12&13新特性/';
// dir = '/mnt/sdcard/$MuMu共享文件夹/shop/';
dir = '/mnt/sdcard/$MuMu共享文件夹/flink/';


var jsFiles = files.listDir(dir, function(name){
    // return name.startsWith("09") && !name.startsWith("090") && name.endsWith(".avi") && files.isFile(files.join(dir, name));
    // return name.startsWith("11") && name.endsWith(".avi") && files.isFile(files.join(dir, name));
    // return name.endsWith(".avi") && files.isFile(files.join(dir, name));
    return (name.endsWith(".avi") || name.endsWith(".mp4") || name.endsWith(".wmv")) && files.isFile(files.join(dir, name));
    // return name.endsWith(".wmv")
    // return name.endsWith(".mp4")
    // name.indexOf('尚硅谷')!=-1 
    // && parseInt(name) > 10
    
    // return true;1234567891012345677
});
// var jsFiles = files.listDir(dir);
// log(jsFiles);
// sleep(5000);
// return;


jsFiles.sort();
// log(jsFiles);
// log(JSON.stringify(jsFiles));
// var list = JSON.parse(JSON.stringify(jsFiles));

jsFiles.forEach(content => {
    log(content);
    upload(content);
    // if(content.startsWith('09') && !content.startsWith('090')){
   
});



function upload(content){
    // var file = 'file:///mnt/sdcard/$MuMu共享文件夹/Go语言核心编程/';
    var file = 'file://' + dir + content;
    // var content = '091_尚硅谷_Go核心编程_for循环语法和执行流程.avi';
    // file += content;
   log(file);
    var uri = app.getUriForFile(file);
    log(uri);
    app.startActivity({
        action: "android.intent.action.SEND",
        type: "video/*",
        extras: {
          "android.intent.extra.STREAM": uri
        },
        packageName: "com.google.android.youtube",
        className: "com.google.android.youtube.UploadIntentHandlingActivity"
    });
    
    sleep(3000);
    // text('天河公园').waitFor();
    // text("天河公园").click();
    
    // sleep(500);
    content = content.replace('.avi','');
    content = content.replace('.mp4','');
    content = content.replace('.wmv','');
    content = content.replace('【www.hoh0.com】','');
    content = content.replace('【瑞客论 坛 www.ruike1.com】','');
    content = content.replace('【交流群244930845免费分享】','');
    content = content.replace('尚硅谷_','');
    content = content.replace('尚硅谷-','');
    text('编程视频教程').waitFor();
    // text('标题').click();  
    id("title_edit").findOne().click();
    sleep(200);
    // input(0,content);
    // sleep(1000);
    // text('说明').click();id/description_edit
    id("description_edit").findOne().click();
    sleep(200);
    // input(1,content);
    setText(content);
    sleep(500);
    log(content);
    // sleep(1000);
    id("menu_upload_activity_done").findOne().click();
    toast('上传中：' + content);
    sleep(3*1000);
    id("upload_status_message").waitFor();
    text(content).waitFor();
   /*
    
    className("android.support.v7.widget.RecyclerView").findOne().children().forEach(child => {
        var target = child.findOne(className("android.widget.TextView").text("119_尚硅谷_Go核心编程_函数注意事项和细节(2)"));
        // target.;
        });
    while(id("menu_upload_activity_done").text()){

    }
*/
    // var content ='119_尚硅谷_Go核心编程_函数注意事项和细节(2)';
    // var title = text(content).findOne();
    // log(title);
    // log(title.parent());
    // log(title.parent().children());
    // var target = title.parent().findOne(className("android.widget.TextView").id("upload_status_message"));
    sleep(3*1000);

    // var status = title.parent().findOne(id("upload_status_message"));
    var msg = text(content).findOne().parent().findOne(className("android.widget.TextView").id("upload_status_message")).text();
    var i = 0 ;
    while(!msg || msg.indexOf('已开始处理')==-1){
        log(i+' ' + msg);
        if(msg){
            if(
                msg.indexOf('视频已就绪')!=-1
            // ||msg.indexOf('正在处理…')!=-1
            ||msg.indexOf('99')!=-1
            ||msg.indexOf('98')!=-1
            ||msg.indexOf('97')!=-1
            ||msg.indexOf('96')!=-1
            ||msg.indexOf('95')!=-1
            ||msg.indexOf('94')!=-1
            ||msg.indexOf('93')!=-1
            ||msg.indexOf('92')!=-1
            ||msg.indexOf('91')!=-1
            ||msg.indexOf('90')!=-1
            ||msg.indexOf('89')!=-1
            ||msg.indexOf('88')!=-1
            ||msg.indexOf('87')!=-1
            ||msg.indexOf('86')!=-1
            ||msg.indexOf('85')!=-1
            ||msg.indexOf('84')!=-1
            ||msg.indexOf('83')!=-1
            ||msg.indexOf('82')!=-1
            ||msg.indexOf('81')!=-1
            ||msg.indexOf('80')!=-1
            ||msg.indexOf('79')!=-1
            // ||msg.indexOf('处理')!=-1
            ||msg.indexOf('已处理')!=-1
            ||msg.indexOf('已处理 9')!=-1
            ||msg.indexOf('无人观看')!=-1
            ||msg.indexOf('此视频可以观看啦')!=-1
            // ||msg.indexOf('已上传 99')!=-1
            ){
                break;
            }
        }
        text(content).waitFor();
        // id("upload_status_message").waitFor();
        // msg = text(content).findOne().parent().findOne(className("android.widget.TextView").id("upload_status_message")).text();
        var parent = text(content).findOne().parent();
        while(!parent){
            parent = text(content).findOne().parent();
        }
        var statusMessage = parent.findOne(id("upload_status_message"));
        if(!statusMessage){
            statusMessage = parent.findOne(id("upload_status_detailed_message"));
        }
        if(!statusMessage){
            statusMessage = parent.findOne(id("details"));
        }
        if (statusMessage){
            msg = statusMessage.text();
        }
        
        sleep(1*1000);
        i++;
    }
    log(i+' ' + msg);
    
    
    // 已上传
    // 正在处理
    // 正在处理...
    // back();
    home();
    sleep(500);
    launchApp("YouTube");
    log(content,'上传成功');
}



