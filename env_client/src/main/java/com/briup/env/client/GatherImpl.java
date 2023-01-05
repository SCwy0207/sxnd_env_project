package com.briup.env.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Test;

import com.briup.env.common.entity.Environment;
import com.briup.env.common.interfaces.Gather;

public class GatherImpl implements Gather{

	//定义一个数字和环境名称的关系的集合
	public static Map<String,String>map;
	
	static {
		map= new HashMap<>();
		map.put("16", "温湿度");
		map.put("256", "光照强度");
		map.put("1280", "二氧化碳浓度");
	}
	@Override
	public Collection<Environment> Gather() {
		// 提前定义一个集合，用来做返回
		Collection<Environment> coll = new LinkedList<>();
		// 使用适当的流读取日志文件
		//声明流
		BufferedReader br = null;
		FileReader fr = null;
		try {
		// 创建流
		fr = new FileReader(new File("src/main/resources/radwtmp"));
		br = new BufferedReader(fr);
		// 使用流
		String s = null;
		while((s = br.readLine())!=null) {
			//s就代表了文件中每一行的内容
			//根据数据格式，按照|进行分割
			String[] value = s.split("[|]");
			// 新建一个Environment对象，将value的数据对号入座
			Environment environment = new Environment();
			//定义传感器类型的变量
			String address = value[3];
			//定义环境数值的变量
			String data=value[6];
			// 给environment的属性赋值
			//不需要处理的数据，保证格式正确即可放入
			environment.setSrcId(value[0]);
			environment.setDesId(value[1]);
			environment.setDevId(value[2]);
			environment.setAddress(value[3]);//该值可以确定是哪个传感器的数据
			environment.setCount(Integer.parseInt(value[4]));
			environment.setCmd(value[5]);
			environment.setStatus(Integer.parseInt(value[7]));
			environment.setTime(new Timestamp(Long.parseLong(value[8])));
			//name和data数据需要处理
			if("16".equals(address)) {
				//温湿度
				//16的数据需要拆成两个Environment对象
				String name1="温度";
				environment.setName(name1);
				//温度上截取前两个字节
				int v1=Integer.parseInt(data.substring(0,4),16);
				float f1=(float)(((float)v1*0.00268127)-46.85);
				environment.setData(f1);
				//重新创建Environment对象
				Environment env=new Environment();
				env.setSrcId(value[0]);
				env.setDesId(value[1]);
				env.setDevId(value[2]);
				env.setAddress(value[3]);//该值可以确定是哪个传感器的数据
				env.setCount(Integer.parseInt(value[4]));
				env.setCmd(value[5]);
				env.setStatus(Integer.parseInt(value[7]));
				env.setTime(new Timestamp(Long.parseLong(value[8])));
				String name2="湿度";
				env.setName(name2);
				int v2=Integer.parseInt(data.substring(4,8),16);
				float f2=(float)(((float)v2*0.00190735)-6);
				env.setData(f2);
				coll.add(env);
			}else {
				//光照强度和二氧化碳浓度名字直接从map中取
				environment.setName(map.get(address));
				//将16进制转换为10进制方法
				int v=Integer.parseInt(data.substring(0,4),16);
				environment.setData(v);
			}
			/*
			environment.setData(0); // 这里需要计算
			environment.setName(""); // 这里没有办法从文件数据中获得，需要判断输入
			*/
			//每循环一次，至少添加一次
			coll.add(environment);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//流的关闭
		}
		//在控制台上输出日志文件里的内容
		return coll;
		}
		//对上面的方法进行测试
		@Test
		public void test(){
		//idk8的遍历
			Collection<Environment>coll=new GatherImpl().Gather();
			coll.forEach(System.out::println);
			System.out.println("总的数据清单的条数：");
			System.out.println(new GatherImpl().Gather().size());
		}


}
