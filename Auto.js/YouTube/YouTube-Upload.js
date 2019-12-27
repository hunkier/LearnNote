toast('开始执行脚本。。。');
// var u2 =  app.parseUri('file:///storage/emulated/0/$MuMu共享文件夹/Go语言核心编程/');
// var uri = app.getUriForFile('/storage/emulated/0/$MuMu共享文件夹/Go语言核心编程/091_尚硅谷_Go核心编程_for循环语法和执行流程.avi');

// console.show();
// log('uri: ', uri );
// log('u2: ', u2 );


// var dir = "/mnt/sdcard/$MuMu共享文件夹/upload/";
var dir = "/mnt/sdcard/$MuMu共享文件夹/Go语言核心编程/";

var jsFiles = files.listDir(dir, function(name){
    // return name.startsWith("09") && !name.startsWith("090") && name.endsWith(".avi") && files.isFile(files.join(dir, name));
    return name.startsWith("11") && name.endsWith(".avi") && files.isFile(files.join(dir, name));
    // return name.endsWith(".avi") && files.isFile(files.join(dir, name));
    // return true;
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
    // upload(content);
    // if(content.startsWith('09') && !content.startsWith('090')){
   
});



function upload(content){
    // var file = 'file:///mnt/sdcard/$MuMu共享文件夹/Go语言核心编程/';
    var file = 'file://' + dir + content;
    // var content = '091_尚硅谷_Go核心编程_for循环语法和执行流程.avi';
    // file += content;
    
    var uri = app.getUriForFile(file);
    app.startActivity({
        action: "android.intent.action.SEND",
        type: "video/*",
        extras: {
          "android.intent.extra.STREAM": uri
        },
        packageName: "com.google.android.youtube",
        className: "com.google.android.youtube.UploadIntentHandlingActivity"
    });
    
    sleep(5000);
    // text('天河公园').waitFor();
    // text("天河公园").click();
    
    sleep(500);
    content = content.replace('.avi','');
    text('编程视频教程').waitFor();
    // text('标题').click();  
    id("title_edit").findOne().click();
    sleep(500);
    // input(0,content);
    sleep(1000);
    // text('说明').click();id/description_edit
    id("description_edit").findOne().click();
    sleep(500);
    // input(1,content);
    setText(content);
    sleep(1000);
    log(content);
    sleep(1000);
    id("menu_upload_activity_done").findOne().click();
    toast('上传中：' + content);
    id("upload_status_message").waitFor();
    text(content).waitFor();
    while(true){
        text(content).findOne().parent().findOne(id("menu_upload_activity_done").text());
    }
    
    className("android.support.v7.widget.RecyclerView").findOne().children().forEach(child => {
        var target = child.findOne(className("android.widget.TextView").text("119_尚硅谷_Go核心编程_函数注意事项和细节(2)"));
        // target.;
        });
    while(id("menu_upload_activity_done").text()){

    }
    
    sleep(240*1000);
    // 已上传
    // 正在处理
    // 正在处理...
    home();
    sleep(1000);
    log(content,'上传成功');
}

var content ='119_尚硅谷_Go核心编程_函数注意事项和细节(2)';
var title = text(content).findOne();
log(title);
log(title.parent());
// log(title.parent().children());
var target = title.parent().findOne(className("android.widget.TextView").id("upload_status_message"));

var status = title.parent().findOne(id("upload_status_message"));
log(status);

