package com.ethaneldridge.salvo.aspect;

import javax.swing.Action;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ethaneldridge.salvo.vassal.membrane.VassalEngine;

@Aspect
public class SwingJToolBarAspect {

//	@Around("execution(javax.swing.JButton javax.swing.JToolBar.add(..))")
	@Around("call(javax.swing.JButton javax.swing.JToolBar.add(..))")
	public Object aroundJToolBarAdd(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.debug("afterJToolBarAdd");
		
		Object parameter = joinPoint.getArgs()[0];
		Action action = (Action)parameter;

		// This action is on a toolbar.  Let's see if it is the "Undo" action
		StringBuilder sb = new StringBuilder();
		sb.append("action.getValue(ACCELERATOR_KEY) = %s");
		sb.append("\t action.getValue(DEFAULT) = %s");
		sb.append("\t action.getValue(DISPLAYED_MNEMONIC_INDEX_KEY) = %s");
		sb.append("\t action.getValue(LARGE_ICON_KEY) = %s");
		sb.append("\t action.getValue(LONG_DESCRIPTION) = %s");
		sb.append("\t action.getValue(MNEMONIC_KEY) = %s");
		sb.append("\t action.getValue(NAME) = %s");
		sb.append("\t action.getValue(SELECTED_KEY) = %s");
		sb.append("\t action.getValue(SHORT_DESCRIPTION) = %s");
		sb.append("\t action.getValue(SMALL_ICON) = %s");
		
		System.out.println(String.format(sb.toString(),
				action.getValue(Action.ACCELERATOR_KEY),
				action.getValue(Action.DEFAULT),
				action.getValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY),
				action.getValue(Action.LARGE_ICON_KEY),
				action.getValue(Action.LONG_DESCRIPTION),
				action.getValue(Action.MNEMONIC_KEY),
				action.getValue(Action.NAME),
				action.getValue(Action.SELECTED_KEY),
				action.getValue(Action.SHORT_DESCRIPTION),
				action.getValue(Action.SMALL_ICON)));

		return joinPoint.proceed();
	}

	private static final Logger logger = LoggerFactory.getLogger(SwingJToolBarAspect.class);
	private static final VassalEngine vassalEngine = VassalEngine.theVassalEngine();

}
