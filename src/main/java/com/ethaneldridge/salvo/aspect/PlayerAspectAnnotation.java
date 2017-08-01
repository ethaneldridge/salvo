package com.ethaneldridge.salvo.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import VASSAL.launch.Player;

@Aspect
public class PlayerAspectAnnotation {

	@Pointcut("execution(* VASSAL.launch.Player.*(..))")
	void player() {}
	
	@AfterReturning("player()")
	public void test2() {
//		System.out.println("annotation test2");
	}
}
