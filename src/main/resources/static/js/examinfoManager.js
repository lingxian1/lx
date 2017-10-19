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
                alert("身份认证失败");
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
            "<div class='twitter-panel pn' style='margin: 20px'><i class='fa fa-pencil-square-o fa-4x' onclick='showArea(event)'></i><p><strong>"+message.examinationId+"</strong>"+':'+message.examinationName+"<span id='examState"+i+"'></span>"+"</p>"+
            "<p>"+"参与人数："+message.examineeCount+"</p>"+
            "<p><span>"+"有效时间："+getDateAndTime(message.examinationStart)+"——"+"</span>"+"<span>"+getDateAndTime(message.examinationEnd)+"</span></p>"+"</div>";
        var timestamp = (new Date()).valueOf();
        contentRight.append(messageBox);
        if(timestamp>message.examinationEnd){
            $("#examState"+i).html("(已完成)").css({fontWeight:600,color:"#424a5d"});
        }else if(timestamp<message.examinationStart){
            $("#examState"+i).html("(未开始)").css({fontWeight:600,color:"#424a5d"});
        }
        else {
            $("#examState"+i).html("(进行中)").css({fontWeight:600,color:"#424a5d"});
        }
    })
}

function showArea(e) {
    var id=$(e.target).next().find("strong").text();

    localStorage.setItem('examinationId',id);
    console.log(id);
    window.open('gradeArea.html','gradeArea',
        'height=800,width=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no, status=no')
}