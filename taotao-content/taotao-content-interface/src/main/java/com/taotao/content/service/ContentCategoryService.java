package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {

	public List<EasyUITreeNode> getContentCategoryList(long parentId);
	
	public TaotaoResult addContentCategory(Long parentId, String name);
	
	public void updateContentCategory(Long id, String name);
}
