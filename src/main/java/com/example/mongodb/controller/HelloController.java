package com.example.mongodb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.example.mongodb.hello.Customer;
import com.example.mongodb.hello.CustomerRepository;

@Controller
public class HelloController {
	@Autowired
	private CustomerRepository custRepo;
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String form(){
		return "form";
	}
	
	@RequestMapping(value="/hello", method=RequestMethod.PUT)
	public String write(@Valid Customer cust, BindingResult bndResult){
		if(bndResult.hasErrors()){
			return "form";
		}
		return "redirect:/hello/" + custRepo.save(cust).getId();
	}
	
	@RequestMapping("/hello/list")
	public String list(Model model, @PageableDefault(sort={"id"}, direction=Direction.DESC) Pageable pageable){
		Page<Customer> custPage = custRepo.findAll(pageable);
		model.addAttribute("custPage", custPage);
		return "custList";
	}
	
	@RequestMapping("/hello/{id}")
	public String View(Model model, @PathVariable int id){
		Customer cust = custRepo.findOne(id);
		model.addAttribute(cust);
		return "hello";
	}
	
}
