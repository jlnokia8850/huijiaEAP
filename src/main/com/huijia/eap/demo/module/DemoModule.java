package com.huijia.eap.demo.module;

import java.util.Random;

import org.nutz.dao.Dao;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import com.alibaba.druid.stat.DruidDataSourceStatManager;
import com.huijia.eap.demo.bean.Pet;

@IocBean(create="init")
@InjectName
@At("/demo")
public class DemoModule {
	
	/**确保数据源已经被初始化*/
	@Inject
	Dao dao;

	@At("/stat")
	@Ok("jsp:jsp.demo.stat")
	public Object stat(){
		return DruidDataSourceStatManager.getDruidDataSourceInstances();
	}
	
	/**跑几条SQL测试一下*/
	public void init() {
		dao.create(Pet.class, true);
		for (int i = 0; i < 10000; i++) {
			Pet pet = new Pet();
			pet.setAge(i);
			pet.setName("XXX" + System.currentTimeMillis() + "_" + i);
			dao.insert(pet);
		}
		
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			dao.fetch(Pet.class, random.nextInt());
		}
	}
}
