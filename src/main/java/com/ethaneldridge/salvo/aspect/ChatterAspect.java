package com.ethaneldridge.salvo.aspect;

import java.time.ZonedDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ethaneldridge.salvo.dal.SalvoChatDal;
import com.ethaneldridge.salvo.dal.impl.SalvoChatDalImpl;
import com.ethaneldridge.salvo.data.SalvoChat;

@Aspect
public class ChatterAspect {

	@Around("execution(* VASSAL.build.module.Chatter.show(..))")
	public Object aroundChatterShow(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.debug("aroundChatterShow");

		Object[] args = joinPoint.getArgs();
		Object parameter = args[0];
		String message = (String)parameter;
		
		SalvoChat salvoChat = new SalvoChat(Long.toString(id++),
				ZonedDateTime.now(),
				message);
		salvoChatDal.save(salvoChat);

		return joinPoint.proceed(args);
		
	}

	private static long id = 0;
	private static final SalvoChatDal salvoChatDal = new SalvoChatDalImpl();
	private static final Logger logger = LoggerFactory.getLogger(ChatterAspect.class);

}
