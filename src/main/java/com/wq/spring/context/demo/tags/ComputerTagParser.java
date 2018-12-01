package com.wq.spring.context.demo.tags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/29
 * @Description:
 * @Resource:
 */
public class ComputerTagParser extends AbstractBeanDefinitionParser {

	private static final String TAG_NAME = "computer";

	private static final String OWNER = "owner";

	private static final String PRICE = "price";

	private static final String SUB_TAG_CORE = "core";

	private static final String SUB_TAG_EXTEND_SERVICE = "ext-service";

	private static final String CPU = "cpu";

	private static final String DISK = "disk";

	private static final String DISK_SIZE = "disk-size";

	private static final String GPU = "gpu";

	private static final String ROM = "rom";

	private static final String KEYBOARD = "keyboard";

	private static final String MONITOR = "monitor";

	private static final String MOUSE = "mouse";

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
		if(!TAG_NAME.equals(element.getLocalName())){
			logger.debug("illegal element name[{}] and expect [{}]",element.getLocalName(),TAG_NAME);
			return null;
		}

		AbstractBeanDefinition beanDefinition = new RootBeanDefinition(Computer.class);

		String owner = element.getAttribute(OWNER);
		String price = element.getAttribute(PRICE);

		if(StringUtils.isEmpty(owner)){
			logger.debug("empty owner and choose current system user name");
			owner = System.getProperty("user.name");
		}

		if(StringUtils.isEmpty(price)){
			logger.debug("empty price and will set zero value for it");
			price = "0.00";
		}
		beanDefinition.getPropertyValues().addPropertyValue(OWNER,owner);
		beanDefinition.getPropertyValues().addPropertyValue(PRICE,Double.valueOf(price));

		List<Element> children = DomUtils.getChildElements(element);
		for(Element child : children){

			if(SUB_TAG_CORE.equals(child.getLocalName())) {
				PropertyValue propertyValue = new PropertyValue("computerCore", parseComputerCore(child, parserContext));
				beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
			}

			if(SUB_TAG_EXTEND_SERVICE.equals(child.getLocalName())){
				PropertyValue propertyValue = new PropertyValue("expandService", parseExtendService(child, parserContext));
				beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
			}
		}

		beanDefinition.setBeanClass(Computer.class);

		beanDefinition.setSource(parserContext.extractSource(element));
		return beanDefinition;
	}

	private BeanDefinition parseExtendService(Element element,ParserContext parserContext){
		if(!SUB_TAG_EXTEND_SERVICE.equals(element.getLocalName())){
			logger.debug("empty sub tags ext-service");
			return null;
		}

		BeanDefinition beanDefinition = new GenericBeanDefinition();
		String keyboard = element.getAttribute(KEYBOARD);
		String monitor = element.getAttribute(MONITOR);
		String mouse = element.getAttribute(MOUSE);

		beanDefinition.setBeanClassName(ExpandService.class.getName());
		((GenericBeanDefinition) beanDefinition).setSource(parserContext.extractSource(element));

		beanDefinition.getPropertyValues().addPropertyValue(KEYBOARD,keyboard);
		beanDefinition.getPropertyValues().addPropertyValue(MONITOR,monitor);
		beanDefinition.getPropertyValues().addPropertyValue(MOUSE,mouse);

		return beanDefinition;

	}

	private BeanDefinition parseComputerCore(Element element,ParserContext parserContext){

		if(!SUB_TAG_CORE.equals(element.getLocalName())){
			logger.debug("empty sub tags core");
			return null;
		}

		BeanDefinition beanDefinition = new GenericBeanDefinition();
		String cpu = element.getAttribute(CPU);
		String disk = element.getAttribute(DISK);
		String diskSize = element.getAttribute(DISK_SIZE);
		String gpu = element.getAttribute(GPU);
		String rom = element.getAttribute(ROM);

		beanDefinition.getPropertyValues().addPropertyValue(CPU,cpu);
		beanDefinition.getPropertyValues().addPropertyValue(DISK,disk);
		beanDefinition.getPropertyValues().addPropertyValue("diskSize",Integer.valueOf(diskSize));
		beanDefinition.getPropertyValues().addPropertyValue(GPU,gpu);
		beanDefinition.getPropertyValues().addPropertyValue(ROM,rom);
		beanDefinition.setBeanClassName(ComputerCore.class.getName());
		((GenericBeanDefinition) beanDefinition).setSource(parserContext.extractSource(element));

		return beanDefinition;

	}



}
