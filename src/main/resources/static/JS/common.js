function exit() {
    var msg = "退出当前登陆";
    if (confirm(msg)==true){
        delCookie("examineeId");
        delCookie("areaId");
        localStorage.clear();
        location="/signin";
    }else{
        return false;
    }
}
function exitadmin() {
    var msg = "退出当前登陆";
    if (confirm(msg)==true){
        delCookie("userId");
        delCookie("token");
        localStorage.clear();
        location="/";
    }else{
        return false;
    }
}
//毫秒转换
function getDateAndTime(ms) {
    var d = new Date(ms);
    return d.toLocaleString();
}
function getDate(ms) {
    var d = new Date(ms);
    var year = d.getFullYear();
    var month = d.getMonth();
    var date = d.getDate();
    return year + "-" + month + "-" + date;
}

//设置cookies
function setCookie(c_name, value, expiredays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + expiredays);
    document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
}

//读取cookies
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return (arr[2]);
    else
        return null;
}

//删除cookies
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null)
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

function log(json) {
    console.log(JSON.stringify(json));
}


function formDate(date) {
    var dates = new Date(date);
    return (dates.getYear()+1900)+"-"+(dates.getMonth()+1)+"-"+dates.getDay();
}