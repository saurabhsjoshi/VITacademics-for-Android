package com.karthikb351.vitinfo2.dagger;

import com.karthikb351.vitinfo2.VITacademics;
import com.karthikb351.vitinfo2.activity.HomeActivity;
import com.karthikb351.vitinfo2.api.VITacademicsAPI;
import com.karthikb351.vitinfo2.dagger.modules.NetworkingProvider;
import com.karthikb351.vitinfo2.dagger.modules.SystemServicesModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by aashrai on 21/3/15.
 */
@Singleton
@Component(modules = {SystemServicesModule.class, NetworkingProvider.class})
public interface D2EComponent {


    void inject(VITacademics app);

    void inject(HomeActivity activity);

    void inject(VITacademicsAPI api);

    public class Initializer {

        public static D2EComponent init() {
            return Dagger_D2EComponent.builder()
                    .build();
        }

    }

}
