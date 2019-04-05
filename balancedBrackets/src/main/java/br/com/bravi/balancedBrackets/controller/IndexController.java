package br.com.bravi.balancedBrackets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@PostMapping("/validateBrackets")
	public ModelAndView validateBrackets(String bracketsSequence) {
		ModelAndView modelAndView = new ModelAndView("index");
		Integer openParentheses = 0;
		Integer closeParentheses = 0;
		Integer openBrackets = 0;
		Integer closeBrackets = 0;
		Integer openKeys = 0;
		Integer closeKeys = 0;

		for (char character : bracketsSequence.toCharArray()) {
			openParentheses = character == '(' ? ++openParentheses : openParentheses;
			closeParentheses = character == ')' ? ++closeParentheses : closeParentheses;
			openBrackets = character == '[' ? ++openBrackets : openBrackets;
			closeBrackets = character == ']' ? ++closeBrackets : closeBrackets;
			openKeys = character == '{' ? ++openKeys : openKeys;
			closeKeys = character == '}' ? ++closeKeys : closeKeys;
		}

		boolean isContemBrackets = (openParentheses == closeParentheses && openBrackets == closeBrackets && openKeys == closeKeys) && 
				(openParentheses > 0 || closeParentheses > 0 || openBrackets > 0 || closeBrackets > 0 || openKeys > 0 || closeKeys > 0);
		
		modelAndView.addObject("resultBracketsValid", (isContemBrackets && !bracketsSequence.isEmpty()) ? bracketsSequence + " is valid" : null);
		modelAndView.addObject("resultBracketsInvalid", (!isContemBrackets || bracketsSequence.isEmpty()) ? bracketsSequence + " is invalid" : null);
		
		return modelAndView;
	}
}
