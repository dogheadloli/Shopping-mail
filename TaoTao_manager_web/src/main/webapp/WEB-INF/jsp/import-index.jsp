<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/4 0004
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <a class="easyui-linkbutton" onclick="importIndex()">一键导入商品数据到索引库</a>
</div>
<script type="text/javascript">
    function importIndex() {
        $.post("/index/import", function (data) {
            if (data.status == 200) {
                $.messager.alert('提示', '导入索引库成功');
            } else {
                $.messager.alert('提示', '导入索引库实拍');
            }
        });
    }
</script>
