package com.piggsoft.retrofit.spring.boot.autoconfigue;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.protobuf.ProtoConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.converter.wire.WireConverterFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by piggs on 2017/1/20.
 */
@Configuration
@EnableConfigurationProperties(RetrofitProperties.class)
@Import({RetrofitAutoConfiguration.RetrofitScannerConfigurer.class})
@Order(Integer.MAX_VALUE)
public class RetrofitAutoConfiguration implements ApplicationContextAware {

    @Autowired
    private RetrofitProperties retrofitProperties;

    private ApplicationContext applicationContext;

    @Bean
    @ConditionalOnMissingBean(RetrofitHolder.class)
    public RetrofitHolder retrofitHolder() {
        if (retrofitProperties.getItems().isEmpty()) {
            return null;
        }
        RetrofitHolder holder = new RetrofitHolder();
        for (RetrofitProperties.Item item : retrofitProperties.getItems()) {
            Converter.Factory factory = getFactory(item.getConverter());
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(item.getBaseUrl());
            if (factory != null) {
                builder.addConverterFactory(factory);
            }
            holder.getMap().put(item.getName(), builder.build());
        }
        return holder;
    }

    private Converter.Factory getFactory(String name) {
        if (StringUtils.isEmpty(name)) {
            Map<String, Converter.Factory> factoryMap = applicationContext.getBeansOfType(Converter.Factory.class);
            if (factoryMap.isEmpty()) {
                //// TODO: 2017/1/21
            }
            return factoryMap.entrySet().iterator().next().getValue();
        }
        return applicationContext.getBean(name, Converter.Factory.class);
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public RetrofitProperties getRetrofitProperties() {
        return retrofitProperties;
    }

    public void setRetrofitProperties(RetrofitProperties retrofitProperties) {
        this.retrofitProperties = retrofitProperties;
    }

    public static class RetrofitScannerConfigurer implements ImportBeanDefinitionRegistrar, BeanFactoryAware {
        private BeanFactory beanFactory;

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

            List<String> pkgs = AutoConfigurationPackages.get(this.beanFactory);
            /*List<String> pkgs = Arrays.asList("com.piggsoft");*/
            RetrofitScanner scanner = new RetrofitScanner(registry);
            scanner.setAnnotationClass(RetrofitService.class);
            scanner.registerFilters();
            scanner.scan(StringUtils.toStringArray(pkgs));
        }

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }
    }

    @Configuration
    public static class RetrofitFactoryConfiguration {

        public static final String NAME_GSON = "gson";
        public static final String NAME_JACKSON = "jackson";
        public static final String NAME_MOSHI = "moshi";
        public static final String NAME_PROTO = "proto";
        public static final String NAME_SIMPLEXML = "simplexml";
        public static final String NAME_WIRE = "wire";

        @Configuration
        @ConditionalOnClass({GsonConverterFactory.class})
        static class GsonConverterFactoryConfiguration{
            @Bean(name = NAME_GSON)
            public GsonConverterFactory gsonConverterFactory() {
                return GsonConverterFactory.create();
            }
        }
        @Configuration
        @ConditionalOnClass({JacksonConverterFactory.class})
        static class JacksonConverterFactoryConfiguration{
            @Bean(name = NAME_JACKSON)
            public JacksonConverterFactory jacksonConverterFactory() {
                return JacksonConverterFactory.create();
            }
        }

        @Configuration
        @ConditionalOnClass({MoshiConverterFactory.class})
        static class MoshiConverterFactoryConfiguration{
            @Bean(name = NAME_MOSHI)
            public MoshiConverterFactory moshiConverterFactory() {
                return MoshiConverterFactory.create();
            }

        }

        @Configuration
        @ConditionalOnClass({ProtoConverterFactory.class})
        static class ProtoConverterFactoryConfiguration{
            @Bean(name = NAME_PROTO)
            public ProtoConverterFactory protoConverterFactory() {
                return ProtoConverterFactory.create();
            }
        }

        @Configuration
        @ConditionalOnClass({SimpleXmlConverterFactory.class})
        static class SimpleXmlConverterFactoryConfiguration{
            @Bean(name = NAME_SIMPLEXML)
            public SimpleXmlConverterFactory simpleXmlConverterFactory() {
                return SimpleXmlConverterFactory.create();
            }
        }

        @Configuration
        @ConditionalOnClass({WireConverterFactory.class})
        static class WireConverterFactoryConfiguration{
            @Bean(name = NAME_WIRE)
            public WireConverterFactory wireConverterFactory() {
                return WireConverterFactory.create();
            }
        }


    }


}
