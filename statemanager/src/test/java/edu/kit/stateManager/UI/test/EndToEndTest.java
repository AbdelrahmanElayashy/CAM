package edu.kit.stateManager.UI.test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import org.junit.Test;


public class EndToEndTest {

    @Test
    public void openPage() {
        open("http://localhost:4300//");


    }

}
