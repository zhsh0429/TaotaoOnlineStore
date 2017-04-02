package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;

@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	/**
	 * 根据页面传递的categoryId查找Content列表并返回
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return EasyUIDataGridResult(JSON format)
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentListByCategoryId(Long categoryId, int page, int rows){
		EasyUIDataGridResult result = contentService.getContentListByCategoryId(categoryId, page, rows);
		return result;
	}
	
	/**
	 * adding a new content
	 * @param content
	 * @return
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent content){
		TaotaoResult result = contentService.addContent(content);
		return result;
	}
	
	/**
	 * updateConetent
	 * @param content
	 * @return
	 */
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public TaotaoResult updateContent(TbContent content){
		TaotaoResult result = contentService.updateContent(content);
		return result;
	}
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public TaotaoResult deleteContentByIds(String ids){
		TaotaoResult result = contentService.deleteContentByIds(ids);
		return result;
	}
}
