package br.com.casadocodigod.loja.conf;

import org.springframework.aop.framework.DefaultAdvisorChainFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigod.loja.controllers.HomeController;
import br.com.casadocodigod.loja.daos.ProdutoDAO;
import br.com.casadocodigod.loja.infra.FileSaver;

@EnableWebMvc //aqui digo que qro utilizar a parte web do spring
@ComponentScan(basePackageClasses= {HomeController.class,ProdutoDAO.class,//aqui indico onde esta o pacote da classe controller e o pacote da classe DAO
		FileSaver.class})//coloco o pacote do filesaver tambem
public class AppWebConfiguration {

	@Bean//essa classe é gerenciada pelo spring
	public InternalResourceViewResolver internalResourceViewResolver() {//metodo que retorna a classe inetenal
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();//essa classe é o resolvedor interno de views
		resolver.setPrefix("/WEB-INF/Views/");//aqui indico onde esta a view
		resolver.setSuffix(".jsp");//digo a extensao da view
		
		return resolver;
	}
	
	@Bean //digo que o spring ira gerenciar !
	public MessageSource messageSource () {//aqui informo as configurações do message.properties
		ReloadableResourceBundleMessageSource messageSource //essa classe é que retorna a configuração de mensagem de erro
		= new ReloadableResourceBundleMessageSource(); 
		messageSource.setBasename("/WEB-INF/message");//onde esta o arquivo de mensagem de erro
		messageSource.setDefaultEncoding("UTF-8");//configuração de encoding
		messageSource.setCacheSeconds(1);//quanto segundos para recarregar o reloader, da um tempo pra redigir o message.prop
		
		return messageSource;
	}
	
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService //faz a parte de serviço de converção
		= new DefaultFormattingConversionService();
		DateFormatterRegistrar registrar 
		= new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy")); //passa o formato desejado
		registrar.registerFormatters(conversionService);
		
		return conversionService;
	}
	
	@Bean
	public MultipartResolver multipartResolver () {//resolvedor de arquivos de multiplos formatos
		return new StandardServletMultipartResolver();
	}
	
}
