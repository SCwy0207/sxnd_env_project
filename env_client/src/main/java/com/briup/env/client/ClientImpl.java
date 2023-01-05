package com.briup.env.client;


import com.briup.env.common.entity.Environment;
import com.briup.env.common.interfaces.Client;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;

public class ClientImpl implements Client {

	@Override
	public void send(Collection<Environment> coll) {
			//定义网络模块的客户端
			Socket socket=null;
			String ip="127.0.0.1";
			int port=8888;
			//对象输出流()
			ObjectOutputStream oos=null;
			try{
				System.out.println("【客户端正在访问<"+ip+":"+port+">服务】");
				socket=new Socket(ip,port);
				oos=new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(coll);
				oos.flush();
				oos.close();
			}catch(IOException e){
				e.printStackTrace();
			}finally {
				if (socket != null)
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		@Test
		public void test(){
			new ClientImpl().send(null);
		}
}
