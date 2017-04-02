package com.taotao.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
public class CartController {

	@Value("${CART_KEY}")
	private String CART_KEY;
	@Value("${CART_EXPIRE}")
	private Integer CART_EXPIRE;
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/cart/add/{itemId}")
	public String addItemCart(@PathVariable Long itemId, 
			@RequestParam(defaultValue="1")Integer num,
			HttpServletRequest request, HttpServletResponse response){
		//get cartlist from cookie 
		List<TbItem> cartList = getCartList(request);
		//whether the item has existed in the list?
		boolean flage = false;
		for (TbItem tbItem : cartList) {
			//existed
			if(tbItem.getId() == itemId.longValue()){
				tbItem.setNum(tbItem.getNum() + num);
				flage = true;
				break;
			}
		}
		//new item
		if(!flage){
			TbItem newItem = itemService.getItemById(itemId);
			newItem.setNum(num);
			String image = newItem.getImage();
			if(StringUtils.isNotBlank(image)){
				newItem.setImage(image.split(",")[0]);
			}
			cartList.add(newItem);
		}
		CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartList), CART_EXPIRE, true);
		return "cartSuccess";
	}
	
	private List<TbItem> getCartList(HttpServletRequest request){
		String cartJson = CookieUtils.getCookieValue(request, CART_KEY, true);
		if(StringUtils.isNotBlank(cartJson)){
			List<TbItem> cartList = JsonUtils.jsonToList(cartJson, TbItem.class);
			return cartList;
		}
		return new ArrayList<>();
	}
	
	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request){
		//get cartList from cookie
		List<TbItem> cartList = getCartList(request);
		//to jsp
		request.setAttribute("cartList", cartList);
		//return ModelAndView
		return "cart";
	}
	
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult updateCartListNum(@PathVariable Long itemId, 
			@PathVariable Integer num, 
			HttpServletRequest request, HttpServletResponse response){
		//get cartList from cookie
		List<TbItem> cartList = getCartList(request);
		for (TbItem tbItem : cartList) {
			if( tbItem.getId() == itemId.longValue()){
				tbItem.setNum(num);
				break;
			}
		}
		CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartList), CART_EXPIRE, true);
		return TaotaoResult.ok();
	}
	
	@RequestMapping("/cart/delete/{itemId}")
	public String deletCartItem(@PathVariable Long itemId, 
			HttpServletRequest request, HttpServletResponse response){
		//get cartList from cookie
		List<TbItem> cartList = getCartList(request);
		for (TbItem tbItem : cartList) {
			if( tbItem.getId() == itemId.longValue()){
				cartList.remove(tbItem);
				break;
			}
		}
		CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartList), CART_EXPIRE, true);
		return "redirect:/cart/cart.html";
	}
	
	
}
