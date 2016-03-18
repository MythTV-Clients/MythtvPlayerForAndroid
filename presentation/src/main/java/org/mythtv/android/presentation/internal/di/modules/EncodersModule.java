package org.mythtv.android.presentation.internal.di.modules;

import org.mythtv.android.domain.interactor.GetEncoderList;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dmfrey on 1/19/16.
 */
@Module
public class EncodersModule {

    public EncodersModule() { }

    @Provides
    @PerActivity
    @Named( "encoderList" )
    UseCase provideEncoderListUseCase(GetEncoderList getEncoderList ) {

        return getEncoderList;
    }

}
