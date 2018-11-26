package com.yunde.website.allpay.web.sys.user;

import com.jfinal.base.ReturnResult;
import com.jfinal.ext.plugin.sql.Cnd;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yunde.website.allpay.common.model.SysRole;
import com.yunde.website.allpay.common.model.SysUser;
import com.yunde.website.allpay.common.model.SysUserRole;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SysUserService {
	public static final SysUserService me = new SysUserService();
	public static final SysUser dao = new SysUser();
	
	public boolean isAdmin(SysUser user){
		return user != null && "admin".equals(user.getAccount());
	}
	
//	public void setUserRolesToRedis(String userId, List<String> roleIds){
//		RedisKeyKit.set(RedisKey.SysUserEnum.roles, userId, roleIds);
//	}
	
	public Page<Record> getByPage(Map<String, String[]> params, int pageNumber, int pageSize) {
		Cnd cnd = Cnd.$query()
				.setCnd(SysUser.class, "u")
				.setCnd(SysUserRole.class, "ur")
				.setCnd(SysRole.class,"r")
				.setParaMap(params)
				.where()
				.groupBy("u.id")
				.build();
		return Db.paginate(pageNumber, pageSize,true, "SELECT " + "u.*, GROUP_CONCAT(r.name) roleName, r.remark roleRemark ",
				    " FROM sys_user AS u " 
					+ "LEFT JOIN sys_user_role AS ur ON u.id = ur.userId " 
					+ "LEFT JOIN sys_role AS r ON ur.roleId = r.id "+ cnd.getSql(),
				cnd.getParas()); 
	}
	
	/**
	 * 获取用户角色Id集合
	 * @param userId
	 * @return
	 */
	public List<String> getUserRoleIds(String userId){
//		return RedisKeyKit.getSet(RedisKey.SysUserEnum.roles, userId, new IDataLoader() {
//			@Override
//			public Object load() {
//				Cnd cnd = Cnd.$select()
//						.select(String.format("select roleId from %s", SysUserRole.dao.getTableName()))
//						.where(SysUserRole.c.userId, userId)
//						.build();
//
//				List<String> roleIds = Db.query(cnd.getSql(), cnd.getParas());
//				return roleIds;
//			}
//		});
		return null;
	}
	
	/**
	 * 用户角色授权
	 * Enhancer.enhance(SysUserService.class, Tx.class).grantRoleToUser(roleIds,userIds)
	 */
	public ReturnResult grantRoleToUser(String[] roleIds, String[] userIds) {
		SysUserRole.dao.delete(SysUserRole.c.userId, Cnd.Type.in, userIds);
		
		for(String userId : userIds){
			for(String roleId : roleIds){
					new SysUserRole().setId(StrKit.uuidNoneLine()).setRoleId(roleId).setUserId(userId).save();
			}
			//【用户角色业务】redis数据共享
//			setUserRolesToRedis(userId, Arrays.asList(roleIds));
		}
		return ReturnResult.success();
	}
	
	public String encryptPws(String password, String salt) {
		return HashKit.sha256(salt + password);
	}
	
	public ReturnResult saveUser(SysUser user, String roleIds){
		user.setId(StrKit.uuidNoneLine());
		
		if(!StrKit.isBlank(user.getPassword())){
			user.setPassword(SysUserService.me.encryptPws(user.getPassword(), user.getId()));
		}
		
		boolean result = user.save();
		if(result){
			if(StrKit.notBlank(roleIds)){
				return grantRoleToUser(roleIds.split(","), new String[]{user.getId()});
			}
		}
		return ReturnResult.create(result);		
	}
	
	public ReturnResult updateUser(SysUser user, String roleIds){
		boolean result = user.update();
		if(result){
			if(StrKit.notBlank(roleIds)){
				return grantRoleToUser(roleIds.split(","), new String[]{user.getId()});
			}
		}
		return ReturnResult.create(result);		
	}
	
	public ReturnResult deleteUsers(Object[] userIds){
		SysUserRole.dao.delete(SysUserRole.c.userId, Cnd.Type.in, userIds);
		boolean b = dao.deletes(userIds);
		return ReturnResult.create(b);
	}
	
	
	public ReturnResult updateUserPwd(SysUser user, String pwd, String new_pwd){
		if(!user.getPassword().equals(SysUserService.me.encryptPws(pwd, user.getId()))){
			return ReturnResult.failure("旧密码错误");
		}
		
		user.setPassword(SysUserService.me.encryptPws(new_pwd, user.getId()));
		boolean b = user.update();
		return ReturnResult.create(b);
	}
}
