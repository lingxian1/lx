$(function(){
    $.ajax({
        type: "get",
        url: "/examinfo1Examinees",
        data: {"examineeId":getCookie("userId")},
        success: function (result) {
            if(result.status==200){
                var data=result;
               // console.log(JSON.stringify(result));
                msg_display(data.data);
            }else{
                alert(result.message);
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
        if(message.examinationType=="正式考试"){
            var messageBox =
                "<blockquote style='background-color: #abe4e9'><p>"+message.examinationName+"</p>"+
                "<p>"+message.examinationInfo+"</p>"+
                "<span>"+"满分："+message.examinationScoreAll+"</span>"+"<span>"+"&nbsp&nbsp时间："+message.answerTime/60 +"分钟"+
                "</span><span><strong>"+"&nbsp&nbsp类型："+message.examinationType+"（必做）"+"</strong></span>"+
                "<div><span>"+"有效时间："+getDateAndTime(message.examinationStart)+"——"+"</span>"+"<span>"+getDateAndTime(message.examinationEnd)+"</span></div>"+
                "<button class='btn btn-info' id='" + message.examinationId + "' onclick='goExam(this)'>"+"进入考试"+"</button>"+"</blockquote>";
        }else{
            var messageBox =
                "<blockquote><p>"+message.examinationName+"</p>"+
                "<p>"+message.examinationInfo+"</p>"+
                "<span>"+"满分："+message.examinationScoreAll+"</span>"+"<span>"+"&nbsp&nbsp时间："+message.answerTime/60 +"分钟"+
                "</span><span>"+"&nbsp&nbsp类型："+message.examinationType+"（选做）"+"</span>"+
                "<div><span>"+"有效时间："+getDateAndTime(message.examinationStart)+"——"+"</span>"+"<span>"+getDateAndTime(message.examinationEnd)+"</span></div>"+
                "<button class='btn btn-info' id='" + message.examinationId + "' onclick='goExam(this)'>"+"开始练习"+"</button>"+"</blockquote>";
        }

        contentRight.append(messageBox);
    })
}

function goExam(e) {
    console.log(e.id);
    setCookie("examinationIdU",e.id);
    location = "/examsExaminees";        //跳转
}