package com.fiuni.adoptamena;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("AdoptameNa Test Suite")
@SelectPackages({
                "com.fiuni.adoptamena.auth",
                "com.fiuni.adoptamena.user"
})
public class AdoptameNaApplicationTests {
}
