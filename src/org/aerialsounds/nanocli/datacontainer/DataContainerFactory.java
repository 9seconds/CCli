package org.aerialsounds.nanocli.datacontainer;

import org.aerialsounds.nanocli.CliParser;



public final class DataContainerFactory {
    
    static public DataContainer create(CliParser repository) {
        return new DataContainer(repository);
    }

}
