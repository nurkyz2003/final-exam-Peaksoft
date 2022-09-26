package test.dao;

import test.model.Library;

public class Dao {
    private Library library;

    public Dao(Library library) {
        this.library = library;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
