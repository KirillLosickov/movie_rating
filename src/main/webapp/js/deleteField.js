var block = {
    "country": 0,
    "genre": 0,
    "person" : 0
};

var I = {
    "country": 0,
    "genre": 0,
    "person" : 0
};

$(document).ready(function () {
    block["country"] = $("#Select" + "country").html();
    block["genre"] = $("#Select" + "genre").html();
    block["person"] = $("#Select" + "person").html();

    I["country"] = $(".countcountry").length;
    I["genre"] = $(".countgenre").length;
    I["person"] = $(".countperson").length;
});

function addField(What) {
    $("#" + "Div" + What).append(block[What]);
    I[What]++;
    var id = What + "New" + I[What];
    $("#" + "Div" + What + " > " + "#" + What + "New" + "0").attr("id", id);
    var funcValue = "deleteField('" + What + "', '" + "New" + I[What] + "')";
    $("#" + What + "New" + I[What] + " > " + "button").attr("onclick", funcValue);
}

function deleteField(nameField, idField) {
    if (I[nameField]>1) {
        $("#" + nameField + idField).remove();
        I[nameField]--;
    }
}