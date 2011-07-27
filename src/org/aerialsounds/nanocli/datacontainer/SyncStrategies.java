package org.aerialsounds.nanocli.datacontainer;

import org.aerialsounds.nanocli.datacontainer.DataContainer.DataContainerException;



interface SyncStrategies {
    
    void sync(DataContainer first, DataContainer second) throws DataContainerException;

}
