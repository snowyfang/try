package com.package.name;

import org.junit.Assert;
import org.junit.Test;

public class ApiTest {
    @Test
    public void getVersion() {
        Assert.assertEquals(Api.getVersion().equals("1.0.0"),true);
    }
}