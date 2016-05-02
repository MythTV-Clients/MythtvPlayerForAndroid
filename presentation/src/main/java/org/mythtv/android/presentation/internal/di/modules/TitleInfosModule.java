package org.mythtv.android.presentation.internal.di.modules;

import org.mythtv.android.domain.interactor.GetTitleInfoList;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides programs related collaborators.
 *
 * Created by dmfrey on 9/9/15.
 */
@Module
public class TitleInfosModule {

    public TitleInfosModule() {}

    @Provides
    @PerActivity
    @Named( "titleInfoList" )
    UseCase provideTitleInfoListUseCase( GetTitleInfoList getTitleInfoList ) {

        return getTitleInfoList;
    }

}
