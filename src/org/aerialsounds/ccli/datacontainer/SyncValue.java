


package org.aerialsounds.ccli.datacontainer;



import org.aerialsounds.ccli.datacontainer.DataContainer.DataContainerException;
import org.aerialsounds.ccli.datacontainer.DataContainer.OverrideValue;



final class SyncValue
    implements SyncStrategies {


    @Override
    public void sync (final DataContainer first, final DataContainer second) throws DataContainerException {
        if ( first.value == null )
            first.value = second.value;
        else if ( second.value == null )
            second.value = first.value;
        else if ( !first.value.equals(second.value) ) throw new OverrideValue();
    }

}
