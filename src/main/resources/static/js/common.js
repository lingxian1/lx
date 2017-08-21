function exit() {
    var msg = "退出当前登录";
    if (confirm(msg)==true){
        delCookie("examineeIdU");
        delCookie("tokenU");
        localStorage.clear();
        location="/signin";
    }else{
        return false;
    }
}
function exitadmin() {
    var msg = "退出当前登录";
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
    var date = new Date(ms);
    var Y = date.getFullYear() + '-';
    var M = date.getMonth()+1+ '-';
    var D = date.getDate() + ' ';
    var h = (date.getHours()< 10 ? '0'+(date.getHours()) : date.getHours())+ ':';
    var m = (date.getMinutes()< 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
    var s = (date.getSeconds()< 10 ? '0'+(date.getSeconds()) : date.getSeconds());
    // return d.toLocaleString();
    return Y+M+D+h+m+s;
}
function getDate(ms) {
    var d = new Date(ms);
    var year = d.getFullYear();
    var month = d.getMonth()+1;
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

//tip是提示信息，type:'success'是成功信息，'danger'是失败信息,'info'是普通信息,'warning'是警告信息
function ShowTip(tip, type) {
    var $tip = $('#tip');
    if ($tip.length == 0) {
        $tip = $('<strong id="tip" style="position:absolute;top:50px;left: 50%;z-index:9999"></strong>');
        $('body').append($tip);
    }
    $tip.stop(true).prop('class', 'alert alert-' + type).text(tip).css('margin-left', -$tip.outerWidth() / 2).fadeIn(500).delay(2000).fadeOut(500);
}

function ShowMsg(msg) {
    ShowTip(msg, 'info');
}

function ShowSuccess(msg) {
    ShowTip(msg, 'success');
}

function ShowFailure(msg) {
    ShowTip(msg, 'danger');
}

function ShowWarn(msg, $focus, clear) {
    ShowTip(msg, 'warning');
    if ($focus) {
        $focus.focus();
        if (clear) $focus.val('');
    }
    return false;
}