function signal() {
    var uid = $("[name='uid']").val();
    var password = $("[name='password']").val();
    console.log(password);
    var enpass = MD5(password);
    console.log(enpass);
    $("#loginButton").val("登陆中");
    $.ajax({
        type: "post",
        url: "/manager",             //向springboot请求数据的url
        data: {"userId": uid, "userPassword": enpass},
        success: function (result) {
            console.log(JSON.stringify(result));

            if (result.status === 200) {
                // GLOBAL.token = json.data.token;
                setCookie("userId",result.data.uid);
                setCookie("token",result.data.token);
                location = "/manager";        //跳转
            } else{
                alert(result.message);
            }
        }
    });
}