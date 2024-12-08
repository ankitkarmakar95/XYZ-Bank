package com.ankit.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ankit.mvc.service.AccountService;

@Controller
public class AcountController {

	@Autowired
	AccountService accountService;
	
	@GetMapping("/home")
	public String dashboard() {
		return "dashboard";
	}

	@GetMapping("/open-account")
	public String accountOpen() {
		return "openaccount";
	}

	@PostMapping("/register-account")
	public String accountRegistration(@RequestParam("name") String name, @RequestParam("empid") String empid,
			Model model) {
		model.addAttribute("accountid", accountService.createAccount(name, empid));
		model.addAttribute("name", name);
		return "registered";
	}
	@GetMapping("/order-card")
	public String orderDebitCard() {
		return "ordercard";
	}
	@PostMapping("/deliver-card")
	public String deliverCard(@RequestParam("accntid") String accntId, @RequestParam("crdtyp") String crdTyp,
			@RequestParam("limit") String limit,
			Model model) {
		System.out.println(limit);
		try {
			model.addAttribute("cardnumber", accountService.createCard(accntId, crdTyp, limit));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			return "ordercard";
		}
		model.addAttribute("accntId", accntId);
		model.addAttribute("crdTyp", crdTyp);
		return "carddelivered";
	}
	
}
