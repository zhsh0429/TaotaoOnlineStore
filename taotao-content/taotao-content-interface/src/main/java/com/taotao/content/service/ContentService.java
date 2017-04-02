package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

	EasyUIDataGridResult getContentListByCategoryId(Long categoryId, int page, int rows);
	
	TaotaoResult addContent(TbContent content);
	
	TaotaoResult updateContent(TbContent content);

	TaotaoResult deleteContentByIds(String ids);
	
	List<TbContent> getContentListCid(long cid);
}
