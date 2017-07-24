$(function(){
    $.ajax({
        type: "get",
        url: "/examinfo1",             //向springboot请求数据的url
        data: {"examineeId":getCookie("examineeId")},
        success: function (result) {
            var data=result;
            console.log(JSON.stringify(result));
            msg_display(data.data);

        }
    });
});

//考试信息 显示
function msg_display(msgs) {
    // console.log(msgs[0]);
    var contentRight = $("#message");

    // if (typeof(msgs) == 'undefined' || msgs.length == 0) {
    //     var html = "<div class='message no-message'>没有考试信息</div>";
    //     contentRight.append(html);
    // }
    //foreach
    $.each(msgs, function (i, message) {
        // if (message.messageState != 2) {
        var messageBox =
            "<blockquote><p>"+message.examinationName+"</p>"+
            "<p>"+message.examinationInfo+"</p>"+
            "<span>"+"满分："+message.examinationScoreAll+"</span>"+"<span>"+"&nbsp&nbsp时间："+message.answerTime/60 +"分钟"+"</span>"+
            "<div><span>"+"有效时间："+getDateAndTime(message.examinationStart)+"——"+"</span>"+"<span>"+getDateAndTime(message.examinationEnd)+"</span></div>"+
            "<button class='btn btn-info' id='" + message.question_ID + "' onclick='goExam(this)'>"+"进入考试"+"</button>"+"</blockquote>";
        contentRight.append(messageBox);
        // }
    })
}

function goExam(e) {
    console.log(e.id);
}