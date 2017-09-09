Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

layui.use('jquery', function () {
    var $ = layui.jquery;

    $.ajaxSettings.contentType = 'application/json; charset=UTF-8';

    $(document).ajaxError(function (event, xhr, options, exc) {
        if (xhr.status == 401) {
            console.log(xhr.responseText);
        } else if (xhr.status == 405) {
            console.log(xhr.responseText);
        } else {
            console.log(xhr.responseText);
        }
    });

    $.extend({
        post2: function(uri, params, callback) {
            var _params = callback ? params : {};
            var _callback = callback ? callback : params;
            $.post(uri, JSON.stringify(_params), _callback, 'json');
        }
    });
});


layui.define('jquery', function(exports){
    var http = {};

    http.post = function (uri, params, callback) {
        var _params = callback ? params : {};
        var _callback = callback ? callback : params;
        $.post(uri, JSON.stringify(_params), _callback, 'json');
    };

    exports('http', http);
});