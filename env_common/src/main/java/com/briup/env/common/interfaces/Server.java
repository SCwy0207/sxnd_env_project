package com.briup.env.common.interfaces;

import java.util.Collection;

import com.briup.env.common.entity.Environment;

public interface Server {
	//服务器接受方法，返回从服务器接收清单
	public Collection<Environment>receive();
}
