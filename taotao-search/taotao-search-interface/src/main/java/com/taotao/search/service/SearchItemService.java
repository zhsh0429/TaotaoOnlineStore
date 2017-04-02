package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

public interface SearchItemService {

	TaotaoResult importAllItems();
	
	TaotaoResult addDocument(long itemId) throws Exception;
}
