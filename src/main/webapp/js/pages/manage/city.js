/**
 * Created by Lenovo on 2018/5/4.
 */
function initProvince(){
    $('#state').combobox({
        method:'get',
        url: path + "/regionArea/getAllState.action",
        valueField:'id',
        textField:'name',
        onSelect:function(record){
            initCity(record.id);
            $('#city').combobox('clear');
            $('#city').combobox('setValue','');
            $('#street').combobox('clear');
            $('#street').combobox('setValue','');
            $('#street').combobox('loadData',[]);
        }
    });
}


function initProvinceOnly(){
    $('#state').combobox({
        method:'get',
        url: path + "/regionArea/getAllState.action",
        valueField:'id',
        textField:'name'
    });
}

/**
 * 获取2级，区域层级
 * @param pid
 */
function initCity(pid) {
    $('#city').combobox({
        method:'get',
        url: path + "/regionArea/getCity.action?parentId="+pid,
        dataType: "json",
        valueField:'id',
        textField:'name',
        onSelect:function(record){
            initDistrict(record.id);
            $('#street').combobox('clear');
            $('#street').combobox('setValue','');
        },
        onLoadSuccess: function () { //加载完成后,设置选中第一项
            var data= $(this).combobox("getData");
            if (data.length > 0) {
                $('#city').combobox('select', data[0].id);
            }
        }
    });
}

function initDistrict(pid) {
    $('#street').combobox({
        method:'get',
        url: path + "/regionArea/getStreet.action?parentId="+pid,
        valueField:'id',
        textField:'name',
        onLoadSuccess: function () { //加载完成后,设置选中第一项
            var data= $(this).combobox("getData");
            if (data.length > 0) {
                $('#street').combobox('select', data[0].id);
            }
        }
    });
}




function initSearchProvince(){
    $('#searchState').combobox({
        method:'get',
        url: path + "/regionArea/getAllState.action",
        valueField:'id',
        textField:'name',
        onSelect:function(record){
            initSearchCity(record.id);
            $('#searchCity').combobox('clear');
            $('#searchCity').combobox('setValue','');
        }
    });
}

/**
 * 获取2级，区域层级
 * @param pid
 */
function initSearchCity(pid) {
    $('#searchCity').combobox({
        method:'get',
        url: path + "/regionArea/getCity.action?parentId="+pid,
        dataType: "json",
        valueField:'id',
        textField:'name',
        onSelect:function(record){
        },
        onLoadSuccess: function () { //加载完成后,设置选中第一项
            var data= $(this).combobox("getData");
            if (data.length > 0) {
                $('#searchCity').combobox('select', data[0].id);
            }
        }
    });
}