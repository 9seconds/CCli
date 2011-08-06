


package org.aerialsounds.ccli.datacontainer;



import org.aerialsounds.ccli.datacontainer.DataContainer.DataContainerException;
import org.aerialsounds.ccli.datacontainer.DataContainer.OverrideRepository;



final class SyncRepository
    implements SyncStrategies {


    @Override
    public void sync (final DataContainer first, final DataContainer second) throws DataContainerException {
        if ( first.repository != second.repository )
            throw new OverrideRepository();
    }

}
