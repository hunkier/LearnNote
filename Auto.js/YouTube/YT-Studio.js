
auto.waitFor();//辅助权限授予
auto.setMode("normal");


/**
 * @name:延迟函数ms→s 
 * @param {int}mm 
 * @return: null
 */
function toSDelay(params) {
    sleep(params * 1000);
}

/**
 * @name:初始化函数 
 * @param {none} 
 * @return: none
 */
function initScript() {
    toSDelay(2);
    toast('YT Studio 启动中……');
    var initState = launchApp("YT Studio");
    toSDelay(3);
    if (initState == false) {
        toast("启动失败\n找不到该应用")
    }
    return true;
};

function toVideo(){
    // toSDelay(3);
    desc('打开主菜单').waitFor();
    desc('打开主菜单').findOne().click();
    // click(4,80,100,100);
    id('guide_frame').waitFor();
    // text('数据分析').waitFor();
    text('视频').click();
    text('所有视频').waitFor();
    log(currentActivity());
    toast(currentActivity());
    
    // toSDelay(3);
    for(var i=0; i<23; i++){
        toDetail();
        id("content_recycler").findOne().scrollForward();
    }
   
}


function toDetail(){
    var titles = new Array();
    id("content_recycler").findOne().children().forEach(child => { 
        var target = child.findOne(id("video_list_entry_title"));
        if(target && target.text()){
            titles.push(target.text());
        }
    
    });
    titles.forEach(t=>{
        log(t);
        var tt = text(t).findOnce();
        if(tt){
            toast(t);
            tt.parent().parent().click();
            toEdit();
            edit();
        }
    });
}

function toPlayListDetail(){
    var titles = new Array();
    id("content_recycler").findOne().children().forEach(child => { 
        var target = child.findOne(id("video_list_entry_title"));
        if(target && target.text()){
            titles.push(target.text());
        }
    
    });
    titles.forEach(t=>{
        log(t);
        var tt = text(t).findOnce();
        if(tt){
            toast(t);
            tt.parent().parent().click();
            toEdit();
            edit();
        }
    });
}

function toEdit(){
    className("android.widget.TextView").desc("编辑视频 button").waitFor();
    className("android.widget.TextView").desc("编辑视频 button").findOne().click()
}

function edit(){
    // text('基本信息').waitFor();
    // id/mde_linear_list_view
    // scrollUp();
    id("mde_linear_list_view").findOne().scrollForward();
    toSDelay(1);
    // id("mde_linear_list_view").findOne().scrollBackward();
    // if(text('添加新标签').exists()){
    // if(text('添加新标签').exists()){
    //     text('添加新标签').click();
    //     setText(2,'go,golang');
    // }
    id("add_to_playlist_button").waitFor();
    var b = id("add_to_playlist_button").findOne();
    // if(b && b.text()=='添加到播放列表'){
    if(b && b.text()=='go语言基础'){
        b.parent().click();
        // toSDelay(1);
        id("mde_linear_list_view").waitFor();
        id("add_to_playlist_title").waitFor();
        id("mde_linear_list_view").findOne().children().forEach(child => {
            var target = child.findOne(id("add_to_playlist_title"));
            if(target && target.text()=='go语言基础'){
                child.click();
            }
            
            });
        // toSDelay(1);
        desc('返回上一屏幕').click();
        text("保存").waitFor();
    }

    // toSDelay(1);
    
    var save = id("action_save").findOne();

    log((typeof save.enabled()) + ' '+ save.enabled());
   
    if(save.enabled()){
        save.click();
    }else{
        desc('返回上一屏幕').click();
    }
    
    toast('保存成功!');
    // text('视频效果').waitFor();
    className("android.widget.TextView").desc("编辑视频 button").waitFor();
    desc('返回上一屏幕').click();
    // text('所有视频').waitFor();
    toSDelay(1);
   
}

initScript();
// toVideo();
// edit();

for(var i=0; i<20; i++){
    toPlayListDetail();
    id("content_recycler").findOne().scrollForward();
}



