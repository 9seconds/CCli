package org.aerialsounds.nanocli.datacontainer;

import org.aerialsounds.nanocli.datacontainer.DataContainer.DataContainerException;
import org.aerialsounds.nanocli.datacontainer.DataContainer.OverrideValueType;



final class SyncValueType
    implements SyncStrategies {


    @Override
    public void sync (DataContainer first, DataContainer second) throws DataContainerException {
        if ( first.valueType == null )
            first.valueType = second.valueType;
        else if ( second.valueType == null )
            second.valueType = first.valueType;
        else if ( !first.valueType.equals(second.valueType) )
            throw new OverrideValueType();
    }

}