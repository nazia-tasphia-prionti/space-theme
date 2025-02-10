

module com.example.space {
        requires javafx.controls;
       requires javafx.fxml;
  requires javafx.graphics;
  requires java.sql;

        opens com.example.space to javafx.fxml;
        exports com.example.space;
        }
