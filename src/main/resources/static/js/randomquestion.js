var $path_base = "/";
var grid_data;
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
        $.ajax({
            type: "post",
            url: "/examPaperManager/randomQuestion",
            data: {"examinationId2":localStorage.getItem("examinationId2")},
            success: function (result) {
                if(result.status==200){
                    alert(result.data);
                    location.reload(true);
                }
                else{
                    alert(result.message);
                }
            }
        })
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
                    $('#questionsnum').html(result.data.questionSignal);
                    $('#questionmnum').html(result.data.questionMultiple);
                    $('#questionjnum').html(result.data.questionJudgement);
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
    if (isNaN(nubmer) || nubmer <= 0 || !(/^\d+$/.test(nubmer))&&num!="") {
        return false;
    }
    return true;
}
