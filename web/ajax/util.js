/**
 * Created by hunk on 2015/8/17.
 */


function getXHR(){
    var xmlHttp;
    // Firefox,  Opear 8.0+,  Safari
    try{
        xmlHttp = new XMLHttpRequest();
    }catch(e){
        // Internet Explorer
        try{
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        }catch(e){
            try{
                xmlHttp = new ActiveXObject("Misrosoft.XMLHTTP");
            }catch(e){
                alert("您的浏览器不支持AJAX！");
                return false;
            }
        }
    }

    return xmlHttp;
}