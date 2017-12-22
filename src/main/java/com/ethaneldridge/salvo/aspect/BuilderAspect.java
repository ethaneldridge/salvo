package com.ethaneldridge.salvo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Aspect
public class BuilderAspect {

	@Around("execution(* VASSAL.build.Builder.create(..))")
	public Object aroundBuilderCreate(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.debug("aroundBuilderCreate");

		Object[] args = joinPoint.getArgs();
		Object parameter = args[0];
		Element e = (Element)parameter;
		
		Document document = e.getOwnerDocument();
		String elementName = e.getTagName();
		if ("VASSAL.build.module.turn.TurnTracker".equals(elementName)) {
			document.renameNode(e, e.getNamespaceURI(), "VASSAL.build.module.turn.VassalSalvoTurnTracker");
			System.out.println(String.format("Renamed element from [%s] to [%s]", elementName, e.getTagName()));
			args[0] = e;
		} else if ("VASSAL.build.module.ToolbarMenu".equals(elementName)) {
			document.renameNode(e, e.getNamespaceURI(), "VASSAL.build.module.VassalSalvoToolbarMenu");
			System.out.println(String.format("Renamed element from [%s] to [%s]", elementName, e.getTagName()));
			args[0] = e;
		}

		return joinPoint.proceed(args);
		
	}

	private static final Logger logger = LoggerFactory.getLogger(BuilderAspect.class);

}
