# Spring - IOC 控制反转

#### 初始化

```text
ClassPathXmlApplicationContext

AbstractApplicationContext.refresh -> refreshBeanFactory
AbstractRefreshableApplicationContext.refreshBeanFactory -> loadBeanDefinitions
AbstractXmlApplicationContext.loadBeanDefinitions

AbstractBeanDefinitionReader.loadBeanDefinitions
XmlBeanDefinitionReader.doLoadBeanDefinitions -> registerBeanDefinitions

BeanDefinitionDocumentReader.registerBeanDefinitions -> doRegisterBeanDefinitions -> parseBeanDefinitions -> processBeanDefinition

BeanDefinitionParserDelegate.parseBeanDefinitionElement -> parsePropertyValue
```

#### 注入依赖

```text
ClassPathXmlApplicationContext

AbstractApplicationContext.finishBeanFactoryInitialization

ConfigurableListableBeanFactory.preInstantiateSingletons
DefaultListableBeanFactory.preInstantiateSingletons -> getBean -> getSingleton

AbstractAutowireCapableBeanFactory.createBean -> doCreateBean -> createBeanInstance
```

*PS：本文使用的是spring-4.3.7.RELEASE*