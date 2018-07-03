var $path_base = "/";
var grid_data;
var newDate = new Date();
var t= newDate.toJSON();
var flag=0;
var id=localStorage.getItem("examinationId");
$(function(){
    $('#examinationId').html(id);
   // console.log(id);
    if(id==null){
        alert("考试未选择");
        location="/";
    }else {

    $.ajax({
        type: "get",
        url: "/examManager/addquestion",
        data: {"examinationId": id},
        // async:false,
        success: function (result) {
            if(result.status==200){
            //    console.log(JSON.stringify(result));
                $('#count').html("已绑定数量"+result.data.realcount+"/"+result.data.count);
                $('#score').html("已绑定分数"+result.data.realscore+"/"+result.data.score);
            }
            else{
                alert(result.message);
                window.opener=null;
                window.close();
            }
        }
    })
    }
});

function chooseQuestion() {
    var grid_selector = "#grid-table";
    var pager_selector = "#grid-pager";
    if(flag==0){
        flag++;
        jQuery(grid_selector).jqGrid({
        data: grid_data,
        datatype: "local",
        mtype:"POST",
        height: 300,
        colNames:['编号', '题目', '类型',
            '选项A', '选项B', '选项C', '选项D',
            '分类','创建时间'],
        colModel:[
            {name:'questionId',index:'questionId', width:60,editable: false,key:true},
            {name:'questionText',index:'questionText', width:100,editable:false},
            {name:'questionType',index:'questionType', width:80,editable: false},
            {name:'questionChooseA',index:'questionChooseA', width:90, editable:false},
            {name:'questionChooseB',index:'questionChooseB', width:60, editable: false},
            {name:'questionChooseC',index:'questionChooseC', width:60, editable: false},
            {name:'questionChooseD',index:'questionChooseD', width:60, editable: false},
            {name:'questionClassification',index:'questionClassification', width:60, editable: false},
            {name:'questionCreateTime',index:'questionCreateTime', width:60, editable: false, formatter:"date",formatoptions: {language:'zh-CN',srcformat:'u',newformat:'Y-m-d H:i:s'},unformat: pickDate}
        ],

        viewrecords : true,
        rowNum:20,
        rowList:[10,20,30],
        pager : pager_selector,
        altRows: true,
        multiselect: true,
        multiboxonly: false,

        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                styleCheckbox(table);
                updateActionIcons(table);
                updatePagerIcons(table);
                enableTooltips(table);
            }, 0);
        },

        editurl: $path_base+"examManager/handle",
        caption: "试题绑定",
        autowidth: true
    });
    } else{
        $(grid_selector).jqGrid('clearGridData');  //清空表格
        $(grid_selector).jqGrid('setGridParam',{  // 重新加载数据
            datatype:'local',
            data : grid_data,   //  newdata 是符合格式要求的需要重新加载的数据
        }).trigger("reloadGrid");
    }

    function aceSwitch( cellvalue, options, cell ) {
        setTimeout(function(){
            $(cell) .find('input[type=checkbox]')
                .wrap('<label class="inline" />')
                .addClass('ace ace-switch ace-switch-5')
                .after('<span class="lbl"></span>');
        }, 0);
    }

    function pickDate( cellvalue, options, cell ) {
        setTimeout(function(){
            $(cell) .find('input[type=text]')
                .datetimepicker({format:'yyyy-mm-dd HH:ii:ss',weekStart: 1,startDate:new Date(t),
                    todayBtn:1, autoclose:true});
        }, 0);
    }

    jQuery(grid_selector).jqGrid('navGrid',pager_selector,
        { 	//navbar options
            edit: false,
            editicon : 'icon-pencil blue',
            add: false,
            addicon : 'icon-plus-sign purple',
            del: false,
            delicon : 'icon-trash red',
            search: true,
            searchicon : 'icon-search orange',
            refresh: true,
            refreshicon : 'icon-refresh green',
            view: true,
            viewicon : 'icon-zoom-in grey',
        },
        {
            //edit record form
            recreateForm: true,
            beforeShowForm : function(e) {
                var form = $(e[0]);
                form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                style_edit_form(form);
            }
        },
        {
            //new record form
            closeAfterAdd: true,
            recreateForm: true,
            viewPagerButtons: false,
            beforeShowForm : function(e) {
                var form = $(e[0]);
                form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                style_edit_form(form);
            },
            //请求结果处理
            afterComplete:function (data,postdata) {
                alert(data.responseText);
                //刷新页面
                // location.reload(true);
            }
        },
        {
            //delete record form
            recreateForm: true,
            beforeShowForm : function(e) {
                var form = $(e[0]);
                if(form.data('styled')) return false;

                form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                style_delete_form(form);
                form.data('styled', true);
            },
            onClick : function(e) {
                alert(1);
            }
        },
        {
            //search form
            recreateForm: true,
            afterShowSearch: function(e){
                var form = $(e[0]);
                form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
                style_search_form(form);
            },
            afterRedraw: function(){
                style_search_filters($(this));
            },
            multipleSearch: true,
            /**
             multipleGroup:true,
             showQuery: true
             */
        },
        {
            //view record form
            recreateForm: true,
            beforeShowForm: function(e){
                var form = $(e[0]);
                form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
            }
        }
    )

    function styleCheckbox(table) {
         $(table).find('input:checkbox').addClass('ace')
         .wrap('<label />')
         .after('<span class="lbl align-top" />')


         $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
         .find('input.cbox[type=checkbox]').addClass('ace')
         .wrap('<label />').after('<span class="lbl align-top" />');
    }
};

function findexam() {
    var questionClass=$('#questionClass').val();
    var days=$('input:radio[name="days"]:checked').val();
    var questionType=$('input:radio[name="choose"]:checked').val();
    var top=0;
    console.log("question"+questionClass);
    console.log("days"+days+"chooses"+questionType);
    $.ajax({
        type: "get",
        url: "/examManager/choosequestion",
        data: {"examinationId": localStorage.getItem("examinationId"),"questionClass": questionClass,"days":days,"questionType":questionType,"top":top},
        // async:false,
        success: function (result) {
            if(result.status==200){
                console.log(JSON.stringify(result));
                grid_data=result.data;
                chooseQuestion();
            }
            else{
                alert(result.message);
            }
        }
    })
}

function getinfo(){
    var types=$('#types').val();
    var typem=$('#typem').val();
    var typej=$('#typej').val();
    if((checkRate(types)&&checkRate(typem)&&checkRate(typej))==false){
        alert("请输入正整数分值!");
    }else{
        var jsons = [];
        var score=0;
        var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');
        ids.forEach(function (value,index,array) {
            console.log(value);
            console.log("index:"+index);
            //某单元格内容
            var celldata = $('#grid-table').jqGrid('getCell',value,3);
            if(celldata=="single"){
                score=types;
            }else if(celldata="multiple"){
                score=typem;
            }else if(celldata="judgement"){
                score=typej;
            }
            console.log(celldata);
            var temp={"examinationId":id,"questionId":value,"score":score,"accuracy":2};
            jsons[index] = temp;
        });
        console.log(JSON.stringify(jsons));
        $.ajax({
            type: "post",
            url: "/examPaperManager/add",
            data: JSON.stringify(jsons),
            contentType: "application/json",
            // async:false,
            success: function (result) {
                if(result.status==200){
                    console.log(JSON.stringify(result));
                    alert("提交成功");
                    window.opener=null;
                    window.close();
                }
                else{
                    alert(result.message);
                }
            }
        })
    }

}

function checkRate(num) {
    var nubmer=num;
    console.log(num);
    if (isNaN(nubmer) || nubmer <= 0 || !(/^\d+$/.test(nubmer))&&num!="") {
            return false;
    }
    return true;
}