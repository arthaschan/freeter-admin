package com.freeter.common.mpextend.parser;

import java.lang.reflect.Field;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.freeter.common.annotation.MpEQ;
import com.freeter.common.annotation.OuterTable;
import com.freeter.common.mpextend.ClearSuffix;
import com.freeter.common.utils.MPUtil;

import cn.hutool.core.util.StrUtil;

public class ParseEQWrapper {

	 
	public static void parseWrapper(EntityWrapper wrapper, Map<String, Object> map, Field field, String camelFieldName,
			Class ownerClass) {
		MpEQ fieldEQAnno = field.getAnnotation(MpEQ.class);

		OuterTable outer = field.getAnnotation(OuterTable.class);
		Object value = (Object) map.get(camelFieldName);
		map.remove(camelFieldName);
		Class[] tables = {};
		if (outer != null) {
			tables = outer.value();
		}
		String[] classRealNameArr = fieldEQAnno.value();
		int realNameLength = classRealNameArr.length;
		if(realNameLength == 1) {
			camelFieldName = MPUtil.camelToUnderline(classRealNameArr[0]);
		}
		if(realNameLength>1) {
			 
				wrapper.eq("1", 1);
				wrapper.andNew();
				 
			for (int i = 0; i < realNameLength; i++) {
				if (tables.length == realNameLength) {
					ownerClass = tables[i];
				}
				camelFieldName = MPUtil.camelToUnderline(classRealNameArr[i]);
				 
				wrapper.eq(StrUtil.toUnderlineCase(ClearSuffix.clearSuffix(ownerClass))+ camelFieldName,
						value);
				if (i != realNameLength - 1) {
					wrapper.or();
				}
				
				if (i == classRealNameArr.length - 1) {
					wrapper.andNew();
				}
				
			}
		}else {
			map.put(StrUtil.toUnderlineCase(ClearSuffix.clearSuffix(ownerClass)) + camelFieldName,
					value);
		}
	
	}

}
