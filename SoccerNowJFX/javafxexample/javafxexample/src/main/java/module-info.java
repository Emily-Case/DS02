module pt.ul.fc.di.css.javafxexample {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.net.http;
    requires javafx.base;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires java.json;

    opens pt.ul.fc.di.css.javafxexample to javafx.fxml, javafx.web;
    opens pt.ul.fc.di.css.javafxexample.presentation.control to javafx.fxml;
    exports pt.ul.fc.di.css.javafxexample;
    /*exports pt.ul.fc.di.css.javafxexample.presentation.dto to com.fasterxml.jackson.databind;
    exports pt.ul.fc.di.css.javafxexample.presentation.enums to com.fasterxml.jackson.databind;
    exports pt.ul.fc.di.css.javafxexample.presentation.model to com.fasterxml.jackson.databind;*/
}
