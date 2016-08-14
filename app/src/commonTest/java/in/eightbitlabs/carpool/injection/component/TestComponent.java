package in.eightbitlabs.carpool.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import in.eightbitlabs.carpool.injection.module.ApplicationTestModule;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
