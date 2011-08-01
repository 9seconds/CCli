


package org.aerialsounds.ccli.datacontainer;



import org.aerialsounds.ccli.datacontainer.DataContainer.DataContainerException;
import org.aerialsounds.ccli.datacontainer.DataContainer.OverrideValueType;



final class SyncValueType
    implements SyncStrategies {


    @Override
    public void sync (final DataContainer first, final DataContainer second) throws DataContainerException {
        if ( first.valueType == null )
            first.valueType = second.valueType;
        else if ( second.valueType == null )
            second.valueType = first.valueType;
        else if ( !first.valueType.equals(second.valueType) ) throw new OverrideValueType();
    }

}
