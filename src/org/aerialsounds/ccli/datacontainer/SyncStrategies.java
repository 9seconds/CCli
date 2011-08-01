


package org.aerialsounds.ccli.datacontainer;



import org.aerialsounds.ccli.datacontainer.DataContainer.DataContainerException;



interface SyncStrategies {


    void sync (DataContainer first, DataContainer second) throws DataContainerException;

}
