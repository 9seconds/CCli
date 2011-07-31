package org.aerialsounds.ccli.datacontainer;

import org.aerialsounds.ccli.CliParser;



public final class DataContainerFactory {
    
    static public DataContainer create(CliParser repository) {
        return new DataContainer(repository);
    }

}
