toast('开始执行脚本。。。');

auto.waitFor();//辅助权限授予
auto.setMode("normal");



function addToPlayList(paylist){

    while(true){
        var listView = className("android.support.v7.widget.RecyclerView").id('results').findOne();
        // log(listView);
        var title = '';
        var titles = [];
        listView.children().forEach(child => { 

            var target = child.findOne(id("title"));
            // if(target && target.text() && target.text().indexOf('Go核心编程')!=-1 ){
            if(target && target.text() ){
                title = target.text();
                
                // log(parseInt(title));
                // log(parseInt(JSON.stringify(title)),parseInt(title),title);
                // var tt = text(t).findOnce();
                // target.parent().parent().click();
                // toEdit();
                // item = child;
                // num++;
                titles.push(title);
            }
        
        });
        titles.sort();
        // log(titles);
        titles.forEach(content =>{
            // id("content_recycler").waitFor();
            text(content).waitFor();
            add(paylist, content);
            // log(content);
            // text(content).findOne().parent().parent().click();
            // toEdit();
        });
        // id("content_recycler").waitFor();
        // id("content_recycler").findOne().scrollBackward();
        listView = className("android.support.v7.widget.RecyclerView").id('results').findOne();
        listView.scrollBackward();
        sleep(1000);
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
        // log(checkbox.checked());
        item.click();
    }
    sleep(300);

    id('add_to_playlist_bottom_sheet_close_button').findOne().click();
    log(content);
    sleep(100);
}
var paylist= '互联网大厂高频重点Java面试题';
addToPlayList(paylist);

// var item = text(paylist).findOne().parent();
// var checkbox = item.findOne(id("checkbox"));
// if(checkbox.checked()==false){
//     log(checkbox.checked());
//     item.click();
// }
// log(item);
// log(checkbox);