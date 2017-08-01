package com.ethaneldridge.salvo.aspect;

import java.awt.BorderLayout;
import java.awt.Component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import VASSAL.tools.IconButton;

// FIXME: Not Used
@Aspect
public class AwtContainerAspect {

//	@Around("execution(* java.awt.Container.add(..)) && args(comp, constraints)")
//	public Object aroundAwtContainerAdd(ProceedingJoinPoint joinPoint, Component comp, Object constraints) throws Throwable {
	@Around("execution(* java.awt.Container.add(..))")
		public void aroundAwtContainerAdd(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.debug("aroundAwtContainerAdd");

		Component comp = (Component)joinPoint.getArgs()[0];
		Object constraints = joinPoint.getArgs()[1];
		
		if (comp instanceof IconButton && constraints instanceof String && BorderLayout.LINE_END.equals((String)constraints)) {
			System.out.println("Found the TurnWidget nextButton");
		}

		joinPoint.proceed();
		
	}

	private static final Logger logger = LoggerFactory.getLogger(AwtContainerAspect.class);


}
