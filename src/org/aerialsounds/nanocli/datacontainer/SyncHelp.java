package org.aerialsounds.nanocli.datacontainer;

import org.aerialsounds.nanocli.datacontainer.DataContainer.DataContainerException;
import org.aerialsounds.nanocli.datacontainer.DataContainer.OverrideHelp;



final class SyncHelp
    implements SyncStrategies {


    @Override
    public void sync (DataContainer first, DataContainer second) throws DataContainerException {
        if ( first.help == null )
            first.help = second.help;
        else if ( second.help == null )
            second.help = first.help;
        else if ( !first.help.equals(second.help) )
            throw new OverrideHelp();
    }

}
