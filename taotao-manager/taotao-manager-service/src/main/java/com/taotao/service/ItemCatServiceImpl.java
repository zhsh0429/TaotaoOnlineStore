package com.taotao.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> selectByExample = itemCatMapper.selectByExample(example);
		List<EasyUITreeNode> list = new ArrayList<>();
		for (TbItemCat cat : selectByExample) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(cat.getId());
			node.setState(cat.getIsParent()?"closed":"open");
			node.setText(cat.getName());
			list.add(node);
		}
		return list;
	}
	

}
