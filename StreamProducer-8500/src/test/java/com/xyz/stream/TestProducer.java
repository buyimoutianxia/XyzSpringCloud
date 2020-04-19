package com.xyz.stream;

import com.xyz.stream.bingding.MyProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xyz
 * @date 2020-04-17-22:41
 * @decription todo
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestProducer {

    @Autowired
    private MyProducer myProducer;

    @Test
    public void test() {
        for (int i = 0; i < 5; i++) {

            myProducer.send(i);

        }
    }

}
