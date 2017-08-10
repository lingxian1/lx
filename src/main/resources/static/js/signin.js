function signal() {
    var phone = $("[name='uid']").val();
    var password = $("[name='password']").val();
    console.log(password);
    var enpass = MD5(password);
    console.log(enpass);
    $("#loginButton").val("登陆中");
    $.ajax({
        type: "post",
        url: "/signin",             //向springboot请求数据的url
        data: {"phone": phone, "password": enpass},
        success: function (result) {
            console.log(JSON.stringify(result));

            if (result.status === 200) {
                // GLOBAL.token = json.data.token;
                console.log(result.data.examineeId);
                // setCookie("examineeId",result.data.examineeId);
                // setCookie("areaId",result.data.areaId);
                setCookie("examineeId",result.data.uid);
                setCookie("token",result.data.token);
                location = "/examinfo";        //跳转
            } else{
                alert(result.message);
            }
        }
    });
}