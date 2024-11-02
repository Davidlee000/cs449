module cs449 {
  requires javafx.controls;
  requires javafx.fxml;
  requires transitive javafx.graphics;
  opens org.example to javafx.fxml;
  exports org.example;
}