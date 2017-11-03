package com.btx.action;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.btx.domain.Person;
import com.btx.service.IPersonServiceInter;

@Controller
@RequestMapping("/myaction")
public class MyAction {
	@Autowired
	private IPersonServiceInter iPersonServiceInter;
	@RequestMapping("/getAll")
	public @ResponseBody List<Person> getAll()
	{
		Properties pros = new Properties();
		
//		if("zk".equals(flag))
//		{
//			String url="jdbc:mysql://127.0.0.1:3306/myproject?useUnicode=true&amp;characterEncoding=UTF-8";
//			String user="root";
//			String pw="199288";
//			pros.put("jdbcUrl", value)
//			
//		}
//		else
//		{
//			String url="";
//			String user="";
//			String pw="";
//		}
//		String driver="";
		return iPersonServiceInter.getAll();
	}
}
