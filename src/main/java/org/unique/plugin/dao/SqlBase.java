package org.unique.plugin.dao;

import java.util.List;
import java.util.Map;

import org.unique.common.tools.CollectionUtil;
import org.unique.common.tools.StringUtils;

/**
 * 生成sql类
 * 
 * @author rex
 */
public class SqlBase {

private String baseSql;
	
	private Map<String, Object> pp;
	
	private static final String ORDER = "order by"; 
	
	public SqlBase(String baseSQL) {
		this.baseSql = baseSQL;
		this.pp = CollectionUtil.newHashMap();
	}
	
	public SqlBase eq(String field, Object value){
		if(StringUtils.isNotBlank(field) && null != value){
			this.pp.put(field + " = ?", value);
		}
		return this;
	}
	
	public SqlBase notEq(String field, Object value){
		if(StringUtils.isNotBlank(field) && null != value){
			this.pp.put(field + " <> ?", value);
		}
		return this;
	}
	
	public SqlBase like(String field, Object value){
		if(StringUtils.isNotBlank(field) && null != value){
			this.pp.put(field + " like ?", "%" + value + "%");
		}
		return this;
	}
	
	public SqlBase likeLeft(String field, Object value){
		if(StringUtils.isNotBlank(field) && null != value){
			this.pp.put(field + " like ?", "%" + value);
		}
		return this;
	}
	
	public SqlBase likeRight(String field, Object value){
		if(StringUtils.isNotBlank(field) && null != value){
			this.pp.put(field + " like ?", value + "%");
		}
		return this;
	}
	
	public SqlBase in(String field, Object... values){
		if(null != field && null != values && values.length > 0){
			String params = "";
			for(Object obj : values){
				params += obj + ",";
			}
			this.pp.put(field + " in ?", "(" + params.substring(0, params.length() - 1) + ")");
		}
		return this;
	}
	
	public SqlBase notIn(String field, Object... values) {
		if(null != field && null != values && values.length > 0){
			String params = "";
			for(Object obj : values){
				params += obj + ",";
			}
			this.pp.put(field + " not in ?", "(" + params.substring(0, params.length() - 1) + ")");
		}
		return this;
	}
	
	/**
	 * >
	 * @param field
	 * @param value
	 * @return
	 */
	public SqlBase gt(String field, Object value) {
		if(null != field && null != value){
			this.pp.put(field + " > ?", value);
		}
		return this;
	}
	
	/**
	 * >=
	 * @param field
	 * @param value
	 * @return
	 */
	public SqlBase gte(String field, Object value) {
		if(null != field && null != value){
			this.pp.put(field + " >= ?", value);
		}
		return this;
	}
	
	/**
	 * <
	 * @param field
	 * @param value
	 * @return
	 */
	public SqlBase lt(String field, Object value) {
		if(null != field && null != value){
			this.pp.put(field + " < ?", value);
		}
		return this;
	}
	
	/**
	 * <=
	 * @param field
	 * @param value
	 * @return
	 */
	public SqlBase lte(String field, Object value) {
		if(null != field && null != value){
			this.pp.put(field + " < ?", value);
		}
		return this;
	}

	public void order(String order) {
		if(StringUtils.isNotBlank(order)){
			this.pp.put(SqlBase.ORDER, order);
		}
	}
	
	public String getSQL(){
		StringBuffer sb = new StringBuffer(this.baseSql);
		if(!CollectionUtil.isEmpty(this.pp)){
			sb.append(" where ");
			for(String field : this.pp.keySet()){
				if(!field.equals(SqlBase.ORDER)){
					sb.append(field + " and ");
				}
			}
			sb = new StringBuffer(sb.substring(0, sb.length() - 5));
			if(this.pp.containsKey(SqlBase.ORDER)){
				sb.append(" " + SqlBase.ORDER + " ?");
			}
			return sb.toString();
		}
		return sb.toString();
	}
	
	public Object[] getParams(){
		List<Object> params = CollectionUtil.newArrayList();
		if(!CollectionUtil.isEmpty(this.pp)){
			for(String field : this.pp.keySet()){
				if(!field.equals(SqlBase.ORDER)){
					params.add(this.pp.get(field));
				}
			}
			if(this.pp.containsKey(SqlBase.ORDER)){
				params.add(this.pp.get(SqlBase.ORDER));
			}
			return params.toArray();
		}
		return params.toArray();
	}

}
