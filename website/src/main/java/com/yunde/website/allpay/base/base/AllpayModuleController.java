package com.yunde.website.allpay.base.base;

import com.jfinal.base.BaseConfig;
import com.jfinal.base.CommonController;
import com.jfinal.base.ReturnResult;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.yunde.website.allpay.base.plugin.allpay.AllpayModuleTable;

import java.util.Map;

/**
 *BaseController
 */
public abstract class AllpayModuleController<M extends BaseAllpayBean> extends CommonController {
	protected AllpayModuleControllerKit<M> kit = new AllpayModuleControllerKit<M>(this);

	protected abstract AllpayModuleTable table();
	protected Map<String,Object> getMap(){return kit.getMap();}
	protected Page<M> getPage(){return kit.getPage();}
	protected M getBean(){return kit.getBean();}

	/**
	 * 通用删除
	 * 
	 */
	public void delete() {
		String id = getPara();
		if(StrKit.isBlank(id)){
			renderResult(BaseConfig.attrValueEmpty("id"));
			return;
		}
		renderResult(kit.delete(id, null));
	}

	/**
	 * 通用批量删除
	 * 
	 */
	public void deletes(){
		ReturnResult result = checkNotNull("ids");
		if(!result.isSucceed()){
			renderResult(result);
			return;
		}
		renderResult(kit.deletes(getPara("ids").split(","), null));
	}

	/**
	 * 通用新增
	 * 
	 */
	public void save(){
		renderResult(kit.save(kit.getBaseBean(), null));
	}

	/**
	 * 通用修改
	 * 
	 */
	public void update(){
		renderResult(kit.update(kit.getBaseBean(), null));
	}

	/**
	 * 通用分页查找
	 */
	public void getByPage() {
		renderResult(kit.getPage(getParaMap()));
	}

	/**
	 * 通用查找全部
	 */
	public void getAll() {
		renderResult(kit.getList(getParaMap()));
	}

	/**
	 * 通用根据id查找
	 */
	public void getById() {
		String id = getPara();
		if(StrKit.isBlank(id)){
			renderResult(BaseConfig.attrValueEmpty("id"));
			return;
		}
		renderResult(kit.getById(id));
	}
}
