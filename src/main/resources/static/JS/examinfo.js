$(function(){
    $.ajax({
        type: "get",
        url: "/examinfo1",             //向springboot请求数据的url
        data: {"examineeId":getCookie("examineeId")},
        success: function (result) {
            console.log(JSON.stringify(result));
        }
    });
});