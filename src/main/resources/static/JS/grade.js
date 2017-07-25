$(function(){
    $.ajax({
        type: "get",
        url: "/grade",             //向springboot请求数据的url
        data: {"examineeId":getCookie("examineeId")},
        success: function (result) {
            var data=result;
            console.log(JSON.stringify(result));
            msg_display(data.data);

        }
    })
});

//考试信息 显示
function msg_display(msgs) {
    var contentRight = $("#message");

    if (typeof(msgs) == 'undefined' || msgs.length == 0) {
        var html = "<div class='message no-message'>没有成绩信息</div>";
        contentRight.append(html);
    }
    //foreach
    $.each(msgs, function (i, message) {
        var state;
        if(message.examinationState=="00"){
            state="完成"
        }else{
            state="未完成"
        }
        var messageBox =
            "<blockquote><h3>"+message.examinationName+"</h3>"+
            "<div>"+"考试统计时间："+getDate(message.examinationEnd)+"</div>"+
            "<div>"+"考试总分："+message.examinationScoreAll+"</div>"+
            "<span>"+"考试状态："+state+"</span>"+"<span>"+"&nbsp&nbsp考试成绩："+message.grade +"分"+"</span>"+"</blockquote>";
        contentRight.append(messageBox);
    })
}

