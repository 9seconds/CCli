package org.aerialsounds.nanocli.datacontainer;

import org.aerialsounds.nanocli.datacontainer.DataContainer.DataContainerException;
import org.aerialsounds.nanocli.datacontainer.DataContainer.OverrideValue;



final class SyncValue
    implements SyncStrategies {


    @Override
    public void sync (DataContainer first, DataContainer second) throws DataContainerException {
        if ( first.value == null )
            first.value = second.value;
        else if ( second.value == null )
            second.value = first.value;
        else if ( !first.value.equals(second.value) )
            throw new OverrideValue();
    }

}
