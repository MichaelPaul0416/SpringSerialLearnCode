package com.wq.spring.modules.demo.beanfactory;

import com.wq.spring.modules.demo.bean.RemoteServiceImporter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/28
 * @Description:
 * @Resource:
 */
public class SimpleCustomLocalBeanRegister implements ResourceLoader, BeanDefinitionRegistryPostProcessor {

	private static final String CLASSPATH_PREFIX = "classpath:*/";

	private static final String COMMON_PREFIX = "/";

	private static final String HTTP_PREFIX = "http://";

	private Properties properties;


	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		if (this.properties == null) {
			this.properties = new Properties();
			try {
				properties.load(SimpleCustomLocalBeanRegister.class.getResourceAsStream(
						"/com/wq/spring/modules/demo/CustomBeanFactory.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Enumeration<String> allProperties = (Enumeration<String>) properties.propertyNames();
		while (allProperties.hasMoreElements()) {
			String beanName = allProperties.nextElement();
			String value = properties.getProperty(beanName);

			if (StringUtils.isEmpty(value)) {
				continue;
			}

			BeanDefinition beanDefinition = new GenericBeanDefinition();
			String[] properties = value.split("\\|", -1);
			beanDefinition.getPropertyValues().addPropertyValue("serviceName", properties[0]);
			beanDefinition.getPropertyValues().addPropertyValue("remoteAddress", properties[1]);
			((GenericBeanDefinition) beanDefinition).setBeanClass(RemoteServiceImporter.class);
			registry.registerBeanDefinition(beanName, beanDefinition);
		}
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}

	@Override
	public Resource getResource(String location) {
		System.out.println(getClass().getName() + " receive location --> " + location);
		JarProjectSourceLoader jarProjectSourceLoader = new JarProjectSourceLoader(location);
		this.properties = jarProjectSourceLoader.getProperties();

		return jarProjectSourceLoader;
	}

	@Override
	public ClassLoader getClassLoader() {
		return null;
	}

	private class ClassPathResolver implements SourceResolver {

		private String protocol;


		public ClassPathResolver() {
			this.protocol = "classpath:*/";
		}

		@Override
		public String doResolve(String protocol, String path) {
			if (this.protocol.equals(protocol)) {
				if (StringUtils.isEmpty(path)) {
					throw new NullPointerException("empty file path");
				}

				if (!path.startsWith(this.protocol)) {
					throw new IllegalArgumentException("path does not start with protocol[" + this.protocol + "]");
				}

				return path.substring(this.protocol.length());
			}

			return "";
		}
	}

	public Resource resource(String path) {
		return new JarProjectSourceLoader(path);
	}

	private class JarProjectSourceLoader implements Resource {

		private File file;

		private final String protocol;

		private final Map<Class, SourceResolver> resolverMap;

		private Properties properties;

		private static final String BLANK_CODE = "%20";

		public Properties getProperties() {
			if (properties == null) {
				properties = new Properties();
				try {
					properties.load(new FileInputStream(file));
				} catch (IOException e) {
					System.out.println("exception-->[" + e.getMessage() + "]");
				}
			}
			return properties;
		}

		private JarProjectSourceLoader() {
			this.protocol = "classpath:*/";
			this.resolverMap = new ConcurrentHashMap<>();
			this.resolverMap.put(ClassPathResolver.class, new ClassPathResolver());
		}

		public JarProjectSourceLoader(String path) {
			this();
			URL url = this.getClass().getResource("/" + path);
			this.file = new File(url.getPath().replaceAll(BLANK_CODE, " "));
		}

		public JarProjectSourceLoader(File file) {
			this();
			this.file = file;
		}

		@Override
		public boolean exists() {
			return this.file.exists();
		}

		@Override
		public URL getURL() throws IOException {

			return this.file.toURI().toURL();
		}

		@Override
		public URI getURI() throws IOException {
			return this.file.toURI();
		}

		@Override
		public File getFile() throws IOException {
			return this.file;
		}

		@Override
		public long contentLength() throws IOException {
			return this.file.length();
		}

		@Override
		public long lastModified() throws IOException {
			return this.file.lastModified();
		}

		@Override
		public Resource createRelative(String relativePath) throws IOException {
			String path = null;
			for (Map.Entry<Class, SourceResolver> entry : this.resolverMap.entrySet()) {
				SourceResolver resolver = entry.getValue();
				path = resolver.doResolve(this.protocol, relativePath);
				if (!StringUtils.isEmpty(path)) {
					break;
				}
			}

			if (!StringUtils.isEmpty(path)) {
				URL url = this.getClass().getClassLoader().getResource("/" + path);
				return new JarProjectSourceLoader(url.getPath());
			}

			throw new FileNotFoundException(relativePath + " not found");
		}

		@Override
		public String getFilename() {
			return this.file.getName();
		}

		@Override
		public String getDescription() {
			String diskPath = this.file.getAbsolutePath();
			return diskPath;
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return Files.newInputStream(this.file.toPath());
//			return new FileInputStream("D:\\Program Files\\IdeaProjects\\spring-framework\\spring-context\\out\\test\\classes\\com.wq.spring.context.demo\\SimpleApplicationContext.properties");
		}
	}
}
