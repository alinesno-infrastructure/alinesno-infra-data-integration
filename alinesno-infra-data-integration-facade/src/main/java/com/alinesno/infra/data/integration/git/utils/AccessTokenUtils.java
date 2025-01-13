package com.alinesno.infra.data.integration.git.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * token认证授权
 *
 * @author paul
 * @version 1.0.0
 */
@Component
public class AccessTokenUtils {

	private static final Logger log = LoggerFactory.getLogger(AccessTokenUtils.class);

	public String getGiteaUserInfo(String accessToken, String url) {

		log.info("getUserInfo url:{}", url);
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("accept", "application/json");
		requestHeaders.add("Authorization", "token " + accessToken);

		HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
		RestTemplate restTemplate = new RestTemplate();

		// get请求方式
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		String userInfo = response.getBody();
		log.info("userInfo={}", userInfo);
		return userInfo;
	}



}
