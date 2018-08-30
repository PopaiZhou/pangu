var EasyUIDataGrid = {
    //设置列的值(适用于所有列)
    setFieldValue: function (fieldName, value, index, grid) {
        if (grid == undefined || grid == '') {
            grid = $('#materialData');
        }

        if (index === undefined || index === '') {
            if (index == undefined) {
                index = 0;
            }
        }
        var row = grid.datagrid('getRows')[index];
        if (row != null) {
            var editor = grid.datagrid('getEditor', { index: index, field: fieldName });
            if (editor != null) {
                this.setValueToEditor(editor, value);
            }
            else {
                var view = $('.datagrid-view');
                for (var i = 0; i < view.length; i++) {
                    if ($(view[i]).children(grid.selector).length  > 0) {
                        var view = $(view[i]).children('.datagrid-view2');
                        var td = $(view).find('.datagrid-body td[field="' + fieldName + '"]')[index]
                        var div = $(td).find('div')[0];
                        $(div).text(value);
                    }
                }

                row[fieldName] = value;
            }
            grid.datagrid('clearSelections');
        }
    },

    //设置datagrid的编辑器的值
    setValueToEditor: function (editor, value) {
        switch (editor.type) {
            case "combobox":
                editor.target.combobox("setValue", value);
                break;
            case "combotree":
                editor.target.combotree("setValue", value);
                break;
            case "textbox":
                editor.target.textbox("setValue", value);
                break;
            case "numberbox":
                editor.target.numberbox("setValue", value);
                break;
            case "datebox":
                editor.target.datebox("setValue", value);
                break;
            case "datetimebox":
                editor.target.datebox("setValue", value);
                break;
            default:
                editor.html = value;
                break;
        }
    }
}