package in.eightbitlabs.gocollege.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import in.eightbitlabs.gocollege.injection.module.ApplicationTestModule;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
