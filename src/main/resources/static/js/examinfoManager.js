$(function(){
    $.ajax({
        type: "get",
        url: "/manager/examinfos",             //向springboot请求数据的url
        data: {},
        success: function (result) {
            if(result.status==200){
                var data=result;
                console.log(JSON.stringify(result));
                msg_display(data.data);
            }
            else{
                alert(result.message);
                //TODO 跳转
                location="/";
            }
        }
    });
});

//考试信息 显示
function msg_display(msgs) {
    var contentRight = $("#message");
    if (typeof(msgs) == 'undefined' || msgs.length == 0) {
        var html = "<div class='message no-message'>没有考试信息</div>";
        contentRight.append(html);
    }
    //foreach
    $.each(msgs, function (i, message) {
        var messageBox =
            "<div class='twitter-panel pn' style='margin: 20px'><i class='fa fa-pencil-square-o fa-4x'></i><p><strong>"+message.examinationId+":</strong>"+message.examinationName+"</p>"+
            "<p>"+"参与人数："+message.examineeCount+"</p>"+
            "<p><span>"+"有效时间："+getDateAndTime(message.examinationStart)+"——"+"</span>"+"<span>"+getDateAndTime(message.examinationEnd)+"</span></p>"+"</div>";
        contentRight.append(messageBox);
    })
}
