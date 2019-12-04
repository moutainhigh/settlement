/** 级别 手动填写 */
function handInput() {
    $("#div1").hide();
    $("#posLevelSelect").removeAttr("name");
    $("#posLevelSelect").val('');
    $("#div2").show();
    $("#posLevelInput").val('');
    $("#posLevelInput").attr("name","posLevel");
    $("#priceMonth").val('');
    $("#priceMonth").removeAttr("readonly");
    $("#handInputId").hide();
     $("#frameInputId").show();
     layui.form.render();
}

/** 级别 框架选择 */
function frameInput() {
    $("#div1").show();
    $("#posLevelSelect").attr("name","posLevel");
    $("#posLevelSelect").val('');
    $("#div2").hide();
    $("#posLevelInput").removeAttr("name");
    $("#priceMonth").val('');
    $("#priceMonth").attr("readonly","readonly");
    $("#handInputId").show();
    $("#frameInputId").hide();
    layui.form.render();
}