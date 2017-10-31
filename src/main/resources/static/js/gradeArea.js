var $path_base = "/";//this will be used in gritter alerts containing images
var grid_data;

/**
 * 获取区域考试信息
 */
$(function(){
    $.ajax({
        type: "get",
        url: "/gradeManager/area",
        data: {"examinationId":localStorage.getItem("examinationId")},
        success: function (result) {
            if(result.status==200){
                //console.log(JSON.stringify(result));
                grid_data=result.data;
                forms();
            }
            else{
                alert(result.message);
                //TODO 跳转
                location="/";
            }
        }
    })
});

function forms() {
    var grid_selector = "#grid-table";
    var pager_selector = "#grid-pager";

    jQuery(grid_selector).jqGrid({
        data: grid_data,
        datatype: "local",
        mtype:"POST",
        height: 450,
        colNames: ['考试编号', '区域', '参加人数',
            '平均分'],
        colModel: [
            {name: 'examinationId', index: 'examinationId', width: 80, editable: false, key: true},
            {name: 'areaName', index: 'areaName',width: 150,sortable: true,editable: true},
            {name: 'examineeCount',index: 'examineeCount', width: 60, editable: true},
            {name: 'gradeAvg', index: 'gradeAvg', width: 150, sortable: true,editable: true,formatter:'number',formatoptions: {decimalPlaces:1}}
        ],
        viewrecords : true,
        rowNum:10,
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
                updatePagerIcons(table);
                enableTooltips(table);
            }, 0);
        },
        editurl: $path_base,
        caption: "区域统计",
        autowidth: true
    });



    //navButtons
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
            view: false,
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

}




