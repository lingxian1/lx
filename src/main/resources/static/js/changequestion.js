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
                console.log(JSON.stringify(result));
                $('#count').html("已绑定数量"+result.data.realcount+"/"+result.data.count);
                $('#score').html("已绑定分数"+result.data.realscore+"/"+result.data.score);
            }
            else{
                alert(result.message);
                location="/createexamManager";
            }
        }
    })

    $.ajax({
        type: "get",
        url: "/examPaperManager/change",             //向springboot请求数据的url
        data: {"examinationId":localStorage.getItem("examinationId")}, //发送登陆ID及Token
        async:false,
        success: function (result) {
            if(result.status==200){
                console.log(JSON.stringify(result));
                grid_data=result.data;
            }
            else{
                alert(result.message);
                //TODO 跳转
                location="/";
            }
        }
    })
});

jQuery(function($) {
    var grid_selector = "#grid-table";
    var pager_selector = "#grid-pager";

    jQuery(grid_selector).jqGrid({
        //direction: "rtl",
        // url:$path_base+"test",
        data: grid_data,
        datatype: "local",
        mtype:"POST",
        height: 450,
        colNames:['  ','考试编号','试题编号','试题内容','试题类型','当前分值'],
        colModel:[
            {name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false,
                formatter:'actions',
                formatoptions:{
                    delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
                }
            },
            {name:'examinationId',index:'examinationId', width:120,editable: false,key:true},
            {name:'questionId',index:'questionId', width:120,editable: false,key:true},
            {name:'questionText',index:'questionText', width:100,editable: false},
            {name:'questionType',index:'questionType', width:100,editable: false},
            {name:'score',index:'score', width:90, editable: true,editrules:{number:true}},
        ],

        viewrecords : true,
        rowNum:20,
        rowList:[10,20,30],
        pager : pager_selector,
        altRows: true,
        //toppager: true,

        multiselect: false,
        //multikey: "ctrlKey",
        multiboxonly: true,

        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                styleCheckbox(table);
                updateActionIcons(table);
                updatePagerIcons(table);
                enableTooltips(table);
            }, 0);
        },

        editurl: $path_base+"examPaperManager/handle",//nothing is saved
        caption: "修改绑定",
        autowidth: true
    });

    //enable search/filter toolbar
    // jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})

    //switch element when editing inline
    function aceSwitch( cellvalue, options, cell ) {
        setTimeout(function(){
            $(cell) .find('input[type=checkbox]')
                .wrap('<label class="inline" />')
                .addClass('ace ace-switch ace-switch-5')
                .after('<span class="lbl"></span>');
        }, 0);
    }
    //enable datepicker
    function pickDate( cellvalue, options, cell ) {
        setTimeout(function(){
            $(cell) .find('input[type=text]')
                .datepicker({format:'yyyy-mm-dd' , autoclose:true});
        }, 0);
    }


    //navButtons
    jQuery(grid_selector).jqGrid('navGrid',pager_selector,
        { 	//navbar options
            edit: true,
            editicon : 'icon-pencil blue',
            add: false,
            addicon : 'icon-plus-sign purple',
            del: true,
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
            //closeAfterEdit: true,
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
            afterComplete:function (data,postdata) {
                alert(data.responseText);
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
            }
            ,
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
});

function clearAll() {
    var msg = "确定重置";
    if (confirm(msg)==true){
        $.ajax({
            type: "get",
            url: "/examPaperManager/deleteAll",
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
