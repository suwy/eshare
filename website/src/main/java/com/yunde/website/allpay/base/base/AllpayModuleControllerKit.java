package com.yunde.website.allpay.base.base;

import com.jfinal.base.ReturnResult;
import com.jfinal.interfaces.ISuccCallback;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.yunde.website.allpay.base.AllpaySelectKit;
import com.yunde.website.allpay.base._AllpayModuleBuilder;
import com.yunde.website.allpay.base._AllpaySelectBean;
import com.yunde.website.allpay.base.plugin.allpay.AllpayResult;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hang on 2017/7/26 0026.
 */
public class AllpayModuleControllerKit<M extends BaseAllpayBean> {
    private AllpayModuleController controller;

    public AllpayModuleControllerKit(AllpayModuleController controller){
        this.controller = controller;
    }

    protected Map<String,Object> getMap(){
        return getMap(controller.getParaMap());
    }
    protected Map<String,Object> getMap(Map<String, String[]> params){
        Map<String, Object> map = new HashMap<String,Object>();
        String[] value;
        for(Map.Entry<String,String[]> entry : params.entrySet()){
            value = entry.getValue();
            if(value != null){
                map.put(entry.getKey(), value[0]);
            } else {
                map.put(entry.getKey(), "$emtry");
            }
        }
        if(params.get("fuzzyQuery")!=null){
            map.put("fuzzyQuery", params.get("fuzzyQuery")[0]);
        }

        if(params.get("fuzzyCloumns")!=null){
            map.put("fuzzyCloumns", params.get("fuzzyCloumns")[0]);
        }

        if(params.get("orderBy")!=null){
            map.put("orderBy", params.get("orderBy")[0]);
        }
        return map;
    }

    private void toSelect(Object object){
        String $select = controller.getPara("$select");
        if(StrKit.notBlank($select)){
            boolean isBuild = false;
            AllpaySelectKit allpaySelect = AllpaySelectKit.create(object);
            for(String select : $select.split(",")){
                _AllpaySelectBean selectBean = _AllpayModuleBuilder.getSelectBean(select);
                if(selectBean != null){
                    allpaySelect.select(selectBean.getAlias(), selectBean.getObjectKey(), selectBean.getIdKey(), selectBean.getTable(), selectBean.getParams(), selectBean.getAttrs());
                    isBuild = true;
                }
            }
            if (isBuild){
                allpaySelect.build();
            }
        }
    }

    private Class<M> getClazz() {
        Type t = controller.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) t).getActualTypeArguments();
        return (Class<M>) params[0];
    }

    public M getBean(){
        return getBean(getClazz(), "", true);
    }
    public M getBean(Class<M> beanClass, String beanName, boolean skipConvertError){
        return controller.getBean(beanClass, beanName, skipConvertError);
    }

    protected M getBaseBean(){
        M m = getBean();
        Map<String, String[]> parasMap = controller.getRequest().getParameterMap();
        Field[] fields = getClazz().getDeclaredFields();
        for(Field field : fields){
            if(parasMap.containsKey(field.getName()) && StrKit.isBlank(parasMap.get(field.getName())[0])){
                if(Number.class.isAssignableFrom(field.getType())){
                    m.put(field.getName(), "$null");
                } else {
                    m.put(field.getName(), "$emtry");
                }
            }
        }
        return m;
    }

    /**
     * 通用分页查找
     */
    public Page<M> getPage(){
        return getPage(controller.getParaMap());
    }
    public Page<M> getPage(Map<String, String[]> params) {
        AllpayResult result = controller.table().paginate(controller.pageNumber(), controller.pageSize(), getMap(params));
        if(result.isSucceed()){
            Page<M> page = (Page<M>) result.dataToPage(getClazz());
            toSelect(page.getList());
            return page;
        }
        return new Page<M>();
    }

    /**
     * 通用查找全部
     */
    public List<M> getList() {
        return getList(controller.getParaMap());
    }
    public List<M> getList(Map<String, String[]> params) {
        AllpayResult result = controller.table().query(getMap(params));
        List<M> list = result.dataToJsonArray().toJavaList(getClazz());
        toSelect(list);
        return list;
    }

    /**
     * 通用根据id查找
     */
    public M getById(Object id) {
        M m = controller.table().queryById(id).dataToJson().toJavaObject(getClazz());
        toSelect(m);
        return m;
    }

    /**
     * 通用删除
     *
     */
    public ReturnResult delete(Object id, ISuccCallback<ReturnResult> call){
        return deletes(new Object[]{id}, call);
    }

    /**
     * 通用批量删除
     *
     */
    public ReturnResult deletes(Object[] ids, ISuccCallback<ReturnResult> call){
        return controller.table().deleteById(ids).toReturnResult().call(new ISuccCallback<ReturnResult>() {
            @Override
            public ReturnResult callback(ReturnResult returnResult) {
                if(call != null){
                    returnResult.setResult(ids);
                    call.callback(returnResult);
                }
                return returnResult;
            }
        });
    }

    /**
     * 通用新增
     *
     */
    public ReturnResult save(M data, ISuccCallback<ReturnResult> call){
        return controller.table().add(data).toReturnResult().call(new ISuccCallback<ReturnResult>() {
            @Override
            public ReturnResult callback(ReturnResult returnResult) {
                if(call != null){
                    returnResult.setResult(data);
                    call.callback(returnResult);
                }
                return returnResult;
            }
        });
    }

    /**
     * 通用修改
     *
     */
    public ReturnResult update(M data, ISuccCallback<ReturnResult> call){
        return controller.table().updateById(data).toReturnResult().call(new ISuccCallback<ReturnResult>() {
            @Override
            public ReturnResult callback(ReturnResult returnResult) {
                if(call != null){
                    returnResult.setResult(data);
                    call.callback(returnResult);
                }
                return returnResult;
            }
        });
    }
}
