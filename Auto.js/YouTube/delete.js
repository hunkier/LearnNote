toast('开始执行脚本。。。');


function deleteVedio(){
    // var title = id('pending_video_upload_item').parent().findOne();
    var results = id('results').findOne();
    // var title = className("android.support.v7.widget.RecyclerView").findOne();
    log(results);
    log('开始删除视频');
    // log(results.children());
    log(results.children().size);
    results.children().forEach(child => { 
        log(child);
        if(child.id()=='pending_video_upload_item'){

            return;
        }
        // log(child);
         var title = child.findOne(id("title"));
        var target = child.findOne(id("contextual_menu_anchor"));
        log(target)
        sleep(1000);
        text('删除上传的视频').waitFor();
        text('删除上传的视频').findOne().click();
        sleep(1000);
        text('是').waitFor();
        text('是').findOne().click();
        sleep(1000);
        log('删除成功： ' +title);
    });
}

function test(){
    var item = id('pending_video_upload_item').findOne();
// log(item);
var title = item.findOne(id("title")).text;
        var target = item.findOne(id("contextual_menu_anchor"));
        // log(target)
        target.click();
        sleep(2000);
        // text('删除上传的视频').waitFor();
        id("text").findOne().click();
        // text('删除上传的视频').findOne().click();
        sleep(1000);
        text('是').waitFor();
        text('是').findOne().click();
        sleep(1000);
        log('删除成功： ' +title);
}

deleteVedio();