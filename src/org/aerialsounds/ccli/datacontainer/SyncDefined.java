


package org.aerialsounds.ccli.datacontainer;



import org.aerialsounds.ccli.datacontainer.DataContainer.DataContainerException;



final class SyncDefined
    implements SyncStrategies {


    @Override
    public void sync (final DataContainer first, final DataContainer second) throws DataContainerException {
        final boolean defined = (first.defined || second.defined);
        first.defined = defined;
        second.defined = defined;
    }

}
