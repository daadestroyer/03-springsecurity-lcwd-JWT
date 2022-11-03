package com.daadestroyer.springsecuritywithJWT.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	
	@GetMapping("/welcome")
	public String welcome() {
		String txt = "this is private page , this page is not allowed to unauthenticated users";
		return txt;
	}

}
