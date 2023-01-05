package com.briup.env.common.interfaces;
//提供采集功能的接口
import java.util.Collection;

import com.briup.env.common.entity.Environment;

//提示：由于client和server都要引用common模块，所以需将maven本地仓库中，一旦common子模块中有修改，则需要重新打包
//记得再client和server的pom.xml中进行引用.
	public interface Gather {
	// 采集环境数据，环境对象的集合
	public  Collection<Environment> Gather();
}