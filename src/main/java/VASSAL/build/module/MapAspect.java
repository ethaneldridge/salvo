package VASSAL.build.module;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ethaneldridge.salvo.vassal.membrane.VassalEngine;

@Aspect
public class MapAspect {

//	@AfterReturning("execution(* VASSAL.build.module.Map.mouseReleased(..))")
//	public void afterMouseReleased() throws Throwable {
	@Around("execution(* VASSAL.build.module.Map.mouseReleased(..))")
	public Object aroundMouseReleased(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.debug("afterMouseReleased");
		
		Object parameter = joinPoint.getArgs()[0];
		MouseEvent mouseEvent = (MouseEvent)parameter;
		Point p = mouseEvent.getPoint();
		int clicks = mouseEvent.getClickCount();
		System.out.println(String.format("mouseReleased: p.x is %d, p.y is %d, clickCount is %d", p.x, p.y, clicks));

		// Game state should now be current and can be captured
		// But, should know how many releases are expected (1 or 2)
		synchronized (vassalEngine) {
			vassalEngine.setReceivedClicks(clicks);
			if (vassalEngine.isStateReady()) {
				vassalEngine.resetClicks();
				vassalEngine.notifyAll();
			}
		}

		return joinPoint.proceed();
	}

	@Around("execution(* VASSAL.build.module.Map.mouseClicked(..))")
	public Object aroundMouseClicked(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.debug("afterMouseClicked");
		
		Object parameter = joinPoint.getArgs()[0];
		MouseEvent mouseEvent = (MouseEvent)parameter;
		Point p = mouseEvent.getPoint();
		int clicks = mouseEvent.getClickCount();
		System.out.println(String.format("mouseClicked: p.x is %d, p.y is %d, clickCount is %d", p.x, p.y, clicks));
		
		// Game state should now be current and can be captured
		synchronized (vassalEngine) {
			vassalEngine.setReceivedClicks(clicks);
			if (vassalEngine.isStateReady()) {
				vassalEngine.resetClicks();
				vassalEngine.notifyAll();
			}
		}

		return joinPoint.proceed();
		
	}

	private static final Logger logger = LoggerFactory.getLogger(MapAspect.class);
	private static final VassalEngine vassalEngine = VassalEngine.theVassalEngine();

}
