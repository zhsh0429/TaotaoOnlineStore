<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div>
	<a class="easyui-linkbutton importAllItems" href="javascript:void(0)">一键导入全部商品信息到Solr检索系统</a>
</div>
<script type="text/javascript">
	$(".easyui-linkbutton.importAllItems").click(function(){
		 		$.post("/index/importall", function(data){
					if(data.status == 200){
						$.messager.alert('提示','导入商品信息成功!');
					}else{
						$.messager.alert('提示','导入商品信息成功!');
					}
				}); 
		
	});
</script>

