function submit() {
    var old = $("[name='old']").val();
    var new0 = $("[name='new0']").val();
    var new1 = $("[name='new1']").val();
    console.log(new0.length);
    if (new0 !== new1){
        alert("新密码不一致");
    }else if(new0.length<6||new0.length>15){
        alert("长度必须在6-15位之间");
    }else{
        var oldpass= MD5(old);
        var newpass= MD5(new0);
        $.ajax({
            type: "post",
            url: "/examinfo1Examinees/changePassword",             //向springboot请求数据的url
            data: {"userId":getCookie("userId"), "token":getCookie("token"), "old": oldpass,"new0": newpass}, //发送登陆ID及Token
            success: function (result) {
                console.log(result);
                if (result.status === 200) {
                    alert("修改成功");
                }else {
                    alert(result.message);
                }
            }
        })
    }
}
