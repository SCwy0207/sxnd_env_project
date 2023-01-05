package com.briup.env.common.interfaces;

import java.util.Collection;

import com.briup.env.common.entity.Environment;

public interface DbStore {
	//入库方法，服务器接收回来的数据清单
	public void dbstore(Collection<Environment>coll);
}
