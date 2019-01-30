package VASSAL.build.module.documentation;

import java.io.File;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ethaneldridge.salvo.vassal.membrane.VassalEngine;

@Aspect
public class AboutScreenAspect {

	@Around("execution(* VASSAL.build.module.documentation.AboutScreen.setAttribute(..))")
	public Object setAttributeAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Object returnObject = null;
		try {
			
			returnObject = joinPoint.proceed();
			Object[] args = joinPoint.getArgs();
			
			// See VASSAL.build.module.documentation.AboutScreen, line 164
			String key = (String)args[0];
			Object value = args[1];
			if (FILENAME_KEY.equals(key)) {
				String actualFileName;
				if (value instanceof File) {
					actualFileName = ((File) value).getName();
				} else if (value instanceof String){
					actualFileName = (String) value;
				} else {
					throw new Throwable ("Unexpected type of object: " + value);
				}
				
//				vassalEngine.getAboutScreenData().setBackgroundImageFileName(actualFileName);
			}
		} catch (Throwable t) {
			throw t;
		} finally {
			logger.debug("AboutScreen.setAttributeAround: finally");
		}
		
		
		return returnObject;
	}

	private static final String FILENAME_KEY = "fileName";
	private static final Logger logger = LoggerFactory.getLogger(AboutScreenAspect.class);
	private static final VassalEngine vassalEngine = VassalEngine.theVassalEngine();

}
