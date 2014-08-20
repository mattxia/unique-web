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

	/**
	 * where 条件
	 */
	private Map<String, Object> whereMap;

	/**
	 * update set map
	 */
	private Map<String, Object> setMap;

	/*
	 * 默认查询, 2:更新 3:删除
	 */
	private int currentOpt = 1;

	/*
	 * order by 常量 
	 */
	private static final String ORDER = "order by";

	private SqlBase() {
	}

	/**
	 * 查询语句
	 * @param baseSQL
	 * @return
	 */
	public static SqlBase select(String baseSQL) {
		SqlBase base = new SqlBase();
		base.baseSql = baseSQL;
		base.whereMap = CollectionUtil.newHashMap();
		base.currentOpt = 1;
		return base;
	}

	/**
	 * 更新语句
	 * @param baseSQL
	 * @return
	 */
	public static SqlBase update(String baseSQL) {
		SqlBase base = new SqlBase();
		base.baseSql = baseSQL;
		base.setMap = CollectionUtil.newHashMap();
		base.whereMap = CollectionUtil.newHashMap();
		base.currentOpt = 2;
		return base;
	}

	public static SqlBase delete(String baseSQL) {
		SqlBase base = new SqlBase();
		base.baseSql = baseSQL;
		base.whereMap = CollectionUtil.newHashMap();
		base.currentOpt = 3;
		return base;
	}

	public SqlBase eq(String field, Object value) {
		if (StringUtils.isNotBlank(field) && null != value) {
			this.whereMap.put(field + " = ?", value);
		}
		return this;
	}

	public SqlBase notEq(String field, Object value) {
		if (StringUtils.isNotBlank(field) && null != value) {
			this.whereMap.put(field + " <> ?", value);
		}
		return this;
	}

	public SqlBase like(String field, Object value) {
		if (StringUtils.isNotBlank(field) && null != value) {
			this.whereMap.put(field + " like ?", "%" + value + "%");
		}
		return this;
	}

	public SqlBase likeLeft(String field, Object value) {
		if (StringUtils.isNotBlank(field) && null != value) {
			this.whereMap.put(field + " like ?", "%" + value);
		}
		return this;
	}

	public SqlBase likeRight(String field, Object value) {
		if (StringUtils.isNotBlank(field) && null != value) {
			this.whereMap.put(field + " like ?", value + "%");
		}
		return this;
	}

	public SqlBase in(String field, Object... values) {
		if (null != field && null != values && values.length > 0) {
			String params = "";
			for (Object obj : values) {
				params += obj + ",";
			}
			this.whereMap.put(field + " in ?", "(" + params.substring(0, params.length() - 1) + ")");
		}
		return this;
	}

	public SqlBase notIn(String field, Object... values) {
		if (null != field && null != values && values.length > 0) {
			String params = "";
			for (Object obj : values) {
				params += obj + ",";
			}
			this.whereMap.put(field + " not in ?", "(" + params.substring(0, params.length() - 1) + ")");
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
		if (null != field && null != value) {
			this.whereMap.put(field + " > ?", value);
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
		if (null != field && null != value) {
			this.whereMap.put(field + " >= ?", value);
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
		if (null != field && null != value) {
			this.whereMap.put(field + " < ?", value);
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
		if (null != field && null != value) {
			this.whereMap.put(field + " < ?", value);
		}
		return this;
	}

	public void order(String order) {
		if (StringUtils.isNotBlank(order)) {
			this.whereMap.put(SqlBase.ORDER, order);
		}
	}

	public String getSQL() {
		StringBuffer sb = new StringBuffer(this.baseSql);
		//查询语句生成
		if (currentOpt == 1) {
			sb.append(" where ");
			for (String field : this.whereMap.keySet()) {
				if (!field.equals(SqlBase.ORDER)) {
					sb.append(field + " and ");
				}
			}
			sb = new StringBuffer(sb.substring(0, sb.length() - 6));
			if (this.whereMap.containsKey(SqlBase.ORDER)) {
				sb.append(" " + SqlBase.ORDER + " ?");
			}
			return sb.toString();
		}
		//更新语句生成
		if (currentOpt == 2 && !CollectionUtil.isEmpty(this.setMap)) {
			sb.append(" set ");
			for (String field : this.setMap.keySet()) {
				sb.append(field + ",");
			}
			sb = new StringBuffer(sb.substring(0, sb.length() - 1));
			StringBuffer where = new StringBuffer();
			//update xxxx where
			if (!CollectionUtil.isEmpty(this.whereMap)) {
				where.append(" where ");
				for (String field : this.whereMap.keySet()) {
					where.append(field + " and ");
				}
				where = new StringBuffer(where.substring(0, where.length() - 5));
			}
			return sb.append(where.toString()).toString();
		}
		//删除语句生成
		if (currentOpt == 3) {
			sb.append(" where ");
			for (String field : this.whereMap.keySet()) {
				sb.append(field + " and ");
			}
			sb = new StringBuffer(sb.substring(0, sb.length() - 5));
			return sb.toString();
		}
		return sb.toString();
	}

	public Object[] getParams() {
		List<Object> params = CollectionUtil.newArrayList();
		//查询参数
		if (currentOpt == 1) {
			if (!CollectionUtil.isEmpty(this.whereMap)) {
				for (String field : this.whereMap.keySet()) {
					if (!field.equals(SqlBase.ORDER)) {
						params.add(this.whereMap.get(field));
					}
				}
				if (this.whereMap.containsKey(SqlBase.ORDER)) {
					params.add(this.whereMap.get(SqlBase.ORDER));
				}
			}
			return params.toArray();
		}
		//更新参数
		if (currentOpt == 2) {
			if (!CollectionUtil.isEmpty(this.setMap)) {
				for (String field : this.setMap.keySet()) {
					params.add(this.setMap.get(field));
				}
			}
			if (!CollectionUtil.isEmpty(this.whereMap)) {
				for (String field : this.whereMap.keySet()) {
					params.add(this.whereMap.get(field));
				}
			}
			return params.toArray();
		}
		//删除参数
		if (currentOpt == 3) {
			if (!CollectionUtil.isEmpty(this.whereMap)) {
				for (String field : this.whereMap.keySet()) {
					params.add(this.whereMap.get(field));
				}
			}
			return params.toArray();
		}
		return params.toArray();
	}

	/**
	 * update set
	 * @param field
	 * @param value
	 * @return
	 */
	public SqlBase set(String field, Object value) {
		if (StringUtils.isNotBlank(field) && null != value) {
			this.setMap.put(field + " = ?", value);
		}
		return this;
	}
	
}
