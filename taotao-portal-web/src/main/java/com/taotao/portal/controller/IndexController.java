package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AD1Node;

@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;
	
	@Value("${AD1_CATEGORY_ID}")
	private Long AD1_CATEGORY_ID;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_CATEGORY_ID}")
	private Integer AD1_HEIGHT_B;
	
	@RequestMapping("/index")
	public String index(Model model){
		List<AD1Node> resultList = new ArrayList<>();
		List<TbContent> contentList = contentService.getContentListCid(AD1_CATEGORY_ID);
		for (TbContent tbContent : contentList) {
			AD1Node ad1Node = new AD1Node();
			ad1Node.setAlt(tbContent.getTitle());
			ad1Node.setHeight(AD1_HEIGHT);
			ad1Node.setHeightB(AD1_HEIGHT_B);
			ad1Node.setHref(tbContent.getUrl());
			ad1Node.setSrc(tbContent.getPic());
			ad1Node.setSrcB(tbContent.getPic2());
			ad1Node.setWidth(AD1_WIDTH);
			ad1Node.setWidthB(AD1_WIDTH_B);
			resultList.add(ad1Node);
		}
		String ad1String = JsonUtils.objectToJson(resultList);
		model.addAttribute("ad1", ad1String);
		return "index";
	}
}
