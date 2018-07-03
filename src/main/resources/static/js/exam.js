//题目数量
var questionCount = 10;
//当前题目指针
var questionIndex = 0;

//问题ID
var questionID="";
//题目类型
// var questionType = "multiple";
var questionType = "single";
//问题
var question = "题目获取中...";
//选项数量
var selectCount = 4;
//具体选项
var answerA = "问题获取中...";
var answerB = "问题获取中...";
var answerC = "问题获取中...";
var answerD = "问题获取中...";

//临时答案计数数组
var count=new Array(0,0,0,0);
var answerStr="";
//保存题目数据（临时）
var data;

//初始化
$(function(){
    localStorage.clear();
    //获取考试信息
    $.ajax({
        type: "get",
        url: "/examinfo1Examinees/examinfo2",             //向springboot请求数据的url
        data: {"examineeId":getCookie("userId"),"examinationId":getCookie("examinationIdU")},
        success: function (result) {
            //考试时间
           // console.log(JSON.stringify(result));
            var sumtime=result.data.answerTime;
            questionCount=result.data.questionCount;
            setTime(sumtime);
        }
    }),
        $.ajax({
            type: "get",
            url: "/questionExaminees",
            data: {"examineeId":getCookie("userId"),"examinationId":getCookie("examinationIdU")},
            success: function (result) {
                if(result.status==200){
                    data=result.data;
                    setAnswer(0);
                    setQuestion();
                    setSelects();
                }else{
                    console.log(result.message);
                    alert(result.message);
                    location="/signin";
                }

            }
        }),
        initAnswer()
});

//初始化选项选中标志
function initAnswer(){
    $('#OK_A,#OK_B,#OK_C,#OK_D').addClass('hide');
}

//设置答案
function setAnswer(index) {
    questionID=data[index].questionId;
    question=data[index].questionText;
    questionType=data[index].questionType;
    selectCount=data[index].questionChooseCount;
    answerA=data[index].questionChooseA;
    answerB=data[index].questionChooseB;
    answerC=data[index].questionChooseC;
    answerD=data[index].questionChooseD;
    // console.log(questionIndex);
}
//计时器
function setTime(sumtime){
    var starttime = localStorage.getItem('starttime');
    if(!starttime){ //没有就new一个并扔进去(Date会自动toString)
        starttime = new Date();
        localStorage.setItem('starttime',starttime);
    }else{ //有就把字符串转成Date
        starttime = new Date(starttime);
    }
    var interval = setInterval(function () {
        var newtime = new Date();
        var seconds = (newtime - starttime) / 1000;
        var time = secondstotime(parseInt(sumtime - seconds));
        $('#time').text(time);
        $('#time').css("font-size","24px");
        $('#time').css("font-weight","bold");
        $('#time').css("color","#0269c2");
    }, 100);//0.1s刷新1次
    setTimeout(function () {
        clearInterval(interval);
        localStorage.removeItem('starttime'); //清除本地数据
        saveAnswer();
    }, sumtime * 1000)
}

//秒数转换
function secondstotime(seconds) {
    var hours = parseInt(seconds / 3600);
    seconds %= 3600;
    var minutes = parseInt(seconds / 60);
    seconds %= 60;
    return hours + ':' + minutes + ':' + seconds;
}

//下一题
function after() {
    //保存答案
    createAnswer(answerStr);
    questionIndex++;
    if(questionIndex>=questionCount){
        console.log(questionIndex);
        submitAnswer();
    }else {
        if(questionIndex==questionCount-1){
            $("#after").html("提交");
        }
        initAnswer();
        setAnswer(questionIndex);
        setQuestion();
        setSelects();
        setStates();
    }
}

//上一题
function before() {
    createAnswer(answerStr);
    $("#after").html("<span class='glyphicon glyphicon-chevron-down'></span>下一题");
    questionIndex--;
    if(questionIndex>=0){
        initAnswer();
        setAnswer(questionIndex);
        setQuestion();
        setSelects();
        setStates();
    }else{
        questionIndex=0;
    }
}
//设置选中效果
function setStates() {
    var a=localStorage.getItem(questionID);
    console.log(a);
    if(a){
        answerStr=a;
        for(var i=0;i<a.toString().length;i++){
            var temp=a.toString().substring(i,i+1);
            $('#OK_'+temp).removeClass('hide');
            if(questionType == "multiple"){
                count[temp.charCodeAt()-65]=1;
            }
        }
    }
}

//设置问题及其类型
function setQuestion() {
    var index=questionIndex+1;
    var temp;
    if(questionType=="single"){
        temp="单选题";
    }else if(questionType=="multiple") {
        temp="多选题";
    }else{
        temp="判断题";
    }
    $("#questionType").html(temp + "(" + index + "/" + questionCount + ")");
    $("#question").html(index + ")." + question);
}

//设置选项
function setSelects() {
    $("#answerA").html(answerA);
    $("#answerB").html(answerB);
    $("#answerC").html(answerC);
    $("#answerD").html(answerD);
    $("#selectC,#selectD").show();
    if (selectCount == 3) {
        $("#selectD").hide();
    }
    if (selectCount == 2) {
        $("#selectC").hide();
        $("#selectD").hide();
    }
}

//被选中效果 questionType全局
function isSelected(e) {
    if (questionType == "single"||questionType=="judgement") {
        $('[id^="OK_"]').addClass('hide');
        $('#OK_'+e.id.substr(e.id.length-1,1)).removeClass('hide');
        answerStr=e.id.substr(e.id.length-1,1);
    }else if(questionType == "multiple"){
        $('#OK_'+e.id.substr(e.id.length-1,1)).toggleClass('hide');
        var choose=e.id.substr(e.id.length-1,1);
        count[choose.charCodeAt()-65]++;//保存临时答案计数数组
    }
}

//生成考生答案_每道题
function createAnswer(answerStr) {
    if(questionType == "multiple"){//多选答案生成
        var answerStr="";
        for(var i=0;i<4;i++){
            if(count[i]%2==1){
                answerStr+=String.fromCharCode(65+i);
            }
            count[i]=0;//重置临时答案计数数组
        }
    }
    console.log(questionID+"- "+answerStr);
    localStorage.setItem(questionID,answerStr);
}

//递交答案
function submitAnswer() {
    var msg = "确定提交";
    if (confirm(msg)==true){
        saveAnswer();
    }else{
        questionIndex--;
        console.log(questionIndex);
        return false;
    }
}

//答案包装为JSON
function ansToJson() {
    var jsons = [];
    localStorage.removeItem("starttime");
    var examineeId=getCookie("userId");
    var examinationId=getCookie("examinationIdU");
    var i=0;
    for(var k in localStorage) {
        var temp={"examineeId":examineeId,"examinationId":examinationId,"questionId":localStorage.key(i),"examineeAnswer":localStorage[k]};
        jsons[i] = temp;
        i++;
    }
    var json_sorted = JSON.stringify(jsons);
    // console.log(json_sorted);
    return json_sorted;
}
//上传答案
function saveAnswer() {
    $.ajax({
        type: "post",
        url: "/questionExaminees/answer",             //向springboot请求数据的url
        data: ansToJson(),
        success: function (result) {
            console.log(result);
            if(result.status!=200){
                console.log("done");
                alert(result.message);
                location="/examinfoExaminees"
            }else{
                console.log("上传成功");
                localStorage.clear();//清空本地存储
                alert("提交成功");
                location="/gradeExaminees"
            }
        },
        contentType: "application/json",
    })

}

//退出答题
function exitExam(){
    var msg = "退出时将提交数据且不可再次进入！";
    if (confirm(msg)==true){
        saveAnswer();
    }else{
        return false;
    }
}
