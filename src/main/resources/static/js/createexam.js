var $path_base = "/";//this will be used in gritter alerts containing images
var grid_data;
var newDate = new Date();
var t= newDate.toJSON();

var selr;
/**
 * 获取考试信息
 */
$(function(){
    $.ajax({
        type: "get",
        url: "/examManager",
        data: {},
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
        data: grid_data,
        datatype: "local",
        mtype:"POST",
        height: 450,
        colNames:[' ','编号','考试名称','时长（分钟）','类型','试题数量','总分', '考试说明','起始日期', '截止日期','是否发布'],
        colModel:[
            {name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false,
                formatter:'actions',
                formatoptions:{
                    delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
                }
            },
            {name:'examinationId',index:'examinationId', width:60,editable: false,key:true},
            {name:'examinationName',index:'examinationName', width:100,editable: true,edittype: "textarea",editoptions:{maxlength:"255"}},
            {name:'answerTime',index:'answerTime', width:80,editable: true,editrules:{number:true}},
            {name:'examinationType',index:'examinationType', width:90, editable:true,editoptions:{maxlength:"10"}},
            {name:'questionCount',index:'questionCount', width:60, editable: true,editrules:{number:true}},
            {name:'examinationScoreAll',index:'questionCount', width:60, editable: true,editrules:{number:true}},
            {name:'examinationInfo',index:'examinationInfo', width:60, editable: true},
            {name:'examinationStart',index:'examinationStart', width:60, editable: true, formatter:"date",formatoptions: {language:'zh-CN',srcformat:'u',newformat:'Y-m-d H:i:s'},unformat: pickDate},
            {name:'examinationEnd',index:'examinationEnd', width:60, editable: true, formatter:"date",formatoptions: {language:'zh-CN',srcformat:'u',newformat:'Y-m-d H:i:s'},unformat: pickDate},
            {name:'isDel',index:'isDel', width:70, editable: false,edittype:"checkbox",editoptions: {value:"00:01"},unformat: aceSwitch}
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
                styleCheckbox(table);
                updateActionIcons(table);
                updatePagerIcons(table);
                enableTooltips(table);
            }, 0);
        },

        editurl: $path_base+"examManager/handle",
        caption: "考试管理",
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
                .datetimepicker({format:'yyyy-mm-dd HH:ii:ss',weekStart: 1,startDate:new Date(t),
                    todayBtn:1, autoclose:true});
        }, 0);
    }


    //navButtons
    jQuery(grid_selector).jqGrid('navGrid',pager_selector,
        { 	//navbar options
            edit: true,
            editicon : 'icon-pencil blue',
            add: true,
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



    function style_edit_form(form) {
        //enable datepicker on "sdate" field and switches for "stock" field
        form.find('input[name=examinationStart]').datetimepicker({language:'zh-CN',format:'yyyy-mm-dd HH:ii:ss',autoclose:true})
            .end().find('input[name=examinationEnd]').datetimepicker({language:'zh-CN',format:'yyyy-mm-dd HH:ii:ss',autoclose:true})
            .end().find('input[name=isDEL]')
            .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');

        //update buttons classes
        var buttons = form.next().find('.EditButton .fm-button');
        buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
        buttons.eq(0).addClass('btn-primary').prepend('<i class="icon-ok"></i>');
        buttons.eq(1).prepend('<i class="icon-remove"></i>')

        buttons = form.next().find('.navButton a');
        buttons.find('.ui-icon').remove();
        buttons.eq(0).append('<i class="icon-chevron-left"></i>');
        buttons.eq(1).append('<i class="icon-chevron-right"></i>');
    }

    function style_delete_form(form) {
        var buttons = form.next().find('.EditButton .fm-button');
        buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
        buttons.eq(0).addClass('btn-danger').prepend('<i class="icon-trash"></i>');
        buttons.eq(1).prepend('<i class="icon-remove"></i>')
    }

    function style_search_filters(form) {
        form.find('.delete-rule').val('X');
        form.find('.add-rule').addClass('btn btn-xs btn-primary');
        form.find('.add-group').addClass('btn btn-xs btn-success');
        form.find('.delete-group').addClass('btn btn-xs btn-danger');
    }
    function style_search_form(form) {
        var dialog = form.closest('.ui-jqdialog');
        var buttons = dialog.find('.EditTable')
        buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'icon-retweet');
        buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'icon-comment-alt');
        buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'icon-search');
    }

    function beforeDeleteCallback(e) {
        var form = $(e[0]);
        if(form.data('styled')) return false;

        form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        style_delete_form(form);

        form.data('styled', true);
    }

    function beforeEditCallback(e) {
        var form = $(e[0]);
        form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        style_edit_form(form);
    }



    //it causes some flicker when reloading or navigating grid
    //it may be possible to have some custom formatter to do this as the grid is being created to prevent this
    //or go back to default browser checkbox styles for the grid
    function styleCheckbox(table) {
        /**
         $(table).find('input:checkbox').addClass('ace')
         .wrap('<label />')
         .after('<span class="lbl align-top" />')


         $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
         .find('input.cbox[type=checkbox]').addClass('ace')
         .wrap('<label />').after('<span class="lbl align-top" />');
         */
    }


    //unlike navButtons icons, action icons in rows seem to be hard-coded
    //you can change them like this in here if you want
    function updateActionIcons(table) {
        /**
         var replacement =
         {
             'ui-icon-pencil' : 'icon-pencil blue',
             'ui-icon-trash' : 'icon-trash red',
             'ui-icon-disk' : 'icon-ok green',
             'ui-icon-cancel' : 'icon-remove red'
         };
         $(table).find('.ui-pg-div span.ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
         */
    }

    //replace icons with FontAwesome icons like above
    function updatePagerIcons(table) {
        var replacement =
            {
                'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
                'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
                'ui-icon-seek-next' : 'icon-angle-right bigger-140',
                'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
            };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

            if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
        })
    }

    function enableTooltips(table) {
        $('.navtable .ui-pg-button').tooltip({container:'body'});
        $(table).find('.ui-pg-div').tooltip({container:'body'});
    }

});

//新窗口试题绑定
function addQuestion() {
    selr = $('#grid-table').jqGrid('getGridParam','selrow');
    if(selr==null){
        alert("请选择一场考试");
    }
    else {
        localStorage.setItem('examinationId',selr);
        console.log(selr);
        window.open('addquestion.html','addquestion',
            'height=1000,width=1000,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no, status=no')
    }
}

//绑定编辑修改
function reviseQuestion() {
    selr = $('#grid-table').jqGrid('getGridParam','selrow');
    if(selr==null){
        alert("请选择一场考试");
    }
    else {
        localStorage.setItem('examinationId',selr);
        console.log(selr);
        window.open('changequestion.html','changequestion',
            'height=1000,width=1000,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no, status=no')
    }
}

//发布考试
function publishExam() {
    selr = $('#grid-table').jqGrid('getGridParam','selrow');
    if(selr==null){
        alert("请选择一场考试");
    }
    else {
        console.log(selr);
        $.ajax({
            type: "post",
            url: "/examPaperManager/publish",
            data: {"examinationId":selr},
            async:false,
            success: function (result) {
                if(result.status==200){
                    console.log(JSON.stringify(result));
                    alert("发布成功");
                    location.reload(true);
                }
                else{
                    alert(result.message);
                }
            }
        });
    }
}
