package org.aerialsounds.ccli.optionobservable;




public abstract class Observable {

    private Observer observer;

    final protected void registerObserver(Observer repository) {
        observer = repository;
    }

    final protected void notifyObserver(Object initiated) {
        if ( observer != null )
            observer.update(this, initiated);
    }

    final protected void deleteObserver() {
        observer = null;
    }

    @Override
    public boolean equals (Object o) {
        if ( this == o )
            return true;
        else if ( !(o instanceof Observable) )
            return false;

        return observer == ((Observable) o).observer;
    }

}
