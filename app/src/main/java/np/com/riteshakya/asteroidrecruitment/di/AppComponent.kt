package np.com.riteshakya.asteroidrecruitment.di


import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import np.com.riteshakya.asteroidrecruitment.RecruitmentApp
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AppModule::class,
            AndroidSupportInjectionModule::class,
            ActivityBuilderModule::class
        ]
)
interface AppComponent : AndroidInjector<RecruitmentApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<RecruitmentApp>()
}