package com.imooc.myo2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.dto.ShopAuthMapExecution;
import com.imooc.myo2o.entity.PersonInfo;
import com.imooc.myo2o.entity.ShopAuthMap;
import com.imooc.myo2o.enums.ShopAuthMapStateEnum;

public class ShopAuthServiceTest extends BaseTest {
	@Autowired
	private ShopAuthMapService shopAuthService;
    /*
	@Test
	
	public void testUpdateShopAuthMap() {
		Long shopAuthId = 1L;
		String title = "CEO";
		Integer titleFlag = 2;
		Integer enableStatus = null;
		ShopAuthMap shopAuthMap = new ShopAuthMap();
		shopAuthMap.setShopAuthId(shopAuthId);
		shopAuthMap.setTitle(title);
		shopAuthMap.setTitleFlag(titleFlag);
		shopAuthMap.setEnableStatus(enableStatus);
		ShopAuthMapExecution same = shopAuthService
				.modifyShopAuthMap(shopAuthMap);
		assertEquals(ShopAuthMapStateEnum.SUCCESS.getState(), same.getState());
	}

	@Test
	public void testListShopAuthMapByShopId() {
		ShopAuthMapExecution shopAuthMapExecution = shopAuthService
				.listShopAuthMapByShopId(1L, 2, 1);
		List<ShopAuthMap> shopAuthMapList = shopAuthMapExecution
				.getShopAuthMapList();
		assertEquals(1, shopAuthMapList.size());
		shopAuthMapExecution = shopAuthService
				.listShopAuthMapByShopId(1L, 1, 2);
		shopAuthMapList = shopAuthMapExecution.getShopAuthMapList();
		assertEquals(2, shopAuthMapList.size());
	}*/
	/*
	@Test
	public void testaddShopAuthMap2(){
		PersonInfo  personInfo=new PersonInfo();
		personInfo.setName("test1");
		personInfo.setAdminFlag(0);
		personInfo.setCustomerFlag(0);
		personInfo.setEnableStatus(1);
		personInfo.setProfileImg("/upload/images/item/personinfo/2017060608534289617.png");
		personInfo.setShopOwnerFlag(0);
		ShopAuthMap shopAuthMap = new ShopAuthMap();
		shopAuthMap.setShopId(33L);
		shopAuthMap.setName("test1");
		shopAuthMap.setEmployee(personInfo);
		shopAuthMap.setTitle("test1");
		shopAuthMap.setTitleFlag(0);
		ShopAuthMapExecution se=shopAuthService.addShopAuthMap2(shopAuthMap, null);
		System.out.println(se.getStateInfo());
		
	}*/
	@Test
	public void testremoveShopAuthMap2(){
		ShopAuthMapExecution se=shopAuthService.removeShopAuthMap2(29L, 33L);
		System.out.println(se.getStateInfo());
	}
}
