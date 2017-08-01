package com.ethaneldridge.salvo.aspect;

import java.util.HashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ethaneldridge.salvo.vassal.membrane.GameWrapper;

@Aspect
public class WizardDisplayerAspect {

//	@Around("execution(* org.netbeans.api.wizard.WizardDisplayer.showWizard(..))")
//	public Object showWizardAround(ProceedingJoinPoint joinPoint) throws Throwable {
//		logger.debug("showWizardAround");
////		gameWrapper.getInput();
//		return new HashMap();
//	}
	private static final Logger logger = LoggerFactory.getLogger(WizardDisplayerAspect.class);

}

