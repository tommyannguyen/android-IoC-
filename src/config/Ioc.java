package config;

import javax.inject.Singleton;
import com.datalayer.*;
import com.google.inject.AbstractModule;

import interfaces.*;
import services.*;
public class Ioc extends AbstractModule {
	 
	  @Override
	  protected void configure() {
	    bind(ITestService2.class).to(TestService2.class).in(Singleton.class);
	    bind(IDocumentService.class).to(DocumentService.class).in(Singleton.class);
	    bind(AppReaderDbHelper.class);
	    bind(CategoryRepository.class);
	    bind(DocumentRepository.class);
	  }
	}
