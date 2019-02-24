/**
 * @author skht777
 */
module com.skht.markdown {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;
	requires juniversalchardet;
	requires jdk.jsobject;
	exports com.skht777.markdown to javafx.graphics;
	opens com.skht777.markdown to javafx.fxml;
}