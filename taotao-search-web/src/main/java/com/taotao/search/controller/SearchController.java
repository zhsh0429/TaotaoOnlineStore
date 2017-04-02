package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@Value("${SEARCH_RESULT_ROWS}")
	private Integer SEARCH_RESULT_ROWS;
	
	@RequestMapping("/search")
	public String Search(@RequestParam(value="q")String queryString,
			@RequestParam(defaultValue="1")int page, Model model) throws Exception{
		
		
		queryString = new String(queryString.getBytes("ISO-8859-1"),"UTF-8");
		SearchResult searchResult = searchService.search(queryString, page, SEARCH_RESULT_ROWS);
		//把结果传递给页面
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", searchResult.getTotalPages());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", page);
		//返回逻辑视图
		return "search";
	}
	
}
