package org.aerialsounds.nanocli;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.aerialsounds.nanocli.datacontainer.DataContainer;
import org.aerialsounds.nanocli.options.AbstractOption;



public class CliParser implements Observer {

    final protected String[] args;
    final protected CliFactory factory;
    
    private boolean parsed = false;
    
    final protected Map<DataContainer,Set<AbstractOption>> containers;
    final protected Map<String,AbstractOption> options;
    
    public CliParser(String[] args) {
        this.args = args;
        factory = new CliFactory(this);
        containers = new HashMap<DataContainer,Set<AbstractOption>>();
        options = new HashMap<String,AbstractOption>();
    }
    
    public boolean isParsed() {
        return parsed;
    }
    
    public void parse() {
        if ( !isParsed() ) {
            parsed = true;
        }
    }
    
    protected boolean registerOption(AbstractOption option) {
        String name = option.getFullName();
        if ( options.containsKey(name) )
            return false;
        else
            options.put(name, option);
        return true;
    }
    
    protected void registerContainer(DataContainer container) {
        containers.put(container, new HashSet<AbstractOption>());
    }
    
    protected void associate(DataContainer container, AbstractOption option) {
        Set<AbstractOption> set = containers.get(container);
        if ( set != null )
            set.add(option);
    }
    
    @Override
    public void update (Observable o, Object arg) {
        if ( o instanceof AbstractOption && arg instanceof AbstractOption ) {
            DataContainer firstContainer = ((AbstractOption) o).getContainer();
            DataContainer secondContainer = ((AbstractOption) arg).getContainer();

            Set<AbstractOption> firstSet = containers.get(firstContainer);
            Set<AbstractOption> secondSet = containers.get(secondContainer);

            if ( firstSet != null && secondSet != null ) {
                firstSet.addAll(secondSet);
                for ( AbstractOption opt : secondSet )
                    opt.setContainer(firstContainer);
                containers.remove(secondContainer);
            }
        }
    }
    
    static public class HaveSuchOption extends RuntimeException {
        private static final long serialVersionUID = -5248825722401579070L;
    }
    
}
