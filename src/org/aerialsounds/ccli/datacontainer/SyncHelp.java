


package org.aerialsounds.ccli.datacontainer;



import org.aerialsounds.ccli.datacontainer.DataContainer.DataContainerException;
import org.aerialsounds.ccli.datacontainer.DataContainer.OverrideHelp;



final class SyncHelp
    implements SyncStrategies {


    @Override
    public void sync (final DataContainer first, final DataContainer second) throws DataContainerException {
        if ( first.help == null )
            first.help = second.help;
        else if ( second.help == null )
            second.help = first.help;
        else if ( !first.help.equals(second.help) ) throw new OverrideHelp();
    }

}
