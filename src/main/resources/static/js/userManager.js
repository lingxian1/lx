var $path_base = "/";//this will be used in gritter alerts containing images
var grid_data;
$(function(){
    $.ajax({
        type: "get",
        url: "/usersManager",
        data: {"userId":getCookie("userId"),"token":getCookie("token")}, //发送登陆ID及Token
        async:false,
        success: function (result) {
            if(result.status==200){
            //    console.log(JSON.stringify(result));
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
        colNames:[' ','考生Id','姓名','手机','区域', '性别'],
        colModel:[
            {name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false,
                formatter:'actions',
                formatoptions:{
                    delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
                }
            },
            {name:'examineeId',index:'examineeId', width:120,editable: false,key:true},
            {name:'name',index:'name', width:100,editable: true,editoptions:{size:"20",maxlength:"30"}},
            {name:'phone',index:'phone', width:100,editable: true,editoptions:{size:"20",maxlength:"11"},editrules:{required:true}},
            {name:'areaId',index:'areaId', width:90, editable: true,edittype:"select",editoptions:{value:"00:市局;01:定海;02:普陀;03:岱山;04:六横;05:金塘;06:新城;07:嵊泗"}},
            {name:'sex',index:'sex', width:60, editable: true,edittype:"select",editoptions:{value:"男:男;女:女"}}
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

        editurl: $path_base+"usersManager/handle",//nothing is saved
        caption: "考生信息",
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
                }else{
                    ShowMsg(data.responseText);
                    setTimeout(reload1,800);
                }
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

function reload1() {
    location.reload(true)
}

function resetPs() {
    var phone = $("[name='info']").val();
    $.ajax({
        type: "post",
        url: "/usersManager/resetPs",
        data: {"userId":getCookie("userId"),"token":getCookie("token"),"phone":phone}, //发送登陆ID及Token
        success: function (result) {
            if(result.status==200){
                alert("重置成功：已重置为123456");
            }else {
                alert(result.message);
            }
        }
    })
}