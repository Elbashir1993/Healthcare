package com.feign.domain;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("APPOINTMENT-AND-STAFF-SERVICE")
public interface FiegnClientDomain {
	@RequestMapping(method=RequestMethod.GET, value="/testeurekaport")
    String getPort();

	/*
	 * @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}",
	 * produces = "application/json") Post getPostById(@PathVariable("postId") Long
	 * postId);
	 */

}
