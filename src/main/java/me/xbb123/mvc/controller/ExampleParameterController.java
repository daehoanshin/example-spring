package me.xbb123.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.xbb123.mvc.domain.ExampleRequestBodyUser;
import me.xbb123.mvc.parameter.ExampleParameter;

@Controller
@RequestMapping("/example/parameter")
public class ExampleParameterController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/example1")
	public void example1(@RequestParam String id, 
			@RequestParam String code, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("code", code);
	}
	
	/**
	 * Map을 활용한 파라미터 받는방법
	 * @param paramMap
	 * @param model
	 */
	@GetMapping("/example2")
	public void example2(@RequestParam Map<String, Object> paramMap, Model model) {
		model.addAttribute("paramMap", paramMap);
	}
	
	
	/**
	 * Class를 활용한 파라미터 받는방법
	 * @param parameter
	 * @param model
	 */
	@GetMapping("/example3")
	public void example3(ExampleParameter parameter, Model model) {
		model.addAttribute("parameter", parameter);
	}
	
	/**
	 * @PathVariable 를 활용한 파라미터 받는방법
	 * @param id
	 * @param model
	 */
	@GetMapping("/example4/{id}/{code}")
	public String example4(@PathVariable String id, @PathVariable String code, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("code", code);
		return "/example/parameter/example4";
	}
	
	/**
	 * String[] 배열을 받는방법
	 * @param id
	 * @param code
	 * @param model
	 * @return
	 */
	@GetMapping("/example5")
	public String example5(@RequestParam String ids, Model model) {
		model.addAttribute("ids", ids);
		return "/example/parameter/example5";
	}
	
	/**
	 * json 받는방법
	 * @param ids
	 * @param model
	 * @return
	 */
	@GetMapping("/example6/form")
	public void form() {
		
	}
	
	/**
	 * @param requestBody
	 * @param model
	 * @return
	 */
	@PostMapping("/example6/saveData")
	@ResponseBody
	public Map<String, Object> example6(@RequestBody ExampleRequestBodyUser requestBody, Model model) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);
		logger.info("requestBody : {}", requestBody);
		return resultMap;
	}
}
