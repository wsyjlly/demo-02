package cn.wsyjlly.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author wsyjlly
 * @create 2019.06.12 - 16:17
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    Logger logger = LoggerFactory.getLogger(getClass());
    /*@Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }*/

    /*@Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

    }*/

    /*@Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

    }*/

    /*@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

    }*/

    /*@Override
    public void addFormatters(FormatterRegistry registry) {

    }*/

    /*
    * 配置拦截器
    * */
    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
          registry.addInterceptor(new InterceptorDemo1()).addPathPatterns("/cors/fetch").excludePathPatterns("/get").order(1);
          registry.addInterceptor(new FileUploadInterceptor()).addPathPatterns("/upload").order(1);
    }*/

    /*
    * 配置静态资源过滤策略
    * 将静态资源路径映射为访问路径
    * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/");
        String uploadPath = new File(System.getProperty("user.dir"),"uploadFiles").getAbsolutePath().replaceAll("\\\\","/")+"/";
        registry.addResourceHandler("/uploadFiles/**").addResourceLocations("file:"+uploadPath);
        logger.info("上传文件路径映射：/uploadFiles/**  ————>>  "+uploadPath);
    }

    /*
    * 配置全局跨域请求
    * */
    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
           registry.addMapping("/cors/**").allowedHeaders("*").allowedMethods("*").maxAge(1800).allowedOrigins("*");
           registry.addMapping("/**").allowedHeaders("*").allowedMethods("*").maxAge(1800).allowedOrigins("http://localhost:5000");
    }*/

    /*@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("index");
    }*/

    /*@Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }*/

    /*@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

    }*/

    /*@Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {

    }*/

    /*
    * 配置fastJson作为Json解析框架
    * */
    /*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        *//*
        * 创建fastJson消息转换器
        * *//*
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();



        *//*
        * FastJsonConfig配置
        * *//*
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd");
        config.setCharset(Charset.forName("UTF-8"));
        config.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteClassName,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty
        );
        converter.setFastJsonConfig(config);

        *//*
        * MediaType配置
        * *//*
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        converter.setSupportedMediaTypes(supportedMediaTypes);



        *//*
        * 添加fastJson消息转换器
        * *//*
        converters.add(converter);

    }*/

    /*@Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    }*/

    /*@Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

    }*/

    /*@Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

    }*/

    /*@Override
    public Validator getValidator() {
        return null;
    }*/

    /*@Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }*/
}
