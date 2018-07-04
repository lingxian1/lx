var $path_base = "/";//this will be used in gritter alerts containing images
var grid_data;
var grid_selector = "#grid-table";
var flag=0;
function query(){
    var info = $("[name='info']").val();
    // $("#showclass").removeClass("displayno");
   // console.log(info);
    $.ajax({
        type: "get",
        url: "/questionsManager",             //向springboot请求数据的url
        data: {"userId":getCookie("userId"),"token":getCookie("token"),"info":info}, //发送登陆ID及Token
        // async:false,
        success: function (result) {
            if(result.status==200){
                // console.log(JSON.stringify(result));
                grid_data=result.data;
                createform();
            }else if(result.status==10016){
                alert(result.message);
            }else{
                alert(result.message);
                //TODO 跳转
                location="/";
            }
        }
    })
}

function createform() {
   // console.log(flag);
    var pager_selector = "#grid-pager";
    if(flag==0) {
        flag++;
        jQuery(grid_selector).jqGrid({
            //direction: "rtl",
            // url:$path_base+"test",
            data: grid_data,
            datatype: "local",
            mtype: "POST",
            height: 450,
            colNames: [' ', '编号', '题目', '类型',
                '选项A', '选项B', '选项C', '选项D',
                '答案', '分类'],
            colModel: [
                {
                    name: 'myac', index: '', width: 80, fixed: true, sortable: false, resize: false,
                    formatter: 'actions',
                    formatoptions: {
                        delOptions: {recreateForm: true, beforeShowForm: beforeDeleteCallback},
                    }
                },
                {name: 'questionId', index: 'questionId', width: 80, editable: false, key: true},
                {
                    name: 'questionText',
                    index: 'questionText',
                    width: 150,
                    sortable: false,
                    editable: true,
                    edittype: "textarea",
                    editoptions: {maxlength: "255"},
                    editrules:{ required:true}
                },
                {
                    name: 'questionType',
                    index: 'questionType',
                    width: 60,
                    editable: true,
                    edittype: "select",
                    editoptions: {value: "single:单选;multiple:多选;judgement:判断"}
                },
                {
                    name: 'questionChooseA',
                    index: 'questionChooseA',
                    width: 150,
                    sortable: false,
                    editable: true,
                    edittype: "textarea",
                    editoptions: {maxlength: "255"},
                    editrules:{ required:true}
                },
                {
                    name: 'questionChooseB',
                    index: 'questionChooseB',
                    width: 150,
                    sortable: false,
                    editable: true,
                    edittype: "textarea",
                    editoptions: {maxlength: "255"}
                },
                {
                    name: 'questionChooseC',
                    index: 'questionChooseC',
                    width: 150,
                    sortable: false,
                    editable: true,
                    edittype: "textarea",
                    editoptions: {maxlength: "255"}
                },
                {
                    name: 'questionChooseD',
                    index: 'questionChooseD',
                    width: 150,
                    sortable: false,
                    editable: true,
                    edittype: "textarea",
                    editoptions: {maxlength: "255"}
                },
                {
                    name: 'questionAnswer',
                    index: 'questionAnswer',
                    width: 40,
                    editable: true,
                    editoptions: {maxlength: "4"},
                    editrules:{ required:true}
                },
                {
                    name: 'questionClassification',
                    index: 'questionClassification',
                    width: 60,
                    sortable: false,
                    editable: true,
                    edittype: "textarea",
                    editoptions: {maxlength: "20"},
                    editrules:{ required:true}
                }],

            viewrecords: true,
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: pager_selector,
            altRows: true,
            //toppager: true,

            multiselect: false,
            //multikey: "ctrlKey",
            multiboxonly: true,

            loadComplete: function () {
                var table = this;
                setTimeout(function () {
                    styleCheckbox(table);
                    updateActionIcons(table);
                    updatePagerIcons(table);
                    enableTooltips(table);
                }, 0);
            },

            editurl: $path_base + "questionsManager/handle",//nothing is saved
            caption: "试题分类查询结果",
            autowidth: true
        });
    }
    else{
        $(grid_selector).jqGrid('clearGridData');  //清空表格
        $(grid_selector).jqGrid('setGridParam',{  // 重新加载数据
            datatype:'local',
            data : grid_data,   //  newdata 是符合格式要求的需要重新加载的数据
        }).trigger("reloadGrid");
    }
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
            afterComplete:function (data,postdata) {
                // alert(data.responseText);
                if(data.status!=200){
                    alert(data.status);
                }
                ShowMsg(data.responseText);
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
        form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
            .end().find('input[name=stock]')
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

    //var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
}

function qinfo() {
    window.open('/questionclassinfoManager','questionclassinfoManager',
        'height=1000,width=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no, status=no')
}

function uploadExcel() {
    var xhr;
    //上传文件方法
    var fileObj = document.getElementById("file").files[0]; // js 获取文件对象
    var url = "/fileManager/uploadQuestion"; // 接收上传文件的后台地址
    var form = new FormData(); // FormData 对象
    form.append("file", fileObj); // 文件对象
    xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
    xhr.open("post", url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
    xhr.onload = uploadComplete; //请求完成
    xhr.send(form); //开始上传，发送form数据
}
    //上传成功响应
function uploadComplete(evt) {
    //服务断接收完文件返回的结果
    var data = JSON.parse(evt.target.responseText);
    if(data.status===200) {
        alert("上传成功！");
    }else{
        alert(data.message);
    }
    var obj=document.getElementById("file");
    obj.outerHTML=obj.outerHTML;
}
