package com.ethaneldridge.salvo.aspect;

import javax.swing.JToolBar;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ethaneldridge.salvo.dal.SalvoToolbarDal;
import com.ethaneldridge.salvo.dal.SalvoToolbarItemDal;
import com.ethaneldridge.salvo.dal.impl.SalvoToolbarDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoToolbarItemDalImpl;
import com.ethaneldridge.salvo.data.SalvoToolbar;
import com.ethaneldridge.salvo.data.SalvoToolbarItem;
import com.ethaneldridge.salvo.vassal.membrane.VassalEngine;

import VASSAL.tools.LaunchButton;

// FIXME: Not Used
@Aspect
public class AwtContainerAspect {

//	@Around("execution(* java.awt.Container.add(..)) && args(comp, constraints)")
//	public Object aroundAwtContainerAdd(ProceedingJoinPoint joinPoint, Component comp, Object constraints) throws Throwable {

	@Around("call(java.awt.Component java.awt.Container.add(..))")
		public Object aroundAwtContainerAdd(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.debug("aroundAwtContainerAdd");

		if (joinPoint.getArgs().length == 1) {
			Object object = joinPoint.getArgs()[0];

			Object target = joinPoint.getTarget();
			if (target instanceof JToolBar) {
				JToolBar jToolBar = (JToolBar)target;
				if (object instanceof VASSAL.tools.LaunchButton) {
					SalvoToolbarDal salvoToolbarDal = new SalvoToolbarDalImpl(VassalEngine.theVassalEngine().getVassalRepository());
					SalvoToolbar salvoToolbar = salvoToolbarDal.getByAssociatedJToolBar(jToolBar);

//					SalvoToolbarItemDal salvoToolbarItemDal = new SalvoToolbarItemDalImpl(VassalEngine.theVassalEngine().getVassalRepository());
//					SalvoToolbarItem salvoToolbarItem = salvoToolbarItemDal.getByParent(target);
					LaunchButton launchButton = (LaunchButton)object;
					
					String name = getAttributeSafe(launchButton, launchButton.getNameAttribute());
					String hotkey = getAttributeSafe(launchButton, launchButton.getHotkeyAttribute());
					String icon = getAttributeSafe(launchButton, launchButton.getIconAttribute());
					String toolTipText = launchButton.getToolTipText();
					boolean isEnabled = launchButton.isEnabled();
					boolean isVisible = launchButton.isVisible();
	
					SalvoToolbarItem salvoToolbarItem = new SalvoToolbarItem();
					salvoToolbarItem.setParentToolbar(salvoToolbar);
					salvoToolbarItem.setButtonIcon(icon);
					salvoToolbarItem.setButtonText(launchButton.getText());
					salvoToolbarItem.setDescription("");
					salvoToolbarItem.setButtonHotkey(hotkey);
					salvoToolbarItem.setTooltip(toolTipText);
					salvoToolbarItem.setMenuItems(new String[]{});

					salvoToolbar.add(salvoToolbarItem);
//					SalvoToolbarMenuDal salvoToolbarMenuDal = new SalvoToolbarMenuDalImpl(VassalEngine.theVassalEngine().getVassalRepository());
//					salvoToolbarMenuDal.save(salvoToolbarMenuItem);
					salvoToolbarDal.save(salvoToolbar);
				}
			}
		}

		return joinPoint.proceed();
	}

	@Around("call(void java.awt.Container.remove(..))")
	public void aroundAwtContainerRemove(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.debug("aroundAwtContainerRemove");
	
		if (joinPoint.getArgs().length == 1) {
			Object o = joinPoint.getArgs()[0];
	
			Object target = joinPoint.getTarget();
			if (target instanceof JToolBar) {
				if (o instanceof VASSAL.tools.LaunchButton) {
					LaunchButton launchButton = (LaunchButton)o;
					String name = getAttributeSafe(launchButton, launchButton.getNameAttribute());
					String hotkey = getAttributeSafe(launchButton, launchButton.getHotkeyAttribute());
					String icon = getAttributeSafe(launchButton, launchButton.getIconAttribute());
					String toolTipText = launchButton.getToolTipText();
					boolean isEnabled = launchButton.isEnabled();
					boolean isVisible = launchButton.isVisible();
	
					System.out.println(String.format("Removing a LaunchButton: text[%s], name[%s], hotkey[%s], icon[%s], toolTipText[%s], isEnabled[%b], isVisible[%b]",
	//						launchButton.toString(),
							launchButton.getText(),
							name,
							hotkey,
							icon,
							toolTipText,
							isEnabled,
							isVisible));

				}
			}
		}

		joinPoint.proceed();
	}

	private String getAttributeSafe(LaunchButton launchButton, String attribute) {
		String value = attribute == null ? "<empty>" : launchButton.getAttributeValueString(attribute);
		return value;
	}

	private static final Logger logger = LoggerFactory.getLogger(AwtContainerAspect.class);
}
