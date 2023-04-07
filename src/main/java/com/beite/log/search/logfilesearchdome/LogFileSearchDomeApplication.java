package com.beite.log.search.logfilesearchdome;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;


@EnableCaching
@EnableScheduling
@SpringBootApplication
public class LogFileSearchDomeApplication {
	public static void main(String[] args) {
		SpringApplication.run(LogFileSearchDomeApplication.class, args);
	}

	@Component
	public static class SpringContextUtil implements ApplicationContextAware {
		private static ApplicationContext applicationContext;

		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			SpringContextUtil.applicationContext = applicationContext;
		}

		public ApplicationContext getApplicationContext() {
			return applicationContext;
		}

		public static <T> T getBean(String name,Class<T> clazz) throws BeansException {
			return applicationContext.getBean(name, clazz);
		}
	}

}
