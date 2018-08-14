package com.imooc.myo2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.myo2o.dao.PersonInfoDao;
import com.imooc.myo2o.dao.ShopAuthMapDao;
import com.imooc.myo2o.dto.ShopAuthMapExecution;
import com.imooc.myo2o.entity.LocalAuth;
import com.imooc.myo2o.entity.PersonInfo;
import com.imooc.myo2o.entity.ShopAuthMap;
import com.imooc.myo2o.enums.ShopAuthMapStateEnum;
import com.imooc.myo2o.service.ShopAuthMapService;
import com.imooc.myo2o.util.FileUtil;
import com.imooc.myo2o.util.ImageUtil;
import com.imooc.myo2o.util.PageCalculator;

@Service
public class ShopAuthMapServiceImpl implements ShopAuthMapService {
	@Autowired
	private ShopAuthMapDao shopAuthMapDao;
	@Autowired
	private PersonInfoDao personInfoDao;
	@Override
	public ShopAuthMapExecution listShopAuthMapByShopId(Long shopId,
			Integer pageIndex, Integer pageSize) {
		if (shopId != null && pageIndex != null && pageSize != null) {
			int beginIndex = PageCalculator.calculateRowIndex(pageIndex,
					pageSize);
			List<ShopAuthMap> shopAuthMapList = shopAuthMapDao
					.queryShopAuthMapListByShopId(shopId, beginIndex, pageSize);
			int count = shopAuthMapDao.queryShopAuthCountByShopId(shopId);
			ShopAuthMapExecution se = new ShopAuthMapExecution();
			se.setShopAuthMapList(shopAuthMapList);
			se.setCount(count);
			return se;
		} else {
			return null;
		}

	}

	@Override
	@Transactional
	public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap)
			throws RuntimeException {
		if (shopAuthMap != null && shopAuthMap.getShopId() != null
				&& shopAuthMap.getEmployeeId() != null) {
			shopAuthMap.setCreateTime(new Date());
			shopAuthMap.setLastEditTime(new Date());
			shopAuthMap.setEnableStatus(1);
			try {
				int effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
				if (effectedNum <= 0) {
					throw new RuntimeException("添加授权失败");
				}
				return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,
						shopAuthMap);
			} catch (Exception e) {
				throw new RuntimeException("添加授权失败:" + e.toString());
			}
		} else {
			return new ShopAuthMapExecution(
					ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
		}
	}
	@Override
	@Transactional
	public ShopAuthMapExecution addShopAuthMap2(ShopAuthMap shopAuthMap,CommonsMultipartFile profileImg)
			throws RuntimeException {
		if (shopAuthMap != null && shopAuthMap.getShopId() != null) {
			shopAuthMap.setCreateTime(new Date());
			shopAuthMap.setLastEditTime(new Date());
			shopAuthMap.setEnableStatus(1);
			shopAuthMap.setTitleFlag(0);
			if (shopAuthMap.getEmployee() != null
					&& shopAuthMap.getEmployee().getUserId() == null) {
				if (profileImg != null) {
					shopAuthMap.getEmployee().setCreateTime(new Date());
					shopAuthMap.getEmployee().setLastEditTime(new Date());
					shopAuthMap.getEmployee().setEnableStatus(1);
					try {
						addProfileImg(shopAuthMap, profileImg);
					} catch (Exception e) {
						throw new RuntimeException("addUserProfileImg error: "
								+ e.getMessage());
					}
				}
				try {
					PersonInfo employee = shopAuthMap.getEmployee();
					employee.setCustomerFlag(1);
					int effectedNum = personInfoDao
							.insertPersonInfo(employee);
					System.out.println("insert employee");
					shopAuthMap.setEmployeeId(employee.getUserId());
					
					if (effectedNum <= 0) {
						throw new RuntimeException("添加用户信息失败");
					}
				} catch (Exception e) {
					throw new RuntimeException("insertPersonInfo error: "
							+ e.getMessage());
				}
			}
			try {
				int effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
				if (effectedNum <= 0) {
					throw new RuntimeException("添加授权失败");
				}
				return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,
						shopAuthMap);
			} catch (Exception e) {
				throw new RuntimeException("添加授权失败:" + e.toString());
			}
		}
		else {
			return new ShopAuthMapExecution(
					ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
		}
	}
	@Override
	@Transactional
	public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap)
			throws RuntimeException {
		if (shopAuthMap == null || shopAuthMap.getShopAuthId() == null) {
			return new ShopAuthMapExecution(
					ShopAuthMapStateEnum.NULL_SHOPAUTH_ID);
		} else {
			try {
				int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
				if (effectedNum <= 0) {
					return new ShopAuthMapExecution(
							ShopAuthMapStateEnum.INNER_ERROR);
				} else {// 创建成功
					return new ShopAuthMapExecution(
							ShopAuthMapStateEnum.SUCCESS, shopAuthMap);
				}
			} catch (Exception e) {
				throw new RuntimeException("updateShopByOwner error: "
						+ e.getMessage());
			}
		}
	}

	@Override
	@Transactional
	public ShopAuthMapExecution removeShopAuthMap(Long shopAuthMapId)
			throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	@Transactional
	public ShopAuthMapExecution removeShopAuthMap2(Long shopAuthId,Long shopId)
			throws RuntimeException {
		 ShopAuthMap shopAuthMap=null;
		 shopAuthMap=shopAuthMapDao.queryShopAuthMapById(shopAuthId);
		if(shopAuthMap==null)
			return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_ID);
		
		try {
			int effectedNum = shopAuthMapDao.deleteShopAuthMap(shopAuthId, shopId);
			if (effectedNum <= 0) {
				return new ShopAuthMapExecution(
						ShopAuthMapStateEnum.INNER_ERROR);
			} 
		} catch (Exception e) {
			throw new RuntimeException("delete employee shopAuthMap  error: "
					+ e.getMessage());
		}
		PersonInfo employee=null;
		long employeeid=shopAuthMap.getEmployeeId();
		if(employeeid<=0)
			 return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_ID);
		try {
			employee=personInfoDao.queryPersonInfoById(employeeid);
			if(employee==null)
				return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_ID);
			int effectedNum = personInfoDao.deletePersonInfo(employeeid);
			if (effectedNum <= 0) {
				return new ShopAuthMapExecution(
						ShopAuthMapStateEnum.INNER_ERROR);
			} 
		} catch (Exception e) {
			throw new RuntimeException("delete employee personinfo error: "
					+ e.getMessage());
		}
		String storePath=employee.getProfileImg();
		FileUtil.deleteFile(storePath);
	   return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,
				shopAuthMap);
		
	}
	@Override
	public ShopAuthMap getShopAuthMapById(Long shopAuthId) {
		return shopAuthMapDao.queryShopAuthMapById(shopAuthId);
	}
	private void addProfileImg(ShopAuthMap shopAuthMap,
			CommonsMultipartFile profileImg) {
		String dest = FileUtil.getPersonInfoImagePath();
		String profileImgAddr = ImageUtil.generateThumbnail(profileImg, dest);
		shopAuthMap.getEmployee().setProfileImg(profileImgAddr);
	}

}
