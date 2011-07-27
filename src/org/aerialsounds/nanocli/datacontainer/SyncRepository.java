package org.aerialsounds.nanocli.datacontainer;

import org.aerialsounds.nanocli.datacontainer.DataContainer.DataContainerException;
import org.aerialsounds.nanocli.datacontainer.DataContainer.OverrideRepository;



final class SyncRepository
    implements SyncStrategies {


    @Override
    public void sync (DataContainer first, DataContainer second) throws DataContainerException {
        if ( first.repository == null )
            first.repository = second.repository;
        else if ( second.repository == null )
            second.repository = first.repository;
        else if ( !first.repository.equals(second.repository) )
            throw new OverrideRepository();
    }

}
