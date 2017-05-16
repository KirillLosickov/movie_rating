var I = {
    "actor": 0
    ,"country": 0
    ,"person": 0
    ,"genre": 0
    , "position": 0
}

function add(nameDiv) {
    $("#div" + nameDiv).append($("#" + nameDiv + "S").html());
    I[nameDiv]++;
    var id = nameDiv + I[nameDiv];
    $("#div" + nameDiv + "> #" + nameDiv + "0").attr("id", id)
}

function del(nameDiv) {
    if (I[nameDiv]>0) {
        var id = "#" + nameDiv + I[nameDiv];
        $(id).remove();
        I[nameDiv]--;
    }
}