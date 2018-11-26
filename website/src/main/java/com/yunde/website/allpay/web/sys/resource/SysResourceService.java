package com.yunde.website.allpay.web.sys.resource;

import com.jfinal.base.ReturnResult;
import com.jfinal.ext.plugin.sql.Cnd;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.yunde.website.allpay.common.GlobalConfig;
import com.yunde.website.allpay.common.model.SysResource;
import com.yunde.website.allpay.common.model.SysRoleResource;
import com.yunde.website.allpay.common.model.SysUserResource;
import com.yunde.website.allpay.common.util.PermissionFactory;
import com.yunde.website.allpay.web.sys.user.SysUserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SysResourceService {
	
	public static final SysResourceService me = new SysResourceService();
	
	public void setUserResourcesToRedis(String userId, List<String> resources){
//		RedisKeyKit.set(RedisKey.SysUserEnum.resources, userId, resources);
	}
	public void setRoleResourcesToRedis(String roleId, List<String> resources){
//		RedisKeyKit.set(RedisKey.SysRoleEnum.resources, roleId, resources);
	}
	/**
	 * 获取单纯用户资源ID集合
	 * @return
	 */
	public List<String> getSingleUserResourceIds(String userId){
//		return RedisKeyKit.getSet(RedisKey.SysUserEnum.resources, userId, new IDataLoader() {
//			@Override
//			public Object load() {
//				return getSingleUserResourceIdsFromDb(userId);
//			}
//		});
		return null;
	}
	
	private List<String> getSingleUserResourceIdsFromDb(String userId){
		Cnd cnd = Cnd.$select()
				.select(String.format("select resourceId from %s", SysUserResource.dao.getTableName()))
				.where(SysUserResource.c.userId, userId)
				.build();
		
		List<String> resourceIds = Db.query(cnd.getSql(), cnd.getParas());
		return resourceIds;
	}
	
	/**
	 * 获取单纯角色资源ID集合
	 * @return
	 */
	public List<String> getSingleRoleResourceIds(String roleId){
//		return RedisKeyKit.getSet(RedisKey.SysRoleEnum.resources, roleId, new IDataLoader() {
//			@Override
//			public Object load() {
//				return getSingleRoleResourceIdsFromDb(roleId);
//			}
//		});
		return null;
	}
	private List<String> getSingleRoleResourceIdsFromDb(String roleId){
		Cnd cnd = Cnd.$select()
				.select(String.format("select resourceId from %s", SysRoleResource.dao.getTableName()))
				.where(SysRoleResource.c.roleId, roleId)
				.build();
		
		List<String> resourceIds = Db.query(cnd.getSql(), cnd.getParas());
		return resourceIds;
	}
	
	/**
	 * 获取用户资源ID集合
	 * @return
	 */
	public List<String> getUserResourceIds(String userId){
		Set<String> set = new HashSet<String>();
		set.addAll(getSingleUserResourceIds(userId));
		
		for(String roleId : SysUserService.me.getUserRoleIds(userId)){
			set.addAll(getSingleRoleResourceIds(roleId));
		}
		return new ArrayList<String>(set);
	}
	
	/**
	 * 获取单纯角色资源列表
	 * @param roleId
	 * @return
	 */
	public List<SysResource> getSingleRoleResources(String roleId) {
		List<String> roleRes = getSingleRoleResourceIds(roleId);
		Set<SysResource> resources = new HashSet<SysResource>();
		for(String resId : roleRes){
			SysResource resource = SysResource.dao.findById(resId);
			if(resource != null){
				resources.add(resource);
			}
		}
		return new ArrayList<SysResource>(resources);

	} 
	
	/**
	 * 获取单纯用户资源列表
	 * @param roleId
	 * @return
	 */
	public List<SysResource> getSingleUserResources(String roleId) {
		List<String> roleRes = getSingleUserResourceIds(roleId);
		Set<SysResource> resources = new HashSet<SysResource>();
		for(String resId : roleRes){
			SysResource resource = SysResource.dao.findById(resId);
			if(resource != null){
				resources.add(resource);
			}
		}
		return new ArrayList<SysResource>(resources);

	}
	
	/**
	 * 获取用户资源列表
	 * @param userId
	 * @return
	 */
	public List<SysResource> getUserResources(String userId) {
		// 返回资源
		Set<SysResource> resources = new HashSet<SysResource>();
		for(String resId : getUserResourceIds(userId)){
			SysResource resource = SysResource.dao.findById(resId);
			if(resource != null){
				resources.add(resource);
			}
		}
		return new ArrayList<SysResource>(resources);

	} 
	
	/**
	 * 角色资源授权
	 * @param roleId
	 * @param resourcesIds
	 * @return
	 */
	public ReturnResult grantResourcesToRole(String roleId, List<String> resourcesIds){
		if(resourcesIds == null){
			return ReturnResult.failure();
		}
		//可操作的资源ID集合
		List<String> loginUserResourceIds = PermissionFactory.getLoginUserResourceIds();
		//选择角色拥有的资源权限
		List<String> singleRoleResourceIds = getSingleRoleResourceIds(roleId);
		
		//这种情况是清空所有授权
		int resourceIdSize = resourcesIds.size();
		if(resourceIdSize == 0){
			singleRoleResourceIds.retainAll(loginUserResourceIds);
			SysRoleResource.dao.delete(new SysRoleResource().setRoleId(roleId).put(SysRoleResource.c.resourceId, singleRoleResourceIds));
			setRoleResourcesToRedis(roleId, new ArrayList<String>());
		} else {
			resourcesIds = new ArrayList<String>(resourcesIds);
			//剔除没有权限操作的
			resourcesIds.retainAll(loginUserResourceIds);
			if(resourcesIds.size() == 0 || resourcesIds.size() != resourceIdSize){
				return GlobalConfig.ErrorCode.sys_no_permission_by_grant.render();
			}
			
			//新增的权限
			List<String> add_resources = new ArrayList<String>(resourcesIds);
			add_resources.removeAll(singleRoleResourceIds);
			for(String resourceId : add_resources){
				new SysRoleResource().setId(StrKit.uuidNoneLine()).setRoleId(roleId).setResourceId(resourceId).save();
			}
			
			//删除的权限
			singleRoleResourceIds.removeAll(resourcesIds);
			if(singleRoleResourceIds.size() > 0){
				SysRoleResource.dao.delete(new SysRoleResource().setRoleId(roleId).put(SysRoleResource.c.resourceId, singleRoleResourceIds));
			}
			setRoleResourcesToRedis(roleId, getSingleRoleResourceIdsFromDb(roleId));
		}
		return ReturnResult.success();
	}
	
	

}
