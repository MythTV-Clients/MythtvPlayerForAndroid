package org.mythtv.android.data.entity.mapper;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.mythtv.android.data.entity.CommercialBreakEntity;
import org.mythtv.android.data.entity.CutListWrapperEntity;
import org.mythtv.android.data.entity.CuttingEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dmfrey on 10/31/16.
 */

public class CutListEntityJsonMapper {
    private static final String TAG = EncoderEntityJsonMapper.class.getSimpleName();

    private final Gson gson;

    @Inject
    public CutListEntityJsonMapper( Gson gson ) {

        this.gson = gson;

    }

//    public EncoderEntity transformCutListEntity( String cutListJsonResponse ) throws JsonSyntaxException {
//
//        Log.i( TAG, "transformCutListEntity : cutListJsonResponse=" + cutListJsonResponse );
//        Type cutListWrapperEntityType = new TypeToken<EncoderWrapperEntity>() {}.getType();
//        EncoderWrapperEntity encoderWrapperEntity = this.gson.fromJson( cutListJsonResponse, cutListWrapperEntityType );
//
//        return encoderWrapperEntity.getEncoder();
//    }

    public List<CommercialBreakEntity> transformCutListEntityCollection(String cutListJsonResponse ) throws JsonSyntaxException {

        Log.i( TAG, "transformCutListEntityCollection : cutListJsonResponse=" + cutListJsonResponse );
        Type cutListWrapperEntityType = new TypeToken<CutListWrapperEntity>() {}.getType();
        CutListWrapperEntity cutListWrapperEntity = this.gson.fromJson( cutListJsonResponse, cutListWrapperEntityType );
        Log.i( TAG, "transformCutListEntityCollection : cutListWrapperEntity=" + cutListWrapperEntity.toString() );

        List<Long> start = new ArrayList<>();
        List<Long> end = new ArrayList<>();

        for( CuttingEntity cuttingEntity : cutListWrapperEntity.getCutListEntity().getCuttingEntities() ) {

            switch( cuttingEntity.getMark() ) {

                case 4 :

                    start.add( cuttingEntity.getOffset() );

                    break;

                case 5 :

                    end.add( cuttingEntity.getOffset() );

                    break;

            }

        }

        if( start.size() != end.size() ) {

            return new ArrayList<>();
        }

        List<CommercialBreakEntity> breaks = new ArrayList<>();
        for( int i = 0; i < start.size(); i++ ) {

            breaks.add( new CommercialBreakEntity( start.get( i ), end.get( i ) ) );

        }

        return breaks;
    }

}
