package org.aerialsounds.nanocli.datacontainer;

import org.aerialsounds.nanocli.datacontainer.DataContainer.DataContainerException;
import org.aerialsounds.nanocli.datacontainer.DataContainer.OverrideDefaultValue;



final class SyncDefaultValue
    implements SyncStrategies {


    @Override
    public void sync (DataContainer first, DataContainer second) throws DataContainerException {
        if ( first.defaultValue == null )
            first.defaultValue = second.defaultValue;
        else if ( second.defaultValue == null )
            second.defaultValue = first.defaultValue;
        else if ( !first.defaultValue.equals(second.defaultValue) )
            throw new OverrideDefaultValue();
    }

}
