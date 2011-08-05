package org.aerialsounds.ccli.optionobservable;




public abstract class Observable {

    private Observer observer;

    final protected void registerObserver(final Observer repository) {
        observer = repository;
    }

    final protected void notifyObserver(final Object initiated) {
        if ( observer != null )
            observer.update(this, initiated);
    }

    final protected void deleteObserver() {
        observer = null;
    }

    @Override
    public boolean equals (final Object o) {
        if ( this == o )
            return true;
        else if ( !(o instanceof Observable) )
            return false;

        return observer == ((Observable) o).observer;
    }

}
