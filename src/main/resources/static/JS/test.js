function gets() {
    $.ajax({
        url: "/aaa/"+"1000012345",
        type: "get",
        data: {},
        success: function (result) {
            console.log("sss");
            console.log(JSON.stringify(result));
            $('#test').html(result.data[1].name);

        },
        error: function () {
            alert("ajax请求发送失败");
        }
    });
}