var $path_base = "/";
var grid_data;
//s m j保存试题成分数量上限
var s;
var m;
var j;
var id=localStorage.getItem("examinationId");
$(function(){
    $('#examinationId').html(id);
    setCookie("examinationId2",id);
    if(id==null){
        alert("考试未选择");
        location="/";
    }
    $.ajax({
        type: "get",
        url: "/examManager/addquestion",
        data: {"examinationId": id},
        // async:false,
        success: function (result) {
            if(result.status==200){
                console.log(result);
                console.log(JSON.stringify(result));
                $('#count').html("试题总数："+result.data.count);
            }
            else{
                alert(result.message);
                location="/createexam";
            }
        }
    })
});

function getinfo() {
    var msg = "确定提交发布";
    if (confirm(msg)==true){
        var questions=$('#questions').val();
        var questionm=$('#questionm').val();
        var questionj=$('#questionj').val();
        var types=$('#types').val();
        var typem=$('#typem').val();
        var typej=$('#typej').val();
        var question=$('#questionClass').val();
        var flag=checkRate(questions)&&checkRate(questionm)&&checkRate(questionj)&&checkRate(types)&&checkRate(typem)&&checkRate(typej);
        if(flag==false){
            alert("请输入正整数分值或成分!");
        }else if((questions>s||questionm>m||questionj>j)==true){
            alert("请输入不超过上限的分值!");
        }else {
            $.ajax({
                type: "post",
                url: "/examPaperManager/randomQuestion",
                data: {
                    "examinationId": localStorage.getItem("examinationId"),
                    "questions": questions,
                    "questionm": questionm,
                    "questionj": questionj,
                    "types": types,
                    "typem": typem,
                    "typej": typej,
                    "questionClass": question
                },
                success: function (result) {
                    if (result.status == 200) {
                        alert(result.data);

                    }
                    else {
                        alert(result.message);
                    }
                }
            })
        }
    }else{
        return false;
    }
}

function findinfo() {
    var question=$('#questionClass').val();
        $.ajax({
            type: "get",
            url: "/examPaperManager/findQuestion",
            data: {"questionClass":question},
            success: function (result) {
                if(result.status==200){
                    console.log(JSON.stringify(result));
                    $("#panel1").removeClass("displayno");
                    $("#panel2").removeClass("displayno");
                    $("#panel3").removeClass("displayno");
                    s=result.data.questionSignal;
                    m=result.data.questionMultiple;
                    j=result.data.questionJudgement;
                    $('#questionsnum').html(s);
                    $('#questionmnum').html(m);
                    $('#questionjnum').html(j);
                }
                else{
                    console.log(JSON.stringify(result));
                    alert(result.message);
                }
            }
        })
}


function checkRate(num) {
    var nubmer=num;
    console.log(num);
    if (num==""||isNaN(nubmer) || nubmer < 0 || !(/^\d+$/.test(nubmer))&&num!="") {
        return false;
    }
    return true;
}
