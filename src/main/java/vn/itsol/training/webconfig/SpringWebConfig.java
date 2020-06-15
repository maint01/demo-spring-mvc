package vn.itsol.training.webconfig;

import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.util.Locale;
import java.util.Properties;

@EnableWebMvc
@Configuration
@EnableTransactionManagement
@ComponentScan({"vn.itsol.training"})
@PropertySources({
    @PropertySource("classpath:jdbc.properties")
})
@SuppressWarnings("all")
public class SpringWebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        driverManagerDataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        driverManagerDataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        driverManagerDataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return driverManagerDataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return namedParameterJdbcTemplate;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(DataSource dataSource) {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);

        localSessionFactoryBean.setPackagesToScan("vn.itsol.training.entity");

        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        hibernateProperties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));

        localSessionFactoryBean.setHibernateProperties(hibernateProperties);
        return localSessionFactoryBean;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(-1);
        return commonsMultipartResolver;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }


  /* messageSource bean is spring built-in bean name which will manipulate internationalization messages.
   * All message files is saved in src/main/resources/config/ folder, if the config folder do not exist, you need to create it first by hand.
   * Each message file is a properties file, the file base name is messages and suffix with the language or country ISO code, such as messages_en, messages_zh_cn etc.
   * */
  @Bean(name = "messageSource")
  public MessageSource getMessageSource() {
    ReloadableResourceBundleMessageSource ret = new ReloadableResourceBundleMessageSource();

    // Set the base name for the messages properties file.
    ret.setBasename("classpath:messages");

    ret.setCacheSeconds(1);

    ret.setUseCodeAsDefaultMessage(true);

    ret.setDefaultEncoding("utf-8");

    return ret;
  }


  /* The localeResolver is used to resolve user locale data. The CookieLocaleResolver object is used to save user locale information in browser cookie.
   * This way user locale info can be transferred between request. If user disable cookie then you can use SessionLocaleResolver instead. */
  @Bean(name = "localeResolver")
  public CookieLocaleResolver getCookieLocaleResolver(){
    // Create a CookieLocaleResolver object.
    CookieLocaleResolver localeResolver = new CookieLocaleResolver();
    // Set cookie name
    localeResolver.setCookieName("cookie-locale-info");
    // Set default locale value.
    localeResolver.setDefaultLocale(Locale.ENGLISH);
    // Set cookie max exist time.
    localeResolver.setCookieMaxAge(3600);

    return localeResolver;
  }

  /* If user disable cookie then you can use SessionLocaleResolver instead. */
   /*@Bean(name = "localeResolver")
    public SessionLocaleResolver getSessionLocaleResolver(){
      // Create a SessionLocaleResolver object.
      SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        // Set default locale in session.
      localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }*/


  /* The LocaleChangeInterceptor is a interceptor that will intercept user locale change by a parameter value.
   * For example, if we set the locale change parameter name is locale, then request url http://localhost:8088/index.jsp?locale=en will change
   * user locale to en and display messages in src/main/resources/config/messages_en.properties.
   *  */
  @Bean(name="localeInterceptor")
  public LocaleChangeInterceptor getLocaleInterceptor(){
    LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
    interceptor.setParamName("language");
    return interceptor;
  }

  /* Also need to register above locale interceptor in spring then it will intercept user request url and parse out the lang query string to get user request locale.*/
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(getLocaleInterceptor());
  }

}
